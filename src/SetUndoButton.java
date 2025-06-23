package lifegame;
import javax.swing.JButton;

public class SetUndoButton implements BoardListener{
	
	private JButton undo;
	
	public SetUndoButton(JButton b) {
		undo = b;
	}

	public void updated(BoardModel model) {
		undo.setEnabled(model.isUndoable());
	}
}
