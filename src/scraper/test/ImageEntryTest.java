// COURSE: CSCI1620
// TERM: Spring 2019
// 
// NAME: Zhipeng Wang
// RESOURCES: I don't use any resources
package scraper.test;

import static org.junit.Assert.*;

import org.junit.Test;

import scraper.base.ImageEntry;
/**
 * The test that used make sure the ImageEntry class works correctly.
 * @author zhipengwang@unomaha.edu
 *
 */
public class ImageEntryTest {

	/**
	 * The test for constructor.
	 */
	@Test
	public void testConstructor() {
		ImageEntry i = new ImageEntry("aa","bb");
		assertEquals("aa", i.getPageLocation());
		assertEquals("bb", i.getImgLocation());
	}
	/**
	 * The test for toString method.
	 */
	@Test
	public void testToString() {
		ImageEntry i = new ImageEntry("aa","bb");
		assertEquals("aa", i.getPageLocation());
		assertEquals("bb", i.getImgLocation());
		String str = "bb";
		assertEquals(str, i.toString());
	}
	/**
	 * The test for equals method.
	 */
	@Test
	public void testEquals() {
		ImageEntry i = new ImageEntry("aa","bb");
		assertEquals("aa", i.getPageLocation());
		assertEquals("bb", i.getImgLocation());
		ImageEntry b = new ImageEntry("aa","bb");
		assertTrue(i.equals(b));
	}
	/**
	 * The test for equals method with invalid input.
	 */
	@Test
	public void testEqualsWithInvalidInput() {
		ImageEntry i = new ImageEntry("aa","bb");
		assertEquals("aa", i.getPageLocation());
		assertEquals("bb", i.getImgLocation());
		Object o = null;
		assertFalse(i.equals(o));
	}
	/**
	 * The test for equals method with different object.
	 */
	@Test
	public void testEqualsWithDifferentObject() {
		ImageEntry i = new ImageEntry("aa","bb");
		assertEquals("aa", i.getPageLocation());
		assertEquals("bb", i.getImgLocation());
		ImageEntry o = new ImageEntry("cc","dd");
		assertFalse(i.equals(o));
	}
}
