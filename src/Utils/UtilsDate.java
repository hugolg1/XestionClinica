/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.awt.Component;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author hugolg
 */
public class UtilsDate {

    //string fecha dd/mm/yyyy
    public static Date transformStringToDate(String fecha) {
        Date resultado = null;
        try {
            if (fecha.length() == 10) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date dateParsed = dateFormat.parse(fecha);
                resultado = new Date(dateParsed.getTime());
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public static String getFechaEuropea(Date date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");
            return format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error fecha";
        }
    }

}
