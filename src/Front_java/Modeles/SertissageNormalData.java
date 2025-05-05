package Front_java.Modeles;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SertissageNormalData {
  

    private int numCycle ; 
    
    private String numeroOutils;
    
    private String numeroContacts;

    private String sectionFil;


    private double hauteurSertissageEch1;
    
    private double hauteurSertissageEch2;

    private double hauteurSertissageEch3;

    private double hauteurSertissageEchFin;

    private double largeurSertissage;

    private double largeurSertissageEchFin;
    
    private double hauteurIsolant;

    private double hauteurIsolantEchFin;

    private double largeurIsolant;
    
    private double largeurIsolantEchFin;

    private String traction;

    private double tractionFinEch;

    private String produit ; 

    private String serieProduit; 

    private int quantiteCycle; 
    
    private String codeControle; 
    
    private String date;
    
    private String heureCreation;
    
    private int  segment ; 

    private String numeroMachine ; 
    
    private double tolerance ; 



}
