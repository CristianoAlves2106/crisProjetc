/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

/**
 *
 * @author aluno
 */
public class Aluno {
    private String ola ;
    private String mundo;

    public String getOla() {
        return ola;
    }

    public void setOla(String ola) {
        this.ola = ola;
    }

    public String getMundo() {
        return mundo;
    }

    public void setMundo(String mundo) {
        this.mundo = mundo;
    }

    @Override
    public String toString() {
        return  ola.toLowerCase().toUpperCase() + " "+mundo.toUpperCase() ;
    }
    
}
