import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.gmbp.bp.database.Stock;
import com.gmbp.bp.database.YStockADJ;



public class Main {

	/**
	 * TEST
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
 
		//方法1 getInfo()  输出无前后天价格的表单
//		System.out.println(getInfo("yoku"));
		
		//方法2 getInfo() 输出有前后天价格的表单
	//	System.out.println(getInfoWithNextDay("yoku"));
		
		
		 //以下为使用静态方法的计算
		List<List<String>> list1 = getInfoWithNextDay("anat");   //更改TICKER的地方
		
		String writeFileName = "output.csv";
		System.out.println("写入文件： " + writeFileName);
		FileWriter fw = new FileWriter(writeFileName);
		// 计算概率部分  Statistic Calculation Part
		double sUP= 0, sDOWN= 0, pUP= 0, pDOWN= 0, sUPpUP= 0, sDOWNpDOWN= 0, sUPpDOWN= 0, sDOWNpUP= 0;
		int n_sUP = 0, n_sDOWN= 0, n_pUP= 0, n_pDOWN= 0, n_sUPpUP= 0, n_sDOWNpDOWN= 0, n_sUPpDOWN= 0, n_sDOWNpUP= 0,n_sMatch= 0;
		double allN=list1.size() * 1.0;
		System.out.println(allN);
		//1   Surprise 正负 
		fw.write("Data, Period Ending, Estimate, Reported, Surprise, Time, Day1 & Close, Day2 & Open, Day2 & High, Day2 & Low, Day2 & Close " + "\r");
		for(int i = 0; i< list1.size();i++) 
		{	
			System.out.println(list1.get(i));
			fw.write(list1.get(i).toString() + "\r");

		
		}
		fw.close();
		
		
		 //1end
		
		
		
		
			
	}

	private static List<List<String>> getInfoWithNextDay(String ticker) throws IOException {

		List<List<String>> outputList = new ArrayList<List<String>>();
		String[][] priceArr;
		String lastTime ="";
		String currentTime = "";
		String nextTime = "";
		String webLink = "http://www.zacks.com/stock/research/" + ticker + "/earnings-announcements";
		Document Page = Jsoup.connect(webLink).timeout(0).get();
		String[] body = Page.getElementsByClass("quote_body_full").select("script").get(0).toString().split("\\[  \\{");
		String[] list = body[1].replaceAll(" ", "").replaceAll("\r?\n", "").split("\\}");
		 
		ArrayList<String []>price = new ArrayList<String[]>();
		System.out.print("list长度:" );
		System.out.println(list.length-2);
		
		//
		priceArr = getPriceArray(ticker).clone();
		
		//
		if(list.length > 3) // normal situation: the company has more than 1 data line
		{
			
			lastTime = currentTime; //pass lasttime current to "lastTime"
			int i = 0;
			String[] monthData = list[i].split(",");
			ArrayList<String> monthList = new ArrayList<String>();
			monthList.add(monthData[0].substring(8, monthData[0].length() - 1));// date
			monthList.add(monthData[1].substring(16, monthData[1].length() - 1));// period ending
			monthList.add(monthData[2].substring(monthData[2].indexOf("mate") + 7, monthData[2].length() - 1));// estimate
			monthList.add(monthData[3].substring(monthData[3].indexOf("orted") + 8, monthData[3].length() - 1));// reported//time 
			if(monthData[4].length()>20)
			{
				monthList.add(monthData[4].substring(monthData[4].indexOf(">") + 1, monthData[4].lastIndexOf("<")));
			}
			else
			{
				monthList.add("--");
			}
			monthList.add(monthData[5].substring(8, monthData[5].length()-1)); //beforeO or afterC //time 
			currentTime = monthData[5].substring(8, monthData[5].length()-1); //set current time 
			
			if(currentTime != "--")
			{
				price = getTwoDayPrice(monthData[0].substring(8, monthData[0].length() - 1),currentTime,lastTime,priceArr);
			
				 
				if(price.get(0) != null && price.get(1) != null)
				{
					monthList.add(price.get(0)[0].length() >10 ? price.get(0)[0].substring(0, 10) +":$"+price.get(0)[7]:price.get(0)[0] );
					
					monthList.add(price.get(1)[0].length() >10 ?  price.get(1)[0].substring(0, 10) +":$"+price.get(1)[1]:price.get(1)[0] );
					monthList.add(price.get(1)[0].length() >10 ?  ":$"+price.get(1)[2]:price.get(1)[0] );
					monthList.add(price.get(1)[0].length() >10 ?  ":$"+price.get(1)[3]:price.get(1)[0] );
					monthList.add(price.get(1)[0].length() >10 ?  ":$"+price.get(1)[7]:price.get(1)[0] );

					//					monthList.add(price.get(1)[0].length() >10 ? ":$"+price.get(1)[7] : price.get(1)[0] );
				}
				
				
			}
			outputList.add(monthList);
		
			//＊＊＊＊＊＊＊＊＊＊   除了第一个之后的行  ＊＊＊＊＊＊＊＊＊＊＊＊＊
			//[1]-rest[] then deal with [1]- rest[]
 			for (int j = 1; j < list.length - 2; j++) 
			{
 				price.clear();
 				
				String[] monthData1 = list[j].split("\",\"");
 				ArrayList<String> monthList2 = new ArrayList<String>();
				String regex_date, regex_prdEnding, regex_reported, regex_estimate, x5, regex_surprise, regex_time;
 				regex_date = monthData1[0].substring(10, monthData1[0].length() - 0);
 				regex_prdEnding = monthData1[1].substring(monthData[1].indexOf("Ending") + 8,monthData1[1].length() - 0);
				regex_reported = monthData1[2].substring(monthData1[2].indexOf("mate") + 7,monthData1[2].length() );
				regex_estimate = monthData1[3].substring(monthData1[3].lastIndexOf("orted") + 8,monthData1[3].length() );
 				x5 = monthData1[4].substring(11, monthData1[4].length() );
 				if(!x5.equals("--"))
				{
					regex_surprise = x5.substring(x5.indexOf(">") + 1,x5.lastIndexOf("</"));
 				}
				else
				{
					regex_surprise ="--";
				}
 				regex_time = monthData1[5].substring(7, monthData1[5].length()-1); 
 				
				monthList2.add(regex_date);// date
				monthList2.add(regex_prdEnding);// period ending
				monthList2.add(regex_reported);// estimate
				monthList2.add(regex_estimate);// reported
				monthList2.add(regex_surprise);// surprise
				monthList2.add(regex_time); //time
				
				price = getTwoDayPrice(regex_date,regex_time,lastTime,priceArr);
			
					monthList2.add(price.get(0)[0].length() >10 ? price.get(0)[0].substring(0, 10)+":$"+price.get(0)[7]:price.get(0)[0] );
				
					monthList2.add(price.get(1)[0].length() >10 ?  price.get(1)[0].substring(0, 10)+":$"+price.get(1)[1]:price.get(1)[0] );
					monthList2.add(price.get(1)[0].length() >10 ?  ":$"+price.get(1)[2]:price.get(1)[0] );
					monthList2.add(price.get(1)[0].length() >10 ?  ":$"+price.get(1)[3]:price.get(1)[0] );
					monthList2.add(price.get(1)[0].length() >10 ?  ":$"+price.get(1)[7]:price.get(1)[0] );
				
					
				 
				outputList.add(monthList2);

				}
			}
		else// rare : this company has only 1 data line  ************  RARE  ***********
		{
			if(list.length == 3)
			{
				price.clear();
				System.out.println("ONLY 1");
				int i = 0;
				String[] monthData = list[i].split(",");
				ArrayList<String> monthList = new ArrayList<String>();
				monthList.add(monthData[0].substring(8, monthData[0].length() - 1));// date
				monthList.add(monthData[1].substring(16, monthData[1].length() - 1));// period ending
				monthList.add(monthData[2].substring(12, monthData[2].length() - 1));// estimate
				monthList.add(monthData[3].substring(12, monthData[3].length() - 1));// reported
 				String regex_surprise = monthData[4].substring(monthData[4].indexOf("\">")+2,monthData[4].lastIndexOf("<"));
				monthList.add(regex_surprise);// surprise
				monthList.add(monthData[5].substring(8, monthData[3].length())); // time
				price = getTwoDayPrice(monthData[0].substring(8, monthData[0].length() - 1),monthData[5].substring(8, monthData[3].length()),lastTime,priceArr)  ;
				
				monthList.add(price.get(0)[0].length() >10 ? price.get(0)[0].substring(0, 10):price.get(0)[0] );
				monthList.add(price.get(1)[0].length() >10 ?  price.get(1)[0].substring(0, 10)+":$"+price.get(1)[1]:price.get(1)[0] );
				monthList.add(price.get(1)[0].length() >10 ?  ":$"+price.get(1)[2]:price.get(1)[0] );
				monthList.add(price.get(1)[0].length() >10 ?  ":$"+price.get(1)[3]:price.get(1)[0] );
				monthList.add(price.get(1)[0].length() >10 ?  ":$"+price.get(1)[7]:price.get(1)[0] );
			
				outputList.add(monthList);
			
 			}
		}
 		return outputList;
	
	
	}

	private static List<List<String>> getInfo(String ticker) throws IOException {

		List<List<String>> outputList = new ArrayList<List<String>>();
		String webLink = "http://www.zacks.com/stock/research/" + ticker + "/earnings-announcements";
		Document Page = Jsoup.connect(webLink).timeout(0).get();
		String[] body = Page.getElementsByClass("quote_body_full").select("script").get(0).toString().split("\\[  \\{");
		String[] list = body[1].replaceAll(" ", "").replaceAll("\r?\n", "").split("\\}");
		System.out.print("list长度:" );
		System.out.println(list.length-2);
		
		if(list.length > 3) // normal situation: the company has more than 1 data line
		{
			int i = 0;
			String[] monthData = list[i].split(",");
			ArrayList<String> monthList = new ArrayList<String>();
			monthList.add(monthData[0].substring(8, monthData[0].length() - 1));// date
			monthList.add(monthData[1].substring(16, monthData[1].length() - 1));// period ending
			monthList.add(monthData[2].substring(monthData[2].indexOf("mate") + 7, monthData[2].length() - 1));// estimate
			monthList.add(monthData[3].substring(monthData[3].indexOf("orted") + 8, monthData[3].length() - 1));// reported
		//	monthList.add(monthData[4].substring(monthData[4].indexOf(">") + 1, monthData[4].lastIndexOf("</")));// surprise
		//	System.out.println(monthData[4]);
			if(monthData[4].length()>20)
			{
				monthList.add(monthData[4].substring(monthData[4].indexOf(">") + 1, monthData[4].lastIndexOf("<")));
			}
			else
				monthList.add("--");
			monthList.add(monthData[5].substring(8, monthData[5].length()-1)); //beforeO or afterC
			
		
			outputList.add(monthList);
			//[1]-rest[] then deal with [1]- rest[]
 			for (int j = 1; j < list.length - 2; j++) 
			{
				String[] monthData1 = list[j].split("\",\"");
 				ArrayList<String> monthList2 = new ArrayList<String>();
				String regex_date, regex_prdEnding, regex_reported, regex_estimate, x5, regex_surprise, regex_time;
 				regex_date = monthData1[0].substring(10, monthData1[0].length() - 0);
 				regex_prdEnding = monthData1[1].substring(monthData[1].indexOf("Ending") + 8,monthData1[1].length() - 0);
				regex_reported = monthData1[2].substring(monthData1[2].indexOf("mate") + 7,monthData1[2].length() );
				regex_estimate = monthData1[3].substring(monthData1[3].lastIndexOf("orted") + 8,monthData1[3].length() );
 				x5 = monthData1[4].substring(11, monthData1[4].length() );
 				if(!x5.equals("--"))
				{
					regex_surprise = x5.substring(x5.indexOf(">") + 1,x5.lastIndexOf("</"));
 				}
				else
				{
					regex_surprise ="--";
				}
 				//time
 				regex_time = monthData1[5].substring(7, monthData1[5].length()-1);
 			//	System.out.println(regex_time);
				monthList2.add(regex_date);// date
				monthList2.add(regex_prdEnding);// period ending
				monthList2.add(regex_reported);// estimate
				monthList2.add(regex_estimate);// reported
				monthList2.add(regex_surprise);// surprise
				monthList2.add(regex_time);
				outputList.add(monthList2);
				}
			}
		else// rare : this company has only 1 data line 
		{
			if(list.length == 3)
			{
				System.out.println("ONLY 1");
				int i = 0;
				String[] monthData = list[i].split(",");
				ArrayList<String> monthList = new ArrayList<String>();
				monthList.add(monthData[0].substring(8, monthData[0].length() - 1));// date
				monthList.add(monthData[1].substring(16, monthData[1].length() - 1));// period ending
				monthList.add(monthData[2].substring(12, monthData[2].length() - 1));// estimate
				monthList.add(monthData[3].substring(12, monthData[3].length() - 1));// reported
 				String regex_surprise = monthData[4].substring(monthData[4].indexOf("\">")+2,monthData[4].lastIndexOf("<"));
				monthList.add(regex_surprise);// surprise
				monthList.add(monthData[5].substring(8, monthData[3].length())); // time
				outputList.add(monthList);
 			}
		}
 		return outputList;
	
	
	}

	
	 
	private static ArrayList<String[]> getTwoDayPrice(String date, String time, String last,String[][]array)
	{
		String year = date.substring(date.length()-4, date.length());
		String month = "0" + date.substring(0, date.indexOf("/") );
		String day = "0" + date.substring(date.indexOf("/") + 1, date.lastIndexOf("/"));
		String dateInList = year + "-" +month.substring(month.length()-2, month.length()) + "-" + day.substring(day.length()-2, day.length());
		int theDay = 0;
		String []currentPrice = {""};
		String []nextPrice = {""};
		String []difference ={"0"};
		ArrayList<String[]> result = new ArrayList<String[]>();
		for(int i = array.length - 1;i >= 0; i--)
		{
//			System.out.println(stocks[i][0]);
			if(array[i][0].matches( ".*" + dateInList+".*" ))
			{
				theDay = i;
				break;
			}
		}
		if(theDay == 0) // date not found
		{
			System.out.println("no data");
			result.add(currentPrice);
			result.add(nextPrice);
			
		}
		else
		{
			if(time.equals("BeforeOpen"))
			{
				nextPrice = array[theDay-1].clone();
				currentPrice = array[theDay].clone();
			
				 
				result.add(nextPrice);
				result.add(currentPrice);
//				System.out.println(Arrays.toString(currentPrice));
				
			}
			else
			{
				if(time.equals("AfterClose"))
				{
					nextPrice = array[theDay+1].clone();
					currentPrice = array[theDay].clone();
					
					result.add(currentPrice);
					result.add(nextPrice);
//					System.out.println(Arrays.toString(currentPrice));
				}
				else // "--" 
				{
					if(last.equals("BeforeOpen"))
					{
						nextPrice = array[theDay-1].clone();
						currentPrice = array[theDay].clone();
					
						 
						result.add(nextPrice);
						result.add(currentPrice);
//						System.out.println(Arrays.toString(currentPrice));
					}
					else //AfterClose
					{
						nextPrice = array[theDay+1].clone();
						currentPrice = array[theDay].clone();
					
						result.add(currentPrice);
						result.add(nextPrice);
//						System.out.println(Arrays.toString(currentPrice));
					}
					
				}
			}
		}
		return result;	
		
	}
	
	private static String[][] getPriceArray(String ticker)
	{
		Stock s = new YStockADJ(ticker,5000,null,false);
		String [][]stocks = s.stockFullInfo;
		return stocks;
	};
	
	private static ArrayList<String[]> getBeforeOrAfterPrice(String date,String time,String last,String ticker){
		String year = date.substring(date.length()-4, date.length());
		String month = "0" + date.substring(0, date.indexOf("/") );
		String day = "0" + date.substring(date.indexOf("/") + 1, date.lastIndexOf("/"));
		String dateInList = year + "-" +month.substring(month.length()-2, month.length()) + "-" + day.substring(day.length()-2, day.length());
		
		Stock s = new YStockADJ(ticker,5000,null,false);
		String [][]stocks = s.stockFullInfo;
		
		
		int theDay = 0;
		String []currentPrice = {""};
		String []nextPrice = {""};
		String []difference ={"0"};
		ArrayList<String[]> result = new ArrayList<String[]>();
		
 		for(int i = stocks.length - 1;i >= 0; i--)
		{
//			System.out.println(stocks[i][0]);
			if(stocks[i][0].matches( ".*" + dateInList+".*" ))
			{
				theDay = i;
				break;
			}
		}
		if(theDay == 0) // date not found
		{
			System.out.println("no data");
			result.add(currentPrice);
			result.add(nextPrice);
			
		}
		else
		{
			if(time.equals("BeforeOpen"))
			{
				nextPrice = stocks[theDay-1].clone();
				currentPrice = stocks[theDay].clone();
				difference[0] = String.valueOf(
								  Double.parseDouble(currentPrice[7].replaceAll("$", "")) 
								- Double.parseDouble(nextPrice[7].replaceAll("$", "")) 
								);
				 
				result.add(nextPrice);
				result.add(currentPrice);
				result.add(difference);
//				System.out.println(Arrays.toString(currentPrice));
				
			}
			else
			{
				if(time.equals("AfterClose"))
				{
					nextPrice = stocks[theDay+1].clone();
					currentPrice = stocks[theDay].clone();
					difference[0] = String.valueOf(
							  Double.parseDouble(nextPrice[7].replaceAll("$", "")) 
							- Double.parseDouble(currentPrice[7].replaceAll("$", "")) 
							);
					result.add(currentPrice);
					result.add(nextPrice);
					result.add(difference);
//					System.out.println(Arrays.toString(currentPrice));
				}
				else // "--" 
				{
					if(last.equals("BeforeOpen"))
					{
						nextPrice = stocks[theDay-1].clone();
						currentPrice = stocks[theDay].clone();
						difference[0] = String.valueOf(
										  Double.parseDouble(currentPrice[7].replaceAll("$", "")) 
										- Double.parseDouble(nextPrice[7].replaceAll("$", "")) 
										);
						 
						result.add(nextPrice);
						result.add(currentPrice);
						result.add(difference);
//						System.out.println(Arrays.toString(currentPrice));
					}
					else //AfterClose
					{
						nextPrice = stocks[theDay+1].clone();
						currentPrice = stocks[theDay].clone();
						difference[0] = String.valueOf(
								  Double.parseDouble(nextPrice[7].replaceAll("$", "")) 
								- Double.parseDouble(currentPrice[7].replaceAll("$", "")) 
								);
						result.add(currentPrice);
						result.add(nextPrice);
						result.add(difference);
//						System.out.println(Arrays.toString(currentPrice));
					}
					
				}
			}
		}
		return result;	
	}
	
}
