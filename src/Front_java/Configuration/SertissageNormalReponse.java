package Front_java.Configuration;

import lombok.*;

@Getter
@Setter
public class SertissageNormalReponse {
	 private Long id;
	    private String code;
	    private String sectionFil;
	    private String numOutil ; 
	    private String numContact ; 
	    private String date;
	    private int numCycle;
	    private int userSertissageNormal ; 
	    private double hauteurSertissageEch1 ; 
	    private double hauteurSertissageEch2 ; 
	    private double hauteurSertissageEch3 ; 
	    private double hauteurSertissageEchFin ;
		@Override
		public String toString() {
			return "SertissageNormalReponse [id=" + id + ", code=" + code + ", sectionFil=" + sectionFil + ", numOutil="
					+ numOutil + ", numContact=" + numContact + ", date=" + date + ", numCycle=" + numCycle
					+ ", userSertissageNormal=" + userSertissageNormal + ", hauteurSertissageEch1="
					+ hauteurSertissageEch1 + ", hauteurSertissageEch2=" + hauteurSertissageEch2
					+ ", hauteurSertissageEch3=" + hauteurSertissageEch3 + ", hauteurSertissageEchFin="
					+ hauteurSertissageEchFin + "]";
		} 
	    
	

    }