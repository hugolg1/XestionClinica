/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xestionclinica.modelo;

import javax.swing.JDesktopPane;
import xestionclinica.controlador.Coordinador;
import xestionclinica.vista.InternalCansRazas;
import xestionclinica.vista.InternalPropietarios;
import xestionclinica.vista.InternalVacinacions;

/**
 *
 * @author hugolg
 */
public class LogicaEscritorio extends JDesktopPane {

    private Coordinador miCoordinador;
    private boolean abiertaVacinacions;

    public LogicaEscritorio() {
        super();
    }

    public void setCoordinador(Coordinador miCoordinador) {
        this.miCoordinador = miCoordinador;
    }

    public void abrirAltaCansRazas() {
        InternalCansRazas alta = new InternalCansRazas(miCoordinador);
        alta.setVisible(true);
        this.add(alta);
        try {
            alta.setSelected(true);
        } catch (Exception e) {
        }
    }

    public void abrirAltaPropietarios() {
        InternalPropietarios p = new InternalPropietarios(miCoordinador);
        p.setTitle("Novo propietario");
        p.setVisible(true);
        this.add(p);
        try {
            p.setSelected(true);
        } catch (Exception e) {
        }
    }

    public void abrirVacinacions() {
        if (!abiertaVacinacions) {
            InternalVacinacions vac = new InternalVacinacions(miCoordinador);   
            vac.setTitle("Xestión vacinacións");
            vac.setVisible(true);
            this.add(vac);
            try {
                vac.setSelected(true);
            } catch (Exception e) {
            }
            abiertaVacinacions = true;
        }
    }

    public void cerrarVacinacions() {
        abiertaVacinacions = false;
    }

}
