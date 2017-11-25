/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xestionclinica.modelo;

import java.sql.Date;
import Utils.UtilsDate;

/**
 *
 * @author hugolg
 */
public class VacinacionsVo {
    
    private VacinaVo vacina;
    private int doseAplicada;
    private Date data;
    private String observacion;

    public VacinacionsVo() {
    }

    public VacinaVo getVacina() {
        return vacina;
    }

    public void setVacina(VacinaVo vacina) {
        this.vacina = vacina;
    }  

    public int getDoseAplicada() {
        return doseAplicada;
    }

    public void setDoseAplicada(int doseAplicada) {
        this.doseAplicada = doseAplicada;
    }

    public Date getData() {
        return data;
    } 

    public void setData(Date data) {
        this.data = data;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    @Override
    public String toString() {
        return vacina.getNome();
    }
    
    
    
}
