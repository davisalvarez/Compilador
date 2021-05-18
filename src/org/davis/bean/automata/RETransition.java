package org.davis.bean.automata;

import org.davis.bean.automata.REState;
import org.davis.bean.automata.Transition;

public class RETransition extends Transition {
    private REState origenRE;
    private REState destinoRE;

    public RETransition(REState origenRE, String simbolo, REState destinoRE){
        super();
        this.origenRE = origenRE;
        this.destinoRE = destinoRE;
        super.setSimbolo(simbolo);
    }

    public REState getOrigenRE() {
        return origenRE;
    }

    public void setOrigenRE(REState origenRE) {
        this.origenRE = origenRE;
    }

    public REState getDestinoRE() {
        return destinoRE;
    }

    public void setDestinoRE(REState destinoRE) {
        this.destinoRE = destinoRE;
    }
}
