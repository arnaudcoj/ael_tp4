public class ULEntier implements Yytoken{

    //Attributes
    private Token token;
    private Integer value;

    //Methods
    public ULEntier(Integer value) {
	this.token = Token.entier;
	this.value = value;
    }

    public Token getToken() {
	return this.token;
    }

    public Object getValue() {
	return this.value;
    }

    public String toString() {
	return "ULEntier, getToken()=" + this.getToken() + " getValue()=" + this.getValue();
    }
}
