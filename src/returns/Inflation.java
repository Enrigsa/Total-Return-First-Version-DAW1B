package returns;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import static returns.Returns.calculateReturn;

public class Inflation {
    //Obtención de json para datos de inflación:
    public static JsonArray getCPIData(String initialDate, String finalDate) throws IOException {
        //Parametros API:
        String baseUrl = "https://api.stlouisfed.org";
        String resource = "/fred/series/observations";

        String series_idParam = "&series_id=";
        String series_idToken = "CPIAUCSL";

        String unitsParam = "&units=";
        String unitsToken = "lin";

        String frequencyParam = "&frequency=";
        String frequencyToken = "m";

        String observ_startParam = "&observation_start=";
        String observ_startToken = initialDate;

        String observ_endParam = "&observation_end=";
        String observ_endToken = finalDate;

        String sort_orderParam = "&sort_order=";
        String sort_orderToken = "asc";

        String fixedArguments = "&file_type=json";

        String api_param = "?api_key=";
        String api_token = "18dd4c4e808891aa83ff36a52e43d470";

        String endpoint = baseUrl + resource + api_param + api_token + series_idParam
                + series_idToken + unitsParam + unitsToken + frequencyParam + frequencyToken
                + observ_startParam + observ_startToken + observ_endParam + observ_endToken
                + sort_orderParam + sort_orderToken + fixedArguments;

        System.out.println(endpoint);

        URL url = new URL(endpoint);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        InputStreamReader json = new InputStreamReader(url.openStream());
        Gson gson = new Gson();

        //Para extraer los datos necesitamos convertir a JsonObject
        JsonObject data = gson.fromJson(json, JsonObject.class);

        JsonArray observations = data.get("observations").getAsJsonArray();
        System.out.println(data.get("count").getAsInt());
        return observations;
    }

    //Obtención de fechas para periodo de inflación:
    public static String[] getCPIDates(JsonArray jsonArr) {
        String[] CPIDates = new String[jsonArr.size()];
        for (int i = 0; i < jsonArr.size(); i++) {
            JsonObject obj = (jsonArr.get(i)).getAsJsonObject();
            CPIDates[i] = obj.get("date").getAsString();
        }
        return CPIDates;
    }

    //Obtención de los datos de IPC:
    public static float[] getInflationValues(JsonArray jsonArr) {
        float[] CPIValues = new float[jsonArr.size()];
        for (int i = 0; i < jsonArr.size(); i++) {
            JsonObject obj = (jsonArr.get(i)).getAsJsonObject();

            CPIValues[i] = obj.get("value").getAsFloat();
        }
        return CPIValues;
    }

