
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;


public class CornerBlockRight extends JPanel 
{
	ArrayList<Double> data1 = new ArrayList();
	
	 
	 
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		this.setBackground(Color.BLACK);
		g.setColor(new Color(30,156,63));
		g.setFont(new Font("Arial",Font.PLAIN, 18));
		g.drawString("BPFI",5,34);
	
	
	
		
		
	
	}//end of paintComponent
}
