import java.io.* ;
// Fichier de description pour JFlex permettant
// de construire un analyseur lexical supprimant les
// commentaires de sources java du type //

// classe principale
class uncomment {
  public static void main(String arg[]) throws IOException {
    // créer un Yylex qui va prendre ses entrées dans le fichier
    // de nom arg[0]
	  Yylex yy = new Yylex(new BufferedReader(new FileReader(arg[0]))) ;
	  Yytoken token ;
    // la fin de fichier est codée par un token null
	  while ((token = yy.yylex()) != null) System.out.print(token.image());
  }
}

// la classe Yytoken 
class Yytoken {
  private String image ;
  public Yytoken(String image) {this.image = image ;}
  public String image() {return image ;}
}

%%

%unicode


NON_DEBUT_COMMENTAIRE=[^/]|"/"[^/]

%% 

{NON_DEBUT_COMMENTAIRE}* {return new Yytoken(yytext()) ;}

"//".* {}
