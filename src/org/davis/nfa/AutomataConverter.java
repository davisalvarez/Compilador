/*
    UVG
    Diseño de Lenguajes de Programación
    Davis Alvarez - 15842

    Libro del gragón: 153p
    Algorithm 3.20 : The subset construction of a DFA from an NFA.
 */
package org.davis.nfa;

import org.davis.bean.automata.DTransition;
import org.davis.bean.automata.Dstates;
import org.davis.bean.automata.Transition;
import org.davis.bean.automata.QState;
import org.davis.bean.automata.Automata;
import java.util.ArrayList;

public class AutomataConverter {

    private ArrayList<Dstates> dEstados;
    private ArrayList<DTransition> Dtran;

    public AutomataConverter() {
        this.dEstados = new ArrayList<Dstates>();
        this.Dtran = new ArrayList<DTransition>();
    }

    public Automata NFAtoDFA(Automata NFA){
        Automata DFA = new Automata();
        boolean debug = false;
        int cont = 0;
        for (String sim: NFA.getSimbolos()){
            if (!sim.equals("ε"))
                DFA.addSimbolo(sim);
        }

        ArrayList<QState> S = new ArrayList<QState>();
        S.add(NFA.getInidialD());

        Dstates ini = new Dstates();
        ini.setEstado(NFA.e_closure(S,debug));
        ini.setId(cont);
        dEstados.add(ini);

        while (this.checkDstates()) {
            Dstates T = this.getPrimerD();
            T.setMark(true);
            if (debug) System.out.println("---------------- E"+T.getId()+" ----------------");
            for (String a : DFA.getSimbolos()) {
                ArrayList<QState> U = NFA.e_closure(NFA.move(T.getEstado(), a, debug),debug);
                Dstates dU = new Dstates();
                dU.setEstado(U);
                if (!isInDstates(dU) && dU.getEstado().size()>0) {
                    ++cont;
                    dU.setEstado(U);
                    dU.setId(cont);
                    this.dEstados.add(dU);
                    if (debug) System.out.println("Add E"+cont);

                }
                //System.out.println("---> "+dEstados.indexOf(dU));
                if(dU.getEstado().size()>0) {
                    DTransition nTran = new DTransition(T, a, this.obtenerDstate(dU));
                    this.Dtran.add(nTran);
                }else
                    if (debug) System.out.println("No Moves!");

                if (debug) System.out.println();
            }

        }
        S.clear();
        for ( QState f: NFA.getFinalQ()){
            S.add(f);
        }


        if (debug) System.out.println("________________________");

        this.identificarEstadoInicial(NFA.getInidialD());
        this.identificarEstadosFinales(S);


        DFA = this.DtranToDFA(DFA, Dtran);

        return DFA;
    }

    public Automata DtranToDFA(Automata auto, ArrayList<DTransition> dtran){

        for(DTransition tran: dtran){

            Transition flecha =  new Transition(tran.getOrigen(), tran.getSimbolo(), tran.getDestino());
            auto.addTransiciones(flecha);

            if(!auto.getEstados().contains(tran.getOrigen()))
                auto.addEstado(tran.getOrigen());
            if(!auto.getEstados().contains(tran.getDestino()))
                auto.addEstado(tran.getDestino());
        }

        auto.identificarEstados();
        return auto;
    }

    public boolean isInDstates(Dstates nuevo){
        for (Dstates d: this.dEstados){

            if (this.DstateEquals(d.getEstado(), nuevo.getEstado()))
                return true;
        }
        return false;
    }

    public boolean DstateEquals(ArrayList<QState> a, ArrayList<QState> b){
        boolean contenidoA = true;
        boolean contenidoB = true;

        for (QState q: a){
            if (!b.contains(q))
                contenidoA = false;
        }
        for (QState q: b){
            if (!a.contains(q))
                contenidoB = false;
        }
        return contenidoA && contenidoB;
    }

    public Dstates obtenerDstate(Dstates obtener){
        for (Dstates d: this.dEstados){
            if (this.DstateEquals(d.getEstado(), obtener.getEstado()))
                return d;
        }
        System.out.println("--->Error! AutoConverter");
        return null;
    }

    public void identificarEstadoInicial(QState inicial){

        for (Dstates s: this.dEstados){
            if (s.getEstado().contains(inicial))
                s.setInicial(true);
        }
    }

    public void identificarEstadosFinales(ArrayList<QState> finales){

        for (Dstates s: this.dEstados){
            for (QState q: finales) {
                if (s.getEstado().contains(q)){
                    s.setAceptacion(true);
                }
            }
        }
    }

    public boolean checkDstates(){
        for (Dstates d: this.dEstados){
            if(!d.isMark())
                return true;
        }
        return false;
    }

    public Dstates getPrimerD(){
        for (Dstates d: this.dEstados){
            if(!d.isMark())
                return d;
        }
        System.out.println("fin de Dstates");
        return null;
    }

}
