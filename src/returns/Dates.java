package returns;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class Dates {
    //Fecha final:
    public static String getFinalDate(JsonArray historical){
        JsonObject fData = (historical.get(0)).getAsJsonObject();
        String fDate = fData.get("date").getAsString();
        return fDate;
    }
    //Fecha inicial:
    public static String getInitialDate(JsonArray historical){
        JsonObject iData = (historical.get(historical.size() - 1)).getAsJsonObject();
        String iDate = iData.get("date").getAsString();
        return iDate;
    }
    /*Cálculo del tiempo en años entre precio inicial y precio final. 
    Convierte las fechas a Date y devuelve un valor numérico:*/
    public static float getDiffInYears(String initialDate, String finalDate) throws ParseException{
        Date fDate = new SimpleDateFormat("yyyy-MM-dd").parse(finalDate);
        Date iDate = new SimpleDateFormat("yyyy-MM-dd").parse(initialDate);
        long diff = (fDate.getTime() - iDate.getTime());
        
        long daysDenominator = 60 * 60 * 1000;
        long yearsDenominator = 24 * 365;
        
        float diffInDays = diff / daysDenominator;
        float diffInYears = diffInDays / yearsDenominator;
        
        return diffInYears;
    }
}
