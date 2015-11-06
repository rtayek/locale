import junit.framework.*;
//import l.*;
//import l2.*;
import java.util.*;
import java.net.*;
import java.io.*;
public class MainTestCase extends TestCase {
	public MainTestCase(java.lang.String testName) {
		super(testName);
	}
	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTestSuite(l.LTestCase.class); // not sure which of these is best
		suite.addTest(l2.LTestCase.suite()); // not sure which of these is best
		suite.addTestSuite(MainTestCase.class);
		return suite;
	}
	public void testGetFileResource() throws Exception {
		final Class c=this.getClass();
		final ClassLoader cl=c.getClassLoader();
		URL url=null;
		final String resource="l.txt";
		for(Enumeration e= cl.getSystemResources(resource);e.hasMoreElements();) {
			System.out.println(e.nextElement());
		}
	}
	public static void main(String[] argument) {
		junit.textui.TestRunner.run(suite());
	}
}
