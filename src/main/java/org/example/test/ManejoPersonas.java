package org.example.test;

import org.example.datos.Conexion;
import org.example.datos.PersonaDao;
import org.example.datos.PersonaDaoJDBC;
import org.example.domain.PersonaDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ManejoPersonas {

    public static void main(String[] args) {

        Connection conexion = null;

        //Obtener la conexion y manejar la excepcion
        try {
            conexion = Conexion.getConnection();
            if(conexion.getAutoCommit()){
                conexion.setAutoCommit(false);
            }

            //objeto persona y pasar el objeto conexion del constructor Persona JDBC para transaccion
            PersonaDao personaDao = new PersonaDaoJDBC(conexion);


           //probar el metodo select
            List<PersonaDTO> personas = personaDao.select();

            for(PersonaDTO persona: personas){
                System.out.println("Persona DTO: " + persona);
            }

            //si funciona le metodo hacer commit
            conexion.commit();
            System.out.println("Se ha hecho commit de la transaccion");

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            System.out.println("Entramos al rollback");
            try {
                conexion.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace(System.out);
            }
        }
    }
}
