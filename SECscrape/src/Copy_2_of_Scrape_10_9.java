import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

// 基础程序－ 用于抓取sec一级名称、发布时间、两个链接
public class Copy_2_of_Scrape_10_9 {
	

	public static void main(String[] args) throws IOException {
		
		String link13D = "http://www.sec.gov/cgi-bin/browse-edgar?action=getcurrent&type=SC%2013D&company=&dateb=&owner=include&start=0&count=100&output=atom";
	
		String link13G = "http://www.sec.gov/cgi-bin/browse-edgar?action=getcurrent&type=SC%2013G&company=&dateb=&owner=include&start=0&count=100&output=atom";

		String webLink = link13D;
		getLVL1(webLink);
		
	}

	
	
	
	
	
	
	private static void getLVL1(String webLink) throws IOException {
		String YEAR = "2015";
		String rrsLink =webLink;
		Document rssPage = Jsoup.connect(rrsLink).get();

		// titles
		Elements titles = rssPage.getElementsByTag("title"); // head
		titles.remove(0);
		titles.forEach(e ->{
			String eToString = e.toString();
			System.out.println(eToString.substring(eToString.lastIndexOf("-")+1 , eToString.lastIndexOf("</title>")));
		});// end of forEach()
		
		
		//updated time
		
		Elements updatedTimes = rssPage.getElementsByTag("updated"); // head
		updatedTimes.remove(0);
		updatedTimes.forEach(e ->{
			String eToString = e.toString();
		System.out.println(eToString.substring(eToString.lastIndexOf(YEAR) , eToString.lastIndexOf("-04:00")).replaceAll("T", " "));
		});
		
		 // link1
		Elements htmlPages = rssPage.getElementsByAttribute("href");
		htmlPages.remove(0);
		htmlPages.remove(0);
		htmlPages.forEach(e ->{
			String eToString = e.toString();
			System.out.println(eToString.substring(eToString.lastIndexOf("href=\"") + 6  , eToString.lastIndexOf("\" />")));
			
			try {
				findFile1(eToString.substring(eToString.lastIndexOf("href=\"") + 6  , eToString.lastIndexOf("\" />")));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		
		
	}// end of getLVL1 
	
	







	private static void findFile1(String htmlLink) throws IOException {
		// TODO Auto-generated method stub

		Document htmlPage = Jsoup.connect(htmlLink).get();
		Elements tableFile = htmlPage.getElementsByClass("tableFile");
		String fileLink_String = tableFile.select("td").get(2).toString();
		String fileLink = "http://www.sec.gov" + fileLink_String.substring(fileLink_String.lastIndexOf("href=\"") + 6  , fileLink_String.lastIndexOf("\">"));
		System.out.println(fileLink);
		
		//System.out.println(text);

	
	}

}
