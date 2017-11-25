/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xestionclinica.controlador;

import java.util.logging.Level;
import java.util.logging.Logger;
import xestionclinica.modelo.LogicaEscritorio;
import xestionclinica.modelo.Logica;
import xestionclinica.vista.VistaInicioSesion;

/**
 *
 * @author hugo
 */
public class Principal {
    
    private VistaInicioSesion vInicioSesion;
    private LogicaEscritorio escritorio;    
    private Logica miLogica;
    private Coordinador miCoordinador;
    
    public static void main(String[] args) {
        Principal mPrincipal = new Principal();
        mPrincipal.iniciar();
    }
    
    private void iniciar(){        
        try {
            javax.swing.UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        miCoordinador = new Coordinador();
        escritorio = new LogicaEscritorio();
        miLogica = new Logica();        
        vInicioSesion = new VistaInicioSesion();  
        
        escritorio.setCoordinador(miCoordinador);
        miLogica.setCoordinador(miCoordinador);
        vInicioSesion.setCoordinador(miCoordinador); 
        
        miCoordinador.setEscritorio(escritorio);
        miCoordinador.setLogica(miLogica);                     
        miCoordinador.setVistaInicioSesion(vInicioSesion);
        
        vInicioSesion.setVisible(true);        
    }
    
    
}
