package Front_java.Configuration;

import lombok.*;

@Setter
@Getter
public class EmailValidationPdek {
    private String toEmail;
    private String nomResponsable;
    private String nomProcess ; 
    private String posteMachine;
    private String localisation;
    private String valeurMesuree;      
    private String sectionFil ; 
    private String descriptionPDEK ;
    private String dateRemplissage ; 
    private String heureRemplissage ;  
}
