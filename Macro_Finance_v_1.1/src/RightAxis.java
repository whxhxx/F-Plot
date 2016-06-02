import java.awt.Color;
import java.awt.Graphics;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JPanel;

public class RightAxis extends JPanel 
{
	public static  int topInterval ;
	public static  int bottomInterval ;
	//max value 
	static public double maxHeightlvl ;
			
	// min value
	static public double minHeightlvl ;
	public void paintComponent(Graphics g) 
	{

	
		// 11 to top panel screen
		double topInterval = plotFCI.get_topInterval(); // 11

		// 10 to bottom panel screen
		double botInterval = plotFCI.get_bottomInterval(); // 10

		// number of interval line in LEFT axis
		int numberOfScale = 10;

		double panelHeight = this.getHeight() - topInterval - botInterval;
		// scale range / value range
//		double mag_ori = (plotFCI.get_max_ydata2() - plotFCI.get_min_ydata2())
//				* 1.0 / (this.getHeight() - topInterval - botInterval);
//		BigDecimal b2 = new BigDecimal(mag_ori);
//		int mag_roundup = (int) b2.setScale(0, BigDecimal.ROUND_HALF_UP)
//				.doubleValue();

		// 细线间隔 interval of each line in LEFT axis
		double right_Each_Scale_interval = panelHeight / numberOfScale; // （2000--4000）／10 = 600
																		
	//	System.out.println(right_Each_Scale_interval);
	
		double right_Each_Scale_Value = (maxHeightlvl - minHeightlvl) / numberOfScale; // (2000--4000)/20 = 400
		

		super.paintComponent(g);
		this.setBackground(Color.BLACK);

		

		g.setColor(new Color(139,69,19));
		g.drawLine(0, 6, 
				0,this.getHeight());

		// draw left scale
		for (int i = 0; i <= numberOfScale ; i++) 
		{
			g.setColor(new Color(139,69,19));
			g.drawLine(1, (int)(topInterval + right_Each_Scale_interval * i),
					   15, 	 (int)(topInterval + right_Each_Scale_interval * i));
		}
		// draw left scale LABEL value
		for (int i = 0; i <= numberOfScale ; i++)
		{
			g.setColor(new Color(139,69,19));
			g.drawString(String.valueOf((maxHeightlvl - right_Each_Scale_Value * i)/1000.0),
					      16, (int)(topInterval + right_Each_Scale_interval * i + 6));
		}

	}// end of paintComponent
}
