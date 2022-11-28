package io.github.fvarrui.eclipse.plugin.pasteee.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PasteApi {
	
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create(); 
	
	public static String paste(String apiKey, String text, String filename, String type) throws Exception {
		
		if (apiKey == null || apiKey.trim().isEmpty()) {
			throw new Exception("You must set an API KEY in Paste.ee Plugin preferences page.");
		}
		
        if (type != null && !type.isEmpty()) {
            type = "autodetect";
        }
        
        PasteRequestBody reqboby = new PasteRequestBody();
        reqboby.setEncrypted(false);
        reqboby.setDescription("Paste generated with Paste.ee Eclipse Plugin");        
        reqboby.getSections().add(new PasteSection(filename, type, text));
        
        String json = gson.toJson(reqboby, PasteRequestBody.class);

	    HttpRequest request = HttpRequest.newBuilder()
	    		.uri(URI.create("https://api.paste.ee/v1/pastes"))
                .header("Content-Type", "application/json")
                .header("Access-Key", apiKey)
	    		.POST(BodyPublishers.ofString(json, StandardCharsets.UTF_8))
	    		.timeout(Duration.ofSeconds(15))
	    		.build();
	    
		try {
			
			HttpResponse<String> response = HttpClient.newHttpClient().send(request, BodyHandlers.ofString(StandardCharsets.UTF_8));
			
			if (response.statusCode() == 201) {
				PasteResponseBody resbody = gson.fromJson(response.body(), PasteResponseBody.class); 				
				return resbody.getLink();
			} else {
				throw new Exception("Error creating paste: " + response.body() + " (" + response.statusCode() + ")");
			}
			
		} catch (IOException | InterruptedException e) {
			
			throw new Exception("Error creating paste (" + e.getClass().getSimpleName() + "): " + e.getMessage());				
			
		}
		
	}

}
