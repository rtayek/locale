package l;
import junit.framework.*;
import java.util.*;
import java.net.*;
import java.io.*;
public class LTestCase extends TestCase {
	public LTestCase(java.lang.String testName) {
		super(testName);
	}
	public static Test suite() {
		TestSuite suite = new TestSuite(LTestCase.class);
		return suite;
	}
	public void testGetPropertiesFileAsResurceBundle() {
		final ResourceBundle rb=ResourceBundle.getBundle(c.getName());
		assertNotNull(""+c,rb);
	}
	public void testGetAPropertyValue() {
		final ResourceBundle rb=ResourceBundle.getBundle(c.getName());
		assertEquals(expectedValue,rb.getObject(key));
	}
	public void testGetPathfromGetResource() {
		final String resource="files";
		final URL url=c.getResource(resource);
		assertNotNull("check for existence of: "+resource,url);
		final String path=url.getPath();
		assertNotNull("path: ",path);
		final File file=new File(path);
		assertNotNull("file: ",file);
		//assertTrue("file.exists() using: "+file,file.exists());
	}
	public void testOpenStreamfromGetResource() throws Exception {
		final String resource="files/file1.txt";
		final URL url=c.getResource(resource);
		assertNotNull("check for existence of: "+resource,url);
		final InputStream is=url.openStream();
		assertNotNull("check for ability to open this as an input stream: ",is);
		final BufferedReader r=new BufferedReader(new InputStreamReader(is));
		assertNotNull("check for ability to open this as a reader: ",r);
		final String line=r.readLine();
		assertEquals("check to see if we got the corect value: ",value,line);
		is.close();
	}
	public void testGetTestPropertiesFileAsResurceBundle() {
		final ResourceBundle rb=ResourceBundle.getBundle(this.getClass().getName());
		assertNotNull("check to see if we loaded the resource bundld",rb);
	}
	public void testGetATestPropertyValue() {
		final ResourceBundle rb=ResourceBundle.getBundle(this.getClass().getName());
		assertEquals(expectedValue+"Test",rb.getObject(key));
	}
	public void testGetTestPathAsResource() {
		final String resource="testFiles"; // no /'s allowed!
		final URL url=testC.getResource(resource);
		assertNotNull(url.toString(),url);
		assertEquals(""+url,resource,(new File(url.getPath())).getName());
	}
	public void testGetTestFileResourceInFolder() throws Exception {
		final String resource="testFiles/file1.txt";
		final URL url=testC.getResource(resource);
		assertNotNull("check for existence of: "+resource,url);
		final InputStream is=url.openStream();
		assertNotNull("check for ability to open this as an input stream: ",is);
		final BufferedReader r=new BufferedReader(new InputStreamReader(is));
		assertNotNull("check for ability to open this as a reader: ",r);
		final String line=r.readLine();
		assertEquals(url.toString(),value+"Test",line);
		is.close();
	}
	public void testWriteToTempFile() throws Exception{
		final String resource="testFiles";
		final URL url=testC.getResource(resource);
		final File path=new File(url.getPath()); // hack - may not always work!
		assertNotNull("path from resource",path);
		assertTrue("check that path exists",path.exists());
		assertTrue("check that path is a directory",path.isDirectory());
		assertTrue("check that path is readable",path.canRead());
		assertTrue("check that path is writable",path.canWrite());
		final File file=File.createTempFile(this.getClass().getName().substring(0,3),".ext",path);
		assertNotNull("temp file",file);
		final Writer w=new FileWriter(file);
		w.write(key+"\n");
		w.close();
		final BufferedReader r=new BufferedReader(new FileReader(file));
		final String line=r.readLine();
		r.close();
		assertEquals("check what we wrote",key,line);
		file.deleteOnExit();
	}
	public static void main(String[] argument) {
		junit.textui.TestRunner.run(suite());
	}
	private final Class c=L.class,testC=this.getClass();
	private final String key="foo",expectedValue="bar",value="l";
}