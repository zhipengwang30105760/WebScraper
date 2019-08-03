// COURSE: CSCI1620
// TERM: Spring 2019
// 
// NAME: Zhipeng Wang
// RESOURCES: I don't use any resources
package scraper.test;

import static org.junit.Assert.*;

import org.junit.Test;

import scraper.base.WebScraper;
import scraper.utils.PageHistory;
import scraper.utils.ResultSet;
/**
 * The test that make sure the WebScraper method works correctly.
 * @author zhipengwang@unomaha.edu
 *
 */
public class WebScraperTest {

	/**
	 * The test for default constructor.
	 */
	@Test
	public void testDefaultConstructor() {
		WebScraper w = new WebScraper("aa");
		assertEquals("aa", w.getURL());
		assertEquals(0, w.getDepth());
	}
	/**
	 * The test for specific constructor.
	 */
	@Test
	public void testSpecificConstructor() {
		WebScraper w = new WebScraper("aa", 3);
		assertEquals("aa", w.getURL());
		assertEquals(3, w.getDepth());
	}
	/**
	 * The test for URL with invalid input.
	 */
	@Test
	public void testSetURLInvalidInput()
	{
		WebScraper w = new WebScraper("aa");
		assertEquals("aa", w.getURL());
		w.setURL("");
		assertEquals("aa", w.getURL());
		w.setURL(null);
		assertEquals("aa", w.getURL());
	}
	/**
	 * The test for setDepth method with invalid number.
	 */
	@Test
	public void testSetDepthInvalidInput()
	{
		WebScraper w = new WebScraper("aa", 3);
		assertEquals("aa", w.getURL());
		assertEquals(3, w.getDepth());
		w.setDepth(-1);
		assertEquals(0, w.getDepth());
	}
	/**
	 * The test for get empty history from page.
	 */
	@Test
	public void testGetPageHistoryWithEmpty()
	{
		WebScraper w = new WebScraper("aa", 3);
		assertNotEquals(null, w.getPageHistory());
	}
	/**
	 * The test for get imagine method.
	 */
	@Test
	public void testGetImagine()
	{
		WebScraper w = new WebScraper("http://loki.ist.unomaha.edu/~bdorn/csci1620/hw3testpage/page1.html");
		assertEquals("http://loki.ist.unomaha.edu/~bdorn/csci1620/hw3testpage/page1.html", w.getURL());
		assertEquals(0, w.getDepth());
		
		ResultSet rs = w.getImages();
		
		assertEquals(2, rs.getNumEntries());
		assertEquals("http://loki.ist.unomaha.edu/~bdorn/csci1620/hw3testpage/imgs/brodieBolt.jpg", 
				rs.getAllResults()[0].toString());
		assertEquals("http://loki.ist.unomaha.edu/~bdorn/csci1620/hw3testpage/imgs/logo_uno.png",
				rs.getAllResults()[1].toString());
	}
	/**
	 * The test for get imagine method with duplicate imagine.
	 */
	@Test
	public void testGetImagineWithDuplication()
	{
		WebScraper w = new WebScraper("http://loki.ist.unomaha.edu/~bdorn/csci1620/hw3testpage/page2.html");
		assertEquals("http://loki.ist.unomaha.edu/~bdorn/csci1620/hw3testpage/page2.html", w.getURL());
		assertEquals(0, w.getDepth());
		
		ResultSet rs = w.getImages();
		
		assertEquals(7, rs.getNumEntries());
		
		assertEquals("http://loki.ist.unomaha.edu/~bdorn/csci1620/hw3testpage/imgs/Palm_Tree_Emoji_42x42.png", 
				rs.getAllResults()[0].toString());
		assertEquals("http://loki.ist.unomaha.edu/~bdorn/csci1620/hw3testpage/imgs/Slice_Of_Pizza_Emoji_42x42.png",
				rs.getAllResults()[1].toString());
		assertEquals("http://loki.ist.unomaha.edu/~bdorn/csci1620/hw3testpage/imgs/Waning_crescent_moon_emoji_icon_png_42x42.png",
				rs.getAllResults()[5].toString());
		assertEquals("http://loki.ist.unomaha.edu/~bdorn/csci1620/hw3testpage/imgs/logo_uno.png",
				rs.getAllResults()[6].toString());
	}
	
