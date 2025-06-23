package lifegame;
import java.util.ArrayList;

public class BoardModel{
	
	private ArrayList<BoardListener> listeners;
	private ArrayList<boolean[][]> history;
	private int cols;	//列(x軸)
	private int rows;	//行(y軸)
	private boolean[][] cells;
	
	public BoardModel(int c, int r) {
		cols = c;
		rows = r;
		cells = new boolean[rows+2][cols+2];	//ボード外のセルを死んだセルとみなすため，ボードの周囲に1セル分余白を作る
		listeners = new ArrayList<BoardListener>();
		history = new ArrayList<boolean[][]>();
	}
	
	public void addListener(BoardListener listener) {
		listeners.add(listener);
	}
	
	private void fireUpdate() {
		for(BoardListener listener: listeners) {
			listener.updated(this);
		}
	}
	
	public int getCols() {
		return cols;
	}
	
	public int getRows() {
		return rows;
	}

	synchronized public void changeCellState(int x, int y) {
		keepHistory();
		BoardModel updatedBoard = new BoardModel(cols, rows);
		for(int i = 0; i < rows+2; i++) {
			for(int j = 0; j < cols+2; j++) {
				updatedBoard.cells[i][j] = cells[i][j];
			}
		}
		x++; y++;
		if(cells[y][x] == true) {
			updatedBoard.cells[y][x] = false;
		} else {
			updatedBoard.cells[y][x] = true;
		}
		
		this.cells = updatedBoard.cells;
		this.fireUpdate();
	}
	
	synchronized public void next() {
		keepHistory();
		BoardModel nextGeneration = new BoardModel(cols, rows);
		for(int y = 1; y <= rows; y++) {
			for(int x = 1; x <= cols; x++) {
				int live = 0;
				
				for(int i = -1; i <= 1; i++) {		//周囲のセルの生死を確認
					for(int j = -1; j <= 1; j++) {
						if(cells[y+i][x+j] == true) {
							
							live++;
						}
					}
				}
				
				if(cells[y][x] == true) {	//注目セルの生死で場合わけ
					if(live == 3 || live == 4) {	//自分も含めた周囲の生きているセルが3or4個なら生存
						nextGeneration.cells[y][x] = true;
					}
				} else if(live == 3) {	//周囲の生きているセルが3個なら生存
					nextGeneration.cells[y][x] = true;
				}
			}
		}
		
		this.cells = nextGeneration.cells;
		this.fireUpdate();
	}
	
	synchronized public void undo() {
			cells = history.get(history.size()-1);
			history.remove(history.size()-1);
			this.fireUpdate();
	}
	
	private void keepHistory() {	//盤面変更前にこのメソッドを呼び出す．
		if(history.size() == 32) {
			history.remove(0);
		}
		
		history.add(cells);
	}
	
	public boolean isUndoable() {		
		return !(history.isEmpty());
	}
	
	public boolean isAlive(int x, int y) {
		return cells[y+1][x+1];
	}
	
	public void applyFileModel(boolean[][] b) {
		//ファイルを読み込んで，盤面を更新
		keepHistory();
		BoardModel updatedBoard = new BoardModel(cols, rows);
		for(int i = 1; i < rows+1; i++) {
			for(int j = 1; j < cols+1; j++){
				updatedBoard.cells[i][j] = b[i-1][j-1];
			}
		}
		cells = updatedBoard.cells;
		this.fireUpdate();
	}
}