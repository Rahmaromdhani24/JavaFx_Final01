package Front_java.Configuration;

public class TorsadageInformations {
	
	
    /******************************** Torsadage ***********************/
    public static String  specificationsMesure ;  
    public static String projetSelectionner; 
    public static String codeControleSelectionner; 
    public static int testTerminitionCommande ; 
    
    
    public static String numCommande ;
    public static String longueurFinalDebutCde ;
    public static String lognueurBoutDebutC1 ;
    public static String lognueurBoutDebutC2 ;
    public static String lognueurBoutFinC1 ;
    public static String lognueurBoutFinC2 ;
    public static String decalageDebutC1 ;
    public static String decalageDebutC2 ;
    public static String decalageFinC1 ;
    public static String decalageFinC2 ;
    public static String longueurFinalFinCde;
    public static String longueurPasFinCde ;
    public static String ech1 ;
    public static String ech2 ;
    public static String ech3 ;
    public static String ech4 ;
    public static String ech5 ;
    public static String quantiteTotal ;
    public static String quantiteAtteint ;
	public static String numFils;
	public static int numCourant;
    public static String date; 
	public static double moyenne ; 
	public static int ettendu ;
    public static long idTorsadage ; 
	
	public static void reset() {
		idTorsadage = 0 ; 
	    specificationsMesure = null;
	    projetSelectionner = null;
	    codeControleSelectionner = null;
	    testTerminitionCommande = 0;
	    
	    numCommande = null;
	    longueurFinalDebutCde = null;
	    lognueurBoutDebutC1 = null;
	    lognueurBoutDebutC2 = null;
	    lognueurBoutFinC1 = null;
	    lognueurBoutFinC2 = null;
	    decalageDebutC1 = null;
	    decalageDebutC2 = null;
	    decalageFinC1 = null;
	    decalageFinC2 = null;
	    longueurFinalFinCde = null;
	    longueurPasFinCde = null;
	    ech1 = null;
	    ech2 = null;
	    ech3 = null;
	    ech4 = null;
	    ech5 = null;
	    quantiteTotal = null;
	    quantiteAtteint = null;
	    numFils = null;
	    numCourant = 0;
	    date = null;
	    moyenne = 0.0;
	    ettendu = 0;
	}

}
