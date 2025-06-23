package lifegame;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class MakeMenuBar implements ActionListener{

	private JFrame frame;
	private JMenuBar menubar;
	private JMenu fileMenu;
	private JMenuItem saveAsMenuItem;
	private JMenuItem openMenuItem;
	private BoardModel model;
	private JButton fileButton;
	private FileProcess filePro;
	private String fileOpe;

	MakeMenuBar(JFrame f, BoardModel m){
		frame = f;
		model = m;
		menubar = new JMenuBar();
		fileMenu = new JMenu("File");
		menubar.add(fileMenu);
		saveAsMenuItem = new JMenuItem("Save As...");
		openMenuItem = new JMenuItem("Open");
		fileMenu.add(saveAsMenuItem);
		fileMenu.add(openMenuItem);
		
		saveAsMenuItem.addActionListener(this);
		openMenuItem.addActionListener(this);
		
		frame.setJMenuBar(menubar);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == saveAsMenuItem) {
			fileOpe = "Save As";
		} else if(e.getSource() == openMenuItem) {
			fileOpe = "Open";
		}
		mkDialog();
	}
	
	private void mkDialog() {
		JDialog dialog = new JDialog(frame, fileOpe);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		dialog.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);

		JPanel inputPanel = new JPanel();
		JPanel getPanel = new JPanel();
		JLabel fileNameLabel = new JLabel("file name:");
		fileButton = new JButton(fileOpe);
		JTextField inputText = new JTextField("file name", 16);

		filePro = new FileProcess(fileOpe, inputText, model, dialog);
		
		inputPanel.add(fileNameLabel, BorderLayout.WEST);
		inputPanel.add(inputText, BorderLayout.EAST);
		dialog.add(inputPanel, BorderLayout.NORTH);
		
		getPanel.add(fileButton, BorderLayout.CENTER);
		dialog.add(getPanel, BorderLayout.SOUTH);
		fileButton.addActionListener(filePro);
		
		dialog.pack();
		dialog.setVisible(true);
	}
}
