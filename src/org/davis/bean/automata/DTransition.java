package org.davis.bean.automata;

public class DTransition extends Transition {

    private Dstates origenD;
    private Dstates destinoD;

    public DTransition(Dstates origenD, String simbolo, Dstates destinoD){
        super();
        this.origenD = origenD;
        this.destinoD = destinoD;
        super.setSimbolo(simbolo);
    }

    public Dstates getOrigen() {
        return origenD;
    }

    public void setOrigen(Dstates origen) {
        this.origenD = origen;
    }

    public Dstates getDestino() {
        return destinoD;
    }

    public void setDestino(Dstates destino) {
        this.destinoD = destino;
    }

}
