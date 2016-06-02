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
//

// 用于抓取 sec  CUSIP 字段

public class Copy_day2_of_Test {

	public static void main(String[] args) throws IOException {
		get2ndlinks();
		
	}//end main
	
	//该方法  给每个公司获取2级链接
	static void get2ndlinks() throws IOException{

		String YEAR = "2015";
		
//		Document rssPage = Jsoup.connect("http://www.sec.gov/cgi-bin/browse-edgar?action=getcurrent&type=sc%2013&company=&dateb=&owner=include&start=0&count=40&output=atom").get();
		//100
		Document rssPage = Jsoup.connect("http://www.sec.gov/cgi-bin/browse-edgar?action=getcurrent&type=sc%2013&company=&dateb=&owner=include&start=0&count=100&output=atom").get();

		
		
		 // link1
		Elements htmlPages = rssPage.getElementsByAttribute("href");
		htmlPages.remove(0);
		htmlPages.remove(0);
		System.out.println("重复前"+htmlPages.size());
		for (int i =0;i<htmlPages.size()-1;){
			String x = htmlPages.get(i).toString().substring(htmlPages.get(i).toString().lastIndexOf("htm") -13, htmlPages.get(i).toString().lastIndexOf("htm"));
			String y = htmlPages.get(i+1).toString().substring(htmlPages.get(i+1).toString().lastIndexOf("htm") -13, htmlPages.get(i+1).toString().lastIndexOf("htm"));
//			System.out.println(x);
			if(  x.equals(y)){
				htmlPages.remove(i+1);
				
			}else
				i++;
		}
		
		System.out.println("重复后"+htmlPages.size());
		
		htmlPages.forEach(e ->{
			String eToString = e.toString();
	//	System.out.println(eToString.substring(eToString.lastIndexOf("href=\"") + 6  , eToString.lastIndexOf("\" />")));
			
			try {
				getCUSIP(eToString.substring(eToString.lastIndexOf("href=\"") + 6  , eToString.lastIndexOf("\" />")));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		
	
		
	}//end of get2ndlinks
	
	
	// 该方法是找 在2级连接中 cusip提取
	private static void getCUSIP(String htmlLink) throws IOException {
		// TODO Auto-generated method stub

		Document htmlPage = Jsoup.connect(htmlLink).get();
		Elements tableFile = htmlPage.getElementsByClass("tableFile");
		String fileLink_String = tableFile.select("td").get(2).toString();
		String fileLink = "http://www.sec.gov" + fileLink_String.substring(fileLink_String.lastIndexOf("href=\"") + 6  , fileLink_String.lastIndexOf("\">"));
		
		System.out.println(fileLink);
	//新加代码

	
	Document newPage = Jsoup.connect(fileLink).get();

	String a = newPage.body().toString().replaceAll("<.+?>", "")
										.replaceAll("&nbsp;","")
										.replaceAll("\n{2}", "")
										;
	
	// extract functions
	getValue1(a);
	getValue2(a);
	
	
	
	

}// end of get cusip


	private static void getValue2(String a) {
		
		String textStr[] = a.split("\\r?\\n");
		
		int checkPoint = 0;
		ArrayList<String> amountList = new ArrayList();
		ArrayList<String> percentList = new ArrayList();
		for(int j = 0; j < textStr.length; j++){
			if (textStr[j].toUpperCase().matches(".*AGGREGATE AMOUNT .+ EACH REPORT.*")){
//				System.out.println("有Agg  Amount");
				checkPoint = j;
				for(int k= checkPoint- 1;k< checkPoint +16 && k < textStr.length;k++){
//					if(textStr[k]!="\r" && textStr[k]!="\n")
						if(
						 textStr[k].matches(".*,?\\d{3}.*")
						 
//				         ||textStr[k].matches(".*\\d{9}.*")
						){
//							System.out.println("  有数");

						Matcher m1 = Pattern.compile("\\d{1,3},\\d{3},\\d{3},\\d{3}").matcher(textStr[k]);
//						Matcher m2 = Pattern.compile("\\d\\d?(\\d,)?(\\d{3},)?(\\d{3},)?\\d{3}").matcher(textStr[k]);
						Matcher m2 = Pattern.compile("\\d{1,3},\\d{3},\\d{3}").matcher(textStr[k]);

						Matcher m3 = Pattern.compile("\\d{1,3},\\d{3}").matcher(textStr[k]);
						Matcher m4 = Pattern.compile("\\d{1,3}").matcher(textStr[k]);

					
						
							if (m1.find()) {
							    String url = m1.group(); 
	//						    System.out.println();
							    amountList.add(url);
							    break;
							}	
							else{
								if (m2.find()) {
								    String url = m2.group(); 
		//						    System.out.println();
								    amountList.add(url);
								    break;
								}	
								else{
									if (m3.find()) {
									    String url = m3.group(); 
			//						    System.out.println();
									    amountList.add(url);
									    break;
									}	
									else
									{
										if (m4.find()) {
										    String url = m4.group(); 
				//						    System.out.println();
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
		System.out.println("NOT FOUND");
		else
			  System.out.println(amountList);
		System.out.println("\n");
			
	}

	private static void getValue1(String a){
		
		String textStr[] = a.split("\\r?\\n");
		int CUSIPpoint = 0;
		for(int j = 0; j < textStr.length; j++){
			if (textStr[j].toLowerCase().matches(".*cusip.*")){
		
				CUSIPpoint = j;
				for(int k= CUSIPpoint-9;k< CUSIPpoint +1;k++){
//					if(textStr[k]!="\r" && textStr[k]!="\n")
						if(textStr[k].matches(".*\\d{5}\\D.?\\d{3}.*")
				         ||textStr[k].matches(".*\\d{9}.*")
						 ||textStr[k].matches(".*[A-Z]\\d{8}.*")
						 ||textStr[k].matches(".*[A-Z]\\d{4}[A-Z]\\d{3}.*")
						 ||textStr[k].matches(".*\\d{5}[A-Z]\\D\\d{2}\\D\\d.*")

								
						 ){
						
						
						Matcher m1 = Pattern.compile(".?\\d{9}").matcher(textStr[k]);
						Matcher m2 = Pattern.compile(".?[A-Z]\\d{8}").matcher(textStr[k]);
						Matcher m3 = Pattern.compile(".?\\d{5}\\D.?\\d{3}").matcher(textStr[k]);
						
						Matcher m4 = Pattern.compile(".?[A-Z]\\d{4}[A-Z]\\d{3}").matcher(textStr[k]);
						Matcher m5 = Pattern.compile(".?\\d{5}[A-Z]\\D\\d{2}\\D\\d.*").matcher(textStr[k]);

					
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
						
					}
			
				}
				
			}
		}System.out.println("NOT FOUND");
		
	}//end of getValue1





}//end of class All
