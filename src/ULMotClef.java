public class ULMotClef implements Yytoken {
    
    //Attributes
    private Token token;

    //Methods

    public ULMotClef(Token token) {
	this.token = token;
    }

    public Token getToken() {
	return this.token;
    }

    public Object getValue() {
	return null;
    }

    public String toString() {
	return "ULMotClef, getToken()=" + this.getToken() + " getValue()=" + this.getValue();
    }
}
