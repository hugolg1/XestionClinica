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
public class VacinaVo {
    
    private int codigo;
    private String nome;
    private int numDoses;

    public VacinaVo() {
    }

    public VacinaVo(int codigo, String nome, int numDoses) {
        this.codigo = codigo;
        this.nome = nome;
        this.numDoses = numDoses;
    } 

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumDoses() {
        return numDoses;
    }

    public void setNumDoses(int numDoses) {
        this.numDoses = numDoses;
    }

    @Override
    public String toString() {
        return nome;
    }
    
    
    
}
