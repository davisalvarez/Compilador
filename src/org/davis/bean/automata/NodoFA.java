package org.davis.bean.automata;

import java.util.ArrayList;

public class NodoFA {

    private boolean inicial;
    private boolean aceptacion;
    private String nombre;

    public NodoFA(){
        this.inicial=false;
        this.aceptacion=false;
        this.nombre = "";
    }

    public boolean isInicial() {
        return inicial;
    }

    public void setInicial(boolean inicial) {
        this.inicial = inicial;
    }

    public boolean isAceptacion() {
        return aceptacion;
    }

    public void setAceptacion(boolean aceptacion) {
        this.aceptacion = aceptacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
