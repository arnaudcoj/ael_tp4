import java.io.*;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

public class InterpreteurJade {
    // La fenetre où dessiner
    private FenetreJade fenetre;
    // L'analyseur syntaxique décrit dans le fichier "analyseurJade.lex"
    private Yylex analyseur;
    // Liste des Tokens lus dans le cas "repeter n fois actions fin repeter"
    private List<Yytoken> actions;
    // Iterateur dans actions
    private Iterator itAction;
    // Table permettant d'associer un identifiant à une liste d'actions
    private Map<String, List<Yytoken>> idMap;

    /**
     * Constructeur
     */
    public InterpreteurJade() throws drawing.DrawingException {
	this.fenetre = new FenetreJade();
	// créer un Yylex qui va prendre ses entrées au clavier
	this.analyseur = new Yylex(new BufferedReader(new InputStreamReader(System.in)));
	this.idMap = new HashMap<String, List<Yytoken>>();
    }
	
    /**
     * Récupère la prochaine unité lexicale lue par l'analyseur lexical.
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
	Yytoken ulSuivant = ul;
	
	// Vérification que ul est bien un Token.repeter
	if(ulSuivant == null || ulSuivant.getToken() != Token.repeter)
	    return;

	ulSuivant = this.lireProchaineUniteLexicale();
	
	// Vérification que ulSuivant est bien un Token.entier
	if(ulSuivant == null || ulSuivant.getToken() != Token.entier)
	    return;

	value = (int) ulSuivant.getValue();
   
	ulSuivant = this.lireProchaineUniteLexicale();

	// Vérification que ulSuivant est bien un Token.fois
	if(ulSuivant == null || ulSuivant.getToken() != Token.fois)
	    return;
	
	// Remplissage de la liste des actions
	this.remplirActions();
	
	this.repeterActions(value);
    }

    public void traiterDefinir(Yytoken ul) throws Exception {
	Yytoken ulSuivant = ul;
	if(ulSuivant.getToken() != Token.definir)
	    return;
	ulSuivant = this.lireProchaineUniteLexicale();
	if(ulSuivant.getToken() == Token.identificateur)
	    {
		List<Yytoken> actions = new LinkedList<Yytoken>();
		remplirListeActions(actions);
		this.idMap.put((String) ulSuivant.getValue(), actions);
	    }
	else
	    System.out.println("Mot clé réservé");
    }

    public void traiterIdent(Yytoken ul) throws Exception {
	if(ul.getToken() == Token.identificateur) {
	    if(this.idMap.containsKey((String) ul.getValue())) {
		this.actions = (this.idMap.get((String) ul.getValue()));
		this.repeterActions(1);
	    }
	    else
		System.out.println("Commande non reconnue");
	}
	else
	    System.out.println("Commande non reconnue");
    }

    private void remplirActions() throws Exception {
	Yytoken ulSuivant;
	ulSuivant = this.lireProchaineUniteLexicale(); 
	
	/* Tant que pas fini ni un autre repeter,
	   ajout des actions dans this.actions
	*/
	while(ulSuivant != null && ulSuivant.getToken() != Token.fin && ulSuivant.getToken() != Token.repeter) {
	    this.actions.add(ulSuivant);	    
	    ulSuivant = this.lireProchaineUniteLexicale(); 
	}

	
	if(ulSuivant.getToken() == Token.repeter)
	    {
		System.out.println("Impossible d'imbriquer repeter");
		actions.clear();
		return;
	    }
    }

    private void remplirListeActions(List<Yytoken> actions) throws Exception {
	Yytoken ulSuivant;
	ulSuivant = this.lireProchaineUniteLexicale(); 
	
	/* Tant que pas fini ni un autre repeter,
	   ajout des actions dans this.actions
	*/
	while(ulSuivant != null && ulSuivant.getToken() != Token.fin && ulSuivant.getToken() != Token.repeter) {
	    actions.add(ulSuivant);	    
	    ulSuivant = this.lireProchaineUniteLexicale(); 
	}
	
	if(ulSuivant.getToken() == Token.repeter)
	    {
		System.out.println("Impossible d'imbriquer repeter");
		actions.clear();
		return;
	    }
    }

    private void repeterActions(int nbFois) throws Exception  {
	Yytoken ulSuivant;	
	
	// Traitement des unités lexicales de la liste nbFois
	for(int i = 0; i < nbFois; i++) {
	    this.itAction = this.actions.iterator();
	    // Tant que pas fini
	    ulSuivant = this.lireProchaineUniteLexicaleRepeter();
	    while(ulSuivant != null && ulSuivant.getToken() != Token.fin) {
		this.traiterUniteLexicaleRepeter(ulSuivant);
		ulSuivant = this.lireProchaineUniteLexicaleRepeter();
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
	case definir:
	    this.traiterDefinir(ul);
	    break;
	case identificateur:
	    this.traiterIdent(ul);
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
	    this.traiterIdent(ul);
	}
    }
	
    /**
     * La classe principale de l'interpréteur Jade.
     */
    public static void main(String[] args) throws drawing.DrawingException {
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