import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * Test
 * @author Haixin
 *
 */
public class Main {

	public static void main(String[] args) throws IOException {
		
		// input date
		// write data locally
		getOneDayRecord("2015-11-4");
		
		// input  2 dates
		// write data locally
//		getPeriodRecord("2015-10-28","2015-10-30");
		

	}

	/**
	 * this function is to calculate next day after day1
	 * @param day1
	 * @return
	 */
	private static String nextDay(String day1) {
		String x1 = day1;
		
		String year = x1.substring(0, x1.indexOf("-"));
		int intYear = Integer.parseInt(year);
		String month = x1.substring(x1.indexOf("-") + 1, x1.lastIndexOf("-"));
		int intMonth = Integer.parseInt(month);
		String day = x1.substring(x1.lastIndexOf("-")+1, x1.length());
		int intDay = Integer.parseInt(day);
		
		int nextDay = intDay + 1;
		//leap year
		int leapYear = 0;
		
		System.out.println(intYear);
		
		if(( intYear % 100 !=0 && intYear % 4 == 0 )|| intYear % 400 == 0 )
		{
			System.out.println("yes");
			leapYear = 1;
		}
		switch (Integer.parseInt(month)) {
		case 1: 
		{
			if(nextDay > 31)
			{
				nextDay = 1;
				intMonth =2;
			}
		}
		break;
		case 2: 
		{
			if(nextDay > 28 + leapYear)
			{
				nextDay = 1;
				intMonth =3;
			}
		}
		break;
		case 3: 
		{
			if(nextDay > 31)
			{
				nextDay = 1;
				intMonth =4;
			}
		}
		break;
		case 4: 
		{
			if(nextDay > 30)
			{
				nextDay = 1;
				intMonth = 5;
			}
		}
		break;
		case 5: 
		{
			if(nextDay > 31)
			{
				nextDay = 1;
				intMonth = 6;
			}
		}
		break;
		case 6: 
		{
			if(nextDay > 30)
			{
				nextDay = 1;
				intMonth = 7;
			}
		}
		break;
		case 7: 
		{
			if(nextDay > 31)
			{
				nextDay = 1;
				intMonth = 8;
			}
		}
		break;
		case 8: 
		{
			if(nextDay > 31)
			{
				nextDay = 1;
				intMonth =9;
			}
		}
		break;
		case 9: 
		{
			if(nextDay > 30)
			{
				nextDay = 1;
				intMonth =10;
			}
		}
		break;
		case 10: 
		{if(nextDay > 31)
		{
			nextDay = 1;
			intMonth =11;
		}
			
		}
		break;
		case 11: 
		{
			if(nextDay > 30)
			{
				nextDay = 1;
				intMonth = 12;
			}
		}
		break;
		case 12: 
		{
			if(nextDay > 31)
			{
				nextDay = 1;
				intMonth =1;
				intYear = intYear  + 1;
			}
		}
		break;

		default:
		{      }
			break;
		}
		System.out.println(day);
		return intYear+"-"+intMonth+"-"+nextDay;
	}

