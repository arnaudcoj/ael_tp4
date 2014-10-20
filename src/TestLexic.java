import java.io.IOException ;
import java.io.BufferedReader ;
import java.io.InputStreamReader ;

/**
 *  classe de test d'un analyseur lexical pour JADE
 *
 *@author     Anne-Cécile Caron
 */
public class TestLexic {
  /**
   *  méthode principale : lecture d'un flot de caractères saisie aux clavier
   *  et affichage des unites lexicales
   *
   *@param  args             pas de paramètre attendu
   *@exception  IOException  si un problème de lecture
   */
  public static void main(String args[]) throws IOException {
    // créer un Yylex qui va prendre ses entrées au clavier
    Yylex yy = new Yylex(new BufferedReader(new InputStreamReader(System.in)));
    System.out.println("\nBienvenue dans le programme de test de l'analyseur syntaxique de Jade !\n");
    System.out.print("Jade > ");
    Yytoken ul = yy.yylex(); // la premiere unite lexicale
    while (ul.getToken() != Token.eof){
      System.out.println("Token lu : " + ul);
      System.out.print("Jade > ");
      ul = yy.yylex();
    }
    System.out.println("\n\nMerci d'avoir utilisé le programme de test de l'analyseur syntaxique de Jade !\n");
  }
}

