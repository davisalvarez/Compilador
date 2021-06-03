/*
    UVG
    Diseño de Lenguajes de Programación
    Davis Alvarez - 15842

    Libro del gragón: 150
    Algorithm 3.18 : Simulating a DFA.
 */
package org.davis.simulator;

import org.davis.bean.automata.Automata;
import org.davis.bean.automata.QState;
import java.util.ArrayList;

public class SimulateDFA {

    public boolean simularDFA(String cadena, Automata DFA){
        int cont = 0;
        //System.out.println("Simulando cadema: "+ cadena); // <----------------
        cadena = cadena + "ø";

        ArrayList<QState> S = new ArrayList<>();
        S.add(DFA.getInidialD());

        char c = cadena.charAt(cont);

        while (c != 'ø'){

            S = DFA.move(S,Character.toString(c),false);

            cont++;
            c = cadena.charAt(cont);
        }

        for (QState f: DFA.getFinalQ()) {
            if (S.contains(f))
                return true;
        }

        return false;
    }
}
