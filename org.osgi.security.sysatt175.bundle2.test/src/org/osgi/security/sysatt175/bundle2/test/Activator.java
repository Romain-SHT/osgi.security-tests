package org.osgi.security.sysatt175.bundle2.test;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import org.osgi.security.sysatt175.common.api.HelloWorldService;
import org.osgi.security.util.api.Util;

public class Activator implements BundleActivator
{
	private static BundleContext bundleContext;
	private static ServiceReference<?> service;
	private Util util;
	private ServiceRegistration<?> registration;
	private Hashtable<String,String> dict = new Hashtable<String,String>();
	private int key = 0;
	private String filter;
	private String filter1;
	private ServiceListener listener;
	private ServiceListener serviceListener;

	
	private Dictionary<String, String> getDictionary()
	{ 
		if (dict.isEmpty())
		{
			dict.put("servicegroup", "org.osgi.security.sysatt175");
			dict.put("servicenumber", "bundle2.test");
			dict.put("key", Integer.toString(key));
			key++;
		}  
		return (Dictionary<String, String>)dict;
	}


	static BundleContext getContext()
	{
		return bundleContext;
	}

	
	static ServiceReference<?> getService()
    {
		return service;
    }
	

	public void start(BundleContext context) throws Exception
	{
		Activator.bundleContext = context;
		service = getContext().getServiceReference(Util.class.getName());
		
		if (service != null)
		{			
			serviceProcessing();
		}
		else
		{
			listener = new ServiceListener()
			{
				public void serviceChanged(ServiceEvent e)
				{
					service = e.getServiceReference();				
					switch (e.getType())
					{
						case ServiceEvent.REGISTERED:
							serviceProcessing();
							break;
							
						default:
							// Nothing
							break;
					}
				}
			};
			
			filter = "(" + Constants.OBJECTCLASS + "="
					+ Util.class.getName() + ")";
			getContext().addServiceListener(listener, filter);
		}
	}
	
	
	private void serviceProcessing()
	{
		util = (Util) getContext().getService(getService());
		try
		{
			filter1 = "(&(servicenumber=bundle1.test)(servicegroup=org.osgi.security.sysatt175))";
			registration = getContext().registerService(HelloWorldService.class.getName(), new HelloWorldServiceImpl(), getDictionary());
			serviceListener = new ServiceListener()
			{
				public void serviceChanged(ServiceEvent e)
				{
					switch (e.getType())
					{
						case ServiceEvent.REGISTERED:
							new Thread() {
				                  public void run() {
				                	  	try
										{
											Thread.sleep(3000);
										}
										catch (Exception e1)
										{
											e1.printStackTrace();
										}
										getContext().getServiceReference(HelloWorldService.class.getName());
										dict.clear();
										getDictionary();
										util.println("----------------------------------------------------------------------");
										util.println("Bundle2: new key generated.");
										util.println("----------------------------------------------------------------------");
										registration.setProperties(dict);
				                  }
							}.start();
							break;
	      
						case ServiceEvent.MODIFIED:
							dict.clear();
			                getDictionary();
			                util.println("Bundle2: Service of " + e.getServiceReference().getBundle().getSymbolicName() + " changed (New key = " + dict.get("key") + ")");
			                util.println("----------------------------------------------------------------------");
			                if (key % 20 == 0)
			                {
			                	new Thread() {
			                		public void run() {
			                			registration.setProperties(dict);
			                		}
			                	}.start();
			                }
			                else
			                {
			                	registration.setProperties(dict);		          
			                }
			                
							break;
							
						case ServiceEvent.UNREGISTERING:
							getContext().ungetService(e.getServiceReference());
							break;
	     
						case ServiceEvent.MODIFIED_ENDMATCH:
							getContext().ungetService(e.getServiceReference());
							break;
						
						default:
							// Nothing
							break;
					}
				}
			};
	
			try
			{
				getContext().addServiceListener(serviceListener, filter1);
			}
			catch (InvalidSyntaxException e1)
			{
				e1.printStackTrace();
			}
		} 
		catch (Exception e)
		{
			util.err(e);
		}
	} 
	

	public void stop(BundleContext context) throws Exception
	{
		Activator.bundleContext = null;
	}
}
