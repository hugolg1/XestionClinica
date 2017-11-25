/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xestionclinica.modelo;

import java.awt.Component;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import xestionclinica.controlador.Coordinador;

/**
 *
 * @author hugo
 */
public class Logica {

    private Coordinador coordinador;
    private CanDao canDao;
    private PropietarioDao propDao;
    private VacinaDao vacinaDao;

    private DefaultListModel<RazaVo> listRazasCans;
    private DefaultComboBoxModel<CanVo> comboCansPropietario;

    private DefaultListModel<PropietarioVo> listPropietarios;
    private DefaultComboBoxModel<PropietarioVo> comboPropietarios;

    private DefaultComboBoxModel<VacinaVo> comboVacinas;
    private TableVacinasAplicadas modelTblVacinasAplicadas;

    public Logica() {
        canDao = new CanDao();
        propDao = new PropietarioDao();
        vacinaDao = new VacinaDao();
        listRazasCans = new DefaultListModel<>();
        listPropietarios = new DefaultListModel();
        comboCansPropietario = new DefaultComboBoxModel<>();
        comboPropietarios = new DefaultComboBoxModel<>();
        comboVacinas = new DefaultComboBoxModel<>();
        modelTblVacinasAplicadas = new TableVacinasAplicadas();
    }

    public void setCoordinador(Coordinador miCoordinador) {
        this.coordinador = miCoordinador;
    }

    public void validacionConexion(String user, String pass, String ip, String port) {
        if (user.isEmpty()) {
            coordinador.mostrarMsjInfo(null, "Completa o campo usuario");
        } else if (pass.isEmpty()) {
            coordinador.mostrarMsjInfo(null, "Completa o campo contrasinal");
        } else if (ip.isEmpty()) {
            coordinador.mostrarMsjInfo(null, "Completa o campo ip servidor");
        } else if (port.isEmpty()) {
            coordinador.mostrarMsjInfo(null, "Completa o campo porto");
        } else {
            Conexion.setConexion(ip, port, user, pass);
            if (Conexion.conectar()) {
                coordinador.cerrarInicioSesion();
                iniciarConsultasIniciales();
                coordinador.mostrarVentanaXestion();
            } else {
                coordinador.mostrarMsjAlerta(null, "Error al conectarse al servidor");
            }
        }
    }

    private void iniciarConsultasIniciales() {
        for (RazaVo raza : canDao.getRazas()) {
            listRazasCans.addElement(raza);
        }
        for (PropietarioVo prop : propDao.getPropietarios()) {
            listPropietarios.addElement(prop);
            comboPropietarios.addElement(prop);
        }
        for (VacinaVo vac : vacinaDao.getVacinas()) {
            comboVacinas.addElement(vac);
        }
    }

    //CANS//
    public boolean validarAltaCan(Component parent, CanVo can) {
        if (camposCanValidos(can, parent)) {
            if (!canDao.existeCan(can.getChip())) {
                try {
                    canDao.añadirCan(can);
                    coordinador.mostrarMsjExito(parent, "Can añadido");
                    //actualizar modelo cans de propietario
                    try {
                        mostrarCansPropietario(((PropietarioVo) comboPropietarios.getSelectedItem()).getDni());
                    } catch (Exception e) {
                    }
                    return true;
                } catch (SQLException ex) {
                    coordinador.mostrarMsjAlerta(parent, ex.getMessage());
                }
            } else {
                coordinador.mostrarMsjAlerta(parent, "Xa existe un can co chip: " + can.getChip());
            }
        }
        return false;
    }

    private boolean camposCanValidos(CanVo can, Component parent) {
        boolean resultado = false;
        if (can.getNome().isEmpty()) {
            coordinador.mostrarMsjInfo(parent, "Completa o campo nome");
        } else if (can.getChip().isEmpty()) {
            coordinador.mostrarMsjInfo(parent, "Completa o campo chip");
        } else if (can.getCodRaza() == 0) {
            coordinador.mostrarMsjInfo(parent, "Selecciona unha raza");
        } else if (can.getDniPropietario().isEmpty()) {
            coordinador.mostrarMsjInfo(parent, "Selecciona un propietario");
        } else {
            resultado = true;
        }
        return resultado;
    }

    public boolean validarAltaRaza(Component parent, RazaVo raza) {
        boolean resultado = false;
        if (raza.getDescripcion().isEmpty()) {
            coordinador.mostrarMsjInfo(parent, "Completa o campo raza");
        } else if (canDao.existeRaza(raza.getDescripcion())) {
            coordinador.mostrarMsjAlerta(parent, "Xa existe unha raza " + raza.getDescripcion());
        } else {
            try {
                int idRaza = canDao.añadirRaza(raza.getDescripcion());
                coordinador.mostrarMsjExito(parent, "Raza añadida");
                resultado = true;
                raza.setId(idRaza);
                listRazasCans.addElement(raza);
            } catch (SQLException ex) {
                coordinador.mostrarMsjAlerta(parent, ex.getMessage());
            }
        }
        return resultado;
    }

    //PROPIETARIOS//
    public boolean validarAltaPropietario(Component parent, PropietarioVo p) {
        if (camposPropietarioValidos(parent, p)) {
            try {
                propDao.añadirPropietario(p);
                coordinador.mostrarMsjExito(parent, "Propietario añadido");
                listPropietarios.addElement(p);
                comboPropietarios.addElement(p);
                return true;
            } catch (SQLException ex) {
                coordinador.mostrarMsjAlerta(parent, ex.getMessage());
            }
        }
        return false;
    }

