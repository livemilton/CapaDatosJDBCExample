package org.example.datos;

import org.example.domain.UsuarioDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDaoJDBC implements  UsuarioDao{

    //Conexion transaccion, extraer la conexion para usar una transaccion
    private Connection conexionTransaccional;

    private static final String SQL_SELECT = "SELECT id_usuario, username, password FROM usuario";
    private static final String SQL_INSERT = "INSERT INTO usuario(username, password) VALUES(?,?)";
    private static  final String SQL_UPDATE = "UPDATE usuario SET username=?, password=? WHERE id_usuario=?";
    private static final String SQL_DELETE = "DELETE FROM usuario WHERE id_usuario=?";

    //Conexion transaccion, constructor vacio y con atributo de la clase

    public UsuarioDaoJDBC(){}

    public UsuarioDaoJDBC(Connection conexionTransaccional){
        this.conexionTransaccional = conexionTransaccional;
    }



    //METODO PARA QUERY SELECT
    public List<UsuarioDTO> select() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt= null;
        ResultSet rs= null;
        UsuarioDTO usuarioDTO = null;
        List<UsuarioDTO> usuarioDTOS = new ArrayList<UsuarioDTO>();

        try {
            //VALIDACION PARA USAR LA CONEXION TRANSACCIONAL
            conn= this.conexionTransaccional !=null ? this.conexionTransaccional:Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs=stmt.executeQuery();
            while(rs.next()){
                int id_usuario = rs.getInt("id_usuario");
                String username = rs.getString("username");
                String password = rs.getString("password");

                usuarioDTO = new UsuarioDTO();
                usuarioDTO.setId_usuario(id_usuario);
                usuarioDTO.setUsername(username);
                usuarioDTO.setPassword(password);

                usuarioDTOS.add(usuarioDTO);
            }



        }finally {
            Conexion.close(stmt);
            Conexion.close(rs);
            //validacion para cerrar la transaccion
            if (this.conexionTransaccional == null){
                Conexion.close(conn);
            }
        }
        return usuarioDTOS;
    }

    //METODO PARA QUERY INSERT

    public int insert(UsuarioDTO usuarioDTO) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;


            try {
                //VALIDACION PARA USAR LA CONEXION TRANSACCIONAL
                conn= this.conexionTransaccional !=null ? this.conexionTransaccional:Conexion.getConnection();
                stmt = conn.prepareStatement(SQL_INSERT);
                stmt.setString(1, usuarioDTO.getUsername());
                stmt.setString(2, usuarioDTO.getPassword());

                System.out.println("ejecutando query: "+ SQL_INSERT);
                rows = stmt.executeUpdate();
                System.out.println("Registros afectados: "+ rows);


            }finally {
                Conexion.close(stmt);
                //validacion para cerrar la transaccion
                if (this.conexionTransaccional == null){
                    Conexion.close(conn);
                }
            }
            return rows;
    }

    //METODO PARA QUERY UPDATE
    public int update(UsuarioDTO usuarioDTO) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt= null;
        int rows =0;


        try {
            //VALIDACION PARA USAR LA CONEXION TRANSACCIONAL
            conn= this.conexionTransaccional !=null ? this.conexionTransaccional:Conexion.getConnection();
            System.out.println("ejecutando quey: "+ SQL_UPDATE);
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, usuarioDTO.getUsername());
            stmt.setString(2, usuarioDTO.getPassword());
            stmt.setInt(3, usuarioDTO.getId_usuario());

            rows = stmt.executeUpdate();
            System.out.println("Registros actualizado: "+ rows);


        }finally {

            Conexion.close(stmt);
            //validacion para cerrar la transaccion
            if (this.conexionTransaccional == null){
                Conexion.close(conn);
            }
        }
        return rows;

    }

    //METODO PARA QUERY DELETE
    public int delete(UsuarioDTO usuarioDTO) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt =null;
        int rows =0;

        try {
            //VALIDACION PARA USAR LA CONEXION TRANSACCIONAL
            conn= this.conexionTransaccional !=null ? this.conexionTransaccional:Conexion.getConnection();
            System.out.println("Ejecutando Query: " + SQL_DELETE);
            stmt= conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, usuarioDTO.getId_usuario());
            rows = stmt.executeUpdate();
            System.out.println("Registros elimiandos: " + rows);



        }finally {
            Conexion.close(stmt);
            //validacion para cerrar la transaccion
            if (this.conexionTransaccional == null){
                Conexion.close(conn);
            }
        }
    return rows;
    }

}
