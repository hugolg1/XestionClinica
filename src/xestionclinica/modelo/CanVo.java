/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xestionclinica.modelo;

/**
 *
 * @author hugolg
 */
public class CanVo {
    
    private String chip;
    private String nome;
    private int codRaza;
    private String dniPropietario;

    public CanVo() {
    }

    public CanVo(String chip, String nome, int codRaza, String dniPropietario) {
        this.chip = chip;
        this.nome = nome;
        this.codRaza = codRaza;
        this.dniPropietario = dniPropietario;
    }   
    

    public String getChip() {
        return chip;
    }

    public void setChip(String chip) {
        this.chip = chip;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCodRaza() {
        return codRaza;
    }

    public void setCodRaza(int codRaza) {
        this.codRaza = codRaza;
    }

    public String getDniPropietario() {
        return dniPropietario;
    }

    public void setDniPropietario(String dniPropietario) {
        this.dniPropietario = dniPropietario;
    }

    @Override
    public String toString() {
        return nome;
    }
    
    
    
    
}
