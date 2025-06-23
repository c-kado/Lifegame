package lifegame;
import javax.swing.*;
import java.awt.*;

public class Main implements Runnable{
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Main());
	}
	
	public void run() {
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//盤面のサイズ設定ダイアログ作成
		
		JDialog dialog = new JDialog(frame, "Set board size");
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		dialog.setMinimumSize(new Dimension(300, 100));

		JPanel panelCols = new JPanel();
		JPanel panelRows = new JPanel();
		JPanel panelGetButton = new JPanel();
		JLabel labelCols = new JLabel("横(5~50):");
		JLabel labelRows = new JLabel("縦(5~50):");
		JButton get = new JButton("Get");
		JTextField colsText = new JTextField("0", 5);
		JTextField rowsText = new JTextField("0", 5);
		
		panelCols.add(labelCols, BorderLayout.WEST);
		panelCols.add(colsText, BorderLayout.WEST);
		dialog.add(panelCols, BorderLayout.WEST);
		
		panelRows.add(labelRows, BorderLayout.WEST);
		panelRows.add(rowsText, BorderLayout.WEST);
		dialog.add(panelRows, BorderLayout.EAST);
		
		panelGetButton.add(get, BorderLayout.CENTER);
		dialog.add(panelGetButton, BorderLayout.SOUTH);
		ChangeSizeListener csListener = new ChangeSizeListener(dialog, colsText, rowsText);
		get.addActionListener(csListener);
		
		dialog.pack();
		dialog.setVisible(true);
		
		//盤面作成
		
		BoardModel model = new BoardModel(csListener.cols, csListener.rows);
		
		//ゲーム画面作成
		
		JPanel base = new JPanel();
		frame.setContentPane(base);
		base.setPreferredSize(new Dimension(400, 300));
		frame.setMinimumSize(new Dimension(380, 200));
		
		frame.setTitle("Lifegame");
		
		base.setLayout(new BorderLayout());
		BoardView view = new BoardView(model);
		model.addListener(view);
		base.add(view, BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		base.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new FlowLayout());
		
		JButton nextButton = new JButton("Next");
		nextButton.addActionListener(new NextButtonListener(model));
		buttonPanel.add(nextButton);
		
		JButton undoButton = new JButton("Undo");
		SetUndoButton set = new SetUndoButton(undoButton);
		model.addListener(set);
		undoButton.setEnabled(false);
		undoButton.addActionListener(new UndoButtonListener(model));
		buttonPanel.add(undoButton);
		
		JButton newGameButton = new JButton("New Game");
		newGameButton.addActionListener(new NewGameListener());
		buttonPanel.add(newGameButton);
		
		JButton autoButton = new JButton("Auto");
		autoButton.addActionListener(new AutoButtonListener(model, frame, autoButton));
		buttonPanel.add(autoButton);
		
		//メニューバー用のクラス作る
		new MakeMenuBar(frame, model);		
		
		frame.pack();
		frame.setVisible(true);
	}
	
}
