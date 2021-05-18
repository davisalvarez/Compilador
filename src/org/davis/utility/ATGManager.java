/*
    UVG
    Diseño de Lenguajes de Programación
    Davis Alvarez - 15842
 */
package org.davis.utility;


import org.davis.bean.Tree.Nodo;
import org.davis.bean.atg.ATG;
import org.davis.bean.atg.Tupla;
import org.davis.dfa.DirectAutomataBuilder;
import org.davis.grammar.TokenD;
import org.davis.bean.automata.Automata;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ATGManager {

    private ATG myCOCO;
    private ATG coco;

    public ATGManager(){}

    public ArrayList<Automata> procesarATG(ATG coco){
        DirectAutomataBuilder davisHace2 = new DirectAutomataBuilder();
        this.myCOCO = new ATG();
        this.coco = coco;
        this.procesarCARACTERS();
        this.procesarKEYWORDS();
        this.procesarTOKEN();

        ArrayList<Automata> unicron = new ArrayList<>();
        Automata a;

        for (TokenD tok: myCOCO.getToken()){
/*            System.out.println(tok.getName());
            System.out.println(tok.getLexeme());*/
            a = davisHace2.REtoDFA(tok.getLexeme(), false);
            a.setToken(tok);
            unicron.add(a);
        }


        return unicron;
    }

    public void procesarCARACTERS(){

        ArrayList<Tupla> anyList = new ArrayList<>();

        for (Tupla chr: this.coco.getCaracter()){
/*            System.out.println("_______________________________________________");
            System.out.println(chr.getName());
            System.out.println(chr.getContent());*/

            Tupla tup = new Tupla();
            String re = "";
            char symbol;
            String spec = chr.getContent();

            if (spec.matches("'A' .. 'Z'")){
                re = "A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z";
                this.myCOCO.addCaracteres(new Tupla(chr.getName(), re, true));
                anyList.add(new Tupla(chr.getName(), re, true));
                continue;
            } else if (spec.matches("'a' .. 'z'")){
                re = "a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z";
                this.myCOCO.addCaracteres(new Tupla(chr.getName(), re, true));
                anyList.add(new Tupla(chr.getName(), re, true));
                continue;
            }

            String[] anyThing = spec.split("-");
            if (anyThing.length > 0){
                String cadena ="";
                if (anyThing[0].equals("ANY")){
                    //System.out.println("____________ANY_____________");
                    ArrayList<Tupla> myAny = new ArrayList<>(anyList);
                    for (String s: anyThing){
                        s = s.replace(".","");
                        Tupla mala = getCaracterANY(anyList, s);
                        if (mala != null){
                            myAny.remove(mala);
                        }

                    }
                    for (int i=0; i<myAny.size()-1;i++){
                        re = re + myAny.get(i).getContent() + "|";
                    }
                    re = re + myAny.get(myAny.size()-1).getContent();
                    //System.out.println("-"+re);

                    this.myCOCO.addCaracteres(new Tupla(chr.getName(), re, true));
                    continue;
                }
            }


            String cadena="";
            for(int i=0; i<spec.length(); ++i) {
                symbol = spec.charAt(i);
                cadena = cadena + symbol;
                cadena = cadena.trim();
                /*System.out.println("1c: "+cadena);*/
                boolean first = true;

                if (symbol == '\'' && spec.charAt(i+2)=='\''){
                    //System.out.println("---------------> " +spec.charAt(i+1));
                    char temp = spec.charAt(i+1);
                    if (temp =='(') temp = '«';
                    else if (temp==')') temp = '»';
                    else if (temp=='+') temp = '┼';
                    else if (temp=='|') temp = '┤';
                    re = re + temp;
                    //re = re + spec.charAt(i+1);
                    i=i+2;
                }
                if (symbol == '\"' && spec.charAt(i+2)=='\"'){
                    //System.out.println("---------------> " +spec.charAt(i+1));
                    char temp = spec.charAt(i+1);
                    if (temp =='(') temp = '«';
                    else if (temp==')') temp = '»';
                    else if (temp=='+') temp = '┼';
                    else if (temp=='|') temp = '┤';
                    re = re + temp;
                    i=i+2;
                }
                if (symbol == '"'){
                    boolean comillas = true;
                    while (comillas && i<spec.length()-1){

                        i++;
                        symbol = chr.getContent().charAt(i);

                        if (symbol == '"') {
                            comillas = false;
                        }else{

                            if (symbol =='(') symbol = '«';
                            else if (symbol==')') symbol = '»';
                            else if (symbol=='|') symbol = '┤';
                            else if (symbol=='+') symbol = '┼';
                            if (first){
                                re = re + symbol;
                                first = false;
                            }else{
                                re = re + "|"+ symbol;
                            }
                        }
                    }
                    if (comillas)
                        System.out.println("Faltan cerrar comillas en el CHARACTER "+ chr.getName());
                }
                if (symbol == '+'){
                    cadena = "";
                    re = re + "|";
                }
                if (!chekCaracteres(cadena.trim()).equals("$")){
                    re = re + chekCaracteres(cadena.trim());
                    cadena="";
                }
                if (cadena.equals("CHR(")){
                    String can = "";
                    try {
                        double d = Double.parseDouble(spec.charAt(i+1)+"");
                        can = can + spec.charAt(i+1);
                        i = i+1;
                    } catch (NumberFormatException nfe) {}
                    try {
                        double d = Double.parseDouble(spec.charAt(i+1)+"");
                        can = can + spec.charAt(i+1);
                        i = i+1;
                    } catch (NumberFormatException nfe) {}
                    if (can.equals("9")){//Tab
                        //re = re + '\t';
                        //System.out.println("aqui-"+'\t'+"- aqui");
                        re = re + Character.toString(9);
                    }else if (can.equals("10")){//salto de linea
                        //re = re + '\n';
                        //System.out.println("aqui-"+'\n'+"- aqui");
                        re = re + Character.toString(10);
                    }else if (can.equals("13")){//errores
                        // re = re + '\r';
                        re = re + Character.toString(9);
                        //System.out.println("aqui- "+'\r'+" - aqui13");
                    }else if (can.equals("34")){ // comillas
                        //re = re + '"';
                        re = re + Character.toString(34);
                        //System.out.println("aqui-"+'"'+"- aqui");
                    }

                }

                /*System.out.println("re:");
                System.out.println(re);*/
            }

            tup.setName(chr.getName());
            tup.setContent(re);
            tup.setCheck(true);
            this.myCOCO.addCaracteres(tup);
            anyList.add(new Tupla(chr.getName(), re, true));
            //this.myCOCO.addCaracteres(new Tupla(chr.getName(), re, true));

        }
        //this.pintTupla(myCOCO.getCaracter());
    }

    public void procesarTOKEN(){
        for (Tupla chr: this.coco.getTokenFake()){
/*            System.out.println("_______________________________________________");
            System.out.println(chr.getName());
            System.out.println(chr.getContent());*/
            ArrayList<TokenD> tempD = new ArrayList<>();
            String cadena = "";
            char symbol;
            String spec = chr.getContent();


            if (chr.getName().equals("IGNORE")){
                String[] tex=  chr.getContent().split(" ");
                for (String s: tex){
                    Tupla tp = getCaracter(s.trim());
                    if (tp != null){
                        TokenD tk2 = new TokenD();
                        tk2.setName(tp.getName());
                        tk2.setLexeme(tp.getContent());
                        tempD.add(tk2);
                    }
                }
                //this.pintTokenlist(tempD);
                continue;
            }

            for(int i=0; i<spec.length(); ++i) {
                symbol = spec.charAt(i);
                cadena = cadena + symbol ;

                //System.out.print(symbol);
                Tupla tp = getCaracter(cadena.trim());
                if (tp != null){
                    TokenD tk = new TokenD();
                    tk.setName(tp.getName());
                    tk.setLexeme("("+tp.getContent()+")");
                    tempD.add(tk);
                    if (spec.charAt(i+1)!='.' &&
                            //spec.charAt(i+1)!=' ' &&
                            spec.charAt(i+1)!='[' &&
                            spec.charAt(i+1)!=']' &&
                            spec.charAt(i+1)!='}' &&
                            spec.charAt(i+1)!='|' &&
                            spec.charAt(i+1)!='{' &&
                            spec.charAt(i+2)!='E'){
                        tempD.add(new TokenD("·", "·", Nodo.NCAT));
                    }

                    cadena = "";
                }else if (symbol =='\"'){
                    ++i;
                    String ter="";
                    while (spec.charAt(i)!='\"'){
                        char temp = spec.charAt(i);
                        if (temp =='(') temp = '«';
                        else if (temp==')') temp = '»';
                        else if (temp=='+') temp = '┼';
                        else if (temp=='|') temp = '┤';
                        ter=ter+ temp;
                        ++i;
                        if (i<spec.length()){
                            if (spec.charAt(i)!='\"'){
                                ter = ter +"·";
                            }
                        }
                    }
                    if (i+1<spec.length()){
                        if (spec.charAt(i+1)!='.' && spec.charAt(i+1)!=' ' && spec.charAt(i+1)!='[' && spec.charAt(i+1)!=']'){
                            ter = ter +"·";
                        }
                    }
                    //System.out.println("ter: "+ter);
                    tempD.add(new TokenD(ter, ter, Nodo.NTERMINAL));

                    cadena = "";

                }else if (symbol =='{'){
                    if (i>0) tempD.add(new TokenD("·", "·", Nodo.NCAT));
                    tempD.add(new TokenD("(", "(", TokenD.P1));
                    cadena = "";
                }else if (symbol =='}'){
                    tempD.add(new TokenD(")", ")", TokenD.P2));
                    tempD.add(new TokenD("*", "*", Nodo.NKLEEN));
                    cadena = "";
                    if (i+1<spec.length()){
                        if (spec.charAt(i+1)!='.' && spec.charAt(i+2)!='E'){
                            tempD.add(new TokenD("·", "·", Nodo.NCAT));
                        }
                    }
                }else if (symbol =='['){
                    if (i>0) tempD.add(new TokenD("·", "·", Nodo.NCAT));
                    tempD.add(new TokenD("(", "(", TokenD.P1));
                    cadena = "";
                }else if (symbol ==']'){
                    tempD.add(new TokenD(")", ")", TokenD.P2));
                    tempD.add(new TokenD("?", "?", Nodo.NQUESTION));
                    cadena = "";
                    if (i+1<spec.length()){
                        if (spec.charAt(i+1)!='.' && spec.charAt(i+1)!=' '){
                            tempD.add(new TokenD("·", "·", Nodo.NCAT));
                        }
                    }
                }else if (symbol =='|'){
                    tempD.add(new TokenD("|", "|", Nodo.NOR));
                    cadena = "";
                }
            }

            String re = "";
            for (TokenD t: tempD){
                re = re + t.getLexeme();
                //System.out.println(re);
            }
            //System.out.println(re);
            this.myCOCO.addToken(new TokenD(chr.getName(), re));
        }
    }

    public void procesarKEYWORDS(){
        for (Tupla chr: this.coco.getKeyword()) {
/*            System.out.println("_______________________________________________");
            System.out.println(chr.getName());
            System.out.println(chr.getContent());*/

            String re = "";
            char symbol;
            String spec = chr.getContent();

            spec = spec.replace("\"", "");
            spec = spec.replace(".", "");

            for(int i=0; i<spec.length()-1; ++i) {
                re = re + spec.charAt(i) + "·" ;
            }
            re = re + spec.charAt(spec.length()-1);
            //System.out.println("re: "+ re);

            this.myCOCO.addToken(new TokenD(chr.getName(), re));
        }

    }

    public String chekCaracteres(String cadena){

        for (Tupla t: this.myCOCO.getCaracter()){
            if(t.getName().equals(cadena)) {
                return t.getContent();
            }
        }
        return "$";
    }

    public Tupla getCaracter(String cadena){

        for (Tupla t: this.myCOCO.getCaracter()){
            if(t.getName().equals(cadena)) {
                //System.out.println(t.getName());
                return t;
            }
        }
        return null;
    }

    public Tupla getCaracterANY(ArrayList<Tupla> anyList, String cadena){

        for (Tupla t: anyList){
            if(t.getName().equals(cadena)) {
                //System.out.println(t.getName());
                return t;
            }
        }
        return null;
    }

    public void pintTupla(ArrayList<Tupla> lista){
        System.out.println("______________________________");
        for (Tupla t: lista){
            System.out.println(t.getName()+":");
            System.out.println(t.getContent());
            if (t.isIgnore()) System.out.println("IGNORE");
        }
        System.out.println("______________________________");
    }

    public void pintTokenlist(ArrayList<TokenD> lista){
        System.out.println("");
        for (TokenD t: lista){
            System.out.print(t.getName());
        }
        System.out.println("");
    }

    public void pintlist(ArrayList<String> lista){
        System.out.println("");
        for (String t: lista){
            System.out.print(t);
        }
        System.out.println("");
    }

}
