// COURSE: CSCI1620
// TERM: Spring 2019
// 
// NAME: Zhipeng Wang
// RESOURCES: I got help about the logic of crawlPage helper method,
// debugging help related to check if the URL is valid or not by Zexi in CSLC.
package scraper.base;

import scraper.utils.Document;
import scraper.utils.Element;
import scraper.utils.Elements;
import scraper.utils.PageHistory;
import scraper.utils.ResultSet;

/**
 * This class provides a simple mechanism to crawl a series of webpages recursively and 
 * extract all of the images that are found on the pages visited.
 * @author zhipengwang@unomaha.edu
 */
public class WebScraper 
{
	/**
	 * The URL of the web.
	 */
	private String urlInput;
	/**
	 * The depth for extracting images.
	 */
	private int depth;
	/**
	 * The page history that store all the visited link.
	 */
	private PageHistory h = new PageHistory();
	/**
	 * Builds a new WebScraper that should start at the provided URL and will by 
	 * default explore that page at a depth of 0. 
	 * This allows extracting just the details from this page and nothing else.
	 * @param urlIn The URL to explore for images.
	 */
	public WebScraper(String urlIn)
	{
		setURL(urlIn);
		this.depth = 0;
		
		//page.loadPageFromURL(this.setURL(urlIn));
	}
	/**
	 * Builds a new WebScraper that should start at the provided URL 
	 * and will explore recursively to a specified depth.
	 * @param urlIn The URL to begin exploring for images.
	 * @param depthIn The recursive depth to explore, must be >= 0. 
	 * Negative values will be treated as equivalent to 0.
	 */
	public WebScraper(String urlIn, int depthIn)
	{
		setURL(urlIn);
		setDepth(depthIn);
	}
	/**
	 * Updates this WebScraper to explore to a new depth.
	 * @param depthIn The recursive depth to explore, must be >= 0. 
	 * Negative values will be treated as equivalent to 0.
	 */
	public void setDepth(int depthIn) 
	{
		if (depthIn >= 0)
		{
			this.depth = depthIn;
		}
		else
		{
			this.depth = 0;
		}
	}
	/**
	 * Retrieves the exploration depth of this WebScraper.
	 * @return The depth stored in this WebScraper.
	 */
	public int getDepth()
	{
		return depth;
	}
	/**
	 * Updates the base URL to explore for this WebScraper.
	 * @param url The new URL to explore. The WebScraper url is only changed 
	 * if the url value is valid (not null or empty).
	 */
	public void setURL(String url)
	{
		if (url != null && !url.equals(""))
		{
			this.urlInput = url;
		}
	}
	/**
	 * Retrieves the base url for exploration by this WebScraper.
	 * @return The base url.
	 */
	public String getURL()
	{
		return this.urlInput;
	}
	/**
	 * Retrieves the set of images found directly at the initial base URL for this WebScraper. 
	 * This method will not explore any links found at the base page. 
	 * Image information should be stored in the order in which their corresponding 
	 * <img> tags appear in the source HTML code. 
	 * If an image appears more than once in a page, 
	 * only one entry should appear in the ResultSet.
	 * @return A collection of ImageEntry objects for the images found at the base url location.
	 */
	public ResultSet getImages()
	{
		//First create a result set rs.
		ResultSet rs = new ResultSet();
		//Then create an image entry that create a single image what we read
		//from URL.
		ImageEntry ie;
		//Create a page. 
		Document page = new Document();
		//Load the url to page.
		page.loadPageFromURL(getURL());
		//Get how many images on that page.
		//int numIMGs = page.getElementsByTag("img").size(); 
		Elements e = page.getElementsByTag("img");
		//For loop go through the url to get all the images
		while (e.hasNextElement())
		{
			String readImageURL = e.getNextElement().getAttributeValue("src");
			ie = new ImageEntry(getURL(), readImageURL);
			//check is that image already contains in result sets or not. 
			if (!rs.contains(readImageURL))
			{
				rs.addResult(ie);
			}
		}
		return rs;
	}
	/**
	 * This method will recursively explore pages starting at the base url defined for 
	 * this WebScraper to the depth for which the scraper is configured.
	 * The ResultSet will contain all images discovered along the way, 
	 * with images from a page being explored stored in 
	 * the ResultSet prior to any images found on linked pages. 
	 * ImageEntries will always appear in the ResultSetin the order in which 
	 * the corresponding <img> tags are given in the HTML files. 
	 * Only the first occurrence of a particular image (by absoluteURL) is stored in the ResultSet. 
	 * Links on a page will be explored in the sequential order in which 
	 * their corresponding <\a> tags appear in the HTML file. 
	 * The same URLshould never be crawled more than once, even if it is linked from itsel for another later page.
	 * @return The set of ImageEntry objects resulting from this recursive crawl.
	 */
	
