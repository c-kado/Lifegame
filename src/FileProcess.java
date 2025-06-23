package lifegame;

import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class FileProcess implements ActionListener{
	private String fileName;
	private String fileOpe;
	private JTextField inputText;
	private BoardModel model;
	private JDialog dialog;
	private File file;
	private FileReader reader = null;
	public boolean[][] fileModel;
	private int ch;
	
	public FileProcess(String fo, JTextField tf, BoardModel m, JDialog d) {
		fileOpe = fo;
		inputText = tf;
		model = m;
		dialog = d;
		fileModel = new boolean[model.getRows()][model.getCols()];
	}
	
	private void saveAs() {
		System.out.println(fileName);
	}
	
	private void openFile() {
		file = new File(fileName);
		try {
			reader = new FileReader(file);
			if(!(fileName.endsWith(".txt"))) {
				throw new Exc("format error");
			}
			
			for(int i = 0; i < model.getRows(); i++) {
				for(int j = 0; j < model.getCols(); j++) {
					if((ch = reader.read()) == '*') {
						fileModel[i][j] = true;
					} else if(ch == -1 || ch == 10) {
						throw new Exc("size error");
					} else if(ch != '-') {
						throw new Exc("character error in row:" + i + ", col:" + j);
					}
				}
				
				if((i < model.getRows()-1 && reader.read() != 10)
				|| (i == model.getRows()-1 && reader.read() != -1)) {
					throw new Exc("size error");
				}
			}
			model.applyFileModel(fileModel);
		} catch(FileNotFoundException fnfe) {
			System.out.println(fnfe);
		} catch(Exc e) {
			System.out.println(e);
		} catch(IOException ioe) {
			System.out.println(ioe);
		} finally {
			try {
				if(reader != null) {
					reader.close();
		        }
				dialog.dispose();
		    } catch (IOException ioe) {
		    	System.out.println(ioe);
		    }
		}
	}
	
	private void readFileName() {
		fileName = inputText.getText();
	}
	
	public void actionPerformed(ActionEvent e){
		readFileName();
		if(fileOpe.contentEquals("Save As")) {
			saveAs();
		} else if(fileOpe.contentEquals("Open")) {
			openFile();
		}
	}
}

class Exc extends Exception{
	public Exc(String msg) {
		super(msg);
	}
}