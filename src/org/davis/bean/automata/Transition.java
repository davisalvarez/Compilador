/*
    UVG
    Diseño de Lenguajes de Programación
    Davis Alvarez - 15842
 */
package org.davis.bean.automata;

public class Transition {

    private QState origen;
    private QState destino;
    private String simbolo;

    public Transition(){}

    public Transition(QState origen, String simbolo, QState destino){
        this.origen = origen;
        this.destino = destino;
        this.simbolo = simbolo;
    }

    public QState getOrigen() {
        return origen;
    }

    public void setOrigen(QState origen) {
        this.origen = origen;
    }

    public QState getDestino() {
        return destino;
    }

    public void setDestino(QState destino) {
        this.destino = destino;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }
}
