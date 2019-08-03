package scraper.base;



import scraper.utils.Document;
import scraper.utils.Elements;

public class test {
	public static void main(String[] args)
	{
		Document page = new Document(); 
		page.loadPageFromURL("http://www.unomaha.edu"); 
		Elements anchors = page.getElementsByTag("a"); 
		for (int i = 0; i < 3; i++) 
		{
			System.out.printf("Link %d text: %s\n", i, anchors.getNextElement().getInnerHTML());
			
			//System.out.printf("Link %d text: %s\n", i, anchors.getNextElement().getAttributeValue("href"));
		}
		boolean a = true;
		System.out.println(a);
	}
}
