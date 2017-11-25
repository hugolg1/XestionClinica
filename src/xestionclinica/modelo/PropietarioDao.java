/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xestionclinica.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author hugolg
 */
public class PropietarioDao {

    private Connection conexion;
    private PreparedStatement pSt;
    private ResultSet rs;
    private String sentencia;
    private String error;
   

    public boolean existePropietario(String dni) {
        boolean resultado = false;
        conexion = Conexion.getConexion();
        pSt = null;
        rs = null;
        try {
            pSt = conexion.prepareStatement("SELECT COUNT(*) FROM PROPIETARIOS WHERE dni = ?");
            pSt.setString(1, dni);
            rs = pSt.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) > 0) {
                    resultado = true;
                }
            }
        } catch (SQLException e) {
            try {
                e.printStackTrace();
                resultado = false;
                rs.close();
                pSt.close();
            } catch (SQLException ex) {
            }
        }
        return resultado;
    }

    public ArrayList<PropietarioVo> getPropietarios() {
        ArrayList<PropietarioVo> resultado = new ArrayList();
        conexion = Conexion.getConexion();
        sentencia = "SELECT * FROM PROPIETARIOS";
        try (
                PreparedStatement pSt = conexion.prepareStatement(sentencia);
                ResultSet rs = pSt.executeQuery();) {
            PropietarioVo p;
            while (rs.next()) {
                p = new PropietarioVo();
                p.setDni(rs.getString("dni"));
                p.setNome(rs.getString("nome"));
                p.setApellido1(rs.getString("ap1"));
                p.setApellido2(rs.getString("ap2"));
                p.setTlf(rs.getString("tlf"));
                p.setEmail(rs.getString("eMail"));
                resultado.add(p);
            }
        } catch (SQLException pex) {
            pex.printStackTrace();
            
        }
        return resultado;
    }

    
    public void añadirPropietario(PropietarioVo p) throws SQLException {
        conexion = Conexion.getConexion();
        pSt = null;
        error = null;
        if (!existePropietario(p.getDni())) {
            try {
                pSt = conexion.prepareStatement("INSERT INTO PROPIETARIOS VALUES (?,?,?,?,?,?)");
                pSt.setString(1, p.getDni());
                pSt.setString(2, p.getNome());
                pSt.setString(3, p.getApellido1());
                pSt.setString(4, p.getApellido2());
                pSt.setString(5, p.getTlf());
                pSt.setString(6, p.getEmail());
                pSt.executeUpdate();
                conexion.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                conexion.rollback();
                error = "Error al añadir el propietario";
            } finally {
                pSt.close();
            }
        } else {
            error = "Ya existe un usuario con ese dni";
        }
        if (error != null) {
            throw new SQLException(error);
        }
    }

}
