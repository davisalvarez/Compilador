/*
    UVG
    Diseño de Lenguajes de Programación
    Davis Alvarez - 15842
 */

package org.davis.bean.Tree;
import org.davis.bean.grammar.TokenD;

import java.util.ArrayList;
import java.util.Stack;
public class TreeBuilder {

    public Nodo postfixToTree(String regularExp){
        char letra;
        Stack<Nodo> operaciones = new Stack<Nodo>();

        //System.out.println("Postfix tree: "+regularExp);
        for(int i=0; i<regularExp.length(); ++i) {
            letra = regularExp.charAt(i);
            //System.out.print(letra);
            if (letra != '*' &&
                    letra != '+' &&
                    letra != '?' &&
                    letra != '|' &&
                    letra != '(' &&
                    letra != ')' &&
                    letra != '·') {
                if (letra =='«') letra = '(';
                else if (letra=='»') letra = ')';
                else if (letra=='┼') letra = '+';
                else if (letra=='┤') letra = '|';
                Nodo terminal = new Nodo(Character.toString(letra));
                operaciones.push(terminal);
            }else if (letra == '*'){
                Nodo closure = new Nodo(Nodo.NKLEEN, Character.toString(letra), operaciones.pop());
                operaciones.push(closure);
            }else if (letra == '+'){
                Nodo positive = new Nodo(Nodo.NPOSITIVE, Character.toString(letra), operaciones.pop());
                operaciones.push(positive);
            }else if (letra == '?'){
                Nodo question = new Nodo(Nodo.NQUESTION, Character.toString(letra), operaciones.pop());
                operaciones.push(question);
            }else if (letra == '|'){
                Nodo nodoR = operaciones.pop();
                Nodo nodoL = operaciones.pop();
                Nodo nodoOR = new Nodo(Nodo.NOR, Character.toString(letra), nodoL, nodoR);
                operaciones.push(nodoOR);
            }else if (letra == '·') {
                Nodo nodoR = operaciones.pop();
                Nodo nodoL = operaciones.pop();
                Nodo nodoOR = new Nodo(Nodo.NCAT, Character.toString(letra), nodoL, nodoR);
                operaciones.push(nodoOR);
            }

        }
        Nodo temp = operaciones.pop();
/*        System.out.println("_______________SOBRANTE_1_____________");
        while (!operaciones.isEmpty()){
            this.recorrerTree(operaciones.pop());
        }
        System.out.println("_______________SOBRANTE_2_____________");*/
/*        System.out.print("tree: ");
        this.recorrerTree(operaciones.peek());
        System.out.println("");*/

        //return operaciones.pop();
        return temp;

    }

    public NodoTK postfixToTreeToken(ArrayList<TokenD> regularExp){
        Stack<NodoTK> operaciones = new Stack<>();

        for (TokenD token: regularExp){
            if (    token.getValue() != Nodo.NKLEEN && //.equals("*")) &&
                    token.getValue() != Nodo.NPOSITIVE && //.equals("+")) &&
                    token.getValue() != Nodo.NQUESTION && //.equals("?")) &&
                    token.getValue() != Nodo.NOR && //.equals("|")) &&
                    token.getValue() != TokenD.P1 &&//.equals("(")) &&
                    token.getValue() != TokenD.P2 &&//.equals(")")) &&
                    token.getValue() != Nodo.NCAT) {
/*                     if (token.equals("«")) token = "(";
                else if (token.equals("»")) token = ")";
                else if (token.equals("┼")) token = "+";
                else if (token.equals("┤")) token = "|";*/
                NodoTK terminal = new NodoTK(token);
                operaciones.push(terminal);
            }else if (token.getValue() == Nodo.NKLEEN){
                NodoTK closure = new NodoTK(Nodo.NKLEEN, token, operaciones.pop());
                operaciones.push(closure);
            }else if (token.getValue() == Nodo.NPOSITIVE){
                NodoTK positive = new NodoTK(Nodo.NPOSITIVE, token, operaciones.pop());
                operaciones.push(positive);
            }else if (token.getValue() == Nodo.NQUESTION){
                NodoTK question = new NodoTK(Nodo.NQUESTION, token, operaciones.pop());
                operaciones.push(question);
            }else if (token.getValue() == Nodo.NOR){
                NodoTK nodoR = operaciones.pop();
                NodoTK nodoL = operaciones.pop();
                NodoTK nodoOR = new NodoTK(Nodo.NOR, token, nodoL, nodoR);
                operaciones.push(nodoOR);
            }else if (token.getValue() == Nodo.NCAT) {
                NodoTK nodoR = operaciones.pop();
                NodoTK nodoL = operaciones.pop();
                NodoTK nodoOR = new NodoTK(Nodo.NCAT, token, nodoL, nodoR);
                operaciones.push(nodoOR);
            }
        }
        System.out.print("Tree: ");
        this.recorrerTree(operaciones.peek());
        System.out.println("");
        return operaciones.pop();
    }

    public void recorrerTree(NodoTK ceiba){
        if(ceiba != null){
            this.recorrerTree(ceiba.getLeftC());
            System.out.print(ceiba.getToken().getName());
            this.recorrerTree(ceiba.getRightC());
        }
    }

}