	/**
	 * The test for recursive method with depth 0.
	 */
	@Test
	public void testCrawlPageWith0Depth()
	{
		WebScraper w = new WebScraper("http://loki.ist.unomaha.edu/~bdorn/csci1620/hw3testpage/");
		//w.setDepth(1);
		assertEquals(0, w.getDepth());
		ResultSet rs = w.crawlPage();
		assertEquals(0, rs.getNumEntries());
		PageHistory p = w.getPageHistory();
		assertEquals("http://loki.ist.unomaha.edu/~bdorn/csci1620/hw3testpage/", p.getHistory()[0]);
	}
	/**
	 * The test for recursive method with depth 1.
	 */
	@Test
	public void testCrawlPageWith1Depth()
	{
		WebScraper w = new WebScraper("http://loki.ist.unomaha.edu/~bdorn/csci1620/hw3testpage/");
		w.setDepth(1);
		assertEquals(1, w.getDepth());
		assertEquals("http://loki.ist.unomaha.edu/~bdorn/csci1620/hw3testpage/", w.getURL());
		
		ResultSet rs = w.crawlPage();
		assertEquals(14, rs.getNumEntries());
		assertEquals("http://loki.ist.unomaha.edu/~bdorn/csci1620/hw3testpage/imgs/puppy6.jpg", 
				rs.getAllResults()[13].toString());
		PageHistory p = w.getPageHistory();
		assertEquals("http://loki.ist.unomaha.edu/~bdorn/csci1620/hw3testpage/", p.getHistory()[0]);
	}
	
	/**
	 * The test for recursive method with duplicate page.
	 */
	@Test
	public void testCrawlPageWithDuplicate()
	{
		WebScraper w = new WebScraper("http://loki.ist.unomaha.edu/~bdorn/csci1620/hw3testpage/page1.html");
		w.setDepth(4);
		assertEquals(4, w.getDepth());
		assertEquals("http://loki.ist.unomaha.edu/~bdorn/csci1620/hw3testpage/page1.html", w.getURL());
		ResultSet rs = w.crawlPage();
		assertEquals("http://loki.ist.unomaha.edu/~bdorn/csci1620/hw3testpage/page1.html", w.getPageHistory().getHistory()[0]);
		assertEquals("http://loki.ist.unomaha.edu/~bdorn/csci1620/hw3testpage/page2.html", w.getPageHistory().getHistory()[1]);
		assertEquals("http://loki.ist.unomaha.edu/~bdorn/csci1620/hw3testpage/page3.html", w.getPageHistory().getHistory()[2]);
		assertEquals("http://loki.ist.unomaha.edu/~bdorn/csci1620/hw3testpage/secretpage.html", w.getPageHistory().getHistory()[3]);
		assertFalse(w.getPageHistory().wereDuplicatesVisited());
	}
	
	/**
	 * The test for get page history by using page 3 from example web.
	 */
	@Test
	public void testGetPageHistory()
	{
		WebScraper w = new WebScraper("http://loki.ist.unomaha.edu/~bdorn/csci1620/hw3testpage/page3.html");
		w.crawlPage();
		assertEquals("http://loki.ist.unomaha.edu/~bdorn/csci1620/hw3testpage/page3.html", w.getURL());
		assertEquals(0, w.getDepth());
		assertEquals("http://loki.ist.unomaha.edu/~bdorn/csci1620/hw3testpage/page3.html", w.getPageHistory().getHistory()[0]);
		assertFalse(w.getPageHistory().wereDuplicatesVisited());
	}
	/**
	 * The test for invalid URL for crawling.
	 */
	@Test
	public void testInvalidURLForCrawling()
	{
		WebScraper w = new WebScraper("http://loki.ist.unomaha.edu/~bdorn/csci1620/hw3testpage/brokenlinkhereyay.html");
		w.setDepth(3);
		ResultSet rs = w.crawlPage();
		assertNull(rs);
	}
}
