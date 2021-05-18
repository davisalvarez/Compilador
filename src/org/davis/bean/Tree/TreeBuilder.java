/*
    UVG
    Diseño de Lenguajes de Programación
    Davis Alvarez - 15842
 */

package org.davis.bean.Tree;
import java.util.Stack;
public class TreeBuilder {

    public Nodo postfixToTree(String regularExp){
        char letra;

        //System.out.println("Postfix tree: "+regularExp);
        Stack<Nodo> operaciones = new Stack<Nodo>();
        for(int i=0; i<regularExp.length(); ++i) {
            letra = regularExp.charAt(i);
            //System.out.print(letra);
            if (letra != '*' && letra != '+' && letra != '?' && letra != '|'&& letra != '(' && letra != ')' && letra != '·') {
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

    public void recorrerTree(Nodo ceiba){
        if(ceiba != null){
            this.recorrerTree(ceiba.getLeftC());
            System.out.print(ceiba.getSimbolo());
            this.recorrerTree(ceiba.getRightC());
        }
    }

}