	/**
	 * this function is to get and write one day earnings data locally
	 * @param dateString
	 * @throws IOException
	 */
	private static void getOneDayRecord(String dateString) throws IOException {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		String webLink = "http://www.nasdaq.com/earnings/earnings-calendar.aspx?date=" + dateString;
		Elements Page = Jsoup.connect(webLink).timeout(0).get().getElementById("_confirmed").select("tr");
		
		
//		Page.forEach(e->
//		{
//			ArrayList<String> compDataList = new ArrayList<String>();
//			String [] compDataArray =e.toString().split("</td>");
//			String [] a = compDataArray[1].split("<br>");
//			String x1 = a[0].replaceAll("<.+>",	"");
//			compDataList.add();
//			
//			
//		});
		
		FileWriter fw = new FileWriter("Earnings " + dateString + ".csv");
		
		
		for(int i =1;i < Page.size(); i++) //start from 1 cuz line0 is title line
		{
			
			//System.out.println("序号"+ i);
			ArrayList<String> insideList = new ArrayList<String>();
			String [] compDataArray =Page.get(i).toString().split("</td>");
			String [] a = compDataArray[1].split("<br>");
			String compName = a[0].replaceAll("<.+>",	"").replaceAll("\\s+ ", "").replaceAll(",", "").replace("&amp;", "&");
		//	System.out.println(compName);
			
			String a1 = a[1].replaceAll("<.{1,2}>", "");
//			System.out.println(a1);
			String ticker = compName.substring(compName.lastIndexOf("(") + 1, compName.lastIndexOf(")"));
//			System.out.println(ticker);
			
			String compCap = a1.substring(a1.indexOf(":")+1, a1.length()).replaceAll(",", "");
			System.out.println(compCap);
			String compCapNum="";
			if(!compCap.contains("n/a"))
			{
				String lastDigit = compCap.substring(compCap.length()-2, compCap.length()-1);
			//	System.out.println(lastDigit);
			
				double x1 = Double.parseDouble(compCap.substring(2, compCap.length()-2));
				int x2 = 0;
				if(lastDigit.equals("B"))
				{
					x2 = (int)(x1 * 1000000);
				}
				else 
				{
					x2 =(int)( x1 * 1000);
				}
				
				System.out.println(x2+"000");
				compCapNum = x2 +"000";
			}
			
			String date = compDataArray[2].replaceAll("\n? <.+> ", "");
//			System.out.println(date);

			String period = compDataArray[3].replaceAll("\n? <.+> ", "");
//			System.out.println(period);

			String estimate = compDataArray[4].replaceAll("\n? <.+> ", "").replaceAll(",", "");
//			System.out.println(estimate);

			String numOfEsts = compDataArray[5].replaceAll("\n? <.+> ", "");
//			System.out.println(numOfEsts);

			String report = compDataArray[7].replaceAll("\n? <.+> ", "").replaceAll(",", "");
//			System.out.println(report);

			double suprise = 0.0;
			
			if(estimate.contains("n/a") || report.contains("n/a") )
			{
				suprise = 99999; // no suprise flag
			}
			else 
			{
				suprise = (Double.parseDouble( report.replace("$", "") ) - Double.parseDouble(estimate.replace("$", "")))	
						 /Double.parseDouble(estimate.replace("$", ""));
				
			}
			
			insideList.add(compName);
			fw.write(compName+",");
			insideList.add(ticker);
			fw.write(ticker+",");
			insideList.add(compCapNum);
			fw.write(compCapNum+",");
			insideList.add(date);
			fw.write(date+",");
			insideList.add(period);
			fw.write(period+",");
			insideList.add(estimate);
			fw.write(estimate+",");
			insideList.add(numOfEsts);
			fw.write(numOfEsts+",");
			insideList.add(report);
			fw.write(report+",");
			if(suprise!= 99999) //if flag is NOT set
			{
				insideList.add(new DecimalFormat("##.##").format(suprise*100)+"%");
				fw.write(new DecimalFormat("##.##").format(suprise*100)+"%" + "\n");
			}
			else  //if flag is set
			{
				insideList.add("n/a");
				fw.write("n/a" + "\n");
			}
		
	//		System.out.println(new DecimalFormat("##.##").format(suprise*100)+"%");
			list.add(insideList);
			
			
		
		}

		System.out.println(list);
		fw.close();
	}

