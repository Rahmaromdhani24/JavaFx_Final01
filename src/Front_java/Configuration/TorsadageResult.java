package Front_java.Configuration;

import java.util.List;

public class TorsadageResult {
    public static long pdekId;
    public static int pageNumber;
	 public  static  List<TorsadageReponse> reponses ; 

    public static void setPdekId(long id) {
        pdekId = id;
    }

    public static void setPageNumber(int page) {
        pageNumber = page;
    }

    public static long getPdekId() {
        return pdekId;
    }

    public static int getPageNumber() {
        return pageNumber;
    }

	public static List<TorsadageReponse> getReponses() {
		return reponses;
	}

	public static void setReponses(List<TorsadageReponse> reponses) {
		TorsadageResult.reponses = reponses;
	}
 
	  public static void reset() {
	        pdekId = 0;
	        pageNumber = 0;
	        reponses = null;
	    }
	}