/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xestionclinica.modelo;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author hugolg
 */
public class Conexion {

    private static Connection Con;
    private static String url, puerto, usuario, clave;

    public static void setConexion(String sUrl, String sPuerto, String sUsuario, String sClave) {
        Con = null;
        url = sUrl;
        puerto = sPuerto;
        usuario = sUsuario;
        clave = sClave;
    }

    public static boolean conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String bd = "clinica";
            String urlCon = "jdbc:mysql://" + url + ":" + puerto + "/" + bd + "?user=" + usuario + "&password=" + clave;
            Con = DriverManager.getConnection(urlCon);
            Con.setAutoCommit(false);
            //Con setAutocommitFalse  permite hacer varias operaciones en una transaccion
            //En el caso de que sea true se inicia una transacción automaticamente bien cuando se hace una operacion bien cuando
            // se recorren todos los registros en el caso de que se devuelvan en un resulset.
            //En general están en modo de automatico
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Connection getConexion() {
//        try {
//            if (Con.isClosed()) {
//                conectar();
//            }
//        } catch (SQLException ex) {
//        }
        return Con;
    }

}
