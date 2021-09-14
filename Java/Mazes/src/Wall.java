/**
 * This class represents a specific wall to be used by maze structure
 */
public class Wall
{

    char o;
    int x, y;

    // Each wall can be represented by an orientation and and x, y coordinate
    public Wall(char o, int x, int y)
    {
        // Save provided variables
        this.o = o;
        this.x = x;
        this.y = y;
    }

}
