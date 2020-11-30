package ru.digitalhabbits.homework1.service;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import static org.slf4j.LoggerFactory.getLogger;

public class WikipediaClient {
    private static final Logger logger = getLogger(WikipediaClient.class);
    public static final String WIKIPEDIA_SEARCH_URL = "https://en.wikipedia.org/w/api.php";

    @Nonnull
    public String search(@Nonnull String searchString) {
        final URI uri = prepareSearchUrl(searchString);
        // TODO: NotImplemented
        HttpGet request = new HttpGet(uri);
        RequestConfig localConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.STANDARD)
                .build();
        request.setConfig(localConfig);
        request.addHeader("Accept", "application/json");
        request.addHeader("Accept-Charset", "utf-8");
        String text = "";

        try(CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(request)) {

            HttpEntity entity = response.getEntity();

            try(BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()))) {
                String json = reader.readLine();
                JSONArray jsonArray = JsonPath.parse(json).read( "$.query.pages.*.extract");
                text = jsonArray.get(0).toString();
                text = text.replaceAll("\\\\n", "\n").toLowerCase();
            }
        } catch (IOException ex) {
            logger.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
        return text;
    }

    @Nonnull
    private URI prepareSearchUrl(@Nonnull String searchString) {
        try {
            return new URIBuilder(WIKIPEDIA_SEARCH_URL)
                    .addParameter("action", "query")
                    .addParameter("format", "json")
                    .addParameter("titles", searchString)
                    .addParameter("prop", "extracts")
                    .addParameter("explaintext", "")
                    .build();
        } catch (URISyntaxException exception) {
            throw new RuntimeException(exception);
        }
    }
}
