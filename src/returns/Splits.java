package returns;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;

public class Splits {

    public static JsonArray getSplitJsonArray(String ticker, String finalDate, String initialDate)
            throws MalformedURLException, IOException {
        //Parametros API:
        String baseUrl = "https://financialmodelingprep.com";
        String resource = "/api/v3/historical-price-full/stock_split/";

        String fromParam = "&from=";
        String toParam = "&to=";

        String api_param = "?apikey=";
        String api_token = "bd8817f368b307449ba7fba75a15d7db";
        String endpoint = baseUrl + resource + ticker + api_param + api_token + fromParam + initialDate + toParam + finalDate;
        System.out.println("Splits endpoint: " + endpoint);
        URL url = new URL(endpoint);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        InputStreamReader json = new InputStreamReader(url.openStream());
        Gson gson = new Gson();
       
        
        //Para extraer los datos necesitamos convertir a JsonObject
        JsonObject splitsData = gson.fromJson(json, JsonObject.class);
        JsonArray historicalSplits;
        
         /*Falta controlar un posible error de conexion, en vez de que el json esté vacío. 
        Si la conexion fue correcta, pero está vacío, entonces se crea un array de splits neutro. 
        Pero si no se recibe json por error de conexion o petición, se creará un split neutro cuando no debería*/
        try {
            historicalSplits = splitsData.get("historical").getAsJsonArray();
        } catch (NullPointerException e) {
            JsonArray jobj = new JsonArray();

            JsonObject item = new JsonObject();
            item.addProperty("date", "2021-05-10");
            item.addProperty("label", "May 10, 21");
            item.addProperty("numerator", 1);
            item.addProperty("denominator", 1);
            jobj.add(item);

            historicalSplits = jobj;
        }
        return historicalSplits ;
    }

    


public static float[] getSplits(JsonArray json) {
        float[] splits = new float[json.size()];
        for (int i = 0; i < json.size(); i++) {
            JsonObject obj = (json.get(i)).getAsJsonObject();
            float numerator = obj.get("numerator").getAsFloat();
            float denominator = obj.get("denominator").getAsFloat();
            /*El numerador indica acciones antiguas y el denominador acciones nuevas 
            por cada una antigua. Entonces, un ratio de 7 indica que hay 7 veces más 
            de acciones en circulación. Hay que multiplicar los dividendos nuevos por 7.*/
            float ratio = numerator / denominator;
            splits[i] = ratio;
        }
        return splits;
    }

    /*Para ajustar por splits, necesitamos el array a ajustar, sus fechas, los splits 
    y las fechas de los splits*/
    public static float[] adjustForSplits(String[] dividendDates, float[] dividends,
            String[] splitDates, float[] splits) throws ParseException {
        float[] adjustedForSplits = new float[dividends.length];
        System.arraycopy(dividends, 0, adjustedForSplits, 0, dividends.length);
        //Es necesario convertir las fechas a valores comparables:
        Date[] dividendD = new Date[dividendDates.length];
        Date[] splitD = new Date[splitDates.length];
        for (int i = 0; i < dividends.length; i++) {
            dividendD[i] = new SimpleDateFormat("yyyy-MM-dd").parse(dividendDates[i]);
        }
        for (int j = 0; j < splits.length; j++) {
            splitD[j] = new SimpleDateFormat("yyyy-MM-dd").parse(splitDates[j]);
        }
        /*Con dos bucles se comparan las fechas de todos los dividendos con las de todos 
        los splits. Si la fecha del split es menor, entonces hay que ajustar el dividendo.
        Es posible que un dividendo sea ajustado varias veces, reflejando la acumulación de 
        splits previos*/
        for (int i = 0; i < splitD.length; i++) {
            for (int j = 0; j < dividendD.length; j++) {
                if (splitD[i].getTime() > dividendD[j].getTime()) {
                    adjustedForSplits[j] = adjustedForSplits[j] / splits[i];
                }
            }

        }
        return adjustedForSplits;
    }

    public static String[] getSplitDates(JsonArray json) {
        String[] splitDates = new String[json.size()];
        for (int i = 0; i < json.size(); i++) {
            JsonObject obj = (json.get(i)).getAsJsonObject();
            splitDates[i] = obj.get("date").getAsString();
        }
        return splitDates;
    }

    /*public static void main(String[] args) throws MalformedURLException, IOException, ParseException {
        String ticker = "AAPL";
        String fDateUser = "2020-03-01";
        String iDateUser = "2000-03-07";

        JsonArray splitArr = Splits.getSplitJsonArray(ticker, fDateUser, iDateUser);
        JsonArray div = Dividends.getDividendJsonArray(ticker, fDateUser, iDateUser);

        float[] splits = Splits.getSplits(splitArr);
        String[] splitDates = Splits.getSplitDates(splitArr);

        String[] dividendDates = Dividends.getDividendDates(div);
        System.out.println("Fechas dividendos:");
        System.out.println(Arrays.toString(dividendDates));

        float[] dividends = Dividends.getDividends(div);
        System.out.println("Dividendos sin ajustar:");
        System.out.println(Arrays.toString(dividends));

        float[] adjustedDividends = Splits.adjustForSplits(dividendDates, dividends, splitDates, splits);

        System.out.println(Arrays.toString(splitDates));
        System.out.println(Arrays.toString(splits));

        System.out.println("Dividendos ajustados:");
        System.out.println(Arrays.toString(adjustedDividends));
    }*/
}
