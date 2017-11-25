/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xestionclinica.modelo;

import javax.swing.table.DefaultTableModel;
import Utils.UtilsDate;

/**
 *
 * @author hugo
 */
public class TableVacinasAplicadas extends DefaultTableModel{

    public TableVacinasAplicadas() {
        super.setColumnCount(5);
        super.setRowCount(0);
        super.setColumnIdentifiers(new String[]{"Vacina","Num. total doses","Dose aplicada","Data","Observacións"});   
        
        
    }

    @Override
    public boolean isCellEditable(int row, int column) {
       return false;
    }
    
    public void limpiar(){
        setRowCount(0);
    }
    
    public void añadirVacinacion(VacinacionsVo vacinacion){
        setRowCount(getRowCount()+1);
        setValueAt(vacinacion,getRowCount()-1, 0);
        setValueAt(vacinacion.getVacina().getNumDoses(),getRowCount()-1, 1);
        setValueAt(vacinacion.getDoseAplicada(),getRowCount()-1, 2);
        setValueAt(UtilsDate.getFechaEuropea(vacinacion.getData()),getRowCount()-1, 3);
        setValueAt(vacinacion.getObservacion(),getRowCount()-1, 4);        
    }
    
    public VacinacionsVo getVacinacion(int fila){
        return (VacinacionsVo) this.getValueAt(fila,0);
    }    

    
}
