package pl.dietbuilder.utils;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ImageSearch {

    private static final String API_KEY = "b4X109BsK3DRQ29SxuEB6JLX7xSzE6nh636cosGE89l0xGYQaXmUMJSG";

    public static String getFirstImageFromSearch(String query) throws Exception {
        String apiUrl = "https://api.pexels.com/v1/search?query=" + query;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URL(apiUrl).toURI())
                .header("Authorization", API_KEY)
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == HttpURLConnection.HTTP_OK) {
            List<String> photoUrls = JsonParser.parsePhotoUrls(response.body());

            if (!photoUrls.isEmpty()) {
                String imageUrl = photoUrls.get(0);
                saveImageToDisk(imageUrl, "downloaded_image.jpg");
                return imageUrl;
            } else {
                return null;
            }
        } else {
            throw new RuntimeException("Nie udało się pobrać obrazka. Kod odpowiedzi: " + response.statusCode());
        }
    }

    private static void saveImageToDisk(String imageUrl, String fileName) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(imageUrl))
                .build();

        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

        // Zapis obrazu na dysku
        Path filePath = Path.of(fileName);
        Files.write(filePath, response.body());
    }
}

