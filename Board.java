import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Image;

/**
 * @author
 */
public class Board extends JPanel
{
	/**
	 * height  int  represents height of board
	 * width   int  represents width of board
	 * numMines  int  represents how many mines are on the board
	 * statusbar  JLabel  Used to create and modify the status label
	 * minefield  Minefiled  Used to do the calculations and draw
	 * gameOver  boolean  represents if game is over or not
	 * lost  boolean   used to represent if the user has won or lost game
	 */
	private int height;
	private int width;
	private int numMines;
	private JLabel statusbar;
	private Minefield minefield;
	private boolean gameOver;
	private boolean lost;
	

	/**
	 * Constructor
	 * @param height   height
	 * @param width	   width
	 * @param mines    number of mines
	 * @param statusbar   used for statusbar
	 */
	public Board(int height, int width, int mines, JLabel statusbar)
	{
		this.height = height;
		this.width = width; //Initialize instance varibles
		this.numMines = mines;
		this.statusbar = statusbar;
		lost = false;

		minefield = new Minefield(this.height, this.width, mines); //Create new Minefield object
		minefield.mineLaying(mines);
		minefield.addInfoCells();

		setPreferredSize(new Dimension(Configuration.BOARD_WIDTH, Configuration.BOARD_HEIGHT));
		addMouseListener(new MouseReader(this));
	}


	/**
	 * Calls draw method of minefield to draw up each indiviudal icon
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		minefield.draw(g);
	}

	/**
	 * Get method to get the minefield
	 * @return minefield
	 */
	public Minefield getMinefield(){
		return minefield;
	}

	/**
	 * Adjusts status bar text
	 * @param text  that shows up on status bar
	 */
	public void setStatusbar(String text){
		this.statusbar.setText(text);
	}

	/**
	 * 
	 * @return String  what the statusbar is displaying
	 */
	public String getStatusbar(){
		return this.statusbar.getText();
	}

	/**
	 * @return true or false depending on if the number of mines is 0
	 */
	public boolean removeMine(){
		if(numMines > 0){ //If it is greater than zero
			numMines -= 1;
			setStatusbar(numMines + " mines remaining");
			return true;
		}
		else{//If player tries to remove mine when number is 0
			setStatusbar("Invalid Action");
			return false;
		}
	}

	/**
	 * If they try to remove flags when no flags are present return false
	 * @return true or false depending on action is valid 
	 */
	public boolean addMine(){
		if(numMines == Configuration.MINES){//Invalid action 
			setStatusbar("Invalid action");
			return false;
		}
		else{ //add to numMines and update statusbar 
			numMines++;
			setStatusbar(numMines + " mines remaining");
			return true;
		}
	}

	/**
	 * 
	 * @return  getter method that returns true if game is over
	 */
	public boolean isGameOver(){
		//return true and win if all info cells are opened
		if(allInfoOpen()){
			lost = false;
			return true;
		}

		return gameOver;
	}
	/**
	 * Helper function used in isGameOver, determines if all infoCells are uncovered
	 * @return  boolean  True if all infoCells are opened, else false
	 */
	private boolean allInfoOpen(){
		InfoCell info;

		for(int i = 0; i < Configuration.ROWS; i++){
			for(int j = 0; j < Configuration.COLS; j++){

				if(minefield.getCellByRowCol(i, j) instanceof InfoCell){
					info = (InfoCell)minefield.getCellByRowCol(i, j);
					
					if( !(info.getStatus().equals(Configuration.STATUS_OPENED)) ){
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Does the correct action when a user clicks a cell
	 * @param x  x coordinate of clikc
	 * @param y  y coordinate of click
	 * @param button  Which button was clicked
	 */
	public void mouseClickOnLocation(int x, int y, String button)
	{
		if(this.isGameOver()){ //Check if game over after every click
			minefield.revealIncorrectMarkedCells();
			repaint();
			if(lost){
				setStatusbar("Game over - You lost!");
			}
			else{
				setStatusbar("Game over - You won!");
			}
		}

		else{
			Object cell = minefield.getCellByScreenCoordinates(x,y); 
			int row = y / Configuration.CELL_SIZE;
			int col = x / Configuration.CELL_SIZE;
			InfoCell info;
			MineCell mine;
			
			if(button == "left"){   //If left click
				if(cell instanceof MineCell){ //If they click a mine
					mine = (MineCell)cell;
					if(mine.getStatus().equals(Configuration.STATUS_COVERED)){
						mine.setStatus(Configuration.STATUS_OPENED);
						minefield.setMineCell(row, col, mine);
						gameOver = true;
						lost = true;
					}
				}
				else{ 
					info = (InfoCell)cell;
					if(info.getStatus().equals(Configuration.STATUS_COVERED)){
						info.setStatus(Configuration.STATUS_OPENED);
						if(info.getNumOfAdjacentMines() == 0){ //No adjacent mines
							minefield.openCells(cell);
						} 
						minefield.setInfoCell(row, col, info);
					}
				}
			}	
			
			else if(button == "right"){//If right click

				if(cell instanceof InfoCell){ //If Cell is infoCell
					info = (InfoCell)cell;
					if(info.getStatus().equals(Configuration.STATUS_MARKED)){//If marked alread, unmark
						if(addMine()){
							info.setStatus(Configuration.STATUS_COVERED);
							minefield.setInfoCell(row, col, info);
						}
					}

					else{ //Otherwise mark cell
						if(removeMine()){
							info.setStatus(Configuration.STATUS_MARKED);
							minefield.setInfoCell(row, col, info);
						}
						
					}
				}

				else if(cell instanceof MineCell){ //If cell is mine
					mine = (MineCell)cell;
					if(mine.getStatus().equals(Configuration.STATUS_MARKED)){//Unmark if already mark
						addMine();
						mine.setStatus(Configuration.STATUS_COVERED);
						minefield.setMineCell(row, col, mine);
					}
					else{//Otherwise mark it
						removeMine();
						mine.setStatus(Configuration.STATUS_MARKED);
						minefield.setMineCell(row, col, mine);
					}
				}
			}	
			if(this.isGameOver()){ //Check if game over after every click
			minefield.revealIncorrectMarkedCells();
			repaint();
			if(lost){
				setStatusbar("Game over - You lost!");
			}
			else{
				setStatusbar("Game over - You won!");
			}
		}
		}
		repaint();
		
	}
}
