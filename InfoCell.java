import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;

/**
 * @author Mohammad Ullah
 */
public class InfoCell {
    /**
     * row int  row of the cell
     * col  int  col of the cell 
     * status  string  status of cell
     * numMines  int  number of adjacent mines*/ 
    private int row;
    private int col;
    private String status;
    private int numMines;

    /**
     * Constructor
     * @param row  row of the cell
     * @param col  col of the cell
     * @param numOfAdjacentMines  number of adjacentmines
     */
    public InfoCell(int row, int col, int numOfAdjacentMines){
        this.row = row;
        this.col = col;
        this.numMines = numOfAdjacentMines;
    }

    /**
     * 
     * @return Give the status back
     */
    public String getStatus(){
        return this.status;
    }

    /**
     * 
     * @param status  changes the value of cell status
     */
    public void setStatus(String status){
        this.status = status;
    }

    /**
     * @return horizontal pixel value of mine,
     */
    public int getHorizontalPosition(){
        return (col)*Configuration.CELL_SIZE;
    }

    /**
     * @return Vertical positon of mine, would be row field
     */
    public int getVerticalPosition(){
        return (row)*Configuration.CELL_SIZE;
    }

    /**
     * @return int number of adjacent mines
     */
    public int getNumOfAdjacentMines(){
        return this.numMines;
    }

    /**
     * 
     * @param g used to draw image
     */
    public void draw(Graphics g){
        g.drawImage(this.getImage(), this.getHorizontalPosition(), this.getVerticalPosition(), null);
    }

    /**
     * @return Image based on status and adjacent mines around cell
     */
    public Image getImage()
    {
        ImageIcon a;
        Image pic;

        if(this.getStatus().equals(Configuration.STATUS_COVERED) ){ //Covered
            a = new ImageIcon("img/covered_cell.png");
            pic = a.getImage();
        }

        else if(this.getStatus().equals(Configuration.STATUS_MARKED) ){ //Marked
            a = new ImageIcon("img/marked_cell.png");
            pic = a.getImage();
        }
        
        else if(this.getStatus().equals(Configuration.STATUS_WRONGLY_MARKED) ){//wrongly marked
            a = new ImageIcon("img/wrong_mark.png");
            pic = a.getImage();

        }
        
        else{ //Opened, assign icon based on how many mines 
            switch(numMines)
            {
                case 0: a = new ImageIcon("img/info_0.png"); //0 mines
                pic = a.getImage();
                break;

                case 1: a = new ImageIcon("img/info_1.png"); //1
                pic = a.getImage();
                break;

                case 2: a = new ImageIcon("img/info_2.png");//2
                pic = a.getImage();
                break;

                case 3: a = new ImageIcon("img/info_3.png");//3
                pic = a.getImage();
                break;

                case 4: a = new ImageIcon("img/info_4.png");//4
                pic = a.getImage();
                break;

                case 5: a = new ImageIcon("img/info_5.png");//5
                pic = a.getImage();
                break;

                case 6: a = new ImageIcon("img/info_6.png");//6
                pic = a.getImage();
                break;

                case 7: a = new ImageIcon("img/info_7.png");//7
                pic = a.getImage();
                break;

                case 8: a = new ImageIcon("img/info_8.png");//8
                pic = a.getImage();
                break;

                default: a = new ImageIcon("img/covered_cell.png"); //default
                pic = a.getImage();
                break;
            }
        }
        return pic;
    }
}
