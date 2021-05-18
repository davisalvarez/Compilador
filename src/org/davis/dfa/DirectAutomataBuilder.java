/*
    UVG
    Diseño de Lenguajes de Programación
    Davis Alvarez - 15842

    Libro del gragón: 173 - 179p
    Algorithm 3.36 : Construction of a DFA from a regular expression r.
 */

package org.davis.dfa;

import com.sun.source.tree.Tree;
import org.davis.bean.Tree.Nodo;
import org.davis.bean.Tree.TreeBuilder;
import org.davis.bean.automata.*;
import org.davis.utility.Postfix;
import java.util.ArrayList;

public class DirectAutomataBuilder {

    private Automata DFA;
    private int cNodos;
    private ArrayList<FollowposTable> followpos;
    private ArrayList<REState> Dstates;
    private ArrayList<RETransition> Dtran;
    private ArrayList<INodo> terminals;

    public DirectAutomataBuilder(){}

    public Automata REtoDFA(String regularExpression, boolean prepare){
        this.followpos = new ArrayList<FollowposTable>();
        this.Dstates = new ArrayList<REState>();
        this.Dtran = new ArrayList<RETransition>();
        this.terminals = new ArrayList<INodo>();

        Postfix convertir = new Postfix();
        TreeBuilder bobC = new TreeBuilder();
        DFA = new Automata();
        int cont = 0;
        boolean debug = false;
        Nodo ceiba;

        if (prepare){
            regularExpression = regularExpression + "#";
            ceiba = bobC.postfixToTree(convertir.toPostfix(convertir.prePostfix(regularExpression), true));
        }else{
            regularExpression = regularExpression + "·#";
            ceiba = bobC.postfixToTree(convertir.toPostfix(regularExpression, false));
        }


        cNodos = 1;
        this.identifyNodes(ceiba);
        this.startNullable(ceiba);
        this.startFirstpos(ceiba);
        this.startLastpos(ceiba);
        this.doFollowpos(ceiba);

        if (debug) this.printTerminals();
        if (debug) this.pintFollowpos();

        //Algorithm 3.36 :
        REState S = new REState();
        if(debug){
            System.out.print("firstpos(root) = ");
            this.printLista(ceiba.getFistpos());
        }
        S.setEstado(ceiba.getFistpos());
        S.setId(cont);
        DFA.setInidialD(S);
        this.Dstates.add(S);

        while (this.checkDstates()) {
            S = this.getPrimerD();
            S.setMark(true);
            if (debug) System.out.println("---------------- E"+S.getId()+" ----------------");
            for (String a : DFA.getSimbolos()) {
                REState U = executeOrderDtran(S ,a, debug);

                if(U != null) {
                    if (!this.isInDstates(U)) {
                        ++cont;
                        U.setId(cont);
                        this.Dstates.add(U);
                        if (debug) System.out.println("Add E" + cont);
                    }


                        RETransition nTran = new RETransition(S, a, obtenerREState(U));
                        this.Dtran.add(nTran);
                }else {
                    if (debug) System.out.println("No Moves!");
                }
            }
        }

        this.identificarEstadosFinales();

        DFA = this.DtranToDFA(DFA, Dtran);

        return DFA;
    }

    public Automata DtranToDFA(Automata auto, ArrayList<RETransition> dtran) {

        for (RETransition tran : dtran) {

            Transition flecha = new Transition(tran.getOrigenRE(), tran.getSimbolo(), tran.getDestinoRE());
            auto.addTransiciones(flecha);

            if (!auto.getEstados().contains(tran.getOrigenRE()))
                auto.addEstado(tran.getOrigenRE());
            if (!auto.getEstados().contains(tran.getDestinoRE()))
                auto.addEstado(tran.getDestinoRE());
        }

        auto.identificarEstados();

        return auto;
    }

    public void identificarEstadosFinales(){
        int id = -1;

        for (INodo info: this.terminals) {
            if (info.getSimbolo().equals("#"))
                id = info.getId();
        }
        if (id==-1) System.out.print("No hay final!");

        for (REState s: Dstates){
            if(s.getEstado().contains(id))
                s.setAceptacion(true);
        }
    }

    public void startNullable(Nodo n){
        boolean debug=false;
        if(n != null){
            this.startNullable(n.getLeftC());
            this.startNullable(n.getRightC());
            n.nullable();

            if(debug){
                System.out.print(n.getSimbolo() +": "+n.getId()+" - ");
                System.out.println(n.isNullable());
            }

        }
    }

    public void startFirstpos(Nodo n){
        boolean debug=false;
        if(n != null){
            this.startFirstpos(n.getLeftC());
            this.startFirstpos(n.getRightC());
            n.fistpos();
            if(debug){
                System.out.print(n.getSimbolo() +": "+n.getId()+" - ");
                n.printLista(n.getFistpos());
            }
        }
    }

