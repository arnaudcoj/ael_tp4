import java.io.* ;
// Fichier de description pour JFlex permettant
// de construire un analyseur lexical supprimant les
// commentaires de sources java

// classe principale
class uncommentall {
  public static void main(String arg[]) throws IOException {
    // créer un Yylex qui va prendre ses entrées dans le fichier
    // de nom arg[0]
	  Yylex yy = new Yylex(new BufferedReader(new FileReader(arg[0]))) ;
	  Yytoken token ;
    // la fin de fichier est codée par un token null
	  while ((token = yy.yylex()) != null) System.out.print(token.image());
  }
}

// Yytoken défini sous la forme d'interface pour
// utiliser le polymorphisme
interface Yytoken {public String image() ;}

// token correspondant à du texte normal
class Normal implements Yytoken {
  private String image ;
  public Normal(String image) {this.image = image ;}
  public String image() {return image ;}
}

// on pourrait imaginer d'autres tokens ...

%%
%{
  private int compteurCommentaire = 0 ;
%} 

%unicode

%state COMMENTAIRE


NON_DEBUT_COMMENTAIRE=[^/]|"/"[^*/]


%% 

<YYINITIAL> {NON_DEBUT_COMMENTAIRE} {return new Normal(yytext()) ;}

<YYINITIAL> "//".* {}

<YYINITIAL> "/*" {yybegin(COMMENTAIRE) ;} 

<COMMENTAIRE> "/*" {compteurCommentaire++ ;}

<COMMENTAIRE> "*"+"/" {if(compteurCommentaire == 0) yybegin(YYINITIAL); else compteurCommentaire--;}

<COMMENTAIRE> [^]|\n {}









