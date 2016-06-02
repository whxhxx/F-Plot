import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;


public class BottomAxis extends JPanel 
{
	ArrayList<Double> data1 = new ArrayList();
	
	
	 
	public void paintComponent(Graphics g)
	{
//		JButton StartButton = new JButton("button");
//		StartButton.setSize(150, 30);
//		StartButton.setLocation(this.getWidth()/2+150, 20);
//		this.add(StartButton);
		
		
		
		super.paintComponent(g);
		this.setBackground(Color.BLACK);
		
		g.setColor(new Color(139,69,19));
			
		g.drawLine(0, 0, this.getWidth(),0);
		
		String[] year = {"1997","1998","1999","2000","2001","2002","2003","2004","2005","2006",
				         "2007","2008","2009","2010","2011","2012","2013","2014","2015","2016",};
	
		for(int i = 0; i < 25*12; i++)
		{
			
			g.drawLine(4*i,0,4*i,5);
		
		}
		for(int i = 0; i < 20; i++)
		{
			g.drawLine(i * 48,0, i * 48,11);
			g.drawString(year[i], i * 48 - 15,19);
		}
	
	}//end of paintComponent
}
