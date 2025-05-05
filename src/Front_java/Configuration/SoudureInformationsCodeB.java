package Front_java.Configuration;

public class SoudureInformationsCodeB {
    
    public static String codecontroleselectionner; 
    public static int quantiteAtteintCodeB; 
    public static int testTerminitionCommandeCodeB = 0; 
    public static int numCycle; 
    public static int pelageX1; 
    public static String pelageX2; 
    public static String pelageX3; 
    public static String pelageX4; 
    public static String pelageX5; 
    public static String pliage; 
    public static String distanceBC; 
    public static String traction; 

    // Méthode pour réinitialiser toutes les variables statiques
    public static void reset() {
        codecontroleselectionner = null;
        quantiteAtteintCodeB = 0;
        testTerminitionCommandeCodeB = 0;
        numCycle = 0;
        pelageX1 = 0;
        pelageX2 = null;
        pelageX3 = null;
        pelageX4 = null;
        pelageX5 = null;
        pliage = null;
        distanceBC = null;
        traction = null;
    }
}