package lifegame;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

public class BoardView extends JPanel implements BoardListener, MouseListener, MouseMotionListener{
	
	private BoardModel model;
	private int panelWidth = 0;
	private int panelHeight = 0;
	private int boardWidth;
	private int boardHeight;
	private int cellSize;
	private int[] xEdge;		//セルの左端の座標
	private int[] yEdge;		//セルの上端の座標
	private int changedX = -1;
	private int changedY = -1;
	
	public BoardView(BoardModel b) {
		model = b;
		this.addMouseListener(this);
        this.addMouseMotionListener(this);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		if((panelWidth != getWidth()-1) || (panelHeight != getHeight()-1)) {
			panelWidth = getWidth()-1;
			panelHeight = getHeight()-1;
			setBoard(g);
		}
		 makeBoard(g);
	}
	
	private void setBoard(Graphics g) {
		xEdge = new int[model.getCols()+1];
		yEdge = new int[model.getRows()+1];
		
		if(panelWidth/model.getCols() > panelHeight/model.getRows()) {
			cellSize = panelHeight/model.getRows()-2;
		} else {
			cellSize = panelWidth/model.getCols()-2;
		}
		
		boardWidth = (cellSize+2) * model.getCols();
		boardHeight = (cellSize+2) * model.getRows();
		
		xEdge[0] = (panelWidth - boardWidth) / 2 + 1;
		yEdge[0] = (panelHeight - boardHeight) / 2 + 1;
		for(int i = 1; i <= model.getCols(); i++) {
			xEdge[i] = xEdge[i-1] + cellSize +2;
		}
		
		for(int i = 1; i <= model.getRows(); i++) {
			yEdge[i] = yEdge[i-1] + cellSize +2;
		}
	}
	
	private void makeBoard(Graphics g) {
		for(int i = 0; i <= model.getCols(); i++) {
			g.drawLine(xEdge[i]-1, yEdge[0]-1, xEdge[i]-1, yEdge[0]+boardHeight-1);
		}
		
		for(int i = 0; i <= model.getRows(); i++) {
			g.drawLine(xEdge[0]-1, yEdge[i]-1, xEdge[0]+boardWidth-1, yEdge[i]-1);
		}
		
		for(int i = 0; i < model.getRows(); i++) {
			for(int j = 0; j < model.getCols(); j++) {
				if(model.isAlive(j, i)) {
					g.fillRect(xEdge[j], yEdge[i], cellSize, cellSize);
				}
			}
		}
	}

	private int convertXCoord(int x) {		//ウィンドウ上の座標を渡す
		if(x < xEdge[0]-1 || x >= xEdge[model.getCols()]) {
			return -2;
		} else {
			for(int i = 0; i < model.getCols(); i++) {
				if(xEdge[i] <= x && x <= xEdge[i] + cellSize) {
					return i;
				} else if(x < xEdge[i]) {
					return -1;
				}
			}
			return -1;
		}
	}
	
	private int convertYCoord(int y) {		//ウィンドウ上の座標を渡す
		if(y < yEdge[0]-1 || y >= yEdge[model.getRows()]) {
			return -2;
		} else {
			for(int i = 0; i < model.getRows(); i++) {
				if(yEdge[i] <= y && y <= yEdge[i] + cellSize) {
					return i;
				} else if(y < yEdge[i]) {
					return -1;
				}
			}
			return -1;
		}
	}
	
	public void updated(BoardModel m) {
		repaint();
	}
	
	public void mousePressed(MouseEvent e) {	//マウスのボタンが押された
		changedX = convertXCoord(e.getX());
    	changedY = convertYCoord(e.getY());
    	if(changedX >= 0 && changedY >= 0) {
	    	model.changeCellState(changedX, changedY);
    	}
    }
    
    public void mouseDragged(MouseEvent e) {	//ボタンを押しながらの移動：ドラッグ
    	if((convertXCoord(e.getX()) != changedX || convertYCoord(e.getY()) != changedY) &&
    	convertXCoord(e.getX()) != -1 && convertYCoord(e.getY()) != -1) {
	    	changedX = convertXCoord(e.getX());
	        changedY = convertYCoord(e.getY());
	        if(changedX != -2 && changedY != -2) {
	        	model.changeCellState(changedX, changedY);
	        }
    	}
    }
    
	public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}

}
