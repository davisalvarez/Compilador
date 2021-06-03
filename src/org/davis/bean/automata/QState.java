/*
    UVG
    Diseño de Lenguajes de Programación
    Davis Alvarez - 15842
 */
package org.davis.bean.automata;

import org.davis.bean.Tree.Nodo;
import org.davis.bean.grammar.Grammar;
import org.davis.bean.grammar.Production;
import org.davis.bean.grammar.TokenD;

import java.util.ArrayList;

public class QState {

    private int id;
    private boolean inicial;
    private boolean aceptacion;

    public QState(){
        this.aceptacion=false;
        this.inicial=false;
        this.id = -1;
    }
    public QState(int id){
        this.aceptacion=false;
        this.inicial=false;
        this.id = id;
        iniciarQ();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void iniciarQ(){
        ArrayList<TokenD> grammar = new ArrayList<>();
        Grammar g = new Grammar();
        // Expr = ( Stat ·";" · (white)* )* · (white)* ".".
        grammar.add(new TokenD("(", TokenD.P1));
        grammar.add(new TokenD("Stat", "Stat", true));
        grammar.add(new TokenD("·", Nodo.NCAT));
        grammar.add(new TokenD(";", ";", true));
        grammar.add(new TokenD("·", Nodo.NCAT));
        grammar.add(new TokenD("(", TokenD.P1));
        grammar.add(new TokenD("white", "white", true));
        grammar.add(new TokenD(")", TokenD.P2));
        grammar.add(new TokenD("*", Nodo.NKLEEN));
        grammar.add(new TokenD(")", TokenD.P2));
        grammar.add(new TokenD("*", Nodo.NKLEEN));
        grammar.add(new TokenD("·", Nodo.NCAT));
        grammar.add(new TokenD("(", TokenD.P1));
        grammar.add(new TokenD("white", "white", true));
        grammar.add(new TokenD(")", TokenD.P2));
        grammar.add(new TokenD("*", Nodo.NKLEEN));

        TokenD tk1 = new TokenD("Expr", "Expr", true);
        g.addProduction( new Production(tk1, grammar));


        grammar.clear();
        // Stat = Expression.
        grammar.add(new TokenD("Expression","Expression", true));

        TokenD tk2 = new TokenD("Stat", "Stat", true);
        g.addProduction( new Production(tk2, grammar));

        grammar.clear();
        //Expression = Term {"+"Term| "-" Term}.
        grammar.add(new TokenD("Term","Term", true));
        grammar.add(new TokenD("·", Nodo.NCAT));
        grammar.add(new TokenD("(", TokenD.P1));
        grammar.add(new TokenD("(", TokenD.P1));
        grammar.add(new TokenD("+","+", true));
        grammar.add(new TokenD("·", Nodo.NCAT));
        grammar.add(new TokenD("Term","Term", true));
        grammar.add(new TokenD(")", TokenD.P2));
        grammar.add(new TokenD("|", Nodo.NOR));
        grammar.add(new TokenD("(", TokenD.P1));
        grammar.add(new TokenD("-","+", true));
        grammar.add(new TokenD("·", Nodo.NCAT));
        grammar.add(new TokenD("Term","Term", true));
        grammar.add(new TokenD(")", TokenD.P2));
        grammar.add(new TokenD(")", TokenD.P2));
        grammar.add(new TokenD("*", Nodo.NKLEEN));

        TokenD tk3 = new TokenD("E'", "lexema xD", false);
        g.addProduction( new Production(tk3, grammar));

        grammar.clear();
        //Term = Factor{ "*" Factor| "/"Factor}.
        grammar.add(new TokenD("Factor","Factor", true));
        grammar.add(new TokenD("·", Nodo.NCAT));
        grammar.add(new TokenD("(", TokenD.P1));
        grammar.add(new TokenD("(", TokenD.P1));
        grammar.add(new TokenD("*","+", true));
        grammar.add(new TokenD("·", Nodo.NCAT));
        grammar.add(new TokenD("Factor","Factor", true));
        grammar.add(new TokenD(")", TokenD.P2));
        grammar.add(new TokenD("|", Nodo.NOR));
        grammar.add(new TokenD("(", TokenD.P1));
        grammar.add(new TokenD("/","+", true));
        grammar.add(new TokenD("·", Nodo.NCAT));
        grammar.add(new TokenD("Factor","Factor", true));
        grammar.add(new TokenD(")", TokenD.P2));
        grammar.add(new TokenD(")", TokenD.P2));
        grammar.add(new TokenD("*", Nodo.NKLEEN));

        TokenD tk4 = new TokenD("E'", "lexema xD", false);
        g.addProduction( new Production(tk4, grammar));

        grammar.clear();
        //Factor = ["-"] (Number|"("Expression")"
        grammar.add(new TokenD("(", TokenD.P1));
        grammar.add(new TokenD("-","+", true));
        grammar.add(new TokenD(")", TokenD.P2));
        grammar.add(new TokenD("?", Nodo.NQUESTION));
        grammar.add(new TokenD("·", Nodo.NCAT));
        grammar.add(new TokenD("(", TokenD.P1));
        grammar.add(new TokenD("Number","Number", true));
        grammar.add(new TokenD(")", TokenD.P2));
        grammar.add(new TokenD("|", Nodo.NOR));
        grammar.add(new TokenD("(", TokenD.P1));
        grammar.add(new TokenD("(","(", true));
        grammar.add(new TokenD("·", Nodo.NCAT));
        grammar.add(new TokenD("Expression","Expression", true));
        grammar.add(new TokenD(")",")", true));
        grammar.add(new TokenD("·", Nodo.NCAT));
        grammar.add(new TokenD(")", TokenD.P2));

        TokenD tk5 = new TokenD("Factor", "Factor", false);
        g.addProduction( new Production(tk5, grammar));

        grammar.clear();
        //Number = ( number	|decnumber).
        grammar.add(new TokenD("number","number", true));
        grammar.add(new TokenD("|", Nodo.NOR));
        grammar.add(new TokenD("decnumber","decnumber", true));

        TokenD tk6 = new TokenD("Number", "Number", false);
        g.addProduction( new Production(tk6, grammar));
    }

}
