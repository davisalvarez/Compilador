package org.davis.bean.automata;

import org.davis.bean.automata.QState;
import org.davis.bean.automata.Transition;
import org.davis.grammar.TokenD;

import java.util.ArrayList;
import java.util.Stack;

public class Automata {

    private ArrayList<String> simbolos;             //  £  = alfabeto
    private ArrayList<QState> estados;              //  S  = estados
    private QState initialD;                        //  Qº = estado inicial
    private ArrayList<QState> finalQ;               //  Fº = estados de aceptación
    private ArrayList<Transition> transiciones;     //  ƒ  = funcion transición
    private TokenD token;                           //  Token que representa el automata

    public Automata(){
        this.simbolos = new ArrayList<String>();
        this.estados = new ArrayList<QState>();
        this.initialD = new QState();
        this.finalQ = new ArrayList<QState>();
        this.transiciones = new ArrayList<Transition>();
        this.token = null;
    }

    public ArrayList<String> getSimbolos() {
        return simbolos;
    }

    public void setSimbolos(ArrayList<String> simbolos) {
        this.simbolos = simbolos;
    }

    public void addSimbolo(String simbolo){
        if(!simbolos.contains(simbolo)) {
            this.simbolos.add(simbolo);
        }
    }

    public ArrayList<QState> move(ArrayList<QState> states, String simbolo, boolean print){
        ArrayList<QState> reachQ = new ArrayList<QState>();

        if(print) {
            System.out.print("S = ");
            this.printLIstaEstados(states);
        }
        for (QState q: states) {
            for (Transition t : this.transiciones) {
                if (t.getOrigen() == q && t.getSimbolo().equals(simbolo)){
                    //System.out.println("moves!");
                    if (!reachQ.contains(t.getDestino()))
                        reachQ.add(t.getDestino());
                }
            }
        }
        if(print) {
            System.out.print("move(S, '" + simbolo + "') = ");
            this.printLIstaEstados(reachQ);
        }
        return reachQ;
    }

    public ArrayList<QState> e_closure(ArrayList<QState> states, boolean print) {
        ArrayList<QState> reachE = new ArrayList<>();
        Stack<QState> cola = new Stack<QState>();

        if(print) {
            System.out.print("S = ");
            this.printLIstaEstados(states);
        }
        for (QState q: states) {
            cola.push(q);
        }
        while (!cola.empty()){
            ArrayList<QState> tmp = new ArrayList<>();
            tmp.add(cola.pop());
            tmp = this.move(tmp,"ε", false);

            for (QState q: tmp) {
                if (!reachE.contains(q)) {
                    reachE.add(q);
                    cola.push(q);
                }
            }
        }
        for (QState q: states) {
            reachE.add(q);
        }
        if(print) {
            System.out.print("e-closure(S) = ");
            this.printLIstaEstados(reachE);
        }
        return reachE;
    }

    public void nameStates(){
        //int cont= this.estados.size()+1;
        int cont= 0;
        for(QState q: this.estados){
            q.setId(cont);
            cont++;
        }
    }

    public void identificarEstados(){
        //System.out.println("len " + this.estados.size());
        for(QState q: this.estados){
            if (q.isInicial()){
                this.initialD = q;
                //System.out.println("takumi");
            }
            if (q.isAceptacion()) {
                this.finalQ.add(q);
                //System.out.println("add " + q.getId());
            }
        }

    }

    public void tostring(){
        //  £  = alfabeto
        System.out.println("Alfabeto £: ");
        System.out.print("{ ");
        for (int i = 0; i<this.simbolos.size()-1; ++i){
            System.out.print(this.simbolos.get(i) +", ");
        }
        if (this.simbolos.size()>=1) {
            System.out.print(this.simbolos.get(this.simbolos.size()-1));
            System.out.println(" }");
        }

        //  S  = estados
        System.out.println("Estados S: ");
        this.printLIstaEstados(this.estados);

        // Qº = estado Inicial
        System.out.println("Estado inicial Qº: ");
        System.out.println("{ "+this.initialD.getId()+" }");

        // Fº = estados de Aceptación
        System.out.println("Estados de aceptación Fº: "+this.finalQ.size());
        this.printLIstaEstados(this.finalQ);

        //  ƒ  = funcion transición
        System.out.println("Funciónn de transición ƒ: ");
        for(Transition f: this.transiciones){
            System.out.print("ƒ( "+ f.getOrigen().getId());
            System.out.print(", '"+f.getSimbolo() +"' )");
            System.out.println(" = "+f.getDestino().getId());
        }
        System.out.println("");
        System.out.println("");

    }

    public ArrayList<QState> getEstados() {
        return estados;
    }

    public void setEstados(ArrayList<QState> estados) {
        this.estados = estados;
    }

    public void addEstado(QState estado){
        this.estados.add(estado);
    }

    public QState getInidialD() {
        return initialD;
    }

    public void setInidialD(QState inidialD) {
        this.initialD = inidialD;
    }

    public ArrayList<QState> getFinalQ() {
        return finalQ;
    }

    public void setFinalQ(ArrayList<QState> finalQ) {
        this.finalQ = finalQ;
    }

    public void addFinalQ(QState aceptado){
        this.finalQ.add(aceptado);
    }

    public ArrayList<Transition> getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(ArrayList<Transition> transiciones) {
        this.transiciones = transiciones;
    }

    public void addTransiciones(Transition movimiento){
        this.transiciones.add(movimiento);
    }

    public TokenD getToken() {
        return token;
    }

    public void setToken(TokenD token) {
        this.token = token;
    }

    public void printLIstaEstados(ArrayList<QState> listaQ ){
        System.out.print("{ ");
        for (int i = 0; i<listaQ.size()-1; ++i){
            System.out.print(listaQ.get(i).getId() +", ");
        }
        if (listaQ.size()>=1) {
            System.out.print(listaQ.get(listaQ.size() - 1).getId());
        }
        System.out.println(" }");
    }
}