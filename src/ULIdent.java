//import src.Token;

public class ULIdent implements Yytoken {

    //Attributes
    private Token token;
    private String value;

    //Methods
    public ULIdent(String value) {
	this.token = Token.identificateur;
	this.value = value;
    }

    public Token getToken() {
	return this.token;
    }

    public Object getValue() {
	return this.value;
    }

    public String toString() {
	return "ULIdent, getToken()=" + this.getToken() + " getValue()=" + this.getValue();
    }
}
