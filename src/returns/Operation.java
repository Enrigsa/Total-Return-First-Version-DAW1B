
package returns;

import com.google.gson.JsonArray;
import java.io.IOException;
import java.net.ProtocolException;
import java.text.ParseException;
import java.util.Arrays;
import static returns.Returns.getPriceDates;
import static returns.Returns.getPrices;

public class Operation {
    String sumDividends;
    String time;
    String accumulatedReturn;
    String annualReturn;
    String initialPrice;
    String finalPrice;
    Operation (float initialPrice, float finalPrice, float sumDividends, float time, float accumulatedReturn, float annualReturn){
        this.initialPrice = Operation.formatEuros(initialPrice);
        this.finalPrice = Operation.formatEuros(finalPrice);
        this.sumDividends = Operation.formatEuros(sumDividends);
        this.time = Operation.formatTime(time);
        this.accumulatedReturn = Operation.formatReturn(accumulatedReturn);
        this.annualReturn = Operation.formatReturn(annualReturn);
    }
    public static String formatReturn(float rawReturn){
        float formattedReturn = Math.round((rawReturn -1)*100*100);
        return formattedReturn/100 + "%";
    }
    public static String formatTime(float rawTime){
        float formattedTime = Math.round(rawTime * 100);
        return formattedTime/100 + " años";
    }
    public static String formatEuros(float amount){
        float formattedAmount = Math.round(amount *100);
        return formattedAmount/100 + "€ por accion";
    }
    public static Operation calculations(String ticker, String iDateUser, String fDateUser) throws ProtocolException, IOException, ParseException{
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

        System.out.println("Fecha inicial de dividendos: " + (dividendDates[dividendDates.length-1]));
        System.out.println("Fecha inicial de splits: " + (splitDates[splitDates.length-1]));
        //Ajuste de las series de precios y dividendos. Nuevos arrays:
        float[] adjustedDividends = Splits.adjustForSplits(dividendDates, dividends, splitDates, splits);
        
        
        //Obtención de suma de dividendos:
        float sumDividends = Dividends.getSumDividends(adjustedDividends);
        
        //Calculo de rentabilidades:
        float accumulatedReturn = Returns.calculateReturn(prices[prices.length - 1], prices[0], sumDividends);
        float annualReturn = Returns.annualReturn(prices[prices.length - 1], prices[0], sumDividends, time);
        
        System.out.println("Dividendos sin ajustar: " + Arrays.toString(dividends));
        System.out.println("Splits: " + Arrays.toString(splits));
        System.out.println("Dividendos ajustados: " + Arrays.toString(adjustedDividends));
        
        System.out.println("Precio inicial sin ajustar: " + prices[prices.length - 1] + "$ por accion. Precio final sin ajustar: " + prices[0] + "$ por accion");
        System.out.println("Precio ajustado inicial: " + prices[prices.length - 1] + "$ por accion. Precio ajustado final: " + prices[0] + "$ por accion");
        
        System.out.println("Suma de dividendos ajustados = " + sumDividends + "$ por accion");
        System.out.println("Periodo de tiempo: " + time + " años");
        System.out.println("Rentabilidad acumulada es " + (accumulatedReturn - 1) * 100 + "%");
        System.out.println("Annual return is: " + (annualReturn - 1) * 100 + "%");
        
        Operation op = new Operation (prices[prices.length - 1], prices[0], sumDividends, time, accumulatedReturn, annualReturn);
        return op;
    }
    /*public static void main (String [] args) throws IOException, ProtocolException, ParseException{
        Operation op = Operation.calculations("AAPL", "2011-05-30", "2021-05-20");
        System.out.println(op.annualReturn);
    }*/
}
