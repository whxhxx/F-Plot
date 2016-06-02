import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;

import org.rosuda.JRI.Rengine;

//import org.rosuda.JRI.Rengine;
 

public class Marco1  
{
	
	public static ArrayList<String> yAxisData1 = new ArrayList<String>();
	public static ArrayList<String> yAxisData2 = new ArrayList<String>();
	public static ArrayList<Double> doubleList1 = new ArrayList<Double>();
	public static ArrayList<Double> doubleList2 = new ArrayList<Double>();
	public static ArrayList<Integer> timeList = new ArrayList<Integer>();
	public static double threshold;
	public static int yearNeeded = 20;
	public static int screenHeight ;
	public static int screenWidth ;
	
	  
	public static void main(String[] args) throws IOException, InterruptedException 
	{
		Lock lock = new Lock();
		int count = 0;
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
		
		
//step3 use R to plot
		lock.lock();
		rFunc();
		lock.unlock();
		
//step4 绘图		
		{
	        JFrame frame = new JFrame("GridBagLayoutDemo");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        Toolkit tk= Toolkit.getDefaultToolkit();
	        Dimension dim= tk.getScreenSize();
	        frame.setSize(dim.width, dim.height);
	        screenHeight = dim.height;
	        screenWidth = dim.width;
			int xPos =(dim.width/2)-(frame.getWidth()/2);
			int yPos =(dim.height/2)-(frame.getHeight()/2);
			frame.setLocation(xPos, yPos);  // set location
			frame. setResizable(true);
			//调用函数			
			// read the 2 data from R to yAxisData1 
			getTwoLineData();
			addComponentsToPane(frame.getContentPane());
			
	        frame.setVisible(true);   
		}
		
		

	}

	private static void addComponentsToPane(Container pane) {

		
		
	    //Container pane 
		pane.setLayout(new GridBagLayout());
		//constrain c
		GridBagConstraints c = new GridBagConstraints();
		//constrain 1 
		LeftAxis leftAxis = new LeftAxis();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 11;
		pane.add(leftAxis, c);
		//2
		plotFCI p1 = new plotFCI();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 18;
		c.weighty = 11;
		pane.add(p1, c);
		//3
		RightAxis rightAxis = new RightAxis();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 11;
		c.gridwidth = GridBagConstraints.REMAINDER;
		pane.add(rightAxis, c);
		
		//constrain 2 
		GridBagConstraints c2 = new GridBagConstraints();
		//4
		CornerBlockLeft cb1 = new CornerBlockLeft();
		c2.fill = GridBagConstraints.BOTH;
		c2.weightx = 1;
		c2.weighty = 1;
		pane.add(cb1, c2);
		//5	
		BottomAxis bottomAxis = new BottomAxis();
		c2.fill = GridBagConstraints.BOTH;
		c2.weightx = 18;
		c2.weighty = 1;
		pane.add(bottomAxis, c2);
		
		//6
		CornerBlockRight cb2 = new CornerBlockRight();
		c2.fill = GridBagConstraints.BOTH;
		c2.weightx = 1;
		c2.weighty = 1;
		c2.gridwidth = GridBagConstraints.REMAINDER;
		pane.add(cb2, c2);

		
		// pass these 2 data to ydata1 ,ydata2 in plotCFI p1
		p1.ydata1 = yAxisData1;
		p1.ydata2 = yAxisData2;
		plotFCI.max_ydata1 = Collections.max(doubleList1);
		plotFCI.min_ydata1 = Collections.min(doubleList1);
		plotFCI.max_ydata2 = Collections.max(doubleList2);
		plotFCI.min_ydata2 = Collections.min(doubleList2);
		plotFCI.threshold = threshold;
		
		LeftAxis.maxHeightlvl = Collections.max(doubleList1);
		leftAxis.minHeightlvl = Collections.min(doubleList1);
		rightAxis.maxHeightlvl = Collections.max(doubleList2);
		rightAxis.minHeightlvl = Collections.min(doubleList2);
		
		System.out.println(plotFCI.min_ydata2);
		//System.out.println(p1.min_ydata1);
		for(int i = 0 ; i< 12 * 24; i++ ){
			timeList.add(i);
		}
		plotFCI.time = timeList;
		//System.out.println(timeList);
		//fresh the pane 
		new Refresher(pane);
	}

	// read the 2 data from R to yAxisData1 
	private static void getTwoLineData() {
		
		String readFileName = "final1.csv";
		   
		File file = new File(readFileName);
		Scanner input = null;
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e1) {
 			e1.printStackTrace();
		}
		ArrayList<String> inputList = new ArrayList<String>();
		ArrayList<List> outList = new ArrayList<List>();

		while (input.hasNextLine()) 
		{
			inputList.add(input.nextLine());
		}
		inputList.remove(0);
		for(int i =0;i<inputList.size();i++)
		{
			yAxisData1.add(inputList.get(i).toString().replaceAll("[.].+", ""));
			// send to yAxisData1
		}		
		
		yAxisData1.forEach(e->
		{
			doubleList1.add(Double.parseDouble(e));
		}
		);
		
		
		//-------- Line 1
		
		//-------- Line 2
		String readFileName2 = "final3.csv";
		   
		File file2 = new File(readFileName2);
		Scanner input2 = null;
		try {
			input2 = new Scanner(file2);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		ArrayList<String> inputList2 = new ArrayList<String>();
		ArrayList<List> outList2 = new ArrayList<List>();

		while (input2.hasNextLine()) 
		{
			inputList2.add(input2.nextLine());
		}
		inputList2.remove(0);
		for(int i =0;i<inputList2.size();i++)
		{
			yAxisData2.add(String.valueOf(Double.parseDouble(inputList2.get(i))*1000).replaceAll("[.].+", ""));
		}

		yAxisData2.forEach(e->
		{
			doubleList2.add(Double.parseDouble(e));
		}
		);
		
//		//----------- Line 2	
		 
		ArrayList<String> inputList3 = new ArrayList<String>();   
	 
		Scanner input3 = null;
		try {
			input3 = new Scanner(new File("final4.csv"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		while (input3.hasNextLine()) 
		{
			inputList3.add(input3.nextLine());
		}
		//inputList3.remove(0);
		threshold = Double.parseDouble(inputList3.get(1) ) ;
		
		System.out.println("******" +threshold);
		//
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
        String command = "macro(" + 10 + "," + 10 + "," + 0.06 + ")";
         Double a = re.eval(command).asDouble();
         System.out.println("DONE: " +  a);
        // System.exit(0);
			 
	}// end r func

	private static void updateData(ArrayList<String> newData) throws IOException 
	{

		// TODO Auto-generated method stub
		String readFileName = "output.csv";
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
		
		if(!inputList.get(inputList.size()-1).substring(0, 3) .equals(a.substring(0, 3)))
		{
			inputList.add(a);
		}
		
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
