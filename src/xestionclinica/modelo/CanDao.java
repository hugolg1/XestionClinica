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
public class CanDao {

    private  Connection conexion;
    private  PreparedStatement pSt;
    private  ResultSet rs;
    private  String error;
    
    
    public  boolean existeCan(String chip) {
        boolean resultado = false;
        conexion = null;
        pSt = null;
        rs = null;        
        conexion = Conexion.getConexion();
        try {
            pSt = conexion.prepareStatement("SELECT COUNT(*) FROM CANS WHERE chip = ?");
            pSt.setString(1, chip);
            rs = pSt.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) > 0) {
                    resultado = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resultado = false;
        }finally{
            try {
                rs.close();
                pSt.close();
            } catch (SQLException ex) {                
            }
        }

        return resultado;
    }
    public  boolean existeRaza(String nomeRaza) {
        boolean resultado = false;
        conexion = null;
        pSt = null;
        rs = null;        
        conexion = Conexion.getConexion();
        try {
            pSt = conexion.prepareStatement("SELECT COUNT(*) FROM RAZAS WHERE descripcion = ?");
            pSt.setString(1, nomeRaza);
            rs = pSt.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) > 0) {
                    resultado = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resultado = false;
        }finally{
            try {
                rs.close();
                pSt.close();
            } catch (SQLException ex) {                
            }
        }

        return resultado;
    }

    public ArrayList<RazaVo> getRazas() {
        ArrayList<RazaVo> resultado = new ArrayList();
        conexion = null;
        pSt = null;
        rs = null;        
        conexion = Conexion.getConexion();
        try {
            pSt = conexion.prepareStatement("SELECT * FROM RAZAS");
            rs = pSt.executeQuery();
            while (rs.next()) {
                resultado.add(new RazaVo(rs.getInt("codRaza"), rs.getString("descripcion")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                pSt.close();
            } catch (Exception ex) {
            }
        }
        return resultado;
    }
    
    
    public ArrayList<CanVo> getCansDePropietario(String dniProp){
        ArrayList<CanVo> resultado = new ArrayList();
        conexion = null;
        pSt = null;
        rs = null;
        conexion = Conexion.getConexion();
        try {
            pSt = conexion.prepareStatement("SELECT chip,nome,codRaza,dniPropietario FROM CANS WHERE dniPropietario = ?");
            pSt.setString(1,dniProp);
            rs = pSt.executeQuery();
            while(rs.next()){
                resultado.add(new CanVo(rs.getString(1),rs.getString(2),rs.getInt(3),rs.getString(4)));
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                rs.close();
                pSt.close();
            } catch (SQLException ex) {                
            }
        } 
        return resultado;
    }

    public void añadirCan(CanVo can) throws SQLException {
        conexion = null;
        pSt = null;
        error = null;        
        conexion = Conexion.getConexion();
        try {
            pSt = conexion.prepareStatement("INSERT INTO CANS (chip,nome,codRaza,dniPropietario) VALUES (?,?,?,?)");
            pSt.setString(1, can.getChip());
            pSt.setString(2, can.getNome());
            pSt.setInt(3, can.getCodRaza());
            pSt.setString(4, can.getDniPropietario());
            pSt.executeUpdate();
            conexion.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            error = "Error o añadir o can " + can.getNome();
            try {
                conexion.rollback();
            } catch (SQLException s) {
            }
        } finally {
            try {
                pSt.close();
            } catch (Exception e) {
            }
        }
        if (error != null) {
            throw new SQLException(error);
        }
    }
    
    public int añadirRaza(String nombre) throws SQLException{
        conexion = Conexion.getConexion();
        pSt = null;        
        rs = null;
        error = null;
        int idResultado=0;
        try {
            pSt = conexion.prepareStatement("INSERT INTO RAZAS (descripcion) VALUES (?)",PreparedStatement.RETURN_GENERATED_KEYS);
            pSt.setString(1, nombre);
            pSt.executeUpdate();
            rs = pSt.getGeneratedKeys();
            rs.next();
            idResultado = rs.getInt(1);           
            conexion.commit();            
        } catch (SQLException e) {
            e.printStackTrace();
            error = "Error al añadir raza";
            try {
                conexion.rollback();
            } catch (SQLException ex) {                
            }
        }finally{            
            try {
                pSt.close();
            } catch (Exception ex) {                
            }
        }
        if(error != null){            
            throw new SQLException(error);
        }
        return idResultado;
    }

}
