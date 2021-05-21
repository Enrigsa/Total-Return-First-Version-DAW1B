package returns;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class Dates {
    public static String getFinalDate(JsonArray historical){
        JsonObject fData = (historical.get(0)).getAsJsonObject();
        String fDate = fData.get("date").getAsString();
        return fDate;
    }
    
    public static String getInitialDate(JsonArray historical){
        JsonObject iData = (historical.get(historical.size() - 1)).getAsJsonObject();
        String iDate = iData.get("date").getAsString();
        return iDate;
    }
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
    public static void main (String [] args) throws ParseException{
        String finalDate = "2020-03-01";
        String initialDate = "2017-04-07";
        
        System.out.println(Dates.getDiffInYears(initialDate, finalDate));
        
    }
}
