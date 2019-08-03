// COURSE: CSCI1620
// TERM: Spring 2019
// 
// NAME: Zhipeng Wang
// RESOURCES: I don't use any resources
package scraper.base;
/**
 * This class stores information about each image found while crawling pages. 
 * Image URLs are saved along with the URL for the page on which the image appears.
 * @author zhipengwang@unomaha.edu
 */
public class ImageEntry 
{
	/**
	 * The source page URL.
	 */
	private String pAddress;
	/**
	 * The image URL.
	 */
	private String iAddress;
	/**
	 * Creates a new ImageEntry storing an image URL alongside the source page URL.
	 * @param pageAddress the URL for the page where the image was found.
	 * @param imgAddress the full URL of the image itself.
	 */
	public ImageEntry(String pageAddress, String imgAddress)
	{
		this.pAddress = pageAddress;
		this.iAddress = imgAddress;
	}
	/**
	 * Retrieves the source page URL.
	 * @return The web page where the image of this ImageEntry can be found.
	 */
	public String getPageLocation()
	{
		return this.pAddress;
	}
	/**
	 * Retrieves the direct image URL.
	 * @return The full URL of image file itself.
	 */
	public String getImgLocation()
	{
		return this.iAddress;
	}
	/**
	 * Compares this ImageEntry to another object for equality based on the URL of the image file.
	 * Two ImageEntries with the same image URL but different page URLs will be considered equivalent.
	 * @return true if this ImageEntry and other refer to the same image as file on the Web; false otherwise.
	 */
	@Override
	public boolean equals(Object other)
	{
		boolean result = false;
		if (other instanceof ImageEntry && this.getImgLocation().equals(((ImageEntry) other).getImgLocation()))
		{
			result = true;
		}
		return result;
	}
	/**
	 * Retrieves a String version of this ImageEntry. 
	 * The returned string should only contain the URL for the image file.
	 * @return the image file URL from this ImageEntry.
	 */
	@Override
	public String toString()
	{
		return this.getImgLocation();
	}
}
