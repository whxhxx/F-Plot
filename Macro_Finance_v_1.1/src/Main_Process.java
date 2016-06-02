import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main_Process 
{

 
    public static void addComponentsToPane(Container pane) 
    {
    
    //pane 
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
	CornerBlock cb1 = new CornerBlock();
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
	CornerBlock cb2 = new CornerBlock();
	c2.fill = GridBagConstraints.BOTH;
	c2.weightx = 1;
	c2.weighty = 1;
	c2.gridwidth = GridBagConstraints.REMAINDER;
	pane.add(cb2, c2);

    }// end of pane function

	
	

    public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("GridBagLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        Toolkit tk= Toolkit.getDefaultToolkit();
        Dimension dim= tk.getScreenSize();
		int xPos =(dim.width/2)-(frame.getWidth()/2);
		int yPos =(dim.height/2)-(frame.getHeight()/2);
		frame.setLocation(xPos, yPos);     // set location
		addComponentsToPane(frame.getContentPane());
		//Set up the content pane.
		
		new Refresher(frame);
		
        frame.setVisible(true);
    }
   

    public static void main(String[] args) 
    {
        javax.swing.SwingUtilities.invokeLater
        (
        		new Runnable() 
        		{
        			public void run() 
        			{
        				createAndShowGUI();
        			}
        		}
        );
    }
}

