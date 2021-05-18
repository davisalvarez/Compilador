package org.davis.bean.automata;

import org.davis.bean.automata.QState;

import java.util.ArrayList;

public class Dstates extends QState {

    private ArrayList<QState> estado;
    private boolean mark;

    public Dstates(){
        super();
        this.estado = new ArrayList<QState>();
        this.mark = false;
    }

    public ArrayList<QState> getEstado() {
        return estado;
    }

    public void setEstado(ArrayList<QState> estado) {
        this.estado = estado;
    }

    public boolean isMark() {
        return mark;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }
}
