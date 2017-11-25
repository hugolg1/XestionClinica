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
public class VacinaDao {

    private Connection conexion;
    private PreparedStatement pSt;
    private ResultSet rs;

    public ArrayList<VacinaVo> getVacinas() {
        ArrayList<VacinaVo> resultado = new ArrayList();
        conexion = null;
        pSt = null;
        rs = null;
        conexion = Conexion.getConexion();
        try {
            pSt = conexion.prepareStatement("SELECT codVacina,nomeVacina,numTotalDoses FROM VACINAS");
            rs = pSt.executeQuery();
            while (rs.next()) {
                resultado.add(new VacinaVo(rs.getInt(1), rs.getString(2), rs.getInt(3)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                pSt.close();
            } catch (SQLException ex) {
            }
        }
        return resultado;
    }

    public ArrayList<VacinacionsVo> getVacinacionsCan(String chip) {
        ArrayList<VacinacionsVo> resultado = new ArrayList();
        conexion = null;
        pSt = null;
        rs = null;
        conexion = Conexion.getConexion();
        try {
            String sentencia = "SELECT v.codVacina,v.nomeVacina,v.numTotalDoses,vas.dose,vas.data,vas.observacions FROM VACINACIONS as vas JOIN VACINAS v "
                    + "on v.codVacina = vas.codVacina WHERE chip = ? order by v.nomeVacina,vas.dose asc;";
            pSt = conexion.prepareStatement(sentencia);
            pSt.setString(1, chip);
            rs = pSt.executeQuery();
            VacinacionsVo vacinacion;
            while (rs.next()) {
                vacinacion = new VacinacionsVo();
                vacinacion.setVacina(new VacinaVo(rs.getInt(1),rs.getString(2),rs.getInt(3)));                
                vacinacion.setDoseAplicada(rs.getInt(4));
                vacinacion.setData(rs.getDate(5));
                vacinacion.setObservacion(rs.getString(6));
                resultado.add(vacinacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                pSt.close();
            } catch (SQLException ex) {
            }
        }
        return resultado;
    }

    public boolean existenDosesPosteriores(int codVacina,String chip,int dose) {
        boolean resultado = false;
        conexion = null;
        pSt = null;
        rs = null;
        conexion = Conexion.getConexion();
        try {
            pSt = conexion.prepareStatement("SELECT COUNT(*) FROM VACINACIONS WHERE codVacina = ? and chip = ? and dose > ?");
            pSt.setInt(1, codVacina);
            pSt.setString(2, chip);
            pSt.setInt(3, dose);
            rs = pSt.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) > 0) {
                    resultado = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                pSt.close();
            } catch (SQLException ex) {
            }
        }
        return resultado;
    }

    public boolean eliminarVacinacion(int codVacina, String chip, int doseAplicada) {
        boolean resultado = true;
        conexion = null;
        pSt = null;
        conexion = Conexion.getConexion();
        try {
            pSt = conexion.prepareStatement("DELETE FROM VACINACIONS WHERE codVacina = ? AND chip = ? AND dose = ?");
            pSt.setInt(1, codVacina);
            pSt.setString(2, chip);
            pSt.setInt(3, doseAplicada);
            pSt.executeUpdate();
            conexion.commit();
        } catch (SQLException e) {
            resultado = false;
            e.printStackTrace();
            try {
                conexion.rollback();
            } catch (SQLException ex) {
            }
        } finally {
            try {
                pSt.close();
            } catch (SQLException ex) {
            }
        }
        return resultado;
    }

    public int getUltimaDoseAplicada(int codVacina, String chip) {
        int resultado = -1;
        conexion = null;
        pSt = null;
        rs = null;
        conexion = Conexion.getConexion();
        try {
            pSt = conexion.prepareStatement("SELECT dose FROM VACINACIONS WHERE codVacina = ? AND chip = ? order by dose desc limit 1");
            pSt.setInt(1, codVacina);
            pSt.setString(2, chip);
            rs = pSt.executeQuery();
            if(rs.next()){
                resultado = rs.getInt(1);
            }else{
                resultado = 0;
            } 
        } catch (SQLException e) {
            e.printStackTrace();
            resultado = -1;
        }finally{
            try {
                rs.close();
                pSt.close();
            } catch (SQLException ex) {                
            }
        }
        return resultado;        
    }

    public boolean aplicarVacinacion(VacinacionsVo vacinacion, String chip) {
        boolean resultado = false;
        conexion = null;
        pSt = null;
        conexion = Conexion.getConexion();
        try {
            pSt = conexion.prepareStatement("INSERT INTO VACINACIONS (chip,codVacina,data,observacions,dose) VALUES (?,?,?,?,?)");
            pSt.setString(1, chip);
            pSt.setInt(2, vacinacion.getVacina().getCodigo());
            pSt.setDate(3, vacinacion.getData());
            pSt.setString(4, vacinacion.getObservacion());
            pSt.setInt(5, vacinacion.getDoseAplicada());
            pSt.executeUpdate();
            conexion.commit();
            resultado = true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conexion.rollback();
            } catch (SQLException ex) {                
            }
        }finally{
            try {
                pSt.close();
            } catch (SQLException ex) {                
            }
        }
        return resultado;
    }

}
