package Front_java.Configuration;

import java.util.List;

public class SoudureInformations {

    public static String projetSelectionner; 
    public static String sectionFilSelectionner;
    public static String codeControleSelectionner; 
    public static String nbrPelage; 

    public static String pelage; 
    public static int quantiteAtteint;
    public static int numeroKanban; 
    public static int numeroCycle; 

    public static int numerCyclePDEK; 
    public static String pliage; 
    public static String distanceBC; 
    public static String traction; 
    public static int pelageX1; 
    public static int pelageX2; 
    public static int pelageX3; 
    public static int pelageX4; 
    public static int pelageX5; 

    public static int grandeurLot; 
    public static String numNoeud; 

    public static int moyenne; 
    public static int etendu; 
    public static String dateCreation; 

    public static int EtenduValueMax; 
    public static int MoyenneVertMin; 

    public static int moyenneMin;
	public static int minPelage; 
	public static int MoyenneVertMax ; 
	
	public static long idSoudure;


    // Méthode pour réinitialiser toutes les variables
    public static void reset() {
    	
    	idSoudure=0 ; 
        projetSelectionner = null;
        sectionFilSelectionner = null;
        codeControleSelectionner = null;
        nbrPelage = null;

        pelage = null;
        quantiteAtteint = 0;
        numeroKanban = 0;
        numeroCycle = 0;

        numerCyclePDEK = 0;
        pliage = null;
        distanceBC = null;
        traction = null;
        pelageX1 = 0;
        pelageX2 = 0;
        pelageX3 = 0;
        pelageX4 = 0;
        pelageX5 = 0;

        grandeurLot = 0;
        numNoeud = null;

        moyenne = 0;
        etendu = 0;
        dateCreation = null;

        EtenduValueMax = 0;
        MoyenneVertMin = 0;

        moyenneMin = 0;
        minPelage = 0 ;
        MoyenneVertMax =0 ; 
    }
}