    /*Método para actualizar la tabla con los datos de inflación. Debería ser llamado 
    una vez al mes por el administrador de la base de datos*/
    public static void updateCPITable(String[] CPIDates, float[] CPIValues) {
        try {
            //Datos para abrir conexion con MySQL:
            String url = "jdbc:mysql://localhost:3306/STOCKQUERIES";
            String user = "root";
            String pass = "adminenrigsa";
            //Apertura de conexion:
            Connection con = DriverManager.getConnection(url, user, pass);
            PreparedStatement pst = con.prepareStatement("INSERT INTO CPI_DATA (FIRST_DAY_MONTH, CPI_LEVEL) VALUES (?, ?);");
            con.setAutoCommit(false);
            //Añadimos todas las setencias del ArrayList a la batería de queries:
            for (int i = 0; i < CPIDates.length; i++) {
                pst.setString(1, CPIDates[i]);
                pst.setFloat(2, CPIValues[i]);
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
    
    public static ResultSet executeQuery(String query) {
        PreparedStatement pst = null;
        /* Probar con nueva metodología: https://stackoverflow.com/questions/9291619/jdbc-exception-operation-not-allowed-after-resultset-closed*/
        ResultSet rtdo = null;
        try {
            //Datos para abrir conexion con MySQL:
            String url = "jdbc:mysql://localhost:3306/STOCKQUERIES";
            String user = "root";
            String pass = "adminenrigsa";
            //Apertura de conexion:
            Connection con = DriverManager.getConnection(url, user, pass);
            pst = con.prepareStatement(query);
            con.setAutoCommit(false);

            rtdo = pst.executeQuery();

            con.close();
        } catch (SQLException ex) {
            System.err.print("SQLException: " + ex.getMessage());
        }
        return rtdo;
    }
//Método para iniciar conexión a la base de datos:
    private static Connection iniciarConexion() {
        try {
            String url = "jdbc:mysql://localhost:3306/STOCKQUERIES";
            String user = "root";
            String pass = "adminenrigsa";
            //Apertura de conexion:
            Connection con = DriverManager.getConnection(url, user, pass);
            return con;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage()
                    + ". >>> Error de Conexion 2!!");
        }
        return null;
    }

    /*Método para obtener la tasa de inflación anual durante un periodo concreto:*/
    public static float getAccumulatedInflation(String initialDate, String finalDate) throws SQLException, ParseException {
        PreparedStatement pst = null;
        //Datos a obtener:
        float accumulatedInflation = 0;
        float finalCPI = 0;
        float initialCPI = 0;
        String finalCPIDate = "";
        String initialCPIDate = "";
        try {
            Connection con = iniciarConexion();
            pst = con.prepareStatement("SELECT * FROM CPI_DATA WHERE FIRST_DAY_MONTH <= '"
                    + finalDate + "' ORDER BY FIRST_DAY_MONTH DESC LIMIT 1;");
            ResultSet rtdo1 = pst.executeQuery();
            System.out.println("Debugger 1A");
            //Importante: ¡Sin el while no funcionó la extracción de queries!
            while (rtdo1.next()) {
                finalCPI = (float) rtdo1.getFloat("CPI_LEVEL");
                finalCPIDate = rtdo1.getString("FIRST_DAY_MONTH");
            }
            System.out.println("Debugger 1B");
            con.close();
        } catch (SQLException ex) {
            System.err.print("SQLException: " + ex.getMessage());
        }
        //Obtención de valor 
        try {
            Connection con = iniciarConexion();
            pst = con.prepareStatement("SELECT * FROM CPI_DATA WHERE FIRST_DAY_MONTH >= '"
                    + initialDate + "' ORDER BY FIRST_DAY_MONTH ASC LIMIT 1;");
            ResultSet rtdo2 = pst.executeQuery();

            System.out.println("Debugger 2A");
            //Importante: ¡Sin el while no funcionó la extracción de queries!
            while (rtdo2.next()) {
                initialCPI = (float) rtdo2.getFloat("CPI_LEVEL");
                initialCPIDate = rtdo2.getString("FIRST_DAY_MONTH");
            }
            System.out.println("Debugger 2B");
            con.close();
        } catch (SQLException ex) {
            System.err.print("SQLException: " + ex.getMessage());
        }
        //Cálculo rentabilidad acumulada:
        accumulatedInflation = Returns.calculateReturn(initialCPI, finalCPI, 0);
        //Debugger:
        System.out.println("accumulated inflation is: " + accumulatedInflation);
        return accumulatedInflation;
    }
    //Cálculo de tasa de inflación anual en un periodo de tiempo:
    public static float getAnnualInflation(float accumulatedInflation, String initialDate, String finalDate) throws ParseException {
        float time = Dates.getDiffInYears(initialDate, finalDate);
        return (float) Math.pow(accumulatedInflation, 1 / time);
    }
    //Cálculo de rentabilidad real ajustada por inflación:
    public static float realReturn(float nominalReturn, float inflationRate) {
        return nominalReturn / inflationRate;
    }
    
    /*public static void main(String[] args) throws IOException, SQLException, ParseException {
        JsonArray CPI = Inflation.getCPIData("1970-01-01", "2021-05-20");
        String[] CPIDates = Inflation.getCPIDates(CPI);
        float[] CPIValues = Inflation.getInflationValues(CPI);
        Inflation.updateCPITable(CPIDates, CPIValues);
        Inflation.getAccumulatedInflation("2011-05-01", "2021-05-01");
    }*/
}
