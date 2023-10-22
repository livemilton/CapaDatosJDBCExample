package org.example.datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
public class Conexion {

    //Cadena de conexion a MYSQL
    //1. DEFINIR VARIABLES PARA LA CONEXION A LA BBDD
    private static final String JDBC_URL ="jdbc:mysql://localhost/test";
    private static final String JDBC_USER= "root";
    private static final String JDBC_PASS="password";

    //2. DEFINIR METODO PARA LA CONEXION A LA BBDD
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
    }

    //METODOS PARA CERRAR LA CONEXION RESULTSET
    public static void close(ResultSet rs){
        try {
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    //CERRAR DEMAS OBJETOS PREPAREDSTATEMENTE
    public static void close(PreparedStatement stmt){
        try {
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    //CERRAR OBJETO CONNECTION
    public static  void close(Connection conn){
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
