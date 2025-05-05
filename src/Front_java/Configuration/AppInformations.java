package Front_java.Configuration;

import java.util.Locale;

import Front_java.Modeles.OperateurInfo;

public class AppInformations {
    
    public static String token;
    public static OperateurInfo operateurInfo;
    public static String projetSelectionner; 
    public static String sectionFilSelectionner;
    public static String codeControleSelectionner; 
    public static String nbrPelage; 
    public static int testTerminitionCommande = 0; 
    
    public static String  langueSelectionnee;


    // Méthode pour réinitialiser toutes les variables statiques
    public static void reset() {
        token = null;
        operateurInfo = null;
        projetSelectionner = null;
        sectionFilSelectionner = null;
        codeControleSelectionner = null;
        nbrPelage = null;
        testTerminitionCommande = 0;
    }
}
