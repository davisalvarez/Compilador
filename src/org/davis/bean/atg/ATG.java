package org.davis.bean.atg;

import org.davis.grammar.TokenD;

import java.util.ArrayList;

public class ATG {
    private String Compailer;
    private ArrayList<Tupla> caracter;
    private ArrayList<Tupla> keyword;
    private ArrayList<Tupla> tokenFake;
    private ArrayList<TokenD> token;

    public ATG(){
        this.caracter = new ArrayList<>();
        this.keyword = new ArrayList<>();
        this.tokenFake = new ArrayList<>();
        this.token = new ArrayList<>();
    }

    public String getCompailer() {
        return Compailer;
    }

    public void setCompailer(String compailer) {
        Compailer = compailer;
    }

    public ArrayList<Tupla> getCaracter() {
        return caracter;
    }

    public void setCaracter(ArrayList<Tupla> caracter) {
        this.caracter = caracter;
    }
    public void addCaracteres(Tupla caracter){
        this.caracter.add(caracter);
    }

    public ArrayList<Tupla> getKeyword() {
        return keyword;
    }

    public void setKeyword(ArrayList<Tupla> keywords) {
        this.keyword = keywords;
    }

    public void addKeyword(Tupla nueva){
        this.keyword.add(nueva);
    }

    public void addTokenFake(Tupla nueva){
        this.tokenFake.add(nueva);
    }
    public void addToken(TokenD nuevo){
        this.token.add(nuevo);
    }

    public ArrayList<Tupla> getTokenFake() {
        return tokenFake;
    }

    public void setTokenFake(ArrayList<Tupla> tokenFake) {
        this.tokenFake = tokenFake;
    }

    public ArrayList<TokenD> getToken() {
        return token;
    }

    public void setToken(ArrayList<TokenD> token) {
        this.token = token;
    }
}
