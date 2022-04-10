package vttp2022.paf.giphy.services;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;


@Service
public class GiphyService {

    // GIPHY_API_KEY
    // export GIPHY_API_KEY="<apiKey>"
    @Value("${giphy.api.key}")
    private String giphyKey;

    private static final String BASE_URL = "https://api.giphy.com/v1/gifs/search";
    private static final String OFFSET = "0";
    private static final String LANG = "en";

    public List<String> getGiphy(String q) {
        return getGiphy(q, 10, "pg");
    }

    public List<String> getGiphy(String q, Integer limit) {
        return getGiphy(q, limit, "pg");
    }

    public List<String> getGiphy(String q, Integer limit, String rating) {

        List<String> result = new ArrayList<>();
        
        String url = UriComponentsBuilder
            .fromUriString(BASE_URL)
            .queryParam("api_key", giphyKey)
            .queryParam("q", q)
            .queryParam("limit", limit)
            .queryParam("offset", OFFSET) // optional
            .queryParam("rating", rating)
            .queryParam("lang", LANG)
            .toUriString();

        RequestEntity<Void> req = RequestEntity
            .get(url)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;
        try {
            resp = template.exchange(req, String.class);
            // System.out.printf(">>> resp: %s", resp);
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }

        // image fixed-width url
        // JSONPath: $.data.*.images.fixed_width.url
		JsonReader r = Json.createReader(new StringReader(resp.getBody()));
        JsonObject o = r.readObject();
        JsonArray data = o.getJsonArray("data");
        for (int i = 0; i < data.size(); i++) {
            JsonObject gif = data.getJsonObject(i);
            String image = gif
                .getJsonObject("images")
                .getJsonObject("fixed_width")
                .getString("url");
            result.add(image);
        }
        System.out.printf(">>> Image URLs: %s", result);
        return result;
    }
}
