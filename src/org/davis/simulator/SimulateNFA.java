/*
    UVG
    Diseño de Lenguajes de Programación
    Davis Alvarez - 15842

    Libro del gragón: 156p
    Algorithm 3.22 : Simulating an NFA.
 */
package org.davis.simulator;

import org.davis.bean.automata.Automata;
import org.davis.bean.automata.QState;
import java.util.ArrayList;

public class SimulateNFA {

    public boolean simularNFA(String cadena, Automata NFA){
        int cont = 0;
        cadena = cadena + "ø";

        ArrayList<QState> S = new ArrayList<>();
        S.add(NFA.getInidialD());
        S = NFA.e_closure(S,false);

        char c = cadena.charAt(cont);

        while (c != 'ø'){

            S = NFA.move(S,Character.toString(c),false);
            S = NFA.e_closure(S,false);
            cont++;
            c = cadena.charAt(cont);
        }

        for (QState f: NFA.getFinalQ()) {
            if (S.contains(f))
                return true;
        }
        return false;
    }

    public void printS(ArrayList<QState> listaQ ){
        System.out.print("{ ");
        for (QState q: listaQ){
            System.out.print(q.getId() +", ");
        }
        System.out.println(" }");
    }
}
