import java.io.*;

public class InterpreteurJade {
	// La fenetre o� dessiner
	private FenetreJade fenetre;
	// L'analyseur syntaxique d�crit dans le fichier "analyseurJade.lex"
	private Yylex analyseur;
	
	/**
	 * Constructeur
	 */
	public InterpreteurJade(){
		this.fenetre = new FenetreJade();
		// cr�er un Yylex qui va prendre ses entr�es au clavier
		this.analyseur = new Yylex(new BufferedReader(new InputStreamReader(System.in)));
	}
	
	/**
	 * R�cup�re la prochaine unit� lexicale lue par l'analyseur lexical.
	 */
	public Yytoken lireProchaineUniteLexicale() throws Exception {
		return analyseur.yylex();
	}
	
	public void traiterUniteLexicale(Yytoken ul) throws Exception {
	    switch(ul.getToken()) {
	    case nord:
		fenetre.nord();
		break;
	    case ouest:
		fenetre.ouest();
		break;
	    case sud:
		fenetre.sud();
		break;
	    case est:
		fenetre.est();
		break;
	    default:
		System.out.println("Commande non reconnue");
	    }
	}
	
	/**
	 * La classe principale de l'interpr�teur Jade.
	 */
	public static void main(String[] args){
		InterpreteurJade interpreteur = new InterpreteurJade();
		System.out.println("\nBievenue dans l'interpr�teur Jade !\n");
		Yytoken ul = null;
			try{
			while (ul == null || ul.getToken() != Token.eof){
				if(ul != null){
					if(ul.getToken() == Token.erreur){
						System.out.println("Erreur : la valeur entr�e n'est pas une commande Jade valide.");
					} else {
						interpreteur.traiterUniteLexicale(ul);
					}
				}
				System.out.print("Jade > ");
				ul = interpreteur.lireProchaineUniteLexicale();
			}
			System.out.println("\n\nMerci d'avoir utilis� l'interpr�teur Jade !\n");
			System.exit(1);
					} 
			catch(Exception e){
			    e.printStackTrace() ;
			    System.out.println("\n\nUne erreur impr�vue est survenue.\nL'interpr�teur Jade doit se fermer.");
			System.exit(0);
			}
	}
}