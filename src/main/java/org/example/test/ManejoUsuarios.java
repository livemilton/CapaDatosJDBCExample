package org.example.test;

import org.example.datos.Conexion;
import org.example.datos.UsuarioDao;
import org.example.datos.UsuarioDaoJDBC;
import org.example.domain.UsuarioDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ManejoUsuarios {

    public static void main(String[] args) {

        Connection conexion = null;

        //Obtener la conexion y manejar la excepcion
        try {
            conexion = Conexion.getConnection();
            if(conexion.getAutoCommit()){
                conexion.setAutoCommit(false);
            }

            UsuarioDao usuarioDao = new UsuarioDaoJDBC();

            //Ejecutando el listado de usuarioDTOS
            List<UsuarioDTO> usuarioDTOS = usuarioDao.select();
            for(UsuarioDTO usuarioDTO : usuarioDTOS){
                System.out.println("UsuarioDTO:" + usuarioDTO);
            }

            //si funciona le metodo hacer commit
            conexion.commit();
            System.out.println("Se ha hecho commit de la transaccion");

            //Insertamos un nuevo  usuarioDTO
            /*
            UsuarioDTO usuarioDTO = new UsuarioDTO("Pepito.Perez","1234");
            usuarioDaoJDBC.insert(usuarioDTO);

            //si funciona le metodo hacer commit
            conexion.commit();
            System.out.println("Se ha hecho commit de la transaccion");
            */
            //Modificar un usuarioDTO existente
            /*
            UsuarioDTO usuarioDTO = new UsuarioDTO(3, "Hilda.Muñoz", "1234");
            usuarioDaoJDBC.update(usuarioDTO);
            */
            //Eliminar un usuarioDTO existente
            //usuarioDaoJDBC.delete(new UsuarioDTO(3));



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
