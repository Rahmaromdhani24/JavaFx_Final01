package Front_java.Configuration;

public class SertissageIDCInformations {

	public static String projetSelectionner ; 
	public static String sectionFilSelectionner ; 
	public static String codeControleSelectionner ; 
	public static String sectionFil ; 
	public static String date ; 
	
	public static double hauteurSertissageC1Ech1 ; 
	public static double hauteurSertissageC1Ech2 ; 
	public static double hauteurSertissageC1Ech3 ; 
	public static double hauteurSertissageC1EchFin ; 
	
	public static double hauteurSertissageC2Ech1 ; 
	public static double hauteurSertissageC2Ech2 ; 
	public static double hauteurSertissageC2Ech3 ; 
	public static double hauteurSertissageC2EchFin  ; 
	
	public static int forceTractionEch1C1 ; 
	public static int forceTractionEch2C1 ; 
	public static int forceTractionEch3C1 ; 
	public static int forceTractionEchFinC1 ; 
	
	public static int forceTractionEch1C2 ; 
	public static int forceTractionEch2C2 ; 
	public static int forceTractionEch3C2 ; 
	public static int forceTractionEchFinC2 ; 
	
	
	public static String numCycle ; 
	public static String produit ; 
	public static String serieProduit ; 
	public static int quantiteCycle ; 
	public static int numeroMachine ; 

	


	public static int testTerminitionCommande =  0 ;
 
	public static long idSertissage ;

	
    public static void reset() {
	   sectionFil = null;
	   date = null;
	   idSertissage = 0 ; 
	   hauteurSertissageC1Ech1 = 0.0;
	   hauteurSertissageC1Ech2 = 0.0;
	   hauteurSertissageC1Ech3 = 0.0;
	   hauteurSertissageC1EchFin = 0.0;

	   hauteurSertissageC2Ech1 = 0.0;
	   hauteurSertissageC2Ech2 = 0.0;
	   hauteurSertissageC2Ech3 = 0.0;
	   hauteurSertissageC2EchFin = 0.0;

	   numCycle = null;
	   codeControleSelectionner = null;
	   produit = null;
	   serieProduit = null;
	   quantiteCycle = 0;
	   numeroMachine = 0 ; 
       forceTractionEch1C1 = 0;
	   forceTractionEch2C1 = 0;
	   forceTractionEch3C1 = 0;
	   forceTractionEchFinC1 = 0;

	   forceTractionEch1C2 = 0;
	   forceTractionEch2C2 = 0;
	   forceTractionEch3C2 = 0;
	   forceTractionEchFinC2 = 0;
	}
	
	
}
