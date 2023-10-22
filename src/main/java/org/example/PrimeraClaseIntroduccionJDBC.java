package org.example;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PrimeraClaseIntroduccionJDBC {

    public static void main(String[] args) {
        //Conexion a bbdd
        //Paso 1. Creamos nuestra cadena de conexion a mysql
        String url = "jdbc:mysql://localhost:3306/test";


        try {
            //Paso 2. Creamos el objeto de conexion a la base de datos
            Connection conexion = DriverManager.getConnection(url, "root","password");

            //Paso 3. Creamos un objeto de tipo Statement
            Statement instruccion = conexion.createStatement();

            //Paso 4. Creamos el query
            String sql ="select id_persona, nombre, apellido, email, telefono from persona";

            //Paso 5. Ejecucion del query
            ResultSet resultado = instruccion.executeQuery(sql);

            //Paso 6. Procesamos el resultado iterando con while y el metodo next
            while(resultado.next()){
                System.out.print("Id persona: "+ resultado.getInt(1));
                System.out.print(" Nombre: "+ resultado.getString(2));
                System.out.print(" Apellido: "+ resultado.getString(3));
                System.out.print(" Email: "+ resultado.getString(4));
                System.out.println(" Telefono: "+ resultado.getString(5));

            }
            //Cerramos cada objeto que se ha utilizado
            resultado.close();
            instruccion.close();
            conexion.close();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }



    }
}
