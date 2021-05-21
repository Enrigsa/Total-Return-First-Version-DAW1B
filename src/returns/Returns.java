package returns;

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

    public static JsonArray getPriceJsonArray(String ticker, String finalDate, String initialDate)
            throws MalformedURLException, ProtocolException, IOException {
        //Parametros API:
        String baseUrl = "https://financialmodelingprep.com";
        String resource = "/api/v3/historical-price-full/";

        String fromParam = "&from=";
        String toParam = "&to=";

        String api_param = "?apikey=";
        String api_token = "bd8817f368b307449ba7fba75a15d7db";
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
        System.out.println(symbol);

        JsonArray historical = data.get("historical").getAsJsonArray();
        //System.out.println(historical.toString());

        return historical;
    }

    public static float[] getPrices(JsonArray json) {

        float[] prices = new float[json.size()];
        for (int i = 0; i < json.size(); i++) {
            JsonObject obj = (json.get(i)).getAsJsonObject();
            prices[i] = obj.get("close").getAsFloat();
        }
        return prices;
    }

    public static String[] getPriceDates(JsonArray json) {
        String[] priceDates = new String[json.size()];

        for (int i = 0; i < json.size(); i++) {
            JsonObject obj = (json.get(i)).getAsJsonObject();
            priceDates[i] = obj.get("date").getAsString();
        }
        return priceDates;
    }

    public static float getFinalPrice(float[] prices) {
        float finalPrice = prices[0];
        return finalPrice;
    }

    public static float getInitialPrice(float[] prices) {
        float initialPrice = prices[prices.length - 1];
        return initialPrice;
    }

    public static float calculateReturn(float initialPrice, float finalPrice, float sumDividends) {
        return (finalPrice + sumDividends) / initialPrice;
    }

    public static float annualReturn(float initialPrice, float finalPrice, float sumDividends, float time) {
        return (float) Math.pow(calculateReturn(initialPrice, finalPrice, sumDividends), 1 / time);
    }

    /*public static void main(String[] args) throws MalformedURLException, IOException, ParseException {
        
      
        
        String ticker = "DDS";
        String fDateUser = "2021-05-10";
        String iDateUser = "2000-03-07";
        
        //Obtención de los jsons para precios, dividendos y splits:
        JsonArray jsonArr = Returns.getPriceJsonArray(ticker, fDateUser, iDateUser);
        JsonArray divArr = Dividends.getDividendJsonArray(ticker, fDateUser, iDateUser);
        JsonArray splitArr = Splits.getSplitJsonArray(ticker, fDateUser, iDateUser);
        

        //Fechas de los precios final e inicial para calcular el tiempo:
        String finalDate = Dates.getFinalDate(jsonArr);
        String initialDate = Dates.getInitialDate(jsonArr);
        float time = Dates.getDiffInYears(initialDate, finalDate);
        //Obtención de la lista de precios sin ajustar y sus fechas:
        float[] prices = getPrices(jsonArr);
        String[] priceDates = getPriceDates(jsonArr);
        //Obtención de los dividendos sin ajustar y sus fechas:
        String[] dividendDates = Dividends.getDividendDates(divArr);
        float[] dividends = Dividends.getDividends(divArr);
        //Obtención de los splits para realizar los posteriores ajustes:
        float[] splits = Splits.getSplits(splitArr);
        String[] splitDates = Splits.getSplitDates(splitArr);

        
        //Ajuste de las series de precios y dividendos. Nuevos arrays:
        float[] adjustedDividends = Splits.adjustForSplits(dividendDates, dividends, splitDates, splits);
        float[] adjustedPrices = Splits.adjustForSplits(priceDates, prices, splitDates, splits);
        
        //Obtención de suma de dividendos:
        float sumDividends = Dividends.getSumDividends(adjustedDividends);
        
        //Calculo de rentabilidades:
        float accumulatedReturn = Returns.calculateReturn(adjustedPrices[adjustedPrices.length - 1], adjustedPrices[0], sumDividends);
        float annualReturn = Returns.annualReturn(adjustedPrices[adjustedPrices.length - 1], adjustedPrices[0], sumDividends, time);

        System.out.println("Precio ajustado inicial: " + adjustedPrices[0] + "$ por accion. Precio ajustado final: " + adjustedPrices[1] + "$ por accion");
        System.out.println("Suma de dividendos ajustados = " + sumDividends + "$ por accion");
        System.out.println("Periodo de tiempo: " + time + " años");
        System.out.println("Rentabilidad acumulada es " + (accumulatedReturn - 1) * 100 + "%");
        System.out.println("Annual return is: " + (annualReturn - 1) * 100 + "%");
    }*/
}
