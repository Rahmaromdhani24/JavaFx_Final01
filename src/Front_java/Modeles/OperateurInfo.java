package Front_java.Modeles;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor

public class OperateurInfo {
    @JsonProperty("matricule")
    private int matricule;

    @JsonProperty("plant")
    private String plant;

    @JsonProperty("nom")
    private String nom;

    @JsonProperty("prenom")
    private String prenom;

    @JsonProperty("role")
    private String role;

    @JsonProperty("poste")
    private String poste;

    @JsonProperty("segment")
    private String segment;

    @JsonProperty("machine")
    private String machine;
    
    @JsonProperty("operation")
    private String operation;

    @JsonProperty("email")
    private String email;
    
    @Override
    public String toString() {
        return "OperateurInfo {" + "\n" +
               "matricule=" + matricule + "\n" +
               "operation=" + operation + "\n" +
               "plant=" + plant + "\n" +
               "nom=" + nom + "\n" +
               "prenom=" + prenom + "\n" +
               "role=" + role + "\n" +
               "poste=" + poste + "\n" +
               "segment=" + segment + "\n" +
               "machine=" + machine + "\n" +
               "}";
    }


}
