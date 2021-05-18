package org.davis.utility;
/*
    UVG
    Diseño de Lenguajes de Programación
    Davis Alvarez - 15842
 */
import org.davis.grammar.TokenD;

import java.io.*;
import java.util.ArrayList;

class Stack {
    char a[]=new char[1000];
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
            }
            else if (symbol==')'){
                //push everything back to (
                while (operators.peek() != '(') {
                    postfix = postfix + operators.pop();
                }
                operators.pop();        //quito '('
            } else {
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

    public int prec(char x)
    {
        if (x == '*' || x == '+' || x == '?')
            return 3;
        if (x == '·') //Concatenacion
            return 2;
        if (x == '|')
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
}