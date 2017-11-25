/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xestionclinica.controlador;

import xestionclinica.modelo.TableVacinasAplicadas;
import xestionclinica.modelo.PropietarioVo;
import xestionclinica.modelo.Logica;
import xestionclinica.modelo.LogicaEscritorio;
import xestionclinica.modelo.VacinaVo;
import xestionclinica.modelo.RazaVo;
import xestionclinica.modelo.VacinacionsVo;
import xestionclinica.modelo.CanVo;
import java.awt.Component;
import java.awt.Frame;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import xestionclinica.vista.VistaInicioSesion;
import xestionclinica.vista.VistaXestion;

/**
 *
 * @author hugo
 */
public class Coordinador {

    private VistaInicioSesion vInicioSesion;
    private VistaXestion vXestion;
    private LogicaEscritorio escritorio;
    private Logica miLogica;

    void setVistaInicioSesion(VistaInicioSesion vInicioSesion) {
        this.vInicioSesion = vInicioSesion;
    }

    public void setEscritorio(LogicaEscritorio escritorio) {
        this.escritorio = escritorio;
    }

    void setLogica(Logica miLogica) {
        this.miLogica = miLogica;
    }
    
    public void finalizarPrograma(){        
        
        System.exit(0);
    }

    public void cerrarInicioSesion() {
        vInicioSesion.dispose();
    }

    public void mostrarVentanaXestion() {
        vXestion = new VistaXestion();
        vXestion.setCoordinador(this);
        vXestion.add(escritorio);
        vXestion.setExtendedState(Frame.MAXIMIZED_BOTH);
        vXestion.setVisible(true);
    }

    public void iniciarSesion(String ip, String user, String pass, String port) {
        miLogica.validacionConexion(user, pass, ip, port);
    }

    public void abrirInternalCansRazas() {
        escritorio.abrirAltaCansRazas();
    }

    public void abrirInternalPropietarios() {
        escritorio.abrirAltaPropietarios();
    }

    public void abrirInternalVacinacions() {
        escritorio.abrirVacinacions();
    }

    public void cerrarInternalVacinacions() {
        escritorio.cerrarVacinacions();
    }
    
    /**CANS**/
    public boolean altaCan(Component parent,CanVo can){
        return miLogica.validarAltaCan(parent,can);
    }
    
    public boolean altaRaza(Component parent,RazaVo raza){
        return miLogica.validarAltaRaza(parent,raza);
    }
    
    public DefaultListModel<RazaVo> getListRazas(){
        return miLogica.getRazasCan();
    }
    
    public DefaultComboBoxModel<CanVo> getCansDoPropietario(){
        return miLogica.getComboCansPropietario();        
    }
    
    public void mostrarCansPropietario(String dni){
        miLogica.mostrarCansPropietario(dni);
    }
    
    
    /**PROPIETARIO**/
    public boolean altaPropietario(Component parent,PropietarioVo propietario){
        return miLogica.validarAltaPropietario(parent,propietario);
    }
    
    
    public DefaultListModel<PropietarioVo> getListPropietarios(){
        return miLogica.getPropietarios();
    }
    
    public DefaultComboBoxModel<PropietarioVo> getComboPropietarios(){
        return miLogica.getComboPropietarios();
    }
    
    /**VACINAS**/
    public DefaultComboBoxModel<VacinaVo> getComboVacinas(){
        return miLogica.getComboVacinas();
    } 
    
    public void aplicarVacinacion(Component parent,VacinacionsVo vac,String chip){
        miLogica.validarAplicarVacina(vXestion, vac, chip);
    }
    
    public void eliminarVacinaAplicada(Component parent,int filaSelected,String chipCan){
        miLogica.validarEliminacionVacinasAplicadas(parent, filaSelected, chipCan);        
    }
    
    public void mostrarVacinacionsCan(String chip){
        miLogica.cargarVacinasDoCan(chip);
    }
    
    public TableVacinasAplicadas getModeloTblVacinasAplicadas(){
        return miLogica.getTblVacinasAplicadas();
    }
    
    //VARIOS
    public void mostrarMsjAlerta(Component parent,String msj){
        JOptionPane.showMessageDialog(parent, msj,"Atención",JOptionPane.WARNING_MESSAGE);
    }
    public void mostrarMsjInfo(Component parent,String msj){
        JOptionPane.showMessageDialog(parent, msj,"Info",JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void mostrarMsjExito(Component parent,String msj){
        JOptionPane.showMessageDialog(parent, msj,"Exito",JOptionPane.PLAIN_MESSAGE);
    }

}
