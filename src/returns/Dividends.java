package returns;

import returns.gitignore.gitignore;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;

public class Dividends {

    public static JsonArray getDividendJsonArray(String ticker, String finalDate, String initialDate)
            throws MalformedURLException, IOException {
        //Parametros API:
        String baseUrl = "https://financialmodelingprep.com";
        String resource = "/api/v3/historical-price-full/stock_dividend/";

        String fromParam = "&from=";
        String toParam = "&to=";

        String api_param = "?apikey=";
        String api_token = gitignore.api_tokenFM;
        String endpoint = baseUrl + resource + ticker + api_param + api_token + fromParam + initialDate + toParam + finalDate;

        URL url = new URL(endpoint);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        InputStreamReader json = new InputStreamReader(url.openStream());
        Gson gson = new Gson();

        //Para extraer los datos necesitamos convertir a JsonObject
        JsonObject dividendData = gson.fromJson(json, JsonObject.class);
        JsonArray historicalDividends;

        //En caso de que no haya dividendos, capturamos la excepción creando un array con valor 0:
        try {
            historicalDividends = dividendData.get("historical").getAsJsonArray();
        } catch (NullPointerException e) {
            JsonArray jobj = new JsonArray();

            JsonObject item = new JsonObject();
            item.addProperty("date", "2021-05-10");
            item.addProperty("label", "May 10, 21");
            item.addProperty("dividend", 0);

            jobj.add(item);

            historicalDividends = jobj;
        }
        return historicalDividends;
    }
    //Obtención de la serie de dividendos:
    public static float[] getDividends(JsonArray json) {
        float[] dividends = new float[json.size()];
        for (int i = 0; i < json.size(); i++) {
            JsonObject obj = (json.get(i)).getAsJsonObject();
            dividends[i] = obj.get("dividend").getAsFloat();
        }
        return dividends;
    }

    public static String[] getDividendDates(JsonArray json) {
        String[] dividendDates = new String[json.size()];

        for (int i = 0; i < json.size(); i++) {
            JsonObject obj = (json.get(i)).getAsJsonObject();
            dividendDates[i] = obj.get("date").getAsString();
        }
        return dividendDates;
    }

    public static float getSumDividends(float[] dividends) {
        float sumDividends = 0;
        for (float element : dividends) {
            sumDividends = sumDividends + element;
        }
        return sumDividends;
    }
}