    private boolean camposPropietarioValidos(Component parent, PropietarioVo p) {
        boolean resultado = false;
        if (p.getDni().isEmpty()) {
            coordinador.mostrarMsjInfo(parent, "Completa o campo dni");
        } else if (propDao.existePropietario(p.getDni())) {
            coordinador.mostrarMsjAlerta(parent, "Xa existe un propietario co dni: " + p.getDni());
        } else if (p.getNome().isEmpty()) {
            coordinador.mostrarMsjInfo(parent, "Completa o campo nome");
        } else if (p.getApellido1().isEmpty()) {
            coordinador.mostrarMsjInfo(parent, "Completa o campo apellido 1");
        } else if (p.getApellido2().isEmpty()) {
            coordinador.mostrarMsjInfo(parent, "Completa o campo apellido 2");
        } else if (p.getTlf().isEmpty()) {
            coordinador.mostrarMsjInfo(parent, "Completa o campo telefono");
        } else if (p.getEmail().isEmpty()) {
            coordinador.mostrarMsjInfo(parent, "Completa o campo email");
        } else {
            resultado = true;
        }
        return resultado;
    }

    //VACINACIONS//
    public void cargarVacinasDoCan(String chip) {
        modelTblVacinasAplicadas.limpiar();
        if (!chip.isEmpty()) {
            for (VacinacionsVo vacinacion : vacinaDao.getVacinacionsCan(chip)) {
                modelTblVacinasAplicadas.añadirVacinacion(vacinacion);
            }
        }
    }

    public void validarAplicarVacina(Component parent, VacinacionsVo vacinacion, String chip) {
        if (camposValidosAplicarVacina(parent, vacinacion, chip)) {
            //comprobar que quedan doses disponibles a aplicar a ese can
            int ultimaDoseAplicada = vacinaDao.getUltimaDoseAplicada(vacinacion.getVacina().getCodigo(), chip);
            if (ultimaDoseAplicada != -1) {
                if (ultimaDoseAplicada < vacinacion.getVacina().getNumDoses()) {
                    vacinacion.setDoseAplicada(ultimaDoseAplicada + 1);
                    if (vacinaDao.aplicarVacinacion(vacinacion, chip)) {
                        modelTblVacinasAplicadas.añadirVacinacion(vacinacion);
                    } else {
                        coordinador.mostrarMsjAlerta(parent, "Erro o aplicar a vacinacion. Comprobar si xa se aplicou a vacinacion anteriormente");
                    }
                } else {
                    coordinador.mostrarMsjAlerta(parent, "Non é posible vacinar ao can de " + vacinacion.getVacina().getNome()
                            + ". Xa foron administradas tódalas doses desta vacina ");
                }
            } else {
                coordinador.mostrarMsjAlerta(parent, "Erro o aplicar a vacinacion");
            }
        }
    }

    private boolean camposValidosAplicarVacina(Component parent, VacinacionsVo vacinacion, String chip) {
        if (chip.isEmpty()) {
            coordinador.mostrarMsjInfo(parent, "Selecciona o can o que se lle vai aplicar a vacina");
        } else if (vacinacion.getVacina() == null) {
            coordinador.mostrarMsjInfo(parent, "Selecciona unha vacina a aplicar ");
        } else if (vacinacion.getData() == null) {
            coordinador.mostrarMsjInfo(parent, "Fecha invalida. \nFormato requerido: dd/mm/yyyy ");
        } else if (vacinacion.getObservacion().isEmpty()) {
            coordinador.mostrarMsjInfo(parent, "Completa o campo observacións");
        } else {
            return true;
        }
        return false;
    }

    public void validarEliminacionVacinasAplicadas(Component parent, int nFila, String chip) {
        VacinacionsVo vacinacion = modelTblVacinasAplicadas.getVacinacion(nFila);
        int numDose = vacinacion.getDoseAplicada();
        if (vacinaDao.existenDosesPosteriores(vacinacion.getVacina().getCodigo(),chip,numDose)) {
            coordinador.mostrarMsjAlerta(parent, "Non é posible eliminar a dose " + numDose + " da vacinación seleccionada sen antes"
                    + " eliminar as doses anteriores");
        } else if (vacinaDao.eliminarVacinacion(vacinacion.getVacina().getCodigo(), chip, vacinacion.getDoseAplicada())) {
            modelTblVacinasAplicadas.removeRow(nFila);
            coordinador.mostrarMsjExito(parent, "Eliminacion correcta");
        } else {
            coordinador.mostrarMsjAlerta(parent, "Error o eliminar a vacinacion");
        }
    }

    public void mostrarCansPropietario(String dni) {
        if (comboCansPropietario.getSize() > 0) {
            comboCansPropietario.removeAllElements();
        }
        modelTblVacinasAplicadas.limpiar();
        for (CanVo can : canDao.getCansDePropietario(dni)) {
            comboCansPropietario.addElement(can);
        }
    }

    //GETTERS
    public DefaultListModel<PropietarioVo> getPropietarios() {
        return listPropietarios;
    }

    public DefaultComboBoxModel<PropietarioVo> getComboPropietarios() {
        return comboPropietarios;
    }

    public DefaultComboBoxModel<CanVo> getComboCansPropietario() {
        return comboCansPropietario;
    }

    public DefaultListModel<RazaVo> getRazasCan() {
        return listRazasCans;
    }

    public DefaultComboBoxModel<VacinaVo> getComboVacinas() {
        return comboVacinas;

    }

    public TableVacinasAplicadas getTblVacinasAplicadas() {
        return modelTblVacinasAplicadas;
    }

}
