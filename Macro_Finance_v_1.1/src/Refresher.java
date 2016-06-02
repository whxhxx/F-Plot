import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Refresher {
//    	Refresher(final Container jframe) {}

		public Refresher(Container pane) {
    		new Thread(new Runnable() {

				@Override
				public void run() {
					
					while (true) {
						try {
							Thread.sleep(30);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						pane.repaint();

					}
				}
    			
    		}).start();
    	}

		
    }