package Front_java.Modeles;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NoArgsConstructor;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class SoudureDTO {

	  @JsonProperty("numeroCycle")
	    private int numeroCycle;

	    @JsonProperty("sectionFil")
	    private String sectionFil;

	    @JsonProperty("limitePelage")
	    private String limitePelage;

	    @JsonProperty("pliage")
	    private String pliage;

	    @JsonProperty("distanceBC")
	    private String distanceBC;

	    @JsonProperty("traction")
	    private String traction;

	    @JsonProperty("pelageX1")
	    private int pelageX1;

	    @JsonProperty("pelageX2")
	    private int pelageX2;

	    @JsonProperty("pelageX3")
	    private int pelageX3;

	    @JsonProperty("pelageX4")
	    private int pelageX4;

	    @JsonProperty("pelageX5")
	    private int pelageX5;

	    @JsonProperty("nombreKanban")
	    private long nombreKanban;

	    @JsonProperty("grendeurLot")
	    private long grendeurLot;

	    @JsonProperty("nombreNoeud")
	    private String nombreNoeud;

	    @JsonProperty("moyenne")
	    private double moyenne;

	    @JsonProperty("etendu")
	    private int etendu;

	    @JsonProperty("date")
	    private String date; // Format String ou LocalDate à ajuster

	    @JsonProperty("heureCreation")
	    private String heureCreation; 
	    
	    @JsonProperty("quantiteAtteint")
	    private int quantiteAtteint;

	    @JsonProperty("code")
	    private String code;

	    @JsonProperty("pdekId")
	    private Long pdekId; // Assure-toi de transformer ce champ correctement

	    @JsonProperty("userMatricule")
	    private int userMatricule;

	    @Override
	    public String toString() {
	        return "SoudureDTO {" + "\n" +
	                "numeroCycle=" + numeroCycle + "\n" +
	                "sectionFil=" + sectionFil + "\n" +
	                "limitePelage=" + limitePelage + "\n" +
	                "pliage=" + pliage + "\n" +
	                "distanceBC=" + distanceBC + "\n" +
	                "traction=" + traction + "\n" +
	                "pelageX1=" + pelageX1 + "\n" +
	                "pelageX2=" + pelageX2 + "\n" +
	                "pelageX3=" + pelageX3 + "\n" +
	                "pelageX4=" + pelageX4 + "\n" +
	                "pelageX5=" + pelageX5 + "\n" +
	                "nombreKanban=" + nombreKanban + "\n" +
	                "grendeurLot=" + grendeurLot + "\n" +
	                "nombreNoeud=" + nombreNoeud + "\n" +
	                "moyenne=" + moyenne + "\n" +
	                "etendu=" + etendu + "\n" +
	                "date=" + date + "\n" +
	                "heureCreation=" + heureCreation + "\n" +
	                "quantiteAtteint=" + quantiteAtteint + "\n" +
	                "code=" + code + "\n" +
	                "pdekId=" + pdekId + "\n" +
	                "userMatricule=" + userMatricule + "\n" +
	                "}";
	    }
	}