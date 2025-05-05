package Front_java.Configuration;

import lombok.*;

@Getter
@Setter
public class SoudureReponse {
    private Long id;
    private String code;
    private String sectionFil;
    private String date;
    private int numeroCycle;
    private int userSoudure; // ou String si c'est un matricule
    private double moyenne;
    private int etendu;
	@Override
	public String toString() {
		return "SoudureReponse [id=" + id + ", code=" + code + ", sectionFil=" + sectionFil + ", date=" + date
				+ ", numeroCycle=" + numeroCycle + ", userSoudure=" + userSoudure + ", moyenne=" + moyenne + ", etendu="
				+ etendu + "]";
	}



    }