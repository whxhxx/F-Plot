import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import org.rosuda.JRI.Rengine;


public class CopyOfMarco1 
{

	public static void main(String[] args) throws IOException 
	{
		ArrayList<String> output = new ArrayList<String>();
		
		// step1 get data
		getNewData(output);
		if(output.size() == 5)
		{
			System.out.println(output);
		}
		
		//step2 update data in form
		updateData(output);
		//会有输出显示历史数据 新一行为新数据
		
		
		//use R to plot
	//	rFunc();
		{
			
		}
		
		
//		Main_Process.createAndShowGUI();
        javax.swing.SwingUtilities.invokeLater
        (
        		new Runnable() 
        		{
        			public void run() 
        			{
        				System.out.println("out");
        				Main_Process.createAndShowGUI();
        			}
        		}
        );
	}

	private static void rFunc() {

		Rengine re = new Rengine(new String[] { "--vanilla" }, false, null);
        System.out.println("Rengine created, waiting for R");
 
        // the engine creates R is a new thread, so we should wait until it's ready
        // since it is a separate thread, we make one instance in one project is enough
        if (!re.waitForR()) 
        {
            System.out.println("Cannot load R");
            return;
        }
  
        re.eval("source(\"./r/macro3.r\")");   //absolute directory import self defined function
        String command = "macro_analysis(" + 10 + "," + 10 + "," + 0.06 + ")";
        
        Double a = re.eval(command).asDouble();
       
        System.out.println("DONE: " +  a);
      //  System.exit(0);
			
	}

	private static void updateData(ArrayList<String> newData) throws IOException 
	{

		// TODO Auto-generated method stub
		String readFileName = "input.csv";
		String writeFileName = "output.csv";
		System.out.println("读取文件：" + readFileName);
		System.out.println("写入文件： " + writeFileName);

		File file = new File(readFileName);
		Scanner input = new Scanner(file);
		ArrayList<String> inputList = new ArrayList<String>();
		ArrayList<List> outList = new ArrayList<List>();

		while (input.hasNextLine()) 
		{
			inputList.add(input.nextLine());
		}
		
		//*******
		//添加新行
		Calendar cal = Calendar.getInstance();
		String a=(whichMonth(cal.get(Calendar.MONTH))) +" "+cal.get(Calendar.YEAR) + ",";
		for(int i = 0; i < newData.size(); i++)
		{
			a += (newData.get(i) + ",");
		}
		
		a+="0.07";// policy rate 假冒填充数据  ！！
		System.out.println(a);
		inputList.add(a);
		
		FileWriter fw = new FileWriter(writeFileName);
		
		inputList.forEach
		(e -> 
			{
				try 
				{
					fw.write(e.toString() + "\r");
					System.out.println(e);
				} 
				catch (Exception e1) 
				{
				e1.printStackTrace();
				}
			}
		);
		fw.close();
			
	}

	private static String whichMonth(int i) 
	{
		switch(i)
		{
		case (0):{return ("Jan");}
		case (1):{return ("Feb");}
		case (2):{return ("Mar");}
		case (3):{return ("Apr");}
		case (4):{return ("May");}
		case (5):{return ("Jun");}
		case (6):{return ("Jul");}
		case (7):{return ("Aug");}
		case (8):{return ("Sep");}
		case (9):{return ("Oct");}
		case (10):{return ("Nov");}
		case (11):{return ("Dec");}
		default: return null;
		}
	}

	private static void getNewData(ArrayList<String> output) throws IOException {
		MarcoScrape.get_threeMonths_Treasury_Note(output);
		MarcoScrape.get_threeMonths_Commercial_Paper(output);
		MarcoScrape.get_TenYear_Note_Yeild(output);
		MarcoScrape.get_Fed_Funds_Rate(output);
		MarcoScrape.get_M2_Money_Stock(output);
	}

}
