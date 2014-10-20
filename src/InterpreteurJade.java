import java.io.*;

public class InterpreteurJade {
	// La fenetre où dessiner
	private FenetreJade fenetre;
	// L'analyseur syntaxique décrit dans le fichier "analyseurJade.lex"
	private Yylex analyseur;
	
	/**
	 * Constructeur
	 */
	public InterpreteurJade(){
		this.fenetre = new FenetreJade();
		// créer un Yylex qui va prendre ses entrées au clavier
		this.analyseur = new Yylex(new BufferedReader(new InputStreamReader(System.in)));
	}
	
	/**
	 * Récupère la prochaine unité lexicale lue par l'analyseur lexical.
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
	 * La classe principale de l'interpréteur Jade.
	 */
	public static void main(String[] args){
		InterpreteurJade interpreteur = new InterpreteurJade();
		System.out.println("\nBievenue dans l'interpréteur Jade !\n");
		Yytoken ul = null;
			try{
			while (ul == null || ul.getToken() != Token.eof){
				if(ul != null){
					if(ul.getToken() == Token.erreur){
						System.out.println("Erreur : la valeur entrée n'est pas une commande Jade valide.");
					} else {
						interpreteur.traiterUniteLexicale(ul);
					}
				}
				System.out.print("Jade > ");
				ul = interpreteur.lireProchaineUniteLexicale();
			}
			System.out.println("\n\nMerci d'avoir utilisé l'interpréteur Jade !\n");
			System.exit(1);
					} 
			catch(Exception e){
			    e.printStackTrace() ;
			    System.out.println("\n\nUne erreur imprévue est survenue.\nL'interpréteur Jade doit se fermer.");
			System.exit(0);
			}
	}
}