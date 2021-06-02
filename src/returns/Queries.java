package returns;

import java.sql.*;

import java.security.Timestamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.ResultSet;

public class Queries {

    public static String addStockQuery(Operation op, float accumulatedInflation, float annualInflationRate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String accumInflation = accumulatedInflation + "";
        String annualInflation = annualInflationRate + "";
        //Formamos la query correspondiente con los parámetros introducidos:
        String query = "INSERT INTO QUERIES (TICKER, INITIAL_DATE, FINAL_DATE, "
                + "SUM_DIVIDENDS, TIME_PERIOD, ACCUM_RETURN, ACCUM_INFLATION, ANNUAL_RETURN, ANNUAL_INFLATION, QUERY_DATE) VALUES ('"
                + op.ticker + "', '" + op.initialDate + "', '" + op.finalDate + "', '" + op.sumDividends
                + "', '" + op.time + "', '" + op.accumulatedReturn + "', '" + accumInflation + "', '" 
                + op.annualReturn + "', '" + annualInflation + "', '" + dtf.format(now) + "'); ";
        
        //Falta corregir el formato de la hora actual (ultimo campo).
        return query;
    }

    public static String addStock() {
        String query = "";
        return query;
    }

    public static String addClient() {
        String query = "";
        return query;
    }

    public static void executeQueries(String query1) {
        try {
            //Datos para abrir conexion con MySQL:
            String url = "jdbc:mysql://localhost:3306/stockqueries";
            String user = "root";
            String pass = "adminenrigsa";
            //Apertura de conexion:
            Connection con = DriverManager.getConnection(url, user, pass);
            Statement stmt = con.createStatement();
            con.setAutoCommit(false);
            //Añadimos todas las setencias del ArrayList a la batería de queries:

            stmt.addBatch(query1);

            //Recogemos los datos de filas modificadas por cada query y los mostramos:
            int[] registrosAfectados = stmt.executeBatch();
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

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
    }
}
