package Front_java.Services;


import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import Front_java.Configuration.AppInformations;

public class ServiceSoudure {

	/********************* Recuperation de valeur MAx de etendu ***********************************/
	public CompletableFuture<String> getValeurEtenduFromApi() {
	    return CompletableFuture.supplyAsync(() -> {
	        try {
	            double sectionValue = extraireValeurNumeriqueSectionFil(AppInformations.sectionFilSelectionner);
	            
	            // Vérifie si c’est un entier ou un double
	            String formattedSection;
	            if (sectionValue == Math.floor(sectionValue)) {
	                formattedSection = String.valueOf((int) sectionValue);
	            } else {
	                formattedSection = String.format(Locale.US, "%.2f", sectionValue);
	            }

	            String url = "http://localhost:8281/operations/soudure/etenduMax/" 
	                         + URLEncoder.encode(formattedSection, StandardCharsets.UTF_8);

	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(url))
	                    .header("Authorization", "Bearer " + AppInformations.token)
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

	            if (response.statusCode() == 200) {
	                return response.body(); // Si OK, retourner la réponse
	            } else {
	                return "Erreur : " + response.statusCode() + " - " + response.body(); // Gérer l'erreur
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	            return "Erreur lors de la récupération de la valeur"; // En cas d'exception
	        }
	    });
	}



	public int fetchValeurEtenduMax() {
	    try {
	        String result = getValeurEtenduFromApi().get(); 
	        return Integer.parseInt(result.trim());
	    } catch (Exception e) {
	        System.err.println("Erreur lors de la récupération de la valeur : " + e.getMessage());
	        return 0; // Retourner une valeur par défaut en cas d'erreur
	    }
	}


	

/********************************************* Recuperation de de valeur min de moyenne *********************************/
	public CompletableFuture<String> getValeurMoyenneMinFromApi() {
	    return CompletableFuture.supplyAsync(() -> {
	        try {
	            double sectionValue = extraireValeurNumeriqueSectionFil(AppInformations.sectionFilSelectionner);
	            
	            // Vérifie si c'est un entier (par exemple 5.0)
	            String formattedSection;
	            if (sectionValue == Math.floor(sectionValue)) {
	                // C'est un entier, on l'envoie sans décimales
	                formattedSection = String.valueOf((int) sectionValue);
	            } else {
	                // C'est un décimal, on formate avec un point
	                formattedSection = String.format(Locale.US, "%.2f", sectionValue);
	            }

	            String url = "http://localhost:8281/operations/soudure/valeurMoyenneVertMin/" 
	                         + URLEncoder.encode(formattedSection, StandardCharsets.UTF_8);
	            
	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(url))
	                    .header("Authorization", "Bearer " + AppInformations.token)
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

	            if (response.statusCode() == 200) {
	                return response.body(); // Si OK, retourner la réponse
	            } else {
	                return "Erreur : " + response.statusCode() + " - " + response.body(); // Gérer l'erreur
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	            return "Erreur lors de la récupération de la valeur"; // En cas d'exception
	        }
	    });
	}


	private int valeurMoyenneMin;

	public int fetchValeurMoyenneMin() {
	    try {
	        String result = getValeurMoyenneMinFromApi().get(); // Bloque jusqu'à la réponse
	        this.valeurMoyenneMin = Integer.parseInt(result.trim());
	        return this.valeurMoyenneMin;
	    } catch (Exception e) {
	        System.err.println("Erreur lors de la récupération de la valeur : " + e.getMessage());
	        return 0; // Valeur par défaut en cas d'échec
	    }
	}
	/********************************************* Recuperation de de valeur max de moyenne *********************************/
	public CompletableFuture<String> getValeurMoyenneMaxFromApi() {
	    return CompletableFuture.supplyAsync(() -> {
	        try {
	            double sectionValue = extraireValeurNumeriqueSectionFil(AppInformations.sectionFilSelectionner);
	            
	            // Vérifie si c’est un entier ou un double
	            String formattedSection;
	            if (sectionValue == Math.floor(sectionValue)) {
	                formattedSection = String.valueOf((int) sectionValue);
	            } else {
	                formattedSection = String.format(Locale.US, "%.2f", sectionValue);
	            }

	            String url = "http://localhost:8281/operations/soudure/valeurMoyenneVertMax/" 
	                         + URLEncoder.encode(formattedSection, StandardCharsets.UTF_8);

	            HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(url))
	                    .header("Authorization", "Bearer " + AppInformations.token)
	                    .build();

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

	            if (response.statusCode() == 200) {
	                return response.body();
	            } else {
	                return "Erreur : " + response.statusCode() + " - " + response.body();
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	            return "Erreur lors de la récupération de la valeur";
	        }
	    });
	}


	private int valeurMoyenneMax;

	public int fetchValeurMoyenneMax() {
	    try {
	        String result = getValeurMoyenneMaxFromApi().get(); // Bloque jusqu'à la réponse
	        this.valeurMoyenneMax = Integer.parseInt(result.trim());
	        return this.valeurMoyenneMax;
	    } catch (Exception e) {
	        System.err.println("Erreur lors de la récupération de la valeur : " + e.getMessage());
	        return 0; // Valeur par défaut en cas d'échec
	    }
	}
	/****************** extraire mm depuis section fil ****/
	public double extraireValeurNumeriqueSectionFil(String sectionFil) {
	    return Double.parseDouble(sectionFil.trim().split(" ")[0] );
	}
	/****************************************************************************************************************/
	/*******  Recuperer valeur de pelage *******************/
	 public  String  getPelageFromApi() {
		  try {
	            String formattedSection = (extraireValeurNumeriqueSectionFil(AppInformations.sectionFilSelectionner) + "").replace(",", ".").trim(); // Supprime " mm²"
	            String url = "http://localhost:8281/operations/soudure/pelage/nombre/" 
	                         + URLEncoder.encode(formattedSection, StandardCharsets.UTF_8);
	            
		            // Création du client HTTP
		            HttpClient client = HttpClient.newHttpClient();

		            // Création de la requête GET avec le token d'authentification
		            HttpRequest request = HttpRequest.newBuilder()
		                    .uri(URI.create(url))
		                    .header("Authorization", "Bearer " + AppInformations.token) // Ajout du token
		                    .GET()
		                    .build();

		            // Envoi de la requête et récupération de la réponse
		            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		            // Vérification du statut de la réponse
		            if (response.statusCode() == 200) {
		                return response.body(); // Retourner la valeur du pelage
		            } else {
		                return "Erreur: " + response.body(); // Retourner l'erreur en cas d'échec
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		            return "Erreur lors de la récupération du pelage";
		        }
		    }
}