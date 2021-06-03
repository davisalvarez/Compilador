package org.davis.bean.grammar;

import java.util.ArrayList;

public class Grammar {

    private String name;
    private ArrayList<Production> productions;

    public Grammar(){
        name = "";
        this.productions = new ArrayList<>();
    }

    public void first() {

        for (Production pro: this.productions){

        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Production> getProductions() {
        return productions;
    }

    public void setProductions(ArrayList<Production> productions) {
        this.productions = productions;
    }

    public void addProduction(Production p){
        this.productions.add(p);
    }
}
