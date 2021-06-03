package org.davis.bean.Tree;

import org.davis.bean.grammar.TokenD;

import java.util.ArrayList;

public class NodoTK{

    private int tipo;
    private TokenD token;
    private NodoTK leftC;
    private NodoTK rightC;
    private int id;

    private ArrayList<TokenD> first;

    public NodoTK() {}

    //Nodo token
    public NodoTK(TokenD tonken) {
        this.token = tonken;
        this.leftC = null;
        this.rightC = null;
    }

    //Nodo operador simple (1 child)
    public NodoTK(int tipo, TokenD token, NodoTK nodo) {
        this.tipo = tipo;
        this.token = token;
        this.leftC = nodo;
        this.rightC = null;
    }

    //Nodo operador complex (2 child)
    public NodoTK(int tipo, TokenD token, NodoTK nodoL, NodoTK nodoR) {
        this.tipo = tipo;
        this.token = token;
        this.leftC = nodoL;
        this.rightC = nodoR;
    }

    public void iniciarNodoRE() {
        this.id = -1;
        this.first = new ArrayList<>();
    }

    public void first(){
        if (tipo == Nodo.NCAT){
            this.first.addAll(leftC.first);
        }else if (tipo == Nodo.NOR){
            this.first.addAll(leftC.first);
            this.first.addAll(rightC.first);
        }else if (tipo == Nodo.NKLEEN){
            this.first.addAll(leftC.first);
        }else if (tipo == Nodo.NQUESTION){
            this.first.addAll(leftC.first);
            this.first.add(new TokenD("ε", TokenD.EP));
        }else{
            this.first.add(token);
        }
    }



    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public TokenD getToken() {
        return token;
    }

    public void setToken(TokenD token) {
        this.token = token;
    }

    public NodoTK getLeftC() {
        return leftC;
    }

    public void setLeftC(NodoTK leftC) {
        this.leftC = leftC;
    }

    public NodoTK getRightC() {
        return rightC;
    }

    public void setRightC(NodoTK rightC) {
        this.rightC = rightC;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void printFirst(){
        System.out.println("");
        System.out.print("{ ");
        for (TokenD t: this.first){
            System.out.print(t.getName());
            System.out.print(", ");
        }
        System.out.print(" }");

    }
}
