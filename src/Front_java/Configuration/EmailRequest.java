package Front_java.Configuration;

import lombok.*;

@Getter
@Setter
public class EmailRequest {
    private String toEmail;
    private String nomResponsable;
    private String localisation;
    private String posteMachine ; 
    private String nomProcess;
    private String sectionFil;
    private String descriptionErreur;
    private String valeurMesuree;
    private String limitesAcceptables;

   
}
