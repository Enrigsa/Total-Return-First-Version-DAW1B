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
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.io.*;
import javax.swing.*;

public class Returns {

    //Obtención de arrays de precios:
    public static JsonArray getPriceJsonArray(String ticker, String finalDate, String initialDate)
            throws MalformedURLException, ProtocolException, IOException {
        //Parametros API:
        String baseUrl = "https://financialmodelingprep.com";
        String resource = "/api/v3/historical-price-full/";

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
        JsonObject data = gson.fromJson(json, JsonObject.class);

        String symbol = data.get("symbol").getAsString();

        JsonArray historical = data.get("historical").getAsJsonArray();
        //System.out.println(historical.toString());

        return historical;
    }

    //Obtención de precios del array:
    public static float[] getPrices(JsonArray json) {

        float[] prices = new float[json.size()];
        for (int i = 0; i < json.size(); i++) {
            JsonObject obj = (json.get(i)).getAsJsonObject();
            prices[i] = obj.get("close").getAsFloat();
        }
        return prices;
    }

    //Obtención de fechas:
    public static String[] getPriceDates(JsonArray json) {
        String[] priceDates = new String[json.size()];

        for (int i = 0; i < json.size(); i++) {
            JsonObject obj = (json.get(i)).getAsJsonObject();
            priceDates[i] = obj.get("date").getAsString();
        }
        return priceDates;
    }

    //Obtención del precio final:
    public static float getFinalPrice(float[] prices) {
        float finalPrice = prices[0];
        return finalPrice;
    }

    //Obtención del precio inicial:
    public static float getInitialPrice(float[] prices) {
        float initialPrice = prices[prices.length - 1];
        return initialPrice;
    }

    //Cálculo de rentabilida acumulada:
    public static float calculateReturn(float initialPrice, float finalPrice, float sumDividends) {
        return (finalPrice + sumDividends) / initialPrice;
    }

    //Cálculo de rentabilidad anual:
    public static float annualReturn(float initialPrice, float finalPrice, float sumDividends, float time) {
        return (float) Math.pow(calculateReturn(initialPrice, finalPrice, sumDividends), 1 / time);
    }
}
