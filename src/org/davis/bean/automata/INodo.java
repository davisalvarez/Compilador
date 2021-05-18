package org.davis.bean.automata;

public class INodo {
    private int id;
    private String simbolo;

    public INodo(int id, String simbolo){
        this.id = id;
        this.simbolo = simbolo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }
}
