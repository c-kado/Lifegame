package lifegame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGameListener implements ActionListener{
	
	private Main newGame;

	public void actionPerformed(ActionEvent e) {
		newGame = new Main();
		newGame.run();
	}
	
}
