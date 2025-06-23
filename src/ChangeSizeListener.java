package lifegame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ChangeSizeListener implements ActionListener{
	
	private JDialog dialog;
	private JTextField colsText;
	private JTextField rowsText;
	public int cols = 12;
	public int rows = 12;

	public ChangeSizeListener(JDialog d, JTextField c, JTextField r){
		dialog = d;
		colsText = c;
		rowsText = r;
	}
	
	public void actionPerformed(ActionEvent e) {
		cols = Integer.parseInt(colsText.getText());
		rows = Integer.parseInt(rowsText.getText());
		if(5 <= cols && cols <= 50 && 5 <= rows && rows <= 50) {
			dialog.dispose();
		} else {
			cols = 12;
			rows = 12;
		}
	}
}