	public ResultSet crawlPage()
	{
		//helper that start from 0 to the depth.
		//I used to pass Document object as parameter in the helper method, but
		//I got tips from Zexi that pass URL directly is more easier. 
		//for setURL method and check whether we can load the URL or not statement, I
		//used to put them at the bottom. Then I think I should put them at the front
		//so that we can make sure the initial URL is valid. 
		return crawlPageHelper(getURL(), 0, getDepth());
		//WebScraper w = new WebScraper(getURL());
		//return crawlPageHelper(w, 0, getDepth());
	}
	/**
	 * A recursive helper that used to crawl pages and get more information out.
	 * @param urlIn The URL that we want to track.
	 * @param depthIn the rest depth what we need to reached.
	 * @param index The current index tracing.
	 * @return a set of images that we get from crawling.
	 */
	
	private ResultSet crawlPageHelper(String urlIn, int index, int depthIn)
	{
		//update the url for get images method. I got debugging help from Zexi that if I don't update in this
		//class, when I call getImage() method, I still load the image from old page.
		setURL(urlIn);
		ResultSet rs = null;
		Document p = new Document();
		//p.loadPageFromURL(getURL());
		//This is the debugging place that I got helped by Zexi that check if the url we load is valid or not
		//because I got some "web address is invalid" messages when I run GUI.
		boolean avaliableURL = p.loadPageFromURL(getURL());
		//Not matter the URL is valid or not, we must mark it so that we can track the history.
		h.markVisited(getURL());
		if (avaliableURL && !h.wereDuplicatesVisited())
		{
			//get all the images in the current page.
			//rs = getImages();
			//System.out.println(getURL());
			//h.markVisited(urlIn);
			//check is that duplicate visited.
			//if (!h.wereDuplicatesVisited())
			//{
			
			rs = getImages();
			//base case 
			if (index >= depth)
			{
				rs = rs.merge(null);
			}
			//general case
			else
			{
				//got all the link from page
				Elements e = p.getElementsByTag("a");
				while (e.hasNextElement())
				{
					Element n = e.getNextElement();
					//got the new url.
					String newURL = n.getAttributeValue("href");
					if (!h.visited(newURL))
					{
						//System.out.println(newURL);
						rs = rs.merge(crawlPageHelper(newURL, index + 1, getDepth()));
					}
				}
			}
		}
		//}
	//if not we still need to mark that we visited it. 
	/*
	else
	{
		h.markVisited(urlIn);
	}
	*/
		return rs;
	}
	/*
	private ResultSet crawlPageHelper(WebScraper w, int index, int depthIn)
	{
		ResultSet rs = null;
		Document p = new Document();
		boolean avaliableURL = p.loadPageFromURL(getURL());
		h.markVisited(getURL());
		if (avaliableURL && !h.wereDuplicatesVisited())
		{
			//rs = w.getImages();
			if (index >= depth)
			{
				rs = w.getImages().merge(null);
			}
			else
			{
				//got all the link from page
				Elements e = p.getElementsByTag("a");
				while (e.hasNextElement())
				{
					Element n = e.getNextElement();
					//got the new url.
					String newURL = n.getAttributeValue("href");
					if (!h.visited(newURL))
					{
						w.setURL(newURL);
						rs = w.getImages().merge(crawlPageHelper(w, index + 1, getDepth()));
					}
						//System.out.println(newURL);
						
					}
				}
			}
		return rs;
	}
	*/

	
	/**
	 * Retrieves the historical trail of pages visited during the last top-level call to crawlPage.
	 * @return The most recent collection of pages visited. 
	 * If the crawlPage method has not been previously called, the PageHistory object will be empty.
	 */
	public PageHistory getPageHistory()
	{
		return h;
	}
	
}
