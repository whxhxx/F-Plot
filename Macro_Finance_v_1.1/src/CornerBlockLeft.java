import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;


public class CornerBlockLeft extends JPanel 
{
	ArrayList<Double> data1 = new ArrayList();
	
	 
	 
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		this.setBackground(Color.BLACK);
		g.setColor(new Color(51,153,255));
		g.setFont(new Font("Arial",Font.PLAIN, 18));
		g.drawString("S&P500",3,34);
	
		
		
	
	}//end of paintComponent
}
