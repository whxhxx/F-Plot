import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class PrintTest {
	public int count1 = 0;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		getCUSIP();
		
	}//end main
	
	static void getCUSIP() throws IOException{

		String YEAR = "2015";
		
		Document rssPage = Jsoup.connect("http://www.sec.gov/cgi-bin/browse-edgar?action=getcurrent&type=sc%2013&company=&dateb=&owner=include&start=0&count=40&output=atom").get();

		
		 // link1
		Elements htmlPages = rssPage.getElementsByAttribute("href");
		htmlPages.remove(0);
		htmlPages.remove(0);
		System.out.println("重复前"+htmlPages.size());
		for (int i =0;i<htmlPages.size()-1;){
			String x = htmlPages.get(i).toString().substring(htmlPages.get(i).toString().lastIndexOf("htm") -13, htmlPages.get(i).toString().lastIndexOf("htm"));
			String y = htmlPages.get(i+1).toString().substring(htmlPages.get(i+1).toString().lastIndexOf("htm") -13, htmlPages.get(i+1).toString().lastIndexOf("htm"));
			if(  x.equals(y)){
				htmlPages.remove(i+1);
				
			}else
				i++;
		}
		
		System.out.println("重复后"+htmlPages.size());
		//把 html 网址 传入 findFile1函数 找里面的文件
		String eToString = htmlPages.get(   0  ).toString(); ////////////     test HERE 
			
			try {
				findFile1(eToString.substring(eToString.lastIndexOf("href=\"") + 6  , eToString.lastIndexOf("\" />")));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		
		
		
	}//end of getCUSIP
	
	private static void findFile1(String htmlLink) throws IOException {
		int xx = 1;
		Document htmlPage = Jsoup.connect(htmlLink).get();
		Elements tableFile = htmlPage.getElementsByClass("tableFile");
		String fileLink_String = tableFile.select("td").get(2).toString();
		String fileLink = "http://www.sec.gov" + fileLink_String.substring(fileLink_String.lastIndexOf("href=\"") + 6  , fileLink_String.lastIndexOf("\">"));
		
//		System.out.println(fileLink);
	 String hehe ="http://www.sec.gov/Archives/edgar/data/1427119/000095010315008102/dp60335_sc13g-axalta.htm";
		System.out.println(hehe);

	 Document newPage = Jsoup.connect(hehe).get();
	 
//	String a = newPage.body().toString().replaceAll("<.+>", "");
	String a = newPage.body().toString().replaceAll("<.+?>", "")
										.replaceAll("&nbsp;","")
										.replaceAll("--------------","\n")
										;
	
	//写
	FileWriter fw = new FileWriter("PrintTest");
//	System.out.println(newPage.toString());

	  getCUSIP(a);// cusip
//	  getAmount(a);
//	  getPercent(a);
//	  getAmountOri(a);
	

}// end of findfile
	
	

	private static void getAmountOri(String a) {
		String textStr[] = a.split("\r?\n");
		int checkPoint = 0;
		ArrayList<String> amountList = new ArrayList();
		for (int i = 0; i < textStr.length; i++) 
		{
			if (textStr[i].toUpperCase().matches(".*AGGREGATE AMOUNT .+ OWN.*")) 
			{
				System.out.println("aggre line: "+i);
				

				for (int k = i; k < i+26 && i<textStr.length; k++) 
				{
				//	System.out.println(textStr[k]);
					if (textStr[k].matches(".*,?\\d{1,3}.*")) 
					{

						Matcher m1 = Pattern.compile("\\d{1,3},\\d{3},\\d{3},\\d{3}").matcher(textStr[k]);
						Matcher m2 = Pattern.compile("\\d{1,3},\\d{3},\\d{3}").matcher(textStr[k]);
						Matcher m3 = Pattern.compile("\\d{1,3},\\d{3}").matcher(textStr[k]);
						Matcher m4 = Pattern.compile("PERSON \\d{1,9}\\b").matcher(textStr[k].toUpperCase());
						
						if (m1.find()) 
						{
							String url = m1.group();
							amountList.add(url);
							break;
						} 
						else 
						{
							if (m2.find()) 
							{
								String url = m2.group();
								amountList.add(url);
								break;
							} 
							else 
							{	
								if (m3.find()) 
								{
									String url = m3.group();
									amountList.add(url);			
									break;
								} 
								else 
								{
									if (m4.find()) 
									{
										String url = m4.group();
										amountList.add(url);
										break;
									} 
								}

							}
						}
					}

				}

			}
		}

		if (amountList.size() == 0)
			System.out.println("NO Amount");
		else
			System.out.println(amountList);
			

		System.out.println("\n");
	}

	private static void getPercent(String a) {

		String textStr[] = a.split("\r?\n");
		ArrayList<String> percentList = new ArrayList();
		
		for (int i = 0; i < textStr.length; i++) 
		{System.out.println(textStr[i]);
			if ( textStr[i].toUpperCase().matches(".*PERCENT OF CLASS REPRESENTED.*")) 
			{
				
				for(int j = i; j < i + 30 && j < textStr.length; j++)
				{
					
					Matcher m1 = Pattern.compile("(\\d{1,2}[.])?\\d{1,2}( )?%").matcher(textStr[j]);
					if (m1.find()) 
					{
						
						String url = m1.group();
						percentList.add(url);	
						break;
					}
				}	
			}
		
		}


		if (percentList.size() == 0)
			System.out.println("NOT FOUND Percent");
		else
			System.out.println(percentList);
		System.out.println("\n");

	}

	private static void getAmount(String a) {
		String textStr[] = a.split("\r?\n");
		int checkPoint = 0;
		ArrayList<String> amountList = new ArrayList();
		for (int i = 0; i < textStr.length; i++) 
		{
			if (textStr[i].toUpperCase().matches(".*AGGREGATE AMOUNT .+ OWN.*")) 
			{
				for (int k = i; k < i+26 && i<textStr.length; k++) 
				{
					if (textStr[k].matches(".*,?\\d{1,3}.*")) 
					{	
						System.out.println(textStr[k]);
						Matcher m1 = Pattern.compile("\\d{1,3},\\d{3},\\d{3},\\d{3}").matcher(textStr[k]);
						Matcher m2 = Pattern.compile("\\d{1,3},\\d{3},\\d{3}").matcher(textStr[k]);
						Matcher m3 = Pattern.compile("\\d{1,3},\\d{3}").matcher(textStr[k]);
						Matcher m4 = Pattern.compile("\\b\\d{1,9}\\b").matcher(textStr[k]);
						
						if (m1.find()) 
						{
							String url = m1.group();
							amountList.add(url);
							break;
						} 
						else 
						{
							if (m2.find()) 
							{
								String url = m2.group();
								amountList.add(url);
								break;
							} 
							else 
							{	
								if (m3.find()) 
								{
									String url = m3.group();
									amountList.add(url);			
									break;
								} 
								else 
								{
									if (m4.find()) 
									{
										String url = m4.group();
										amountList.add(url);
										break;
									} 
								}

							}
						}
					}

				}

			}
		}

		if (amountList.size() == 0)
			System.out.println("NO Amount");
		else
			System.out.println(amountList);

		System.out.println("\n");
	}

	private static void getCUSIP(String a) {

		
		String textStr[] = a.split("\\r?\\n");
		int CUSIPpoint = 0;
		for(int j = 0; j < textStr.length; j++)
		{
			if (textStr[j].toLowerCase().matches(".*cusip.*"))
			{
				System.out.println("有cusip");
				
				CUSIPpoint = j;
				for(int k= CUSIPpoint-9;k< CUSIPpoint +9 && k<textStr.length;k++){
//					if(textStr[k]!="\r" && textStr[k]!="\n")
					System.out.println(textStr[k]);

						if(textStr[k].matches(".*\\d{5}\\D.?\\d{3}.*")
				         ||textStr[k].matches(".*\\d{9}.*")
						 ||textStr[k].matches(".*[A-Z]\\d{8}.*")
						 ||textStr[k].matches(".*[A-Z]\\d{4}[A-Z]( )?\\d{3}.*")
						 ||textStr[k].matches(".*\\d{5}[A-Z]\\D\\d{2}\\D\\d.*")

								
						 ){
							
							System.out.println("有match");

						
						Matcher m1 = Pattern.compile(".?\\d{9}").matcher(textStr[k]);
						Matcher m2 = Pattern.compile(".?[A-Z]\\d{8}").matcher(textStr[k]);
						Matcher m3 = Pattern.compile(".?\\d{5}\\D.?\\d{3}").matcher(textStr[k]);
						
						Matcher m4 = Pattern.compile(".?[A-Z]\\d{4}[A-Z]( )?\\d{3}").matcher(textStr[k]);
						Matcher m5 = Pattern.compile(".?\\d{5}[A-Z]\\D\\d{2}\\D\\d.*").matcher(textStr[k]);
						Matcher m6 = Pattern.compile(".*\\d{5}\\D[A-Z]\\D\\d{3}.*").matcher(textStr[k]);

					
						if (m1.find()) {
						    String url = m1.group(); 
						    System.out.println(url);
						    System.out.println( );
						    return;
						}
						if(m2.find()) {
						    String url = m2.group(); 
						    System.out.println(url);
						    System.out.println();
						    return;
							}
						if(m3.find()) {
							    String url = m3.group(); 
							    System.out.println(url);
							    System.out.println();
							    return;
								}
						 if(m4.find()) {
								    String url = m4.group(); //value "http://www.mywebsite.com"
								    System.out.println(url);
								    System.out.println();
								    return;
									}
						 if(m5.find()) {
							    String url = m5.group(); //value "http://www.mywebsite.com"
							    System.out.println(url);
							    System.out.println();
							    return;
								}
						 if(m6.find()) {
							    String url = m6.group(); //value "http://www.mywebsite.com"
							    System.out.println(url);
							    System.out.println();
							    return;
								}
						
					}
			
				}
				
			}
		}System.out.println("NOT FOUND");
		
			
	}

}//end of class All
