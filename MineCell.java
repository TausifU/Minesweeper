import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;

/**
 * @author Mohammad Ullah
 */
public class MineCell {
    /**
     * row int - row for MineCell
     * col int - col for MineCell
     * status String - status of MineCell
     */
    private int row;
    private int col;
    private String status;


    /**
     * Constructor
     * @param row Is the value for the row
     * @param col Is the value for col
     */
    public MineCell(int row, int col){
        this.row = row;
        this.col = col;
    }


    /**
     * @param g Graphic
     * Draws an image representing the status of cell
     */
    public void draw(Graphics g){
        g.drawImage(this.getImage(), this.getHorizontalPosition(), this.getVerticalPosition(), null);
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
     * @return status of cell
     */
    public String getStatus(){
        return this.status;
    }

    /**
     * @param status new value for status field of mineCell
     */
    public void setStatus(String status){
        this.status = status;
    }

    /**
     * Checks status and returns respective image using imageIcon
     * @return Image based on status
     */
    public Image getImage(){
        ImageIcon a;
        Image pic;

        if(this.status.equals(Configuration.STATUS_MARKED) ){  // If mineCell is marked (flagged cell)
            a = new ImageIcon("img/marked_cell.png");
            pic = a.getImage();
        }
        else if(this.status.equals(Configuration.STATUS_OPENED) ){  // If mineCell uncovered(shows mine)
            a = new ImageIcon("img/mine_cell.png");
            pic = a.getImage();
        }
        else{  //If mineCell is covered (grey square)
            a = new ImageIcon("img/covered_cell.png");
            pic = a.getImage();
        }

        return pic;
    }
}
