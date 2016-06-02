import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JPanel;


public class LeftAxis extends JPanel 
{
	ArrayList<Double> data1 = new ArrayList();
	public static  double topInterval ;
	public static  double bottomInterval ;
	//max value 
	static public double maxHeightlvl ;
			
	// min value
	static public double minHeightlvl ;
	
	 
	 
	public void paintComponent(Graphics g)
	{
		
		
		//11 to top panel screen
		double topInterval = plotFCI.get_topInterval(); 
		
		//10 to bottom panel screen
		double botInterval = plotFCI.get_bottomInterval();
		
		// number of interval line in LEFT axis
		int numberOfScale = 10 ;
		
		//panel height
		double panelHeight = this.getHeight() - topInterval - botInterval;

		// scale range / value range
//		double mag_ori = (plotFCI.get_max_ydata1()-plotFCI.get_min_ydata1()) * 1.0/(this.getHeight()- topInterval - botInterval);
//		BigDecimal bdTest = new BigDecimal(mag_ori);
//		bdTest = bdTest.setScale(0, BigDecimal.ROUND_HALF_UP);
//		int mag_roundup = (int)bdTest.doubleValue();
	//	System.out.println(mag_roundup);
		
		//细线间隔 interval of each line in LEFT axis
		double left_Each_Scale_interval = panelHeight / numberOfScale; // 500／（10） ＝ 50
		//细线上数值间隔  number on each interval line :maxHeightlvl to minHeightlvl by 150 each
		Double left_Each_Scale_Value = (maxHeightlvl - minHeightlvl) / numberOfScale; // (2200-700)/10 = 150
		
		super.paintComponent(g);
		this.setBackground(Color.BLACK);
		
		
		g.setColor(new Color(139,69,19));	
		g.drawLine(this.getWidth() - 15, 6, this.getWidth() - 15, this.getHeight() );
		
		//draw left scale
		for(int i = 0; i <= numberOfScale  ; i++)
		{
			g.setColor(new Color(139,69,19));
			g.drawLine(this.getWidth() - 15, (int)(topInterval + left_Each_Scale_interval * i),
					   this.getWidth()   , (int)(topInterval + left_Each_Scale_interval * i));
		}
		// draw left scale label value
		for(int i = 0; i <= numberOfScale ; i++)
		{
			
			g.setColor(new Color(139,69,19));
			g.drawString( new DecimalFormat("####.##").format(maxHeightlvl - left_Each_Scale_Value*i), this.getWidth() - 60, (int)(topInterval + left_Each_Scale_interval * i + 6));
		}
		
		
	
		
	}//end of paintComponent
}
