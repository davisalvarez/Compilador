/*
    UVG
    Diseño de Lenguajes de Programación
    Davis Alvarez - 15842

    Libro del gragón: 159
    Algorithm 3.23 : The McNaughton-Yamada-Thompson algorithm to convert a regular expression to an NFA.
 */
package org.davis.nfa;

import org.davis.bean.Tree.TreeBuilder;
import org.davis.bean.Tree.Nodo;
import org.davis.bean.automata.QState;
import org.davis.bean.automata.Transition;
import org.davis.bean.automata.Automata;
import org.davis.utility.Postfix;
import java.util.Stack;

public class ThompsonAutomataBuilder {

    private Stack<Automata> automatas;
    private Automata kakaroto;

    public ThompsonAutomataBuilder(){
        this.automatas = new Stack<Automata>();
        this.kakaroto = new Automata();
    }

    public Automata armarMcNaughtonYamadaThompson(String regularExpression, boolean prepare){

        Postfix convertir = new Postfix();
        TreeBuilder bobC = new TreeBuilder();
        Nodo ceiba;
        if (prepare){
            ceiba = bobC.postfixToTree(convertir.toPostfix(convertir.prePostfix(regularExpression), true));
        }else{
            ceiba = bobC.postfixToTree(convertir.toPostfix(regularExpression, false));
        }
        //System.out.println("Arbol: ");
        this.recorrerTree(ceiba);

        Automata goku = this.automatas.pop();
        goku.nameStates();
        //goku.tostring();

        return goku;
    }

    public void recorrerTree(Nodo ceiba){

        if(ceiba != null){

            this.recorrerTree(ceiba.getLeftC());
            //System.out.print(ceiba.getSimbolo());
            this.recorrerTree(ceiba.getRightC());

            if(ceiba.getTipo()==Nodo.NTERMINAL){
                //System.out.println("TERMINAL");
                this.armarNFATerminal(ceiba.getSimbolo());
            }else if (ceiba.getTipo()==Nodo.NKLEEN){
                //System.out.println("KLEEN");
                this.armarNFAKleen(this.automatas.pop());

            }else if (ceiba.getTipo()==Nodo.NPOSITIVE){
                //System.out.println("POSITIVE");
                this.armarNFAPositive(this.automatas.pop());

            }else if (ceiba.getTipo()==Nodo.NQUESTION){
                //System.out.println("QUESTION");
                this.armarNFAQuestion(this.automatas.pop());

            }else if (ceiba.getTipo()==Nodo.NOR){
                //System.out.println("OR");
                this.armarNFAOR(this.automatas.pop(), this.automatas.pop());

            }else if (ceiba.getTipo()==Nodo.NCAT){
                //System.out.println("CAT");
                Automata fin = this.automatas.pop();
                this.armarNFACAT(this.automatas.pop(), fin);
            }

        }

    }

    public void armarNFATerminal(String simbolo){
        QState androide17 = new QState();
        QState androide18 = new QState();

        Transition flecha = new Transition(androide17, simbolo, androide18);

        Automata terminal = new Automata();
        terminal.setInidialD(androide17);
        terminal.addFinalQ(androide18);
        terminal.addSimbolo(simbolo);
        terminal.addEstado(androide17);
        terminal.addEstado(androide18);
        terminal.addTransiciones(flecha);

        automatas.push(terminal);
    }

    public void armarNFAOR(Automata leftOR, Automata rightOR){
        QState optimos = new QState();
        QState megatron = new QState();

        Transition flecha1 = new Transition(optimos, "ε", leftOR.getInidialD());
        Transition flecha2 = new Transition(optimos, "ε", rightOR.getInidialD());

        leftOR.setInidialD(optimos);

        Transition flecha3 = new Transition(leftOR.getFinalQ().get(0), "ε", megatron);
        Transition flecha4 = new Transition(rightOR.getFinalQ().get(0), "ε", megatron);

        leftOR.getFinalQ().clear();
        leftOR.addFinalQ(megatron);

        for (QState s : rightOR.getEstados()){
            if(!leftOR.getEstados().contains(s))
                leftOR.addEstado(s);
        }
        leftOR.addEstado(megatron);
        leftOR.addEstado(optimos);

        leftOR.addSimbolo("ε");
        rightOR.getSimbolos().forEach((letra) -> leftOR.addSimbolo(letra));

        rightOR.getTransiciones().forEach((trans) -> leftOR.addTransiciones(trans));
        leftOR.addTransiciones(flecha1);
        leftOR.addTransiciones(flecha2);
        leftOR.addTransiciones(flecha3);
        leftOR.addTransiciones(flecha4);

        automatas.push(leftOR);
    }

