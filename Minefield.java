import java.util.Random;
import java.awt.Graphics;


/**
 * @author Mohammad Ullah
 */
public class Minefield {
    /**
     * field  is Object[][], 2D array of cell objects
     * rows and cols represent number of rows and cols
     * mines are the number of mines to be put on the field
     * i & j are used in loops are declared here to avoid repitition
     * mineList is a array of mines that are put on the field
     */
    private Object[][] field;
    private int rows;
    private int cols;
    private int numMines;
    private int i;
    private int j;


    /**
     * Constructor
     *@param numRows int  number of rows
     *@param numCols int  number of cols
     *@param numMines int  number of mines
     */
    //Default Constructor
    public Minefield(){
        rows = 10;
        cols = 10;
        numMines = 10;
        field = new Object[rows][cols];
    }
    //Constructor
    public Minefield(int numRows, int numCols, int numMines){
        this.rows = numRows;
        this.cols = numCols;
        this.numMines = numMines;
        this.field = new Object[rows][cols];
    }


    /**
     * Place Minecell objects randomly across the board
     * @param numOfMines objects of type type Minecell
     */
    public void mineLaying(int numOfMines){
        Random r = new Random();
        int random1;
        int random2;
        MineCell mine;
        
        for(i=0; i < numOfMines; i++){ //Loop for number of mines needed
            random1 = r.nextInt(rows);
            random2 = r.nextInt(cols);
            
            if(field[random1][random2]==null){//Check if location empty and then add mine
                mine = new MineCell(random1, random2);
                mine.setStatus(Configuration.STATUS_COVERED);
                field[random1][random2] = mine;
            }
            else{ //If not empty, assign new location
                random1 = r.nextInt(rows);
                random2 = r.nextInt(cols);
                mine = new MineCell(random1, random2);
                mine.setStatus(Configuration.STATUS_COVERED);
                field[random1][random2] = mine;
            }
        }
    }

    /**
     * Adds InfoCells to field locations that do not contain a mineCell
     */
    public void addInfoCells(){
        int adjacent;
        InfoCell info;

        for(i = 0; i < field.length; i++){
            for(j = 0; j < field[i].length; j++){
                if(field[i][j] == null){ //add new infoCell if empty location
                    adjacent = numAdjacent(i, j);
                    info = new InfoCell(i,j,adjacent);
                    info.setStatus(Configuration.STATUS_COVERED);
                    field[i][j] = info;
                }
            }
        }
    }

