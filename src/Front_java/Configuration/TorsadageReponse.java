package Front_java.Configuration;

import lombok.*;

@Getter
@Setter
public class TorsadageReponse {
    private Long id;
    private String code;
    private String specificationMesure;
    private String date;
    private int numeroCycle;
    private int userTorsadage; // ou String si c'est un matricule
    private double moyenne;
    private int etendu;
	@Override
	public String toString() {
		return "TorsadageReponse [id=" + id + ", code=" + code + ", specificationMesure=" + specificationMesure + ", date=" + date
				+ ", numeroCycle=" + numeroCycle + ", userTorsadage=" + userTorsadage + ", moyenne=" + moyenne + ", etendu="
				+ etendu + "]";
	}



    }