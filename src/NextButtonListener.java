package lifegame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NextButtonListener implements ActionListener{
	
	private BoardModel model;
	
	public NextButtonListener(BoardModel b) {
		model = b;
	}
	
	public void actionPerformed(ActionEvent e) {
		model.next();
	}
	
}
