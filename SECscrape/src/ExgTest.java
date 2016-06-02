import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class ExgTest 
{

	public static void main(String[] args) throws IOException   
	{
		
		String a2 =" OWNED BY EACH REPORTING PERSON///               ///             ///             ///              ///               ///             ///             ///              ///               ///            ///            ///             ///             ///              696,080///               ///             ///             ///              ///               ///             ///             ///              ///               ///            ///            ///             ///             ///              ///               ///             ///             ///              ///               ///            ///            ///             ///             ///              12///               ///             ///             ///              CHECK BOX IF THE AGGREGATE AMOUNT IN ROW (11) EXCLUDES CERTAIN SHARES (SEE INSTRUCTIONS)///               ///             ///             ///              ///               ///             ///             ///              ☐///               ///            ///            ///             ///             ///             ///              ///               ///             ///             ///              ///               ///            ///            ///             ///             ///              ///               ///             ///             ///              ///               ///            ///            ///             ///             ///              13///               ///             ///             ///              PERCENT OF CLASS REPRESENTED BY AMOUNT IN ROW (11)///               ///             ///             ///              ///               ///             ///             ///              ///               ///            ///            ///             ///             ///              41.4% (2)///               ///             ///             ///              ///               ///             ///             ///              ///               ///            ///            ///             ///             ///              ///               ///             ///             ///              ///               ///            ///            ///             ///             ///              14///               ///             ///             ///              TYPE OF REPORTING PERSON///               ///             ///             ///              ///               ///             ///             ///              ///               ///            ///            ///             ///             ///              OO – TRUST///               ///             ///             ///              ///               ///             ///             ///              ///               ///            ///            ///             ///             ///              ///               ///             ///             ///              ///               ///            ///          ///          ///         ///        ///         ///         ///         ///          ///          ///            ///             ///            (1) ///            THE JAMES J. COTTER LIVING TRUST (THE “TRUST”) IS A MEMBER OF A GROUP FOR PURPOSES OF SCHEDULE 13D. THE OTHER MEMBERS OF THE GROUP ARE THE ESTATE OF JAMES J. COTTER, SR. (THE “ESTATE”), MS. MARGARET COTTER AND MS. ELLEN COTTER. THE TRUST IS SEPARATELY FILING THIS REPORT ON SCHEDULE 13D FROM THE OTHER MEMBERS OF THE GROUP. ///            ///          ///          ///         ///        ///          ///         ///         ///          ///          ///            ///             ///            (2) ///            BASED UPON 1,680,590 SHARES OF CLASS B VOTING COMMON STOCK, $0.01 PAR VALUE PER SHARE (THE “VOTING STOCK”), OUTSTANDING, WHICH CONSIST OF (I) 1,580,590 SHARES OF THE VOTING STOCK OUTSTANDING AS OF JUNE 30, 2015, AS REPORTED ON THE ISSUER’S FORM 10-Q FILED WITH THE SECURITIES AND EXCHANGE COMMISSION ON AUGUST 10, 2015 AND (II) 100,000 SHARES OF VOTING STOCK ISSUED UPON THE EXERCISE OF THE ESTATE OF 100,000 OPTIONS TO ACQUIRE VOTING STOCK.  ///            ///          ///          ///         ///         ///         ///          2 OF 6///          ///          ///           ///          ///         ///        ///         ITEM 1. SECURITY AND ISSUER///         ///        ///          ///         ///        ///         THE COMMON STOCK OF READING INTERNATIONAL, INC., A NEVADA CORPORATION (THE “ISSUER” OR THE “COMPANY”), IS DIVIDED INTO TWO CLASSES, CLASS A NON-VOTING COMMON STOCK, $0.01 PAR VALUE PER SHARE (THE “NON-VOTING STOCK”), AND CLASS B VOTING COMMON STOCK, $0.01 PAR VALUE PER SHARE (THE “VOTING STOCK” AND TOGETHER WITH THE NON-VOTING STOCK, THE “SHARES”). THIS SCHEDULE 13D (THIS “SCHEDULE 13D”) IS BEING FILED BY THE JAMES J. COTTER LIVING TRUST (THE “TRUST” OR THE “REPORTING PERSON”) WITH RESPECT TO THE VOTING STOCK BY MS. ELLEN COTTER AND MS. MARGARET COTTER, TWO OF THE THREE CO-TRUSTEES OF THE TRUST.THE SHARES OF THE VOTING STOCK AND THE SHARES OF THE NON-VOTING STOCK ARE LISTED ON NASDAQ.///         ///        ///          ///         ///        ///         THE ADDRESS OF THE PRINCIPAL EXECUTIVE OFFICES OF THE ISSUER IS READING INTERNATIONAL, INC., 6100 CENTER DRIVE, SUITE 900, LOS ANGELES, CALIFORNIA 90045.///         ///        ///          ///         ///        ///         ITEM 2. IDENTITY AND BACKGROUND///         ///        ///          ///         ///        ///         THE TRUST IS A TRUST ORGANIZED UNDER THE LAWS OF CALIFORNIA. DURING THE LIFETIME OF MR. JAMES J. COTTER, SR., THE TRUST WAS REVOCABLE BY MR. JAMES J. COTTER, SR., BUT THE TRUST BECAME IRREVOCABLE UPON THE DEATH OF MR. JAMES J. COTTER, SR. ON SEPTEMBER 13, 2014. THE TRUST SERVES AS A VEHICLE FOR THE MANAGEMENT AND DISTRIBUTION OF THE ASSETS OF MR. JAMES J. COTTER, SR. ACCORDING TO A PURPORTED AMENDMENT TO THE TRUST SIGNED ON JUNE 19, 2014 (“2014 AMENDMENT”), THE CHILDREN OF MR. JAMES J. COTTER, SR., INCLUDING MS. ELLEN COTTER, MS. MARGARET COTTER AND MR. JAMES J. COTTER, JR., SERVE AS CO-TRUSTEES OF THE TRUST AND THEREFORE MAY BE DEEMED TO SHARE VOTING AND INVESTMENT POWER OVER THE SHARES OF THE VOTING STOCK DIRECTLY BENEFICIALLY OWNED BY THE TRUST. IN LITIGATION FILED IN THE SUPERIOR COURT OF THE STATE OF CALIFORNIA, COUNTY OF LOS ANGELES, CAPTIONED ///         IN RE JAMES J. COTTER LIVING TRUST DATED AUGUST 1, 2000 (CASE NO. BP159755) (“TRUST LITIGATION”), MS. ELLEN COTTER AND MS. MARGARET COTTER HAVE CHALLENGED THE VALIDITY OF THE 2014 AMENDMENT; ACCORDING TO THE PRE-EXISTING TRUST AGREEMENT, ONLY MS. ELLEN COTTER AND MS. MARGARET COTTER WERE NAMED AS CO-TRUSTEES. THE EXTENT OF ANY PECUNIARY INTEREST IN THE VOTING STOCK OWNED BY THE TRUST ATTRIBUTABLE TO MS. MARGARET COTTER AND MS. ELLEN COTTER AS CO-TRUSTEES OF THE TRUST IS DEPENDENT UPON THE OUTCOME OF THE TRUST LITIGATION. THE TRUST’S PRINCIPAL BUSINESS OFFICE ADDRESS IS C/O READING INTERNATIONAL, INC., 6100 CENTER DRIVE, SUITE 900, LOS ANGELES, CALIFORNIA 90045.///         ///        ///          ///         ///        ///         DURING THE LAST FIVE YEARS, THE REPORTING PERSON HAS NOT BEEN (A) CONVICTED IN A CRIMINAL PROCEEDING (EXCLUDING TRAFFIC VIOLATIONS OR SIMILAR MISDEMEANORS) OR (B) A PARTY TO ANY CIVIL PROCEEDING OF A JUDICIAL OR ADMINISTRATIVE BODY OF COMPETENT JURISDICTION AND AS A RESULT OF WHICH SUCH PERSON WAS OR IS SUBJECT TO A JUDGMENT, DECREE OR FINAL ORDER ENJOINING FUTURE VIOLATIONS OF, OR PROHIBITING OR MANDATING ACTIVITIES SUBJECT TO, FEDERAL OR STATE SECURITIES LAWS, OR FINDING ANY VIOLATION WITH RESPECT TO SUCH LAWS.///         ///        ///          ///         ///        ///         ITEM 3. SOURCE AND AMOUNT OF FUNDS OR OTHER CONSIDERATION///         ///        ///          ///         ///        ///         THE TRUST WAS ESTABLISHED BY A DECLARATION OF TRUST, DATED AUGUST 1, 2000, AS AMENDED FROM TIME TO TIME, AND WAS INITIALLY FUNDED WITH THE SHARES OF THE VOTING STOCK OWNED BY MR. JAMES J. COTTER, SR. MR. JAMES J. COTTER, SR. PASSED AWAY ON SEPTEMBER 13, 2014, AND THE TRUST BECAME AN IRREVOCABLE LIVING TRUST.///         ///        ///          ///         ///        ///         ITEM 4. PURPOSE OF TRANSACTION///         ///        ///          ///         ///        ///         THE REPORTING PERSON IS DEEMED TO HAVE ACQUIRED BENEFICIAL OWNERSHIP OF 696,080 SHARES OF THE VOTING STOCK AS A RESULT OF MR. JAMES J. COTTER, SR.’S DEATH, AS DESCRIBED IN ITEM 3 OF THIS SCHEDULE 13D. SUCH SHARES OF THE VOTING STOCK WERE DEEMED TO HAVE BEEN OWNED BY MR. JAMES J. COTTER, SR. THROUGH THE TRUST DURING HIS LIFETIME AND, UPON MR. JAMES J. COTTER, SR.’S DEATH AND THE TRUST’S CONVERSION INTO AN IRREVOCABLE TRUST, ARE NOW DEEMED TO BE DIRECTLY BENEFICIALLY OWNED BY THE TRUST, OF WHICH THE CHILDREN OF MR. JAMES J. COTTER, SR. SERVE AS CO-TRUSTEES. ///         THE SHARES OF THE VOTING STOCK DIRECTLY BENEFICIALLY OWNED BY THE TRUST ULTIMATELY WILL BE HELD IN FURTHER TRUST FOR THE BENEFIT OF THE DESCENDANTS OF MR. JAMES J. COTTER, SR., AND SUCH SHARESWILL BE HELD FOR INVESTMENT PURPOSES AND THE CO-TRUSTEES OF THE TRUST ARE DIRECTED TO RETAIN SUCH SHARES FOR AS LONG AS POSSIBLE AND ARE RELIEVED FROM ANY OBLIGATION TO DIVERSIFY THE TRUST’S INVESTMENTS.///         ///        ///          ///         ///        ///         ON SEPTEMBER 21, 2015, THE ESTATE EXERCISED VESTED STOCK OPTIONS AND RECEIVED 100,000 SHARES OF VOTING STOCK. ON APRIL 8, 2015, MS. MARGARET COTTER EXERCISED VESTED STOCK OPTIONS AND RECEIVED 12,500 SHARES OF NON-VOTING STOCK. ON APRIL 17, 2015, MS. MARGARET COTTER EXERCISED VESTED STOCK OPTIONS AND RECEIVED 35,100 SHARES OF VOTING STOCK. ON APRIL 16, 2015, MS. ELLEN COTTER EXERCISED VESTED STOCK OPTIONS AND RECEIVED 50,000 SHARES OF VOTING STOCK. MS. ELLEN COTTER AND MS. MARGARET COTTER CURRENTLY INTEND TO HOLD ANY SHARES OF VOTING STOCK DIRECTLY BENEFICIALLY OWNED BY THEM FOR INVESTMENT PURPOSES.///         ///        ///          ///         ///         ///         ///          3 OF 6///          ///          ///           ///          ///         ///        ///         MS. ELLEN COTTER AND MS. MARGARET COTTER CURRENTLY INTEND TO VOTE ALL OF THE SHARES OF VOTING STOCK THAT THEY CONTROL, INCLUDING ALL OF THE SHARES OF VOTING STOCK OWNED BY THEM INDIVIDUALLY, BY THE ESTATE AND BY THE TRUST, AT THE COMPANY’S 2015 ANNUAL MEETING OF STOCKHOLDERS.///         ///        ///          ///         ///        ///         EACH OF MS. ELLEN COTTER AND MS. MARGARET COTTER, AS A CO-TRUSTEE OF THE TRUST, HAS BEEN IN THE PAST AND WILL BE IN THE FUTURE INVOLVED ON BEHALF OF THE COMPANY IN THEIR RESPECTIVE CAPACITIES AS SENIOR EXECUTIVE ///         OFFICERSOF, DIRECTORS OF AND/OR CONSULTANTS TO THE COMPANY, AS APPLICABLE, IN REVIEWING AND EVALUATING POSSIBLE TRANSACTIONS INVOLVING THE COMPANY AND IDENTIFYING CANDIDATES TO SERVE ON THE COMPANY’S BOARD OF DIRECTORS, INCLUDING TRANSACTIONS OF THE SORT DESCRIBED IN CLAUSES (A) THROUGH (F) OF ITEM 4 OF SCHEDULE 13D. IN LIGHT OF THEIR RESPONSIBILITIES TO THE COMPANY, MS. ELLEN COTTER AND MS. MARGARET COTTER DO NOT ANTICIPATE MAKING ANY DISCLOSURES IN CONNECTION WITH THEIR PARTICIPATION IN THE TRANSACTIONS AND ACTIVITIES OF THE COMPANY SEPARATE AND APART FROM RELEVANT DISCLOSURES BY THE COMPANY.///         ///        ///          ///         ///        ///         THE REPORTING PERSON INTENDS TO REVIEW ITS INVESTMENT IN THE ISSUER ON A CONTINUING BASIS AND MAY FROM TIME TO TIME AND AT ANY TIME IN THE FUTURE DEPENDING ON VARIOUS FACTORS, INCLUDING, WITHOUT LIMITATION///         , THE REQUIREMENTS OF THE TRUST, THE ISSUER’S FINANCIAL POSITION AND STRATEGIC DIRECTION, ACTIONS TAKEN BY THE BOARD OF DIRECTORS OF THE ISSUER, PRICE LEVELS OF THE SHARES, OTHER INVESTMENT OPPORTUNITIES AVAILABLE TO THE REPORTING PERSON, CONDITIONS IN THE SECURITIES MARKET AND GENERAL ECONOMIC AND INDUSTRY CONDITIONS, TAKE SUCH ACTIONS WITH RESPECT TO THE INVESTMENT IN THE ISSUER AS THE REPORTING PERSON DEEMS APPROPRIATE, INCLUDING: (I) ACQUIRING ADDITIONAL SHARES AND/OR OTHER EQUITY, DEBT, NOTES, OTHER SECURITIES, OR DERIVATIVE OR OTHER INSTRUMENTS OF THE ISSUER THAT ARE BASED UPON OR RELATE TO THE VALUE OF THE SHARES OR THE ISSUER (COLLECTIVELY, “SECURITIES”) IN THE OPEN MARKET OR OTHERWISE; (II) DISPOSING OF ANY OR ALL OF THEIR SECURITIES IN THE OPEN MARKET OR OTHERWISE; (III) ENGAGING IN ANY HEDGING OR SIMILAR TRANSACTIONS WITH RESPECT TO THE SECURITIES; OR (IV) PROPOSING OR CONSIDERING ONE OR MORE OF THE ACTIONS DESCRIBED IN SUBSECTIONS (A) THROUGH (J) OF ITEM 4 OF SCHEDULE 13D.///         ///        ///          ///         ///        ///         ITEM 5. INTEREST IN SECURITIES OF THE ISSUER///         ///        ///          ///         ///        ///         AS OF THE DATE HEREOF, THE TRUST DIRECTLY BENEFICIALLY OWNS 696,080 SHARES OF THE VOTING STOCK, REPRESENTING 41.4% OF OUTSTANDING VOTING STOCK OF THE ISSUER. BECAUSE THE CHILDREN OF MR. JAMES J. COTTER, SR. SERVE AS CO-TRUSTEES, THE CHILDREN MAY BE DEEMED TO BE INDIRECT BENEFICIAL OWNERS OF 696,080 SHARES OF THE VOTING STOCK DIRECTLY BENEFICIALLY OWNED BY THE TRUST. THE EXTENT OF ANY PECUNIARY INTEREST IN THE VOTING STOCK DIRECTLY BENEFICIALLY OWNED BY THE TRUST ATTRIBUTABLE TO MS. MARGARET COTTER AND MS. ELLEN COTTER, ASCO-TRUSTEES, IS DEPENDENT UPON THE OUTCOME OF THE TRUST LITIGATION. AS OF THE DATE HEREOF, THE TRUST ALSO DIRECTLY BENEFICIALLY OWNS 1,897,649 SHARES OF THE NON-VOTING STOCK, REPRESENTING 8.7% OF OUTSTANDING NON-VOTING STOCK OF THE ISSUER.///         ///        ///          ///         ///        ///         BECAUSE MS. ELLEN COTTER AND MS. MARGARET COTTER (TWO OF THE THREE CHILDREN OF MR. JAMES J. COTTER, SR.) ALSO SERVE AS CO-EXECUTORS (THE “CO-EXECUTORS”) OF THE ESTATE, EACH OF THEM MAY BE DEEMED TO SHARE INDIRECT BENEFICIAL OWNERSHIP OF 427,808 SHARES OF THE VOTING STOCK DIRECTLY BENEFICIALLY OWNED BY THE ESTATE, REPRESENTING 25.5% OF OUTSTANDING VOTING STOCK OF THE ISSUER. ALL OF THE VOTING STOCK HELD BY THE ESTATE WILL BE TRANSFERRED TO THE TRUST AFTER A REASONABLE PERIOD OF ADMINISTRATION. AS OF THE DATE HEREOF, THE ESTATE ALSO DIRECTLY BENEFICIALLY OWNS 326,800 SHARES OF THE NON-VOTING STOCK, REPRESENTING 1.5% OF OUTSTANDING NON-VOTING STOCK OF THE ISSUER. AS OF THE DATE HEREOF, THE CO-EXECUTORS OF THE ESTATE DISCLAIM BENEFICIAL OWNERSHIP OF THE VOTING STOCK AND NON-VOTING STOCK DIRECTLY BENEFICIALLY OWNED BY THE ESTATE, EXCEPT TO THE EXTENT OF THEIR RESPECTIVE PECUNIARY INTEREST THEREIN.///           ///         ///        ///         AS OF THE DATE HEREOF, (1) MS. ELLEN COTTER ALSO DIRECTLY BENEFICIALLY OWNS 50,000 SHARES OF THE VOTING STOCK, REPRESENTING 3.0% OF OUTSTANDING VOTING STOCK OF THE ISSUER, AND (2) MS. MARGARET COTTER DIRECTLY BENEFICIALLY OWNS 35,100 SHARES OF THE VOTING STOCK SUBJECT TO STOCK OPTIONS, REPRESENTING 2.1% OF OUTSTANDING VOTING STOCK OF THE ISSUER. AS OF THE DATE HEREOF, (1) MS. ELLEN COTTER ALSO DIRECTLY BENEFICIALLY OWNS 819,765 SHARES OF THE NON-VOTING STOCK (WHICH AMOUNT ALSO INCLUDES CURRENTLY EXERCISABLE OPTIONS TO ACQUIRE AN ADDITIONAL 20,000 SHARES OF THE NON-VOTING STOCK), REPRESENTING 3.8% OF OUTSTANDING NON-VOTING STOCK OF THE ISSUER, (2) MS. MARGARET COTTER ALSO DIRECTLY BENEFICIALLY OWNS 804,173///         SHARES OF THE NON-VOTING STOCK, REPRESENTING 3.7% OF OUTSTANDING NON-VOTING STOCK OF THE ISSUER AND (3) MR. JAMES J. COTTER, JR. (THE THIRD CHILD OF MR. JAMES J. COTTER, SR.) ALSO DIRECTLY BENEFICIALLY OWNS 856,426 SHARES OF THE NON-VOTING STOCK, REPRESENTING 4.0% OF OUTSTANDING NON-VOTING STOCK OF THE ISSUER, ACCORDING TO MR. JAMES COTTER, JR.’S PUBLIC FILINGS.///           ///         ///         ///         ///          4 OF 6///          ///          ///           ///          ///         ///        ///          ///         ///        ///         MS. MARGARET COTTER ALSO SERVES AS A CO-TRUSTEE OF THE JAMES. J. COTTER GRANDCHILDREN TRUST, A TRUST FOR MR.JAMES J. COTTER, SR.’S GRANDCHILDREN, WHICH HOLDS 289,390 SHARES OF THE NON-VOTING STOCK, REPRESENTING 1.3% OF OUTSTANDING NON-VOTING STOCK OF THE ISSUER. MS. ELLEN COTTER AND MS. MARGARET COTTER ALSO SERVE AS CO-TRUSTEES OF THE JAMES J. COTTER FOUNDATION, WHICH HOLDS 120,751///         SHARES OF THE NON-VOTING STOCK, REPRESENTING 0.5% OF OUTSTANDING NON-VOTING STOCK OF THE ISSUER.///         ///        ///          ///         ///        ///         THE PERCENTAGES REPORTED IN THIS ITEM 5 ARE ///         BASED UPON 21,707,938 SHARES OF THE NON-VOTING STOCK OUTSTANDING AND 1,680,590 SHARES OF THE VOTING STOCK OUTSTANDING, WHICH CONSIST OF (I) 1,580,590 SHARES OF THE VOTING STOCK OUTSTANDING AS OF JUNE 30, 2015, AS REPORTED ON THE ISSUER’S FORM 10-Q FILED WITH THE SECURITIES AND EXCHANGE COMMISSION ON AUGUST 10, 2015 AND (II) 100,000 SHARES OF VOTING STOCK ISSUED UPON THE EXERCISE OF THE ESTATE OF 100,000 OPTIONS TO ACQUIRE VOTING STOCK.///         ///        ///          ///         ///        ///         (B) SEE ROWS 7-10 OF THE COVER PAGE FOR INFORMATION REGARDING THE POWER TO VOTE OR DIRECT THE VOTE AND THE POWER TO DISPOSE OR DIRECT THE DISPOSITION OF THE SHARES BY THE REPORTING PERSON. THE ESTATE, MS. MARGARET COTTER AND MS. ELLEN COTTER HAVE SEPARATELY FILED A SCHEDULE 13D ON THE DATE HEREOF.///         ///        ///          ///         ///        ///         (C)     EXCEPT AS DESCRIBED HEREIN, NONE OF THE REPORTING PERSON, THE ESTATE, MS. MARGARET COTTER AND MS. ELLEN COTTER HAVE ACQUIRED, OR DISPOSED OF, ANY SHARES OF THE VOTING STOCK OF THE ISSUER DURING THE PAST 60 DAYS.///         ///        ///          ///         ///        ///         (D)     NO PERSONS OTHER THAN MS. MARGARET COTTER AND MS. ELLEN COTTER, AS CO-TRUSTEES OF THE TRUST, AND THE BENEFICIARIES OF THE TRUST HAVE THE RIGHT TO RECEIVE, OR THE POWER TO DIRECT THE RECEIPT OF DIVIDENDS FROM, THE PROCEEDS FROM THE SALE OF THE SHARES TO WHICH THIS SCHEDULE 13D RELATES.///         ///        ///          ///         ///         ///          ///          ///            ///             ///            (E) ///            NOT APPLICABLE. ///            ///          ///          ///         ///        ///          ///         ///        ///         ITEM 6. CONTRACTS, ARRANGEMENTS, UNDERSTANDINGS OR RELATIONSHIPS WITH RESPECT TO SECURITIES OF THE ISSUER///         ///        ///          ///         ///        ///         EXCEPT AS DESCRIBED IN ITEM 3, ITEM 4 AND ITEM 5, THE REPORTING PERSON HAS NO CONTRACTS, ARRANGEMENTS, UNDERSTANDINGS OR RELATIONSHIPS (LEGAL OR OTHERWISE) WITH ANY PERSON WITH RESPECT TO ANY VOTING SECURITIES OF THE COMPANY, INCLUDING, BUT NOT LIMITED TO, THE TRANSFER OR VOTING OF ANY OF THE SECURITIES, FINDER’S FEES, JOINT VENTURES, LOAN OR OPTION ARRANGEMENTS, PUTS OR CALLS, GUARANTEES OF PROFITS, DIVISION OF PROFITS OR LOSSES, OR THE GIVING OR WITHHOLDING OF PROXIES.///         ///        ///          ///         ///        ///         ITEM 7. MATERIALS TO BE FILED AS EXHIBITS///         ///        ///          ///         ///        ///         NONE.///         ///        ///          ///         ///         ///         ///          5 OF 6///          ///          ///           ///          ///         ///        ///          ///         ///        ///         AFTER REASONABLE INQUIRY AND TO THE BEST OF MY KNOWLEDGE AND BELIEF, THE UNDERSIGNED CERTIFIES THAT THE INFORMATION SET FORTH IN THIS STATEMENT IS TRUE, COMPLETE AND CORRECT.///         ///        ///          ///         ///        ///         DATED: OCTOBER 9, 2015///         ///        ///          ///         ///         ///         ///           ///            ///            ///            ///             JAMES J. COTTER LIVING TRUST///              ///           ///           ///            ///            ///            ///           ///           ///            ///            ///            ///             BY:///              ///            ///            ///             /S/ MARGARET COTTER///              ///           ///           ///            ///            ///            ///            ///             NAME: MARGARET COTTER///              ///           ///           ///            ///            ///            ///            ///             TITLE: CO-TRUSTEE///              ///           ///           ///            ///            ///            ///           ///           ///            ///            ///            ///             BY:///              ///            ///            ///             /S/ ELLEN COTTER///              ///           ///           ///            ///            ///            ///            ///             NAME: ELLEN COTTER///              ///           ///           ///            ///            ///            ///            ///             TITLE: CO-TRUSTEE///              ///           ///         ///         ///        ///         ///         ///        ///       ///        ///        ///        ///        ///         6 OF 6///         ///        ///        ///        ///         ///        ///       ///        ///          ///       ///     ///    ///   ///  ///  ///";
		String b = "9)  AGGREGATE AMOUNT BENEFICIALLY OWNED:	2,318,938	See Exhibit A";
		String a = "Sep 2015 $n/a ";
		
		String symbol = "NLY";
		int i = 1;

		String weblink = "http://www.forbes.com/innovative-companies/list/#tab:rank";
		Document Page = Jsoup.connect(weblink).timeout(0).get();
		Element pp = Page.getElementById("list-table-body");
		Elements g = Page.getElementsByClass("data");
		System.out.println(g);
//		String[] output = { "", "", "", "", "", "", "", "", "", "", ""};
//		String webLink = "http://www.advfn.com/stock-market/NASDAQ/"
//				+ symbol + "/financials?btn=istart_date&istart_date=" + i
//				+ "&mode=quart erly_reports";
//		Document Page = Jsoup.connect(webLink).timeout(0).get();
//		Element dateList = Page.getElementById("istart_dateid");
//		Elements option = dateList.getElementsByTag("option");
//
//		option.forEach(e -> {
//			if (e.toString().contains("selected")) {
//				output[0] = e.toString().substring(
//						e.toString().indexOf(">") + 1,
//						e.toString().indexOf("</"));
//			}
//		}); // 发现选择页面所选择时间
//
//		String sb = Page.getElementsByClass("sb").toString()	;
//		// 新几个
//		String totalAsset = "", currentAsset = "", totalLiability = "", totalCurrentLiability = "", Eps = "", Dps = "", totalCommonSharesOut = "", totalNetIncome = "", cashEquivalents = "";
//		// total asset
//		String str1 = sb.substring(
//				sb.indexOf(">total assets<") + 15,
//				sb.length());
//		if (str1.length() > 10) {
//			totalAsset = str1.substring(str1.indexOf("t\">") + 3,
//					str1.indexOf("/td") - 1);
////			System.out.println(totalAsset);
//		}
//		// current asset
//		String str2 = sb.matches(".*total current assets.*")? 
//				sb.substring(sb.indexOf("total current assets") + 30,	sb.length()) 
//				: "";
//		if (str2.length() > 10) {
//			currentAsset = str2.substring(str2.indexOf(">") + 1,
//					str2.indexOf("/td") - 1);
//		}
//		System.out.println(currentAsset);
//		// total liability
//		String str3 = sb.substring(
//				sb.indexOf("total liabilities") + 30,
//				sb.length());
//		if (str3.length() > 10) {
//			totalLiability = str3.substring(str3.indexOf(">") + 1,
//					str3.indexOf("/td") - 1);
////			System.out.println(totalLiability);
//		}
//		// total current liabilities
//		String str4 = sb.contains("total current liabilities")?sb.substring(sb.indexOf("total current liabilities") + 30,
//				sb.length()) : "";
//		if (str4.length() > 10) {
//			totalCurrentLiability = str4.substring(str4.indexOf(">") + 1,
//					str4.indexOf("/td") - 1);
//			System.out.println(str4);
//		}
//		// EPS
//		String str5 = sb.contains("Basic EPS - Total")?sb.substring(
//				sb.indexOf("Basic EPS - Total") + 23,
//				sb.indexOf("Basic EPS - Normalized")): "";
//		if (str5.length() > 10) {
//			Eps = str5.substring(str5.indexOf(">") + 1, str5.indexOf("</"));
////			System.out.println(Eps);
//		}
//		// Dividends Paid Per Share (DPS)
//		String str6 = sb.contains("(DPS)")?sb
//				.substring(sb.indexOf("(DPS)") + 15,
//						sb.length()) : "";
//		if (str6.length() > 10) {
//			Dps = str6.substring(str6.indexOf(">") + 1,
//					str6.indexOf("/td") - 1);
////			System.out.println(Dps);
//		}
//		// total common shares out
//		String str7 = sb.contains("total common shares out")? sb.substring(
//				sb.indexOf("total common shares out") + 40,
//				sb.length()) : "";
////		System.out.println(str7);
//		if (str7.length() > 10) {
//			totalCommonSharesOut = str7.substring(str7.indexOf(">") + 1,
//					str7.indexOf("/td") - 1);
//			
//		}
////		System.out.println(totalCommonSharesOut);
//		// total net income
//		String str8 = sb.contains("total net income")?sb.substring(
//				sb.indexOf("total net income") + 30,
//				sb.length()) : "";
//		if (str8.length() > 10) {
//			totalNetIncome = str8.substring(str8.indexOf(">") + 1,
//					str8.indexOf("/td") - 1);
////			System.out.println(totalNetIncome);
//		}
//		// cash & equivalents
//		String s = Page.getElementsByClass("s").toString();
//		String str9 = s.contains("cash &amp; equivalents") ? s.substring(
//				s.indexOf("cash &amp; equivalents") + 30,
//				s.length()) : "";
//		if (str9.length() > 10) {
//			cashEquivalents = str9.substring(str9.indexOf(">") + 1,
//					str9.indexOf("/td") - 1);
////			System.out.println(cashEquivalents);
//		}
//
//		output[1] = totalAsset.replaceAll(",", "");
//		output[2] = currentAsset.replaceAll(",", "");
//		output[3] = totalLiability.replaceAll(",", "");
//		output[4] = totalCurrentLiability.replaceAll(",", "");
//		output[5] = Eps.replaceAll(",", "");
//		output[6] = Dps.replaceAll(",", "");
//		output[7] = totalCommonSharesOut.replaceAll(",", "");
//		output[8] = totalNetIncome.replaceAll(",", "");
//		output[9] = cashEquivalents.replaceAll(",", "");
//		output[10] = "\n";
		// 新几个－end

		
		
	
		// System.out.println(output[0] + "::" + output[1]);

	
	
	}	
		
}


