package org.example.datos;



import org.example.domain.PersonaDTO;
import org.example.domain.UsuarioDTO;

import java.sql.SQLException;
import java.util.List;

public interface UsuarioDao {

    public List<UsuarioDTO> select() throws SQLException;

    public int insert(UsuarioDTO usuario) throws SQLException;

    public int update(UsuarioDTO usuario) throws SQLException;

    public int delete(UsuarioDTO usuaario) throws SQLException;

}
