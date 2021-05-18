package org.davis.bean.automata;

import java.util.ArrayList;

public class REState extends QState {
    private ArrayList<Integer> estado;
    private boolean mark;

    public REState(){
        super();
        this.estado = new ArrayList<Integer>();
        this.mark = false;
    }

    public ArrayList<Integer> getEstado() {
        return estado;
    }

    public void setEstado(ArrayList<Integer> estado) {
        this.estado = estado;
    }

    public boolean isMark() {
        return mark;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }
}