	/**
	 * this function works for getPeriodRecord(String startDay, String endDay);
	 * @param dateString
	 * @param fw
	 * @throws IOException
	 */
	private static void getOneDayRecord2(String dateString, FileWriter fw) throws IOException {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		String webLink = "http://www.nasdaq.com/earnings/earnings-calendar.aspx?date=" + dateString;
		Elements Page = Jsoup.connect(webLink).timeout(0).get().getElementById("_confirmed").select("tr");
		
		
//		Page.forEach(e->
//		{
//			ArrayList<String> compDataList = new ArrayList<String>();
//			String [] compDataArray =e.toString().split("</td>");
//			String [] a = compDataArray[1].split("<br>");
//			String x1 = a[0].replaceAll("<.+>",	"");
//			compDataList.add();
//			
//			
//		});
		
		
		
		
		for(int i =1;i < Page.size(); i++) //start from 1 cuz line0 is title line
		{
			
		//	System.out.println("序号"+ i);
			ArrayList<String> insideList = new ArrayList<String>();
			String [] compDataArray =Page.get(i).toString().split("</td>");
			String [] a = compDataArray[1].split("<br>");
			String compName = a[0].replaceAll("<.+>",	"").replaceAll("\\s+ ", "").replaceAll(",", "").replace("&amp;", "&");
	//		System.out.println(compName);
			
			String a1 = a[1].replaceAll("<.{1,2}>", "");
//			System.out.println(a1);
			String ticker = compName.substring(compName.lastIndexOf("(") + 1, compName.lastIndexOf(")"));
//			System.out.println(ticker);
			
			String compCap = a1.substring(a1.indexOf(":")+1, a1.length()).replaceAll(",", "");
			System.out.println(compCap);
			String compCapNum="";
			if(!compCap.contains("n/a"))
			{
				String lastDigit = compCap.substring(compCap.length()-2, compCap.length()-1);
			//	System.out.println(lastDigit);
			
				double x1 = Double.parseDouble(compCap.substring(2, compCap.length()-2));
				int x2 = 0;
				if(lastDigit.equals("B"))
				{
					x2 = (int)(x1 * 1000000);
				}
				else 
				{
					x2 =(int)( x1 * 1000);
				}
				
				System.out.println(x2+"000");
				compCapNum = x2 +"000";
			}
			

			String date = compDataArray[2].replaceAll("\n? <.+> ", "");
//			System.out.println(date);

			String period = compDataArray[3].replaceAll("\n? <.+> ", "");
//			System.out.println(period);

			String estimate = compDataArray[4].replaceAll("\n? <.+> ", "").replaceAll(",", "");
//			System.out.println(estimate);

			String numOfEsts = compDataArray[5].replaceAll("\n? <.+> ", "");
//			System.out.println(numOfEsts);

			String report = compDataArray[7].replaceAll("\n? <.+> ", "").replaceAll(",", "");
//			System.out.println(report);

			double suprise = 0.0;
			
			if(estimate.contains("n/a") || report.contains("n/a") )
			{
				suprise = 99999; // no suprise flag
			}
			else 
			{
				suprise = (Double.parseDouble( report.replace("$", "") ) - Double.parseDouble(estimate.replace("$", "")))	
						 /Double.parseDouble(estimate.replace("$", ""));
				
			}
			
			insideList.add(compName);
			fw.write(compName+",");
			insideList.add(ticker);
			fw.write(ticker+",");
			insideList.add(compCapNum);
			fw.write(compCapNum+",");
			insideList.add(date);
			fw.write(date+",");
			insideList.add(period);
			fw.write(period+",");
			insideList.add(estimate);
			fw.write(estimate+",");
			insideList.add(numOfEsts);
			fw.write(numOfEsts+",");
			insideList.add(report);
			fw.write(report+",");
			if(suprise!= 99999) //if flag is NOT set
			{
				insideList.add(new DecimalFormat("##.##").format(suprise*100)+"%");
				fw.write(new DecimalFormat("##.##").format(suprise*100)+"%" + "\n");
			}
			else  //if flag is set
			{
				insideList.add("n/a");
				fw.write("n/a" + "\n");
			}
		
	//		System.out.println(new DecimalFormat("##.##").format(suprise*100)+"%");
			list.add(insideList);
			
			
		
		}

		System.out.println(list);
	
	}

	/**
	 * this function is to get and write more than 1 day earnings data locally
	 * @param startDay
	 * @param endDay
	 * @throws IOException
	 */
	private static void getPeriodRecord(String startDay, String endDay) throws IOException
	{
		FileWriter fw = new FileWriter("Earnings "+ startDay +":"+ endDay + ".csv");
		getOneDayRecord2(startDay, fw);
		String nextDay = nextDay(startDay);
		
		while(!nextDay.equals(endDay))
		{
			getOneDayRecord2(nextDay, fw);
			 nextDay = nextDay(nextDay);
		}
		
		getOneDayRecord2(nextDay, fw);
		
		fw.close();
		
	}
}
