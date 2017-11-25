/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.ArrayList;

/**
 *
 * @author hugo
 */
public class PoliticaFocus extends FocusTraversalPolicy {

    private final ArrayList<Component> componentes;

    public PoliticaFocus() {
        componentes = new ArrayList<>();
    }
    
    public void añadirComponent(Component c){
        this.componentes.add(c);
    }
    
    public void quitarComponent(Component c){
        this.componentes.remove(c);
    }

    @Override
    public Component getComponentAfter(Container aContainer, Component aComponent) {
        int indice = componentes.indexOf(aComponent) + 1;
        return (indice > componentes.size()-1) ? componentes.get(0) : componentes.get(indice);
    }

    @Override
    public Component getComponentBefore(Container aContainer, Component aComponent) {        
        int indice = componentes.indexOf(aComponent) -1;
        return (indice < 0 ) ? componentes.get(componentes.size()-1) : componentes.get(indice);
    }

    @Override
    public Component getFirstComponent(Container aContainer) {
        return componentes.get(0);
    }

    @Override
    public Component getLastComponent(Container aContainer) {
        return componentes.get(componentes.size()-1);
    }

    @Override
    public Component getDefaultComponent(Container aContainer) {
         return componentes.get(0);
    }

}
