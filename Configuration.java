/**
*    @author Mohammad Ullah
*/
public class Configuration {
    
    /**
     * ROWS int
     * COLS int
     * CELL_SIZE int
     * MINES int
     * BOARD_WIDTH int
     * BOARD_HEIGHT int
     * STATUS_COVERED String
     * STATUS_OPENED String
     * STATUS_MARKED String
     * STATUS_WRONGLY_MARKED String
     */
    public static int ROWS;
    public static int COLS;
    public static int CELL_SIZE;
    public static int MINES;
    public static int BOARD_WIDTH;
    public static int BOARD_HEIGHT;
    public static String STATUS_COVERED;
    public static String STATUS_OPENED;
    public static String STATUS_MARKED;
    public static String STATUS_WRONGLY_MARKED;


    /**
     * This method assigns values to attributes based on class
     * Loops through all lines of file and assigns repspective values
     * 
     * @param filename that has the values in order to create the configs
     */
    public static void loadParameters(){
        try{                
            ROWS = 20;
                    
            COLS = 20;

            CELL_SIZE = 15;

            MINES = 50;

            STATUS_COVERED = "cover";

            STATUS_OPENED = "open";

            STATUS_MARKED = "mark";

            STATUS_WRONGLY_MARKED = "wrong_mark"; 
            
            BOARD_HEIGHT = ROWS* CELL_SIZE + 1;
            BOARD_WIDTH = COLS * CELL_SIZE + 1;

        }
        catch(Exception e){}
    }    
}
