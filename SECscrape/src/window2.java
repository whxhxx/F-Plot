import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.awt.event.*;
import java.io.IOException;import java.util.ArrayList;


public class window2 extends JFrame{
	JButton button1;
	JTextField textField1;
	JTextArea textArea1;
	int buttonClicked;
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new window2();

	}

	public window2(){
		
		//------------------------------window set
		this.setSize(800,500);
		Toolkit tk= Toolkit.getDefaultToolkit();
		Dimension dim= tk.getScreenSize();
		int xPos =(dim.width/2)-(this.getWidth()/2);
		int yPos =(dim.height/2)-(this.getHeight()/2);
		this.setLocation(xPos, yPos);     // set location
		//this.setLocationRelativeTo(null); 
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // X to exit
		this.setTitle("lesson 21");
		
		
		JPanel thePanel = new JPanel();
		//----------------------------------widgets
		
		/*
		JLabel label1 = new JLabel("text1");  // label : label1
		label1.setLocation(140, 20);
		thePanel.add(label1);  
		*/
		/*
		JTextField textField1 = new JTextField("Type here",15); 	// text field : textField1
		textField1.setLocation(140, 70);
		LForKeys l1 = new LForKeys();
		textField1.addKeyListener(l1);
		thePanel.add(textField1);
		*/
		
		
		JTextArea textArea1 = new JTextArea(24,60);					//Text Area : textArea1
		textArea1.setText("");
		textArea1.setLocation(2, 60);
		textArea1.setLineWrap(true);
		textArea1.setWrapStyleWord(true);
		textArea1.append("");								// add the text Area to the scroll pane: scrollbar1
		JScrollPane scrollbar1= new JScrollPane(textArea1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		thePanel.add(scrollbar1);
		
		
		JButton button1 = new JButton("Click Here");
		button1.setLocation(140, 200);
		button1.addActionListener(ae -> {
			ArrayList list = new ArrayList();
//			label1.setText(textField1.getText());
			try {
				 list.addAll(getSEC());
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list.forEach(e-> {textArea1.append(e.toString()+"\n");});
			
			
		});							 // action listener on button : button1
		thePanel.add(button1);
		
		
		
		this.add(thePanel);
		
		
	
		
		
		
		this.setVisible(true);
	}// end of 21
	
	
	
	private ArrayList getSEC() throws IOException {

		String YEAR = "2015";
		Document rssPage = Jsoup.connect("http://www.sec.gov/cgi-bin/browse-edgar?action=getcurrent&type=sc%2013&company=&dateb=&owner=include&start=0&count=40&output=atom").get();
		ArrayList re1 = new ArrayList();
		ArrayList re2 = new ArrayList();
		ArrayList re3 = new ArrayList();
		// titles
		Elements titles = rssPage.getElementsByTag("title"); // head
		titles.remove(0);
		titles.forEach(e ->{
			String eToString = e.toString();
			System.out.println(eToString.substring(eToString.lastIndexOf("-")+1 , eToString.lastIndexOf("</title>")));
			re1.add(eToString.substring(eToString.lastIndexOf("-")+1 , eToString.lastIndexOf("</title>")) +"\r");
		});// end of forEach()
		
		
		//updated time
		Elements updatedTimes = rssPage.getElementsByTag("updated"); // head
		updatedTimes.remove(0);
		updatedTimes.forEach(e ->{
			String eToString = e.toString();
			re2.add(eToString.substring(eToString.lastIndexOf(YEAR) , eToString.lastIndexOf("-04:00")).replaceAll("T", " "));
//		System.out.println(eToString.substring(eToString.lastIndexOf(YEAR) , eToString.lastIndexOf("-04:00")).replaceAll("T", " "));
		});
		
		for(int i = 0; i < re1.size(); i++){
			re3.add( re2.get(i)+"              "+re1.get(i));  //每一行显示
			
		}
		
		
		 // link1
		Elements htmlPages = rssPage.getElementsByAttribute("href");
		htmlPages.remove(0);
		htmlPages.remove(0);
		htmlPages.forEach(e ->{
			String eToString = e.toString();
//			System.out.println(eToString.substring(eToString.lastIndexOf("href=\"") + 6  , eToString.lastIndexOf("\" />")));
			
			try {
				findFile1(eToString.substring(eToString.lastIndexOf("href=\"") + 6  , eToString.lastIndexOf("\" />")));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		
	
		return re3;
	}


	private static void findFile1(String htmlLink) throws IOException {
		// TODO Auto-generated method stub

		Document htmlPage = Jsoup.connect(htmlLink).get();
		Elements tableFile = htmlPage.getElementsByClass("tableFile");
		String fileLink_String = tableFile.select("td").get(2).toString();
		String fileLink = "www.sec.gov" + fileLink_String.substring(fileLink_String.lastIndexOf("href=\"") + 6  , fileLink_String.lastIndexOf("\">"));
		System.out.println(fileLink);
		
		//System.out.println(text);
	

	
	}

	private class LForKeys implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
	
			//	System.out.printf("key:"+e.getKeyChar()+"\n");
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}}

}



