import java.io.*;

/**
 *  classe de test de la classe de dessin pour JADE
 *
 *@author     Yoann Kubera
 */
public class TestDessin {
  /**
   *  méthode principale : création d'une fenetre, et
	dessin de certaines formes pour tester l'ensemble
	des méthodes écrites.
   *
   *@param  args             pas de paramètre attendu
   *@exception  IOException  si un problème de lecture
   */
  public static void main(String args[]) throws Exception {
	FenetreJade f = new FenetreJade();
	/* Réalisation du dessin équivalent au code :
		origine (50,70)
		pas 5
		10 fois nord 
		8 fois est
		6 fois sud
		4 fois ouest
		4 fois sud
		8 fois est
		10 fois nord 
		8 fois est
		6 fois sud
		4 fois ouest
		4 fois sud
		8 fois est
		10 fois nord 
		8 fois est
		6 fois sud
		4 fois ouest
		4 fois sud
		8 fois est
		10 fois nord 
		8 fois est
		6 fois sud
		4 fois ouest
		4 fois sud
		8 fois est
		lever
		10 fois nord 
		8 fois est
		6 fois sud
		4 fois ouest
		4 fois sud
		8 fois est
		baisser
		10 fois nord 
		8 fois est
		6 fois sud
		4 fois ouest
		4 fois sud
		8 fois est
	*/	
	f.origine(new java.awt.Point(50,70));
	f.pas(5);
	unDessin(f);
	unDessin(f);
	unDessin(f);
	unDessin(f);
	f.lever();
	unDessin(f);
	f.baisser();
	unDessin(f);
  }
  
  private static void unDessin(FenetreJade f) throws Exception {
	for(int i = 0; i < 10; i++){f.nord();}
	for(int i = 0; i < 8; i++){f.est();}
	for(int i = 0; i < 6; i++){f.sud();}
	for(int i = 0; i < 4; i++){f.ouest();}
	for(int i = 0; i < 4; i++){f.sud();}
	for(int i = 0; i < 8; i++){f.est();}
  }
}

