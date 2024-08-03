package ca.ucalgary.edu.ensf380;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class NewsFetcher {

    private static final String API_KEY = "608ed87fc66e426b90679673b6429938";
    private static final String URL_STRING = "https://newsapi.org/v2/top-headlines?country=us&apiKey=" + API_KEY;

    public String[] fetchNews() throws IOException {
        URL url = new URL(URL_STRING);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        }

        Scanner scanner = new Scanner(url.openStream());
        StringBuilder inline = new StringBuilder();
        while (scanner.hasNext()) {
            inline.append(scanner.nextLine());
        }
        scanner.close();

        JSONObject json = new JSONObject(inline.toString());
        JSONArray articles = json.getJSONArray("articles");

        String[] headlines = new String[5];
        for (int i = 0; i < 5 && i < articles.length(); i++) {
            JSONObject article = articles.getJSONObject(i);
            headlines[i] = article.getString("title");
        }

        return headlines;
    }
}