    public void armarNFACAT(Automata start, Automata end){
        QState edward = start.getFinalQ().get(0);
        QState alphonse = end.getInidialD();

        // Sustituir todos los ed en start por al
        for(Transition trans: start.getTransiciones()){
            if(trans.getDestino() == edward){
                trans.setDestino(alphonse);
            }else if (trans.getOrigen() == edward){
                trans.setOrigen(alphonse);
            }
        }

        start.getFinalQ().clear();
        start.addFinalQ(end.getFinalQ().get(0));

        end.getSimbolos().forEach((letra) -> start.addSimbolo(letra));
        end.getTransiciones().forEach((trans) -> start.addTransiciones(trans));

        start.getEstados().remove(edward);
        for (QState s : end.getEstados()){
            if(!start.getEstados().contains(s))
                start.addEstado(s);
        }

        automatas.push(start);
    }

    public void armarNFAKleen(Automata trukns) {
        QState kakaroto = new QState();
        QState vegeta = new QState();

        Transition flecha1 = new Transition(trukns.getFinalQ().get(0), "ε", trukns.getInidialD());
        Transition flecha2 = new Transition(trukns.getFinalQ().get(0), "ε", vegeta);
        Transition flecha3 = new Transition(kakaroto, "ε", trukns.getInidialD());
        Transition flecha4 = new Transition(kakaroto, "ε", vegeta);

        trukns.setInidialD(kakaroto);
        trukns.getFinalQ().clear();
        trukns.addFinalQ(vegeta);
        trukns.addSimbolo("ε");
        trukns.addEstado(kakaroto);
        trukns.addEstado(vegeta);
        trukns.addTransiciones(flecha1);
        trukns.addTransiciones(flecha2);
        trukns.addTransiciones(flecha3);
        trukns.addTransiciones(flecha4);

        this.automatas.push(trukns);
    }

    public void armarNFAPositive(Automata tangiro) {
        QState inosuke = new QState();
        QState zenitsu = new QState();

        Transition flecha1 = new Transition(tangiro.getFinalQ().get(0), "ε", tangiro.getInidialD());
        Transition flecha2 = new Transition(tangiro.getFinalQ().get(0), "ε", zenitsu);
        Transition flecha3 = new Transition(inosuke, "ε", tangiro.getInidialD());

        tangiro.setInidialD(inosuke);
        tangiro.getFinalQ().clear();
        tangiro.addFinalQ(zenitsu);
        tangiro.addSimbolo("ε");
        tangiro.addEstado(inosuke);
        tangiro.addEstado(zenitsu);
        tangiro.addTransiciones(flecha1);
        tangiro.addTransiciones(flecha2);
        tangiro.addTransiciones(flecha3);

        this.automatas.push(tangiro);
    }

    public void armarNFAQuestion(Automata takumi) {
        QState ryosuke = new QState();
        QState keisuke = new QState();

        Transition flecha1 = new Transition(ryosuke, "ε", takumi.getInidialD());
        Transition flecha2 = new Transition(takumi.getFinalQ().get(0), "ε", keisuke);
        Transition flecha3 = new Transition(ryosuke, "ε", keisuke);

        takumi.setInidialD(ryosuke);
        takumi.getFinalQ().clear();
        takumi.addFinalQ(keisuke);
        takumi.addSimbolo("ε");
        takumi.addEstado(ryosuke);
        takumi.addEstado(keisuke);
        takumi.addTransiciones(flecha1);
        takumi.addTransiciones(flecha2);
        takumi.addTransiciones(flecha3);

        this.automatas.push(takumi);
    }
}