    /**
     * Private helper method used to find number of adjacent mines of a cell
     * @param row  row number of cell
     * @param col  col number of cell
     * @return   How many adjacent mines of a cell
     */
    private int numAdjacent(int row, int col){
        int count = 0;

        //If Cell is in corner
        if(row == 0 && col == 0){ //If top left corner, 
            if(field[row][col+1] instanceof MineCell) count++;
            if(field[row+1][col] instanceof MineCell) count++;
            if(field[row+1][col+1] instanceof MineCell) count++;
            return count;
        }
        else if(row == 0 && col == this.cols-1){ //If top Right corner,
            if(field[row][col-1] instanceof MineCell) count++;
            if(field[row+1][col] instanceof MineCell) count++;
            if(field[row+1][col-1] instanceof MineCell) count++;
            return count;
        }
        else if(row == this.rows-1 && col == 0){ //If bottom left corner, 
            if(field[row][col+1] instanceof MineCell) count++;
            if(field[row-1][col] instanceof MineCell) count++;
            if(field[row-1][col+1] instanceof MineCell) count++;
            return count;
        }
        else if(row == this.rows-1 && col == this.cols-1){// If bottom right corner
            if(field[row][col-1] instanceof MineCell) count++;
            if(field[row-1][col] instanceof MineCell) count++;
            if(field[row-1][col-1] instanceof MineCell) count++;
            return count;
        }
        //If cell is on edge of board
        else if(row == 0){ //Top edge
            if(field[row][col-1] instanceof MineCell) count++;
            if(field[row][col+1] instanceof MineCell) count++;
            if(field[row+1][col-1] instanceof MineCell) count++;
            if(field[row+1][col] instanceof MineCell) count++;
            if(field[row+1][col+1] instanceof MineCell) count++;
            return count;
        }
        else if(row == this.rows-1){ //bottom edge
            if(field[row][col-1] instanceof MineCell) count++;
            if(field[row][col+1] instanceof MineCell) count++;
            if(field[row-1][col-1] instanceof MineCell) count++;
            if(field[row-1][col] instanceof MineCell) count++;
            if(field[row-1][col+1] instanceof MineCell) count++;
            return count;
        }
        else if(col == 0){ //left edge
            if(field[row-1][col] instanceof MineCell) count++;
            if(field[row+1][col] instanceof MineCell) count++;
            if(field[row-1][col+1] instanceof MineCell) count++;
            if(field[row][col+1] instanceof MineCell) count++;
            if(field[row+1][col+1] instanceof MineCell) count++;
            return count;
        }
        else if(col == this.cols-1){ //Right edge
            if(field[row-1][col] instanceof MineCell) count++;
            if(field[row+1][col-1] instanceof MineCell) count++;
            if(field[row-1][col-1] instanceof MineCell) count++;
            if(field[row][col-1] instanceof MineCell) count++;
            if(field[row+1][col] instanceof MineCell) count++;
            return count;
        }
        //If cell is anywhere else on board
        else{
            if(field[row-1][col-1] instanceof MineCell) count++;
            if(field[row][col-1] instanceof MineCell) count++;
            if(field[row+1][col-1] instanceof MineCell) count++;
            if(field[row-1][col] instanceof MineCell) count++;
            if(field[row+1][col] instanceof MineCell) count++;
            if(field[row-1][col+1] instanceof MineCell) count++;
            if(field[row][col+1] instanceof MineCell) count++;
            if(field[row+1][col+1] instanceof MineCell) count++;
            return count;
        }
    }

    /**
     * Traverse whole minefield and invokes draw method of each object in field
     * @param g Graphics, helps draw minefield
     */
    public void draw(Graphics g){
        MineCell temp;
        InfoCell temp2;

        for(i = 0; i < field.length; i++){
            for(j = 0; j < field[i].length; j++){ //Loop through field[][] array

                if(field[i][j] instanceof MineCell){ //Check and draw if obj is MineCell
                    temp = (MineCell)field[i][j];
                    temp.draw(g);
                }
                else{ //Check and draw if obj is InfoCell
                    temp2 = (InfoCell)field[i][j];
                    temp2.draw(g);
                }
            }
        }
    }

    /**
     * Setter method
     * @param row
     * @param col
     * @param cell
     */
    public void setMineCell(int row, int col, MineCell cell){
        field[row][col] = cell;
    }

    /**
     * Sets an info cell
     * @param row   row of info cell
     * @param col   col of info cell
     * @param cell  infoCell
     */
    public void setInfoCell(int row, int col, InfoCell cell){
        field[row][col] = cell;
    }

    /**
     * @param row row of cell
     * @param col col of cell
     * @return the cell at specified location
     */
    public Object getCellByRowCol(int row, int col){
        MineCell mine;
        InfoCell info;

        if(field[row][col] instanceof MineCell ){
            mine = (MineCell)field[row][col];
            return mine;
        }
        else{
            info = (InfoCell)field[row][col];
            return info;
        }
    }

    /**
     * Return cell that would be at screen coordinate
     * @param x  coordinate of the cell
     * @param y  coordinate of the cell
     * @return  cell that would be at that coordinate
     */
    public Object getCellByScreenCoordinates(int x, int y){
        int row  = y / Configuration.CELL_SIZE;
        int col = x / Configuration.CELL_SIZE;
        MineCell mine;
        InfoCell info;

        if(field[row][col] instanceof MineCell){
            mine = (MineCell)field[row][col];
            return mine;
        }
        else{
            info = (InfoCell)field[row][col];
            return info;
        }
    }