    public void startLastpos(Nodo n){
        boolean debug=false;
        if(n != null){
            this.startLastpos(n.getLeftC());
            this.startLastpos(n.getRightC());
            n.lasttpos();
            if(debug){
                System.out.print(n.getSimbolo() +": "+n.getId()+" - ");
                n.printLista(n.getLastpos());
            }
        }
    }

    public void doFollowpos(Nodo n){
        if(n != null) {
            this.doFollowpos(n.getLeftC());
            this.doFollowpos(n.getRightC());
            if (n.getTipo() == Nodo.NKLEEN) {
                for(int i: n.getLastpos()){
                    this.getFollowpos(i).addAll(n.getFistpos());
                }
            }
            if (n.getTipo() == Nodo.NCAT) {
                for(int i: n.getLeftC().getLastpos()){
                    this.getFollowpos(i).addAll(n.getRightC().getFistpos());
                }
            }
            if (n.getTipo() == Nodo.NPOSITIVE) {
                for(int i: n.getLastpos()){
                    this.getFollowpos(i).addAll(n.getFistpos());
                }
            }
        }

    }

    public REState executeOrderDtran(REState origen, String simbolo, boolean print){
        REState nuevo = new REState();

        if(print) {
            System.out.print("S = ");
            this.printLista(origen.getEstado());
        }

        for (int i: origen.getEstado()){
            ArrayList<INodo> addFP = this.getTerminal(i, simbolo);
            for (INodo n: addFP){
                for (int fp : this.getFollowpos(n.getId())) {
                    if (!nuevo.getEstado().contains(fp))
                        nuevo.getEstado().add(fp);
                }
            }

        }

        if (print) {
            System.out.print("DTran(S, '" + simbolo + "') = ");
            this.printLista(nuevo.getEstado());
        }

        if (nuevo.getEstado().size()>0)
            return nuevo;
        return null;

    }

    public ArrayList<Integer> getFollowpos(int i){
        for(FollowposTable fp: this.followpos)
            if(fp.getId() == i) return fp.getFollowpos();
        return null;
    }

    public boolean isInDstates(REState nuevo){
        //System.out.println("E: "+nuevo.getEstado());
        for (REState d: this.Dstates){
            if (this.REstateEquals(nuevo.getEstado(), d.getEstado()))
                return true;
        }
        return false;
    }

    public boolean REstateEquals(ArrayList<Integer> a, ArrayList<Integer> b){
        boolean contenidoA = true;
        boolean contenidoB = true;

        for (int q: a){
            if (!b.contains(q))
                contenidoA = false;
        }
        for (int q: b){
            if (!a.contains(q))
                contenidoB = false;
        }
        return contenidoA && contenidoB;

    }

    public REState obtenerREState(REState obtener){
        for (REState d: this.Dstates){
            if (this.REstateEquals(d.getEstado(), obtener.getEstado()))
                return d;
        }
        System.out.println("--->Error! DirectAutoBuildes");
        return null;
    }

    public void pintFollowpos(){
        for(FollowposTable fp: this.followpos){
            System.out.print(fp.getId()+": ");
            this.printLista(fp.getFollowpos());
        }
    }

    public void identifyNodes(Nodo ceiba){

        if(ceiba != null){
            this.identifyNodes(ceiba.getLeftC());
            this.identifyNodes(ceiba.getRightC());
            ceiba.iniciarNodoRE();
            if (ceiba.getTipo() == Nodo.NTERMINAL && (!ceiba.getSimbolo().equals("ε"))) {
                ceiba.setId(this.cNodos);
                DFA.addSimbolo(ceiba.getSimbolo());
                this.followpos.add(new FollowposTable(this.cNodos));
                this.terminals.add(new INodo(cNodos, ceiba.getSimbolo()));
                ++this.cNodos;
            }
        }

    }

    public boolean checkDstates(){
        for (REState d: this.Dstates){
            if(!d.isMark())
                return true;
        }
        return false;
    }
    public REState getPrimerD(){
        for (REState d: this.Dstates){
            if(!d.isMark()){
                //System.out.println("d: "+d.getId());
                return d;
            }
                //return d;
        }
        System.out.println("fin de Dstates");
        return null;
    }

    public ArrayList<INodo> getTerminal(int id, String c){
        ArrayList<INodo> repre = new ArrayList<INodo>();
        for (INodo t: this.terminals){
            if (t.getSimbolo().equals(c) && id==t.getId())
                repre.add(t);
        }
        return repre;
    }
    public void printLista(ArrayList<Integer> lista ){
        System.out.print("{ ");
        for (int i = 0; i<lista.size()-1; ++i){
            System.out.print(lista.get(i) +", ");
        }
        if (lista.size()>=1) {
            System.out.print(lista.get(lista.size() - 1));
        }
        System.out.println(" }");
    }

    public void printTerminals(){
        for (INodo t: this.terminals){
            System.out.println("id: "+t.getId()+" s: "+t.getSimbolo());
        }
    }

}
