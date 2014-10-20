//import src.Token;
import java.awt.Point;

public class ULPoint implements Yytoken {

    //Attributes
    private Token token;
    private Point value;

    //Methods
    public ULPoint(int valueX, int valueY) {
	this.token = Token.point;
	this.value = new Point(valueX, valueY);
    }

    public Token getToken() {
	return this.token;
    }

    public Object getValue() {
	return this.value;
    }

    public String toString() {
	return "ULPoint, getToken()=" + this.getToken() + " getValue()=" + this.getValue();
    }
}
