/*
 * Método para la conexión con la BD
 */
package ConexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ElyyzZ BaRruEtA
 */
public class Conexion {

    public static String ERROR = "";
    private String user = "ASPIRANTE_ITT";
    private String pass = "ASP1R4NT3";
    private String BaseDeDatos = "jdbc:oracle:thin:@192.168.40.103:1521:SIA";//desarrollo

    Connection conexion;

    /**
     * Metodo que permite la conexión con la base de datos mediante la libreria
     * ojdbc14 para la version 8 de Oracle
     *
     * @param usuario recibe el usuario de la base de datos
     * @param password recibe el password de la base de datos
     * @return un objeto de la clase Connection
     */
    public Conexion(String usuario, String password) {
        this.pass = password;
        this.user = usuario;
    }

    public Conexion() {
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        conexion = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            try {
                conexion = DriverManager.getConnection(BaseDeDatos, user, pass);
            } catch (SQLException ex) {
                System.out.println("///" + ex.getMessage() + "///");
            } finally {
                return conexion;
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("///" + ex.getMessage() + "///");
            conexion = null;
        } finally {
            return conexion;
        }
    }

    public void CerraConexion() throws SQLException {
        conexion.close();
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Conexion c = new Conexion();
        c.getConnection();

    }

}
