import java.awt.Point;
import drawing.DrawingFrame;

public class FenetreJade {
    
    //Attributes
    private boolean crayonBaisse;
    private int pas;
    private DrawingFrame frame;

    //Methods
    public FenetreJade() throws drawing.DrawingException {
	this.crayonBaisse = true;
	this.pas = 10;
	this.frame = new DrawingFrame();
	this.frame.goTo(new Point(0,0));
    }

    public void nord() throws drawing.DrawingException {
	Point or = this.frame.getCurrentPoint();
	if(or.y >= this.pas)
	    { 
		if(this.crayonBaisse) 
		    this.frame.drawTo(new Point(or.x, or.y - this.pas));
		else
		    this.frame.goTo(new Point(or.x, or.y - this.pas));
	    }
    }

    public void sud() throws drawing.DrawingException {
	Point or = this.frame.getCurrentPoint();
	if(or.y + this.pas <= this.frame.getHeight())
	    {
		if(this.crayonBaisse)
		    this.frame.drawTo(new Point(or.x, or.y + this.pas));
		else
		    this.frame.goTo(new Point(or.x, or.y + this.pas));
	    }
    }

    public void est() throws drawing.DrawingException {
	Point or = this.frame.getCurrentPoint();
	if(or.x + this.pas <= this.frame.getWidth())
	    {
		if(this.crayonBaisse) 
		    this.frame.drawTo(new Point(or.x + this.pas, or.y));
		else
		    this.frame.goTo(new Point(or.x + this.pas, or.y));
	    }
    }

    public void ouest() throws drawing.DrawingException {
	Point or = this.frame.getCurrentPoint();
	if(or.x >= this.pas)
	    {
		if(this.crayonBaisse)
		    this.frame.drawTo(new Point(or.x - this.pas, or.y));
		else
		    this.frame.goTo(new Point(or.x - this.pas, or.y));    
	    }
    }
    public void origine(Point p) throws drawing.DrawingException {
	this.frame.goTo(p);
    }

    public void lever() throws drawing.DrawingException {
	this.crayonBaisse = false;
    }

    public void baisser() {
	this.crayonBaisse = true;
    }

    public void pas(int p) {
	this.pas = p;
    }

}
