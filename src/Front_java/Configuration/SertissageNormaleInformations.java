package Front_java.Configuration;

public class SertissageNormaleInformations {

	public static String projetSelectionner ; 
	public static String sectionFil ; 
	
	public static String numeroOutils ; 
	public static String numeroContacts ; 

	public static double hauteurSertissageEch1 ; 
	public static double hauteurSertissageEch2 ; 
	public static double hauteurSertissageEch3 ;
	public static double hauteurSertissageEchFin ; 
	public static double largeurSertissage ; 
	public static double largeurSertissageEchFin ; 
	public static double hauteurIsolant ; 
	public static double hauteurIsolantEchFin ; 
	public static double largeurIsolant ; 
	public static double largeurIsolantEchFin ; 
	public static String traction ; 
	public static double tractionFinEch ; 
	public static int numCycle ; 
	public static String produit  ; 
	public static String serieProduit ; 
	public static String quantiteAtteint ; 
	public static String codeControleSelectionner ; 
	public static String machineTraction ; 

    public static double labelLargeurSertissage ; 
    public static double labelHauteurSertissage;
    public static double labelHauteurIsolant;
    public static double labelLargeurIsolant;
    public static String labelTraction ;
    
	public static int testTerminitionCommande =  0 ;
	public static String labelLargeurSertissageComplet;
	public static String labelHauteurIsolantComplet;
	public static String labelLargeurIsolantComplet;

	public static long idSertissageNormal ;


	public static void reset() {
		
		idSertissageNormal = 0 ; 
		labelLargeurSertissageComplet = null ; 
		labelHauteurIsolantComplet= null ; 
		labelLargeurIsolantComplet = null ; 
	    projetSelectionner = null;
	    sectionFil = null;
	    numeroOutils = null;
	    numeroContacts = null;

	    hauteurSertissageEch1 = 0.0;
	    hauteurSertissageEch2 = 0.0;
	    hauteurSertissageEch3 = 0.0;
	    hauteurSertissageEchFin = 0.0;
	    largeurSertissage = 0.0;
	    largeurSertissageEchFin = 0.0;
	    hauteurIsolant = 0.0;
	    hauteurIsolantEchFin = 0.0;
	    largeurIsolant = 0.0;
	    largeurIsolantEchFin = 0.0;
	    traction = null;
	    tractionFinEch = 0.0;
	    produit = null;
	    serieProduit = null;
	    quantiteAtteint = null;
	    codeControleSelectionner = null;
	    machineTraction = null;

	    testTerminitionCommande = 0; // Remet à zéro l'entier
	}

	
}
