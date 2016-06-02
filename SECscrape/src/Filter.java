import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Filter {

	public static void main(String[] args) throws IOException {

	String YEAR = "2015";
		
	Document rssPage = Jsoup.connect("http://www.sec.gov/cgi-bin/browse-edgar?action=getcurrent&type=sc%2013&company=&dateb=&owner=include&start=0&count=40&output=atom").get();

	Elements htmlPages = rssPage.getElementsByAttribute("href");
	htmlPages.remove(0);
	htmlPages.remove(0);
	htmlPages.forEach(e ->{
		String eToString = e.toString();
		//System.out.println(eToString.substring(eToString.lastIndexOf("href=\"") + 6  , eToString.lastIndexOf("\" />")));
		
		try {
			System.out.println("yoho");
			findFile1(eToString.substring(eToString.lastIndexOf("href=\"") + 6  , eToString.lastIndexOf("\" />")));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	});
	
	
	
	}// end main

	private static void findFile1(String htmlLink) throws IOException {

		Document htmlPage = Jsoup.connect(htmlLink).get();
		Elements tableFile = htmlPage.getElementsByClass("tableFile");
		String fileLink_String = tableFile.select("td").get(2).toString();
		String fileLink = "www.sec.gov" + fileLink_String.substring(fileLink_String.lastIndexOf("href=\"") + 6  , fileLink_String.lastIndexOf("\">"));
		System.out.println(fileLink);
		
		
		//System.out.println(text);
	}
	
	private static void filter(String htmlnLink) throws IOException{
		Document htmlPage = Jsoup.connect(htmlnLink).get();
		Element tableFile = htmlPage.body();
		System.out.println(tableFile);
		
		
	}
}
