package Front_java.Modeles;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TorsadageDTO {
    private int numeroCycle;
    private String specificationMesure;
    private long numCommande;
    private int longueurFinalDebutCde;
    private int longueurFinalFinCde;
    private int longueurBoutDebutCdeC1;
	private int longueurBoutDebutCdeC2;
    private int longueurBoutFinCdeC1;
    private int longueurBoutFinCdeC2;
    private int decalageMaxDebutCdec1;
    private int decalageMaxDebutCdec2;
    private int decalageMaxFinCdec1;
    private int decalageMaxFinCdec2;
    private String numerofil;
    private double longueurPasFinCde;
    private int ech1;
    private int ech2;
    private int ech3;
    private int ech4;
    private int ech5;
    private String date;
    private String heureCreation;
    private int quantiteTotale;
    private int quantiteAtteint;
    private String code;
    private double moyenne;
    private int etendu;


    
    @Override
   	public String toString() {
   		return "TorsadageDTO [numeroCycle=" + numeroCycle + ", specificationMesure=" + specificationMesure
   				+ ", numCommande=" + numCommande + ", longueurFinalDebutCde=" + longueurFinalDebutCde
   				+ ", longueurFinalFinCde=" + longueurFinalFinCde + ", longueurBoutDebutCdeC1=" + longueurBoutDebutCdeC1
   				+ ", longueurBoutDebutCdeC2=" + longueurBoutDebutCdeC2 + ", longueurBoutFinCdeC1="
   				+ longueurBoutFinCdeC1 + ", longueurBoutFinCdeC2=" + longueurBoutFinCdeC2 + ", decalageMaxDebutCdec1="
   				+ decalageMaxDebutCdec1 + ", decalageMaxDebutCdec2=" + decalageMaxDebutCdec2 + ", decalageMaxFinCdec1="
   				+ decalageMaxFinCdec1 + ", decalageMaxFinCdec2=" + decalageMaxFinCdec2 + ", numerofil=" + numerofil
   				+ ", longueurPasFinCde=" + longueurPasFinCde + ", ech1=" + ech1 + ", ech2=" + ech2 + ", ech3=" + ech3
   				+ ", ech4=" + ech4 + ", ech5=" + ech5 + ", date=" + date + ", quantiteTotale=" + quantiteTotale
   				+ ", quantiteAtteint=" + quantiteAtteint + ", code=" + code + ", moyenne=" + moyenne + ", etendu="
   				+ etendu 
   				+ "]";
   	}
}