    /**
     * Loop through array and check each cell's status and to count if same
     * @param status Check how many cells have same status
     * @return  Amount of cells with specific status
     */
    public int countCellsWithStatus(String status){
        int count = 0;
        String cellStatus;
        MineCell temp;
        InfoCell temp2;

        for(i = 0; i < field.length; i++){ //loop through field
            for(j = 0; j < field[i].length; j++){
                if(field[i][j] instanceof MineCell){//If MineCell check status
                    temp = (MineCell)field[i][j];
                    cellStatus = temp.getStatus();
                    if(status == cellStatus) count++; //add to count
                }
                else{ //Else infoCell
                    temp2 = (InfoCell)field[i][j];
                    cellStatus = temp2.getStatus();
                    if(status == cellStatus) count++; //Check aand add to count
                }
            }
        }
        return count;
    }

    /**
     * Open the adjacent cells if all cells do not contain a mine
     * @param cell  is the cell used in method
     */
    public void openCells(Object cell){
        InfoCell temp;
        int row = 0;
        int col = 0;

        for(i = 0; i < field.length; i++){  //traverse through array to find index of cell
            for(j = 0; j < field[i].length; j++){
                if(field[i][j] == cell){
                    row = i;
                    col = j;
                    break;
                }
            }
        }

        if(cell instanceof InfoCell){ //Checks not mine
            temp = (InfoCell)cell;
            if(temp.getNumOfAdjacentMines() == 0){ //Check if 0 mines around it
                //Change the status to open of the cells around the cell
                //toprow
                try{
                    if(((InfoCell)field[row-1][col]).getStatus().equals(Configuration.STATUS_COVERED)){
                    ((InfoCell)field[row-1][col]).setStatus(Configuration.STATUS_OPENED);}
                }
                catch(Exception e){}

                try{
                    if(((InfoCell)field[row-1][col-1]).getStatus().equals(Configuration.STATUS_COVERED)){
                    ((InfoCell)field[row-1][col-1]).setStatus(Configuration.STATUS_OPENED);}
                }
                catch(Exception e){}

                try{
                    if(((InfoCell)field[row-1][col+1]).getStatus().equals(Configuration.STATUS_COVERED)){
                    ((InfoCell)field[row-1][col+1]).setStatus(Configuration.STATUS_OPENED);}
                }
                catch(Exception e){}


                //same row
                try{
                    if(((InfoCell)field[row][col+1]).getStatus().equals(Configuration.STATUS_COVERED)){
                    ((InfoCell)field[row][col+1]).setStatus(Configuration.STATUS_OPENED);}
                }
                catch(Exception e){}

                try{
                    if(((InfoCell)field[row][col-1]).getStatus().equals(Configuration.STATUS_COVERED)){
                    ((InfoCell)field[row][col-1]).setStatus(Configuration.STATUS_OPENED);}
                }
                catch(Exception e){}


                //row below
                try{
                    if(((InfoCell)field[row+1][col]).getStatus().equals(Configuration.STATUS_COVERED)){
                    ((InfoCell)field[row+1][col]).setStatus(Configuration.STATUS_OPENED);}
                }
                catch(Exception e){}

                try{
                    if(((InfoCell)field[row+1][col-1]).getStatus().equals(Configuration.STATUS_COVERED)){
                    ((InfoCell)field[row+1][col-1]).setStatus(Configuration.STATUS_OPENED);}
                }
                catch(Exception e){}

                try{
                    if(((InfoCell)field[row+1][col+1]).getStatus().equals(Configuration.STATUS_COVERED)){
                    ((InfoCell)field[row+1][col+1]).setStatus(Configuration.STATUS_OPENED);}
                }
                catch(Exception e){} 
            }
        }
    }

    /**
     * Change the status of infoCells marked as mines to wrongly marked
     * after the game is over
     */
    public void revealIncorrectMarkedCells(){
        InfoCell cell;

        for(i = 0; i < field.length; i++){  
            for(j = 0; j < field[i].length; j++){ // Loop through array

                if(field[i][j] instanceof InfoCell){
                    cell = (InfoCell)field[i][j];
                    if(cell.getStatus().equals(Configuration.STATUS_MARKED)){ //If Cell is marked incorrectly
                        cell.setStatus(Configuration.STATUS_WRONGLY_MARKED);
                        field[i][j] = cell;
                    }
                }
            }
        }
    }  
}
