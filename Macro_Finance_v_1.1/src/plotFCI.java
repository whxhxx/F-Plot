import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class plotFCI extends JPanel 
{
	public static double topInterval ;
	public static double bottomInterval;
	public static double max_ydata1 ;
	public static double min_ydata1 ;
	public static double max_ydata2 ;
	public static double min_ydata2 ;
	public static double panelShowHeight;
	public static double threshold;
	static ArrayList<Integer> time = new ArrayList<Integer>();
	ArrayList<Double> data1 = new ArrayList<Double>();
	ArrayList<Double> timeLink = new ArrayList<Double>();
	ArrayList<String> ydata1 = new ArrayList<String>();
	ArrayList<String> ydata2 = new ArrayList<String>();
	
	
	
	int[] a = {100,400,200};
	int x=0,y=0;
	String xy = "";
	 
	static public Double get_max_ydata1()
	{
		return max_ydata1;
	}
	static public Double get_min_ydata1()
	{
		return min_ydata1;
	}
	static public Double get_max_ydata2()
	{
		return max_ydata2;
	}
	static public Double get_min_ydata2()
	{
		return min_ydata2;
	}
	
	static public Double get_topInterval()
	{
		return topInterval;
	}
	static public Double get_bottomInterval()
	{
		return bottomInterval;
	}
	public void paintComponent(Graphics gr) //895, 509
	{	
		topInterval = this.getHeight() * 0.05;
		bottomInterval = this.getHeight() * 0.05;
		
		panelShowHeight = this.getHeight() - topInterval -bottomInterval;
		
		Double ScaleToData1 = (max_ydata1 - min_ydata1) / panelShowHeight;
		Double ScaleToData2 = (max_ydata2 - min_ydata2) / panelShowHeight;
		

		super.paintComponent(gr);
		this.setBackground(new Color(0,0,50));
		
		gr.setColor(new Color(255,204,255));
		System.out.println(threshold);
		gr.drawString("Threshold = " +  new DecimalFormat("##.###").format(threshold), this.getWidth()-150	, this.getHeight() -60);
		gr.drawLine(0, (int)( (this.getHeight() - bottomInterval -topInterval) *  (max_ydata2 - threshold * 1000)/(max_ydata2 - min_ydata2) +topInterval ),
	  this.getWidth(), (int)( (this.getHeight() - bottomInterval -topInterval) * (max_ydata2 - threshold * 1000)/(max_ydata2 - min_ydata2)  +topInterval)  );
		
				
	//	System.out.println(this.getHeight() - bottomInterval);
		gr.setColor(new Color(51,153,255));
		for(int i = 0;i < ydata1.size() - 1; i++)
		{	
			gr.drawLine(36 + i*4, (int)(this.getHeight() - bottomInterval - (Double.parseDouble(ydata1.get( i ).replaceAll(",", "")) - min_ydata1)/ScaleToData1),
				    36 + (i+1)*4, (int)(this.getHeight() - bottomInterval - (Double.parseDouble(ydata1.get(i+1).replaceAll(",", "")) - min_ydata1)/ScaleToData1));
		}
		
		gr.setColor(new Color(30,156,63));
		for(int i =0;i < ydata2.size() - 1; i++)
		{	
			gr.drawLine(36 + i*4, (int)(this.getHeight() - bottomInterval - (Double.parseDouble(ydata2.get(i).replaceAll(",", "")) - min_ydata2)/ScaleToData2),
				  36 + (i+1)*4, (int)(this.getHeight() - bottomInterval - (Double.parseDouble(ydata2.get(i+1).replaceAll(",", "")) - min_ydata2)/ScaleToData2));
		}
		
		
		
		
//		//----------- Line 2
		String str_sp500="*-*";
		String str_sd="*-*";
		String dateTime="*-*";
		int leftOrRight = 110;
		int topOrBottom = 0;
		try
		{
			int x = this.getMousePosition().x;
			int y = this.getMousePosition().y;
			
			if(x > this.getWidth()*7/10)
			{
				leftOrRight = -230;
			}
			
			if(y < this.getHeight()*2/10)
			{
				topOrBottom = 60;
			}
			
			for(int i =0;i<time.size();i++){
				if((time.get(i+9)*4) <= x && (time.get(i+10)*4)  > x ){
					dateTime = getDate(time.get(i+9)); // return time string
					str_sp500 = "S&P500: " + ydata1.get(i);
					str_sd = "BPFI: " +Double.parseDouble(ydata2.get(i))/1000 ;
					
					//指针上显示，时间 左 右
					gr.setColor(Color.pink);
					gr.setFont(new Font("Arial",Font.BOLD,16));
					gr.drawString( dateTime, x + leftOrRight, y - 34 + topOrBottom);// (string, x, y 
					gr.drawString( str_sp500, x + leftOrRight, y - 21 + topOrBottom);// (string, x, y 
					gr.drawString( str_sd, x + leftOrRight, y - 8 + topOrBottom);// (string, x, y 
					double a = (Double.parseDouble(ydata1.get(i)) - min_ydata1)/ScaleToData1;
					double b = time.get(i+9)*4;
					double c = (Double.parseDouble(ydata2.get(i)) - min_ydata2)/ScaleToData2;
					//三条线， 左右下
					gr.setColor(new Color(179,109,59));
					gr.drawLine(0,(int)(this.getHeight() - bottomInterval -a), (int)b,(int)(this.getHeight() - bottomInterval -a));
					gr.drawLine((int)b,(int)(this.getHeight() - bottomInterval -c), (int)this.getWidth(),(int)(this.getHeight() - bottomInterval -c));
					gr.drawLine((int)b, (int)topInterval, (int)b,(int)this.getHeight());
					//三条线上的标签
					gr.setFont(new Font("Arial",Font.BOLD,14));
					gr.drawString( dateTime, (int)b, this.getHeight()-24);// (string, x, y 
					gr.drawString( ydata1.get(i), 1, (int)(this.getHeight() - bottomInterval -a) -10);// (string, x, y 
					gr.drawString( String.valueOf(Double.parseDouble(ydata2.get(i))/1000), this.getWidth() - 50, (int)(this.getHeight() - bottomInterval -c)-10);// (string, x, y 
				}
			}
			
		}
		catch(Exception ix)
		{  
			
		}
			
	

					


		
	}//end of paintComponent
	private String getDate(Integer integer) {
		int a  = (integer+1) % 12;
		int b = integer/12;
		String[] year = {"1997","1998","1999","2000","2001","2002","2003","2004","2005","2006",
		         "2007","2008","2009","2010","2011","2012","2013","2014","2015","2016",};
		if (0 == a)
		{
			a = 12;
		}
		return year[b]+"/"+a;
	}
}
