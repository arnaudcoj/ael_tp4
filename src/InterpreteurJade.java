import java.io.*;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

public class InterpreteurJade {
    // La fenetre o� dessiner
    private FenetreJade fenetre;
    // L'analyseur syntaxique d�crit dans le fichier "analyseurJade.lex"
    private Yylex analyseur;
    // Liste des Tokens lus dans le cas "repeter n fois actions fin repeter"
    List<Yytoken> actions;
    // Iterateur dans actions
    Iterator itAction;
	
    /**
     * Constructeur
     */
    public InterpreteurJade() throws drawing.DrawingException {
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

    public Yytoken lireProchaineUniteLexicaleRepeter() throws Exception {
	if(this.itAction.hasNext())
	    return (Yytoken) this.itAction.next();
	else
	    return null;
    }
	
    public void traiterFois(Yytoken ul) throws Exception {
	int value;
	value = (int) ul.getValue();
	ul = this.lireProchaineUniteLexicale();
	if(ul != null && ul.getToken() == Token.fois)
	    {
		ul = this.lireProchaineUniteLexicale();
		if(ul != null)
		    switch(ul.getToken()) {
		    case nord:
			for(int i = 0; i < value; i++)
			    fenetre.nord();
			break;
		    case sud:
			for(int i = 0; i < value; i++)
			    fenetre.sud();
			break;
		    case est:
			for(int i = 0; i < value; i++)
			    fenetre.est();
			break;
		    case ouest:
			for(int i = 0; i < value; i++)
			    fenetre.ouest();
			break;
		    default:
			System.out.println("Commande non reconnue");
		    }
	    }
    }

    public void traiterFoisRepeter(Yytoken ul) throws Exception {
	int value;
	value = (int) ul.getValue();
	ul = this.lireProchaineUniteLexicaleRepeter();
	if(ul != null && ul.getToken() == Token.fois)
	    {
		ul = this.lireProchaineUniteLexicaleRepeter();
		if(ul != null)
		    switch(ul.getToken()) {
		    case nord:
			for(int i = 0; i < value; i++)
			    fenetre.nord();
			break;
		    case sud:
			for(int i = 0; i < value; i++)
			    fenetre.sud();
			break;
		    case est:
			for(int i = 0; i < value; i++)
			    fenetre.est();
			break;
		    case ouest:
			for(int i = 0; i < value; i++)
			    fenetre.ouest();
			break;
		    default:
			System.out.println("Commande non reconnue");
		    }
	    }
    }

    public void traiterPas(Yytoken ul) throws Exception {
	ul = this.lireProchaineUniteLexicale();
	if(ul != null && ul.getToken() == Token.entier)
	    fenetre.pas((int) ul.getValue());
	else
	    System.out.println("Commande non reconnue");
    }


    public void traiterPasRepeter(Yytoken ul) throws Exception {
	ul = this.lireProchaineUniteLexicaleRepeter();
	if(ul != null && ul.getToken() == Token.entier)
	    fenetre.pas((int) ul.getValue());
	else
	    System.out.println("Commande non reconnue");
    }

    public void traiterOrigine(Yytoken ul) throws Exception {
	ul = this.lireProchaineUniteLexicale();
	if(ul != null && ul.getToken() == Token.point)
	    fenetre.origine((java.awt.Point) ul.getValue());
	else
	    System.out.println("Commande non reconnue");
    }
    
    public void traiterOrigineRepeter(Yytoken ul) throws Exception {
	ul = this.lireProchaineUniteLexicaleRepeter();
	if(ul != null && ul.getToken() == Token.point)
	    fenetre.origine((java.awt.Point) ul.getValue());
	else
	    System.out.println("Commande non reconnue");
    }

    public void traiterRepeter(Yytoken ul) throws Exception {
	int value;
	actions = new LinkedList<Yytoken>();
	Yytoken ulRepeter = ul;
	
	// V�rification que ul est bien un Token.repeter
	if(ulRepeter == null || ulRepeter.getToken() != Token.repeter)
	    return;

	ulRepeter = this.lireProchaineUniteLexicale();
	
	// V�rification que ulRepeter est bien un Token.entier
	if(ulRepeter == null || ulRepeter.getToken() != Token.entier)
	    return;

	value = (int) ulRepeter.getValue();
   
	ulRepeter = this.lireProchaineUniteLexicale();

	// V�rification que ulRepeter est bien un Token.fois
	if(ulRepeter == null || ulRepeter.getToken() != Token.fois)
	    return;
	
	// Remplissage de la liste des actions
	this.remplirActions();
	
	this.repeterActions(value);
    }

    private void remplirActions() throws Exception {
	Yytoken ulRepeter;
	ulRepeter = this.lireProchaineUniteLexicale(); 
	
	/* Tant que pas fini ni un autre repeter,
	   ajout des actions dans this.actions
	*/
	while(ulRepeter != null && ulRepeter.getToken() != Token.fin && ulRepeter.getToken() != Token.repeter) {
	    this.actions.add(ulRepeter);	    
	    ulRepeter = this.lireProchaineUniteLexicale(); 
	}

	
	if(ulRepeter.getToken() == Token.repeter)
	    {
		System.out.println("Impossible d'imbriquer repeter");
		actions.clear();
		return;
	    }
    }

    private void repeterActions(int nbFois) throws Exception  {
	Yytoken ulRepeter;	
	
	// Traitement des unit�s lexicales de la liste nbFois
	for(int i = 0; i < nbFois; i++) {
	    this.itAction = this.actions.iterator();
	    // Tant que pas fini
	    ulRepeter = this.lireProchaineUniteLexicaleRepeter();
	    while(ulRepeter != null && ulRepeter.getToken() != Token.fin) {
		this.traiterUniteLexicaleRepeter(ulRepeter);
		ulRepeter = this.lireProchaineUniteLexicaleRepeter();
	    }
	}
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
	case lever:
	    fenetre.lever();
	    break;
	case baisser:
	    fenetre.baisser();
	    break;
	case entier:
	    this.traiterFoisRepeter(ul);
	    break;
	case pas:
	    this.traiterPasRepeter(ul);
	    break;
	case origine:
	    this.traiterOrigineRepeter(ul);
	    break;
	case repeter:
	    this.traiterRepeter(ul);
	    break;
	default:
	    System.out.println("Commande non reconnue");
	}
    }


    public void traiterUniteLexicaleRepeter(Yytoken ul) throws Exception {
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
	case lever:
	    fenetre.lever();
	    break;
	case baisser:
	    fenetre.baisser();
	    break;
	case entier:
	    this.traiterFoisRepeter(ul);
	    break;
	case pas:
	    this.traiterPasRepeter(ul);
	    break;
	case origine:
	    this.traiterOrigineRepeter(ul);
	    break;		
	default:
	    System.out.println("Commande non reconnue");
	}
    }
	
    /**
     * La classe principale de l'interpr�teur Jade.
     */
    public static void main(String[] args) throws drawing.DrawingException {
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