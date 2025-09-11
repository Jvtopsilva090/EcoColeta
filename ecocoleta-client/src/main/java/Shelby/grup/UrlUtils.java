package Shelby.grup;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class UrlUtils {

    // Constrói URL com parâmetros codificados
    public static String buildUrlWithParams(String baseUrl, Map<String, String> params) {
        if (params == null || params.isEmpty()) return baseUrl;

        StringBuilder sb = new StringBuilder(baseUrl);
        sb.append("?");

        params.forEach((key, value) -> {
            String encodedKey = URLEncoder.encode(key, StandardCharsets.UTF_8);
            String encodedValue = URLEncoder.encode(value, StandardCharsets.UTF_8);
            sb.append(encodedKey).append("=").append(encodedValue).append("&");
        });

        sb.setLength(sb.length() - 1); // remove último "&"
        return sb.toString();
    }
}