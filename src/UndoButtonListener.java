package lifegame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UndoButtonListener implements ActionListener{

	private BoardModel model;
	
	public UndoButtonListener(BoardModel b) {
		model = b;
	}
	
	public void actionPerformed(ActionEvent e) {
		model.undo();
	}
	
}
