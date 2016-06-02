import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

// 基础程序－ 用于抓取sec一级名称、发布时间、两个链接
public class Copy_2_of_Scrape_10_12 {

	public static void main(String[] args) throws IOException {

		String link13D = "http://www.sec.gov/cgi-bin/browse-edgar?action=getcurrent&type=SC%2013D&company=&dateb=&owner=include&start=0&count=100&output=atom";

		String link13G = "http://www.sec.gov/cgi-bin/browse-edgar?action=getcurrent&type=SC%2013G&company=&dateb=&owner=include&start=0&count=100&output=atom";

		String webLink = link13G;
		getLVL1(webLink);

	}

	private static void getLVL1(String webLink) throws IOException {
		String YEAR = "2015";
		String rrsLink = webLink;
		Document rssPage = Jsoup.connect(rrsLink).get();

		// titles
		Elements titles = rssPage.getElementsByTag("title"); // head
		titles.remove(0);
		titles.forEach(e -> {
			String eToString = e.toString();
			System.out.println(eToString.substring(
					eToString.lastIndexOf("-") + 1,
					eToString.lastIndexOf("</title>")));
		});// end of forEach()

		// updated time

		Elements updatedTimes = rssPage.getElementsByTag("updated"); // head
		updatedTimes.remove(0);
		updatedTimes.forEach(e -> {
			String eToString = e.toString();
			System.out.println(eToString.substring(eToString.lastIndexOf(YEAR),
					eToString.lastIndexOf("-04:00")).replaceAll("T", " "));

		});

		// link1
		Elements htmlPages = rssPage.getElementsByAttribute("href");
		htmlPages.remove(0);
		htmlPages.remove(0);
		htmlPages.forEach(e -> {
			String eToString = e.toString();
			String link1 = (eToString.substring(
					eToString.lastIndexOf("href=\"") + 6,
					eToString.lastIndexOf("\" />")));

			// port1: 找第二页4个信息
				try {
					System.out.println(link1);
					getPage2Info(link1);

				} catch (Exception e2) {
					e2.printStackTrace();
				}
				// ---------------------------

				// port： 进入第二页的网址 找文件里的信息
				try {
					findFile1(eToString.substring(
							eToString.lastIndexOf("href=\"") + 6,
							eToString.lastIndexOf("\" />")));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				// ----------------------------
			});

	}// end of getLVL1

	private static void getPage2Info(String htmlLink) throws IOException {
		Document htmlPage = Jsoup.connect(htmlLink).get();
		String companyNames = htmlPage.getElementsByClass("companyName").text()
				.replaceAll("[(]see all company filings[)]", "\r");
		System.out.println(companyNames);

	}

	private static void findFile1(String htmlLink) throws IOException {
		// TODO Auto-generated method stub

		Document htmlPage = Jsoup.connect(htmlLink).get();
		Elements tableFile = htmlPage.getElementsByClass("tableFile");
		String fileLink_String = tableFile.select("td").get(2).toString();
		String fileLink = "http://www.sec.gov"
				+ fileLink_String.substring(
						fileLink_String.lastIndexOf("href=\"") + 6,
						fileLink_String.lastIndexOf("\">"));
		System.out.println(fileLink);
		// 新加代码

		Document newPage = Jsoup.connect(fileLink).get();

		String a = newPage.body().toString().replaceAll("<.+?>", "")
				.replaceAll("&nbsp;", "");

		// extract functions
		getCUSIPnum(a);
		getAggAmount(a);
		// getPercent(a);

	}// end of findfile1

	private static void getPercent(String a) {

		String textStr[] = a.split("\\r?\\n");

		int checkPoint = 0;
		ArrayList<String> amountList = new ArrayList();
		ArrayList<String> percentList = new ArrayList();
		for (int j = 0; j < textStr.length; j++) {
			if (textStr[j].toUpperCase().matches(".*PERCENT OF .+ AMOUNT.*")) {
				// System.out.println("有Agg  Amount");
				checkPoint = j;
				for (int k = checkPoint - 1; k < checkPoint + 26
						&& k < textStr.length; k++) {
					// if(textStr[k]!="\r" && textStr[k]!="\n")
					if ( textStr[k].matches(".*%.*")) {

						Matcher m1 = Pattern.compile("(\\d{1,2}[.])?\\d{1,2}( )?%")
								.matcher(textStr[k]);

						// Matcher m5 =
						// Pattern.compile("\\d{1,9}+").matcher(textStr[k]);

						if (m1.find()) {
							String url = m1.group();
							amountList.add(url);
							break;
						}
						
					}

				}

			}
		}

		if (amountList.size() == 0)
			System.out.println("NOT FOUND Amount");
		else
			System.out.println(amountList);
		System.out.println("\n");

	}// end of percent

	private static void getAggAmount(String a) {

		String textStr[] = a.split("\\r?\\n");

		int checkPoint = 0;
		ArrayList<String> amountList = new ArrayList();
		ArrayList<String> percentList = new ArrayList();
		for (int j = 0; j < textStr.length; j++) {
			if (textStr[j].toUpperCase().matches(".*AGGREGATE AMOUNT .+ OWN.*")) {
				// System.out.println("有Agg  Amount");
				checkPoint = j;
				for (int k = checkPoint - 1; k < checkPoint + 26
						&& k < textStr.length; k++) {
					// if(textStr[k]!="\r" && textStr[k]!="\n")
					if (

					textStr[k].matches(".*,?\\d{3}.*")) {

						Matcher m1 = Pattern.compile(
								"\\d{1,3},\\d{3},\\d{3},\\d{3}").matcher(
								textStr[k]);
						Matcher m2 = Pattern.compile("\\d{1,3},\\d{3},\\d{3}")
								.matcher(textStr[k]);
						Matcher m3 = Pattern.compile("\\d{1,3},\\d{3}")
								.matcher(textStr[k]);
						Matcher m4 = Pattern.compile("\\b\\d{1,9}\\b").matcher(
								textStr[k]);
						// Matcher m5 =
						// Pattern.compile("\\d{1,9}+").matcher(textStr[k]);

						if (m1.find()) {
							String url = m1.group();
							amountList.add(url);

//					
							break;
						} else {
							if (m2.find()) {
								String url = m2.group();
								amountList.add(url);
								

								break;
							} else {
								if (m3.find()) {
									String url = m3.group();
									amountList.add(url);
									
									break;
								} else {
									if (m4.find()) {
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
			getPercent(a);

		System.out.println("\n");
		
	
		

	}

	private static void getCUSIPnum(String a) {

		String textStr[] = a.split("\\r?\\n");
		int CUSIPpoint = 0;
		for (int j = 0; j < textStr.length; j++) {
			if (textStr[j].toLowerCase().matches(".*cusip.*")) {

				CUSIPpoint = j;
				for (int k = CUSIPpoint - 9; k < CUSIPpoint + 1; k++) {
					// if(textStr[k]!="\r" && textStr[k]!="\n")
					if (textStr[k].matches(".*\\d{5}\\D.?\\d{3}.*")
							|| textStr[k].matches(".*\\d{9}.*")
							|| textStr[k].matches(".*[A-Z]\\d{8}.*")
							|| textStr[k].matches(".*[A-Z]\\d{4}[A-Z]\\d{3}.*")
							|| textStr[k]
									.matches(".*\\d{5}[A-Z]\\D\\d{2}\\D\\d.*")
							|| textStr[k]
									.matches(".*\\d{5}\\D[A-Z]\\D\\d{3}.*")

					) {

						Matcher m1 = Pattern.compile(".?\\d{9}").matcher(
								textStr[k]);
						Matcher m2 = Pattern.compile(".?[A-Z]\\d{8}").matcher(
								textStr[k]);
						Matcher m3 = Pattern.compile(".?\\d{5}\\D.?\\d{3}")
								.matcher(textStr[k]);
						Matcher m4 = Pattern
								.compile(".?[A-Z]\\d{4}[A-Z]\\d{3}").matcher(
										textStr[k]);
						Matcher m5 = Pattern.compile(
								".?\\d{5}[A-Z]\\D\\d{2}\\D\\d").matcher(
								textStr[k]);
						Matcher m6 = Pattern.compile(
								".?\\d{5}\\D[A-Z]\\D\\d{3}")
								.matcher(textStr[k]);

						if (m1.find()) {
							String url = m1.group();
							System.out.println(url);
							System.out.println();
							return;
						}
						if (m2.find()) {
							String url = m2.group();
							System.out.println(url);
							System.out.println();
							return;
						}
						if (m3.find()) {
							String url = m3.group();
							System.out.println(url);
							System.out.println();
							return;
						}
						if (m4.find()) {
							String url = m4.group(); // value
														// "http://www.mywebsite.com"
							System.out.println(url);
							System.out.println();
							return;
						}
						if (m5.find()) {
							String url = m5.group(); // value
														// "http://www.mywebsite.com"
							System.out.println(url);
							System.out.println();
							return;
						}
						if (m6.find()) {
							String url = m6.group(); // value
														// "http://www.mywebsite.com"
							System.out.println(url);
							System.out.println();
							return;
						}

					}

				}

			}
		}
		System.out.println("NOT FOUND");

	}// end of func

}
