package returns;

import returns.gitignore.gitignore;
import com.google.gson.JsonArray;
import java.io.IOException;
import java.net.ProtocolException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import static returns.Returns.getPriceDates;
import static returns.Returns.getPrices;

public class Stock {
    //Obtención de dividendos de un valor:
    public float [] stockDividends (String ticker, String fDate, String iDate, JsonArray divArr, JsonArray splitArr) throws IOException, ParseException{
        String[] dividendDates = Dividends.getDividendDates(divArr);
        float[] dividends = Dividends.getDividends(divArr);
        
        float[] splits = Splits.getSplits(splitArr);
        String[] splitDates = Splits.getSplitDates(splitArr);
        
        float[] adjustedDividends = Splits.adjustForSplits(dividendDates, dividends, splitDates, splits);
        
        return adjustedDividends;
    }
    //Obtención de precios:
    public static void updateDDS (String ticker, String fDate, String iDate) throws ProtocolException, IOException{
        JsonArray jsonArr = Returns.getPriceJsonArray(ticker, fDate, iDate);
        JsonArray divArr = Dividends.getDividendJsonArray(ticker, fDate, iDate);
        JsonArray splitArr = Splits.getSplitJsonArray(ticker, fDate, iDate);
        
        float [] prices = Returns.getPrices(jsonArr);
        String [] priceDates = Returns.getPriceDates(jsonArr);
        
        try {       
            //Apertura de conexion:
            Connection con = iniciarConexion();
            PreparedStatement pst = con.prepareStatement("INSERT INTO DDS (DATE_DAY, PRICE) VALUES (?, ?);");
            con.setAutoCommit(false);
           
            //Añadimos todas las setencias del ArrayList a la batería de queries:
            for (int i = 0; i < prices.length; i++) {
                pst.setString(1, priceDates[i]);
                pst.setFloat(2, prices[i]);
                pst.addBatch();
            }

            //Recogemos los datos de filas modificadas por cada query y los mostramos:
            int[] registrosAfectados = pst.executeBatch();
            for (int i = 0; i < registrosAfectados.length; i++) {
                System.out.println("Filas modificadas: " + registrosAfectados[i]);
            }
            //Se confirma la transacción a la base de datos:
            con.commit();
            con.close();
            //Se limpia el ArrayList para una sesión posterior:

        } catch (SQLException ex) {
            System.err.print("SQLException: " + ex.getMessage());
        }
    }
    //Método para iniciar conexión a la base de datos:
    private static Connection iniciarConexion() {
        try {
            String url = "jdbc:mysql://localhost:3306/STOCKQUERIES";
            String user = gitignore.user;
            String pass = gitignore.pass;
            //Apertura de conexion:
            Connection con = DriverManager.getConnection(url, user, pass);
            return con;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage()
                    + ". >>> Error de Conexion 2!!");
        }
        return null;
    }
    //Método main para actualizar datos de stocks. No se interactúa con el usuario:
    public static void main (String [] args) throws IOException{
        Stock.updateDDS("DDS", "2021-06-08", "1995-01-01");
    }
}
