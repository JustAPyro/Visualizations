import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class TextManager
{

    Canvas canvas;
    GraphicsContext gc;

    double width, height;

    ArrayList<String> lines = new ArrayList<String>();
    ArrayList<Integer> indent = new ArrayList<Integer>();
    ArrayList<Integer> index = new ArrayList<Integer>();

    public TextManager(Canvas canvas)
    {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();

        width = canvas.getWidth();
        height = canvas.getHeight();

        //gc.setFont(new Font("Ariel", 10));
    }

    public void addText(int index, int indent, String str)
    {
        lines.add(str);
        this.indent.add(indent);
        this.index.add(index);
    }

    public void draw()
    {
        gc.strokeRect(0, 0, width, height);

        for (int i = 0; i < lines.size(); i++)
        {
            gc.fillText(lines.get(i), 25+20*indent.get(i), 20*i+25);
        }
    }


}
