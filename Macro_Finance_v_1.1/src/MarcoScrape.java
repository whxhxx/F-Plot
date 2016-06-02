import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class MarcoScrape 
{

	public static void main(String[] args) throws IOException 
	{
		ArrayList<String> listOf14Values = new ArrayList<String>();
		
		//1 Reserver Asset
		get_Reserve_Asset(listOf14Values);
		
		//2 Fed Debt
//		get_Feddral_Debt(listOf12Values);
		
//		3 Export
		get_Export(listOf14Values);
		
		//4 Import
		get_Import(listOf14Values);
		
		//5 PPI change percent to last year
		get_PPI_Percent_Change_To_LastYear(listOf14Values);
		
		//6 CPI change percent to last year
		get_CPI_Percent_Change_To_LastYear(listOf14Values);
		
		//7 PMI percent monthly change
		get_PMI_Percent_Monthly(listOf14Values);
		
		//8 dollar index
		get_Dollar_Index(listOf14Values);
		
		//9 Ten year note yeild
		get_TenYear_Note_Yeild(listOf14Values);
		
		//10 Unemployment rate
		get_Unemployment_Rate(listOf14Values);
		
		//11 Federal Funds Rate
		get_Fed_Funds_Rate(listOf14Values);
		
		//12 3M-commercial paper
		 get_threeMonths_Commercial_Paper(listOf14Values);
		
		//13 3m-TREASURY NOTE
		get_threeMonths_Treasury_Note(listOf14Values);
		
		//14 M2-Money Stock
		get_M2_Money_Stock(listOf14Values);
		
		//write webpage into a file
		FileWriter fw = new FileWriter("Marco-Data");
		listOf14Values.forEach(e->
		{
			e.toString().replaceAll("( )", "");
			try {
				fw.write(e.toString()+"\r");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		
			

		fw.close();
		
		System.out.println(listOf14Values);
	}




	public static void get_M2_Money_Stock(ArrayList<String> listOf12Values) throws IOException 
	{
		ArrayList<String> list = new ArrayList<String>();
		String webLink = "https://research.stlouisfed.org/fred2/data/M2.txt";
		
		String[] Page = Jsoup.connect(webLink).timeout(0).get().body().text().split(" ");
		System.out.println(webLink + "\n  M2 Money Stock\n" + Page[Page.length - 1] +"\n");
		listOf12Values.add(Page[Page.length - 1]);				
	}




	public static void get_threeMonths_Treasury_Note(ArrayList<String> listOf12Values) throws IOException 
	{
		ArrayList<String> list = new ArrayList<String>();
		String webLink = "https://research.stlouisfed.org/fred2/data/TB3MS.txt";
		
		String[] Page = Jsoup.connect(webLink).timeout(0).get().body().text().split(" ");
		System.out.println(webLink + "\n 3-month TREASURY NOTE\n" + Page[Page.length - 1] +"\n");
		listOf12Values.add(Page[Page.length - 1]);		
	}





	public static void get_threeMonths_Commercial_Paper(ArrayList<String> listOf12Values) throws IOException 
	{

			ArrayList<String> list = new ArrayList<String>();
			String webLink = "http://www.federalreserve.gov/datadownload/Output.aspx?rel=H15&series=5c41bd77529a158c4fedcd76d36b7f3b&lastObs=&from=&to=&filetype=csv&label=include&layout=seriescolumn		";
			String[] Page = Jsoup.connect(webLink).timeout(0).get().body().text().split(",");
			
			System.out.println(webLink + "\n 3-month Commercial Paper\n" + Page[Page.length - 1] +"\n");
			listOf12Values.add(Page[Page.length - 1]);
				
	}












	public static void get_Fed_Funds_Rate(ArrayList<String> listOf12Values) throws IOException 
	{

		ArrayList<String> list = new ArrayList<String>();
		String webLink = "https://research.stlouisfed.org/fred2/data/FEDFUNDS.txt";
		String[] Page = Jsoup.connect(webLink).timeout(0).get().body().text().split(" ");
		
			System.out.println(webLink + "\nFed Funds Rate\n"+Page[Page.length -1] +"\n");
			listOf12Values.add(Page[Page.length -1]);
			
		
	}












	public static void get_Unemployment_Rate(ArrayList<String> listOf12Values) throws IOException 
	{

		ArrayList<String> list = new ArrayList<String>();
		String webLink = "http://data.bls.gov/timeseries/LNS14000000";
		String[] Page = Jsoup.connect(webLink).timeout(0).get().getElementsByClass("regular-data").select("tbody").toString()
											  .replaceAll("<[/].+>","").replaceAll("<.+>","").replaceAll("\n","").split(" ");
//		System.out.println(Arrays.toString(Page));
		
		

		for(int i = Page.length - 1 ; i > 0; i--)
		{
//			System.out.println(i+"---"+Page[i]);
			if(Page[i].matches("\\d{1,2}[.]\\d{1,2}")){
				System.out.println(webLink+"\nUnemployment Rate\n"+Page[i]);
				listOf12Values.add(Page[i]);
				System.out.println();
				break;
			}
		}
//		System.out.println(Page[430]+"yes");
//		if(Page[430].matches("\\d{1,2}[.]\\d{1,2}"))
//			System.out.println(Page[429]+"yes");
		
			
		
	}












	public static void get_TenYear_Note_Yeild(ArrayList<String> listOf12Values) throws IOException
	{

		ArrayList<String> list = new ArrayList<String>();
		String webLink = "https://research.stlouisfed.org/fred2/data/GS10.txt";
		String[] Page = Jsoup.connect(webLink).timeout(0).get().body().text().split(" ");
		System.out.println(webLink + "\n 10 Year Treasury Constant Maturity Rate\n" + Page[Page.length - 1] +"\n");
		listOf12Values.add(Page[Page.length - 1]);
	
	}












	public static void get_Dollar_Index(ArrayList<String> listOf12Values) throws IOException 
	{

		ArrayList<String> list = new ArrayList<String>();
		String webLink = "http://finance.yahoo.com/q?s=DX-Y.NYB";
		String Page = Jsoup.connect(webLink).timeout(0).get().getElementById("yfs_l10_dx-y.nyb").text();
		System.out.println(webLink+"\n dollar index \n"+Page+"\n");
		listOf12Values.add(Page);
	}












	public static void get_PMI_Percent_Monthly(ArrayList<String> listOf12Values) throws IOException 
	{
		ArrayList<String> list = new ArrayList<String>();
		String webLink = "http://www.tradingeconomics.com/united-states/business-confidence";
		Elements Page = Jsoup.connect(webLink).timeout(0).get().getElementsByClass("table-responsive").select("td");
		System.out.println(webLink + "\nPMI Monthly\n" + Page.get(1).text());
		System.out.println();
		listOf12Values.add(Page.get(1).text());
		

			
	}












	public static void get_CPI_Percent_Change_To_LastYear(ArrayList<String> listOf12Values) throws IOException 
	{
		ArrayList<String> list = new ArrayList<String>();
		String webLink = "http://www.bls.gov/news.release/cpi.nr0.htm";
		String Page = Jsoup.connect(webLink).timeout(0).get().getElementsByClass("normalnews").text();
		Matcher m1 = Pattern.compile("all items index increased 0.2 percent").matcher(Page.toLowerCase());

		
		if (m1.find()) 
		{
			String target = m1.group();
			System.out.println(webLink + "\nCPI percent change to last year\n"+ target.toLowerCase().replaceAll("all items index [a-z]+ ", "").replaceAll("percent", "%"));
			System.out.println();
			listOf12Values.add(target.toLowerCase().replaceAll("all items index [a-z]+", "").replaceAll("percent", "%").replaceAll(" ", ""));
		} 

	}












	public static void get_PPI_Percent_Change_To_LastYear(	ArrayList<String> listOf12Values) throws IOException 
	{

		ArrayList<String> list = new ArrayList<String>();
		String webLink = "http://www.bls.gov/news.release/ppi.t01.htm";
		Elements Page = Jsoup.connect(webLink).timeout(0).get().getElementsByClass("datavalue");
		//System.out.println(Page.toString());
		System.out.println(webLink + "\nPPI percent change to last year");
		System.out.println(Page.get(4).text());
		System.out.println();
		listOf12Values.add(Page.get(4).text());
		
	}



	public static void get_Import(ArrayList<String> listOf12Values) throws IOException 
	{
		ArrayList<String> list = new ArrayList<String>();
		String webLink = "http://www.census.gov/foreign-trade/Press-Release/current_press_release/ftdpress.txt";
		String Page = Jsoup.connect(webLink).timeout(0).get().body().text();
		//System.out.println(Page.toString());
		
		Matcher m1 = Pattern.compile("imports were [$]\\d{1,4}(.\\d)?( )?billion").matcher(Page.toLowerCase());

		
		if (m1.find()) 
		{
			String target = m1.group();
			System.out.println(webLink + "\nimport");
			System.out.println(target.toLowerCase().replaceAll("imports were ", "") + "\n");
			listOf12Values.add(target.toLowerCase().replaceAll("imports were ", ""));
		} 

	}


	public static void get_Reserve_Asset(ArrayList<String> listOf12Values) throws IOException 
	{
		ArrayList<String> list = new ArrayList<String>();
		String webLink = "http://www.treasury.gov/resource-center/data-chart-center/IR-Position/Pages/09112015.aspx";
		Document Page = Jsoup.connect(webLink).timeout(0).get();
		Elements td = Page.getElementsByClass("MsoNormalTable").select("tr").removeAttr("td");
		//save value into a list
		td.forEach(e->{list.add(e.toString().replaceAll("<.+>", "").replaceAll("&nbsp;", "").replaceAll("\n", "").replaceAll("   ", ""));});
		
		/*/write webpage into a file
		FileWriter fw = new FileWriter("Reserve Asset");
		for (int i = 0; i< list.size();i++)
		{	
			fw.write(i+"--"+list.get(i)+"\r");
		}
		fw.close();
		*/
		
		// display print
		System.out.println(webLink+  "\n Reserve Asset");
		System.out.println(list.get(3).substring(list.get(3).lastIndexOf(")") + 2,list.get(3).length()-1 ) + "\n");

		listOf12Values.add(list.get(3).substring(list.get(3).lastIndexOf(")") + 2,list.get(3).length()-1 ));
		
	}//end of Reserver Asset
	
	public static void get_Feddral_Debt(ArrayList<String> listOf12Values) 
	{
		//empty so far
	}


	public static void get_Export(ArrayList<String> listOf12Values) throws IOException
	{
		ArrayList<String> list = new ArrayList<String>();
		String webLink = "http://www.census.gov/foreign-trade/Press-Release/current_press_release/ftdpress.txt";
		String Page = Jsoup.connect(webLink).timeout(0).get().body().text();
		//System.out.println(Page.toString());
		
		Matcher m1 = Pattern.compile("exports were [$]\\d{1,4}(.\\d)?( )?billion").matcher(Page.toLowerCase());

		
		if (m1.find()) 
		{
			String target = m1.group();
			System.out.println(webLink + "\nexport");
			System.out.println(target.toLowerCase().replaceAll("exports were ", "") + "\n");
			listOf12Values.add(target.toLowerCase().replaceAll("exports were ", ""));
		} 

	}


}//end of all class
