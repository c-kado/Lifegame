package lifegame;
import java.awt.event.*;
import javax.swing.*;

public class AutoButtonListener implements ActionListener{
	
	private BoardModel model;
	private JFrame frame;
	private JButton button;
	
	public AutoButtonListener(BoardModel m, JFrame f, JButton b) {
		model = m;
		frame = f;
		button = b;
	}

	public void actionPerformed(ActionEvent e){
		Auto auto = new Auto(model);
		Thread thread = new Thread(auto);
		frame.addWindowListener(auto);
		button.setEnabled(false);
		
		thread.start();
	}
}

class Auto implements Runnable, WindowListener{
	
	private BoardModel model;
	private boolean running = true;
	
	public Auto(BoardModel m) {
		model = m;
	}
	
	public void run() {
		while(running) {
			try{
				Thread.sleep(500);
				model.next();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	

	public void windowClosed(WindowEvent e) {
		running = false;
		//ウィンドウが閉じられた時に，スレッドも終了する．
		//runメソッド内のwhile文を終了させてメソッドを終了する．
	}
	
	public void windowOpened(WindowEvent e) {}
	public void windowClosing(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
}