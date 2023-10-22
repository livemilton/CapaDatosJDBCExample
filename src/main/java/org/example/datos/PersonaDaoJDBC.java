package org.example.datos;


import org.example.domain.PersonaDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonaDaoJDBC implements PersonaDao{

    //Conexion transaccion, extraer la conexion para usar una transaccion

    private Connection conexionTransaccional;

    //query para definir los llamados a la BBDD con parametros que se van a sustituir ?
    private static final String SQL_SELECT= "SELECT id_persona, nombre, apellido, email, telefono FROM persona";
    private static final String SQL_INSERT= "INSERT INTO persona(nombre,apellido,email,telefono) VALUES(?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE persona SET nombre=?, apellido=?, email=?, telefono=? WHERE id_persona=?";
    private static final String SQL_DELETE = "DELETE FROM persona WHERE id_persona=?";

    //Conexion transaccion, constructor vacio y con atributo de la clase
    public PersonaDaoJDBC(){

    }

    public PersonaDaoJDBC(Connection conexionTransaccional){
        this.conexionTransaccional = conexionTransaccional;
    }

    //METODO PARA MODIFICAR EL REGISTRO EN LA BBDD
    public List<PersonaDTO> select() throws SQLException {

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs =null;
        PersonaDTO personaDTO = null;
        //Agregar los objetos del rs a la lista
        List<PersonaDTO> personaDTOS = new ArrayList<PersonaDTO>();

        try {

            //VALIDACION PARA USAR LA CONEXION TRANSACCIONAL
            conn= this.conexionTransaccional !=null ? this.conexionTransaccional:Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs= stmt.executeQuery();
            while(rs.next()){
                int id_persona = rs.getInt("id_persona");
                String nombre= rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String email = rs.getString("email");
                String telefono = rs.getString("telefono");

                personaDTO = new PersonaDTO();
                personaDTO.setId_persona(id_persona);
                personaDTO.setNombre(nombre);
                personaDTO.setApellido(apellido);
                personaDTO.setEmail(email);
                personaDTO.setTelefono(telefono);
                personaDTOS.add(personaDTO);
            }

        }
        finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            //validacion para cerrar la transaccion
            if (this.conexionTransaccional == null){
                Conexion.close(conn);
            }

        }
        return personaDTOS;
    }

    //METODO PARA INSERTAR PERSONAS
    public int insert(PersonaDTO personaDTO) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        //ResultSet rs = null;
        int rows = 0;

        try {
            //VALIDACION PARA USAR LA CONEXION TRANSACCIONAL
            conn= this.conexionTransaccional !=null ? this.conexionTransaccional:Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, personaDTO.getNombre());
            stmt.setString(2, personaDTO.getApellido());
            stmt.setString(3, personaDTO.getEmail());
            stmt.setString(4, personaDTO.getTelefono());

            System.out.println("ejecutando query: "+ SQL_INSERT);
            rows= stmt.executeUpdate();
            System.out.println("Registros afectados: "+ rows);

        }

        finally {
            Conexion.close(stmt);
            //validacion para cerrar la transaccion
            if (this.conexionTransaccional == null){
                Conexion.close(conn);
            }
        }
    return rows;
    }

    //METODO PARA ACTUALIZAR REGISTROS DE LA DB
    //INICIALIZAR VARIABLES Y CONEXION
    public int update(PersonaDTO personaDTO) throws SQLException {
        Connection conn =null;
        PreparedStatement stmt = null;
        int rows =0;

        try {
            //VALIDACION PARA USAR LA CONEXION TRANSACCIONAL
            conn= this.conexionTransaccional !=null ? this.conexionTransaccional:Conexion.getConnection();
            System.out.println("ejecutando query: " + SQL_UPDATE);
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, personaDTO.getNombre());
            stmt.setString(2, personaDTO.getApellido());
            stmt.setString(3, personaDTO.getEmail());
            stmt.setString(4, personaDTO.getTelefono());
            stmt.setInt(5, personaDTO.getId_persona());

            rows= stmt.executeUpdate();
            System.out.println("Registros actualizados: "+ rows);


        }
        finally {

            Conexion.close(stmt);
            //validacion para cerrar la transaccion
            if (this.conexionTransaccional == null){
                Conexion.close(conn);
            }

        }
        return rows;
    }

    //METODO PARA ELIMINAR DE LA BBDD
    public int delete(PersonaDTO personaDTO) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows=0;

        try {
            //VALIDACION PARA USAR LA CONEXION TRANSACCIONAL
            conn= this.conexionTransaccional !=null ? this.conexionTransaccional:Conexion.getConnection();
            System.out.println("Ejecutando query: "+ SQL_DELETE);
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, personaDTO.getId_persona());
            rows=stmt.executeUpdate();
            System.out.println("Registros eliminados: "+ rows);


        }
        finally {

            Conexion.close(stmt);
            //validacion para cerrar la transaccion
            if (this.conexionTransaccional == null){
                Conexion.close(conn);
            }
        }
        return rows;
    }

}
