package org.davis.utility;
/*
    UVG
    Diseño de Lenguajes de Programación
    Davis Alvarez - 15842
 */

import org.davis.bean.Tree.Nodo;
import org.davis.bean.grammar.TokenD;

import java.util.ArrayList;

class Stack {
    char a[]=new char[10000];
    int top=-1;
    void push(char c)
    {
        try
        {
            a[++top]= c;
        }
        catch(StringIndexOutOfBoundsException e)
        {
            System.out.println("Stack full, no room to push, size=100");
            System.exit(0);
        }
    }
    char pop()
    {
        return a[top--];
    }
    boolean isEmpty()
    {
        return (top==-1)?true:false;
    }
    char peek()
    {
        return a[top];
    }
}

public class Postfix {
    private Stack operators = new Stack();
    private java.util.Stack<TokenD> operadores;

    public String toPostfix(String infix, boolean quitarK) {
        char symbol;
        String postfix = "";

        //System.out.println("infix: "+infix);

        for(int i=0; i<infix.length(); ++i){

            symbol = infix.charAt(i);
            //if it's an operand, add it to the string

            //System.out.println(symbol + " isLetter: "+ Character.isLetter(symbol));

            if (symbol != '*' && symbol != '+' && symbol != '?' && symbol != '|'&& symbol != '(' && symbol != ')' && symbol != '·') {
                //System.out.println("add: " +symbol);

                postfix = postfix + symbol;
            }else if (symbol=='('){
                //push (
                operators.push(symbol);
            }else if (symbol==')'){
                //push everything back to (
                while (operators.peek() != '(') {
                    postfix = postfix + operators.pop();
                }
                operators.pop();        //quito '('
            }else {
                //print operators occurring before it that have greater precedence
                while (!operators.isEmpty() && !(operators.peek()=='(') && prec(symbol) <= prec(operators.peek()))
                    postfix = postfix + operators.pop();

                if(operators.isEmpty() && prec(symbol) == 3 ){
                    postfix = postfix + symbol;
                }else {
                    operators.push(symbol);
                }
            }
        }
        while (!operators.isEmpty())
            postfix = postfix + operators.pop();


        //System.out.println("postfiX: "+postfix);
        if (quitarK) postfix =quitarKleens(postfix);
        //System.out.println("postfix: "+postfix);
        return postfix;
    }

    public int prec(char x) {
        if (x == '*' || x == '+' || x == '?')
            return 3;
        if (x == '·') //Concatenacion
            return 2;
        if (x == '|')
            return 1;
        return 0;
    }

    public ArrayList<TokenD> tokenToPostfix(ArrayList<TokenD> cadena){
        //String symbol;
        operadores = new java.util.Stack<>();
        ArrayList<TokenD> postfix = new ArrayList<>();

        for (TokenD symbol: cadena){

            if (    symbol.getValue() != Nodo.NKLEEN && //.equals("*")) &&
                    symbol.getValue() != Nodo.NPOSITIVE && //.equals("+")) &&
                    symbol.getValue() != Nodo.NQUESTION && //.equals("?")) &&
                    symbol.getValue() != Nodo.NOR && //.equals("|")) &&
                    symbol.getValue() != TokenD.P1 &&//.equals("(")) &&
                    symbol.getValue() != TokenD.P2 &&//.equals(")")) &&
                    symbol.getValue() != Nodo.NCAT){ //.equals("·"))){
                //System.out.println("add: " +symbol);

                postfix.add(symbol);
            }else if (symbol.getValue() == TokenD.P1){
                //push (
                operadores.push(symbol);
            }else if (symbol.getValue() == TokenD.P2){
                //push everything back to (
                while (operadores.peek().getValue() != TokenD.P1) {
                    postfix.add(operadores.pop());
                }
                operadores.pop();        //quito '('
            }else {
                //print operators occurring before it that have greater precedence
                while (!operadores.isEmpty() &&
                        symbol.getValue() != TokenD.P1 &&
                        precT(symbol) <= precT(operadores.peek()))
                    postfix.add(operadores.pop());

                if(operadores.isEmpty() && precT(symbol) == 3 ){
                    postfix.add(symbol);
                }else {
                    operadores.push(symbol);
                }
            }
        }
        while (!operadores.isEmpty())
            postfix.add(operadores.pop());

        this.pintTokenlist(postfix);

        return postfix;
    }

    public int precT(TokenD t) {
        if (    t.getValue() == Nodo.NKLEEN  ||
                t.getValue() == Nodo.NPOSITIVE  ||
                t.getValue() == Nodo.NQUESTION )
            return 3;
        if (t.getValue() == Nodo.NCAT ) //Concatenacion
            return 2;
        if (t.getValue() == Nodo.NOR)
            return 1;
        return 0;
    }

    public String prePostfix(String cadena){
        char symbol;
        char symbolN;
        String prePostfix = "";

        System.out.println("Expresión Regular:");
        System.out.println(cadena);

        for(int i=0; i<cadena.length()-1; ++i) {
            symbol = cadena.charAt(i);
            symbolN = cadena.charAt(i+1);

            prePostfix = prePostfix + symbol;

            // si es un caracter
            if (symbol != '*' && symbol != '+' && symbol != '?' && symbol != '|'&& symbol != '(') {
                //System.out.println("add: " +symbol);
                if (symbolN != '*' && symbolN != '+' && symbolN != '?' && symbolN != '|'&& symbolN != ')') {
                    prePostfix = prePostfix + "·";
                }

            }else if(symbol == '*' || symbol == '+' || symbol == '?'){
                if (symbolN != '*' && symbolN != '+' && symbolN != '?' && symbolN != '|'&& symbolN != ')')  {
                    prePostfix = prePostfix + "·";
                }
            }

        }

        prePostfix = prePostfix + cadena.charAt(cadena.length()-1);
        return prePostfix;
    }

    public String quitarKleens(String cadena){
        char symbol;
        char symbolN;
        String simp = "";

        for(int i=0; i<cadena.length()-1; ++i) {
            symbol = cadena.charAt(i);
            //symbolN = cadena.charAt(i + 1);
            int j = i;
            if (symbol == '*'){
                for (int x=j; x<cadena.length()-1; ++x){
                    symbolN = cadena.charAt(x + 1);
                    if (symbolN == '*'){
                        //System.out.println("++i");
                        ++i;
                    }
                }
            }
            //System.out.println("s: "+symbol);
            simp = simp + symbol;

        }
        if(cadena.length()>=2) {
            symbol = cadena.charAt(cadena.length() - 1);
            symbolN = cadena.charAt(cadena.length() - 2);
            if (symbol == '*' && symbolN == '*') {
                return simp;
            } else {
                //System.out.println("SI");
                simp = simp + cadena.charAt(cadena.length() - 1);
            }
        }
        return simp;
    }

    public void pintTokenlist(ArrayList<TokenD> lista){
        System.out.print("Postfix: ");
        for (TokenD t: lista){
            System.out.print(t.getName());
        }
        System.out.println("");
    }
}