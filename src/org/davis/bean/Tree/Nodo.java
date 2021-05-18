package org.davis.bean.Tree;

import java.util.ArrayList;

public class Nodo {

    public static final int NTERMINAL = 0;
    public static final int NKLEEN = 1;
    public static final int NPOSITIVE = 2;
    public static final int NQUESTION = 3;
    public static final int NOR = 4;
    public static final int NCAT = 5;

    private int tipo;
    private String simbolo;
    private Nodo leftC;
    private Nodo rightC;

    private int id;
    private boolean nullable;
    private ArrayList<Integer> fistpos;
    private ArrayList<Integer> lastpos;

    public Nodo(String terminal) {
        this.tipo = Nodo.NTERMINAL;
        this.simbolo = terminal;
        this.leftC = null;
        this.rightC = null;
    }

    public Nodo(int tipo, String simbolo, Nodo nodo) {
        this.tipo = tipo;
        this.simbolo = simbolo;
        this.leftC = nodo;
        this.rightC = null;
    }

    public Nodo(int tipo, String simbolo, Nodo nodoL, Nodo nodoR) {
        this.tipo = tipo;
        this.simbolo = simbolo;
        this.leftC = nodoL;
        this.rightC = nodoR;
    }

    public void nullable(){
        if(tipo == Nodo.NTERMINAL && (!simbolo.equals("ε")))
            this.nullable = false;
        else if(tipo == Nodo.NTERMINAL && (simbolo.equals("ε")))
            this.nullable = true;
        else if(tipo == Nodo.NOR)
            this.nullable = leftC.isNullable() || rightC.isNullable();
        else if(tipo == Nodo.NCAT)
            this.nullable = leftC.isNullable() && rightC.isNullable();
        else if(tipo == Nodo.NKLEEN)
            this.nullable = true;
        else if(tipo == Nodo.NPOSITIVE)
            this.nullable = false;
        else if(tipo == Nodo.NQUESTION)
            this.nullable = true;
    }

    public void fistpos(){
        if(tipo == Nodo.NTERMINAL && (!simbolo.equals("ε"))){
            this.fistpos.add(this.id);
        } else if(tipo == Nodo.NOR) {
            this.fistpos.addAll(leftC.fistpos);
            this.fistpos.addAll(rightC.fistpos);
        }else if(tipo == Nodo.NCAT) {
            if (leftC.isNullable()){
                this.fistpos.addAll(leftC.fistpos);
                this.fistpos.addAll(rightC.fistpos);
            }else {
                this.fistpos.addAll(leftC.fistpos);
            }
        }else if(tipo == Nodo.NKLEEN) {
            this.fistpos.addAll(leftC.fistpos);
        }else if(tipo == Nodo.NPOSITIVE) {
            this.fistpos.addAll(leftC.fistpos);
        }else if(tipo == Nodo.NQUESTION) {
            this.fistpos.addAll(leftC.fistpos);
        }
    }

    public void lasttpos(){
        if(tipo == Nodo.NTERMINAL && (!simbolo.equals("ε"))){
            this.lastpos.add(this.id);
        } else if(tipo == Nodo.NOR) {
            this.lastpos.addAll(leftC.lastpos);
            this.lastpos.addAll(rightC.lastpos);
        }else if(tipo == Nodo.NCAT) {
            if (rightC.isNullable()){
                this.lastpos.addAll(leftC.lastpos);
                this.lastpos.addAll(rightC.lastpos);
            }else {
                this.lastpos.addAll(rightC.lastpos);
            }
        }else if(tipo == Nodo.NKLEEN) {
            this.lastpos.addAll(leftC.lastpos);
        }else if(tipo == Nodo.NPOSITIVE) {
            this.lastpos.addAll(leftC.lastpos);
        }else if(tipo == Nodo.NQUESTION) {
            this.lastpos.addAll(leftC.lastpos);
        }
    }

    public void iniciarNodoRE() {
        this.id = -1;
        this.fistpos = new ArrayList<Integer>();
        this.lastpos = new ArrayList<Integer>();
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Nodo getRightC() {
        return rightC;
    }

    public void setRightC(Nodo rightC) {
        this.rightC = rightC;
    }

    public Nodo getLeftC() {
        return leftC;
    }

    public void setLeftC(Nodo leftC) {
        this.leftC = leftC;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public ArrayList<Integer> getFistpos() {
        return fistpos;
    }

    public void setFistpos(ArrayList<Integer> fistpos) {
        this.fistpos = fistpos;
    }

    public ArrayList<Integer> getLastpos() {
        return lastpos;
    }

    public void setLastpos(ArrayList<Integer> lastpos) {
        this.lastpos = lastpos;
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

}