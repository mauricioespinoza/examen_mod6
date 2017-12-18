package sql;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    public Conexion() {
    }
    public static Connection getConexion() {
        Connection connection = null;
        try {
           String driverClassName = "com.mysql.jdbc.Driver";
           String driverUrl="jdbc:mysql://localhost/examen_mod6";
           Class.forName(driverClassName);
           connection = DriverManager.getConnection(driverUrl, "root","");
           //System.out.println("Conexion exitosa!");
        }catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
