/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.osgi.security.template.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.security.test.api.OSGiSecurityTestRunner;

/**
 * This is a template project, which role is to show the important points to follow in order to create
 * a new EnRoute test project.
 * 
 * - Put the Apache license at the beginning of each java file of the project
 * 
 * - Add @RunWith(OSGiSecurityTestRunner.class) annotation at the class declaration level 
 * with associated imports (org.junit.runner.RunWith and org.osgi.security.test.api.OSGiSecurityTestRunner).
 * Then, add org.osgi.security.test.api to Bnd buildpath (in Build tab of the bnd file associated to
 * the project).
 * 		- 
 * - Complete Name and Description fields in Description tab of the bnd file associated to the project.
 * 
 * - Add a more precise description of the test in this section (ie other tests) :
 * 
 * @author Sogeti High Tech
 * 
 * Test of BundleContext initialization
 * 
 * This bundle tests if the BundleContext variable is not null.
 * 
 */

@RunWith(OSGiSecurityTestRunner.class)
public class TemplateTest {
 
	private final BundleContext context = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
    
    @Test
    public void testTemplate() throws Exception {
    	
    	/**
    	 * Function with @Test annotation is the one executed when Run as Bnd OSGi Test Launcher (JUnit) is 
    	 * pressed, so asserts must be done in this function.
    	 */
    	
    	Assert.assertNotNull(context);
    }
}
