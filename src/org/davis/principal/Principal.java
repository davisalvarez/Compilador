/*
    UVG
    Diseño de Lenguajes de Programación
    Davis Alvarez - 15842
 */
package org.davis.principal;

import org.davis.nfa.AutomataConverter;
import org.davis.nfa.ThompsonAutomataBuilder;
import org.davis.dfa.DirectAutomataBuilder;
import org.davis.simulator.LexicalAnalyzer;
import org.davis.utility.Lector;
import org.davis.simulator.SimulateDFA;
import org.davis.simulator.SimulateNFA;
import org.davis.utility.ATGManager;

import org.davis.bean.automata.Automata;
import org.davis.utility.FileManager;

public class Principal {

    public static void main(String args[]) {

        Lector read = new Lector();

        ThompsonAutomataBuilder davisHace = new ThompsonAutomataBuilder();
        DirectAutomataBuilder davisHace2 = new DirectAutomataBuilder();
        SimulateNFA sNFA = new SimulateNFA();
        SimulateDFA sDFA = new SimulateDFA();
        AutomataConverter optimus = new AutomataConverter();
        FileManager crudA = new FileManager();
        LexicalAnalyzer LA = new LexicalAnalyzer();

/*        Automata a = davisHace2.REtoDFA("(a|b)*abb");
        crudA.writeAutomata("primero", a);
        a.tostring();*/

/*        String name= "Proyecto3";
        LA.analizar("ATG/"+name+".ATG", "ATG/"+name+".txt");*/







/*        System.out.printf("_____________________________");*/

/*        //Thompson simulación de NFA
        Automata a =davisHace.armarMcNaughtonYamadaThompson("(a|b)*a(a|b)(a|b)+");
        System.out.println("true = "+sNFA.simularNFA("bbbabbb", a));// YES
        System.out.println("false = "+sNFA.simularNFA("abc", a));
        //System.out.println("false = "+sNFA.simularNFA("ab", a));
        //System.out.println("true = "+sNFA.simularNFA("baababbabaa", a));
        System.out.println("");
        a =davisHace.armarMcNaughtonYamadaThompson("((1?)*)*");  // YES
        System.out.println("true = "+sNFA.simularNFA("1", a));
        System.out.println("false = "+sNFA.simularNFA("abc", a));
        //System.out.println("true = "+sNFA.simularNFA("", a));
        //System.out.println("false = "+sNFA.simularNFA("1111111ε1111ε11ε", a));
        System.out.println("");
        a =davisHace.armarMcNaughtonYamadaThompson("(a|ε)b(a+)c?"); // YES
        System.out.println("false = "+sNFA.simularNFA("abc", a));
        System.out.println("false = "+sNFA.simularNFA("ced", a));
        //System.out.println("true = "+sNFA.simularNFA("ba", a));
        //System.out.println("true = "+sNFA.simularNFA("abaaaaac", a));
        System.out.println("");
        a =davisHace.armarMcNaughtonYamadaThompson("(1|0)+001"); // YES
        System.out.println("true = "+sNFA.simularNFA("1001", a));
        System.out.println("false = "+sNFA.simularNFA("001", a));
        // System.out.println("true = "+sNFA.simularNFA("101010101001010001", a));
        //System.out.println("false = "+sNFA.simularNFA("00101101010000", a));
        System.out.println("");
        a =davisHace.armarMcNaughtonYamadaThompson("(a|ε)b(a+)c?");
        System.out.println("false = "+sNFA.simularNFA("abaaccccccc", a));
        System.out.println("false = "+sNFA.simularNFA("101", a));
        System.out.println("");
        a =davisHace.armarMcNaughtonYamadaThompson("(εa|εb)*abb");  // YES
        System.out.println("true = "+sNFA.simularNFA("abb", a));
        System.out.println("false = "+sNFA.simularNFA("ab123", a));
        //System.out.println("true = "+sNFA.simularNFA("ababbaabb", a));
        //System.out.println("false = "+sNFA.simularNFA("ab", a));*/


/*        //NFA->DFA simulación de DFA
        Automata a =davisHace.armarMcNaughtonYamadaThompson("(a|b)*a(a|b)(a|b)+");
        a = optimus.NFAtoDFA(a);
        System.out.println("true = "+sDFA.simularDFA("bbbabbb", a));//
        System.out.println("false = "+sDFA.simularDFA("abc", a));
        System.out.println("false = "+sDFA.simularDFA("ab", a));
        System.out.println("true = "+sDFA.simularDFA("baababbabaa", a));
        System.out.println("");
        a =davisHace.armarMcNaughtonYamadaThompson("((1?)*)*");  //
        a = optimus.NFAtoDFA(a);
        System.out.println("true = "+sDFA.simularDFA("1", a));
        System.out.println("false = "+sDFA.simularDFA("abc", a));
        System.out.println("true = "+sDFA.simularDFA("", a));
        System.out.println("false = "+sDFA.simularDFA("1111111ε1111ε11ε", a));
        System.out.println("");
        a =davisHace.armarMcNaughtonYamadaThompson("(a|ε)b(a+)c?"); //
        a = optimus.NFAtoDFA(a);
        System.out.println("false = "+sDFA.simularDFA("abc", a));
        System.out.println("false = "+sDFA.simularDFA("ced", a));
        System.out.println("true = "+sDFA.simularDFA("ba", a));
        System.out.println("true = "+sDFA.simularDFA("abaaaaac", a));
        System.out.println("");
        a =davisHace.armarMcNaughtonYamadaThompson("(1|0)+001"); //
        a = optimus.NFAtoDFA(a);
        System.out.println("true = "+sDFA.simularDFA("1001", a));
        System.out.println("false = "+sDFA.simularDFA("001", a));
        System.out.println("true = "+sDFA.simularDFA("101010101001010001", a));
        System.out.println("false = "+sDFA.simularDFA("00101101010000", a));
        System.out.println("");
        a =davisHace.armarMcNaughtonYamadaThompson("(a|ε)b(a+)c?");
        a = optimus.NFAtoDFA(a);
        System.out.println("false = "+sDFA.simularDFA("abaaccccccc", a));
        System.out.println("false = "+sDFA.simularDFA("101", a));
        System.out.println("");
        a =davisHace.armarMcNaughtonYamadaThompson("(εa|εb)*abb");  //
        a = optimus.NFAtoDFA(a);
        System.out.println("true = "+sDFA.simularDFA("abb", a));
        System.out.println("false = "+sDFA.simularDFA("ab123", a));
        System.out.println("true = "+sDFA.simularDFA("ababbaabb", a));
        System.out.println("false = "+sDFA.simularDFA("ab", a));*/

        //RE->DFA simulación de DFA
/*        Automata a =davisHace2.REtoDFA("(a|b)*a(a|b)(a|b)+");//  YES
        System.out.println("true = "+sDFA.simularDFA("bbbabbb", a));
        System.out.println("false = "+sDFA.simularDFA("abc", a));
        System.out.println("false = "+sDFA.simularDFA("ab", a));
        System.out.println("true = "+sDFA.simularDFA("baababbabaa", a));
        System.out.println("");
        a =davisHace2.REtoDFA("((1?)*)*");  //  YES
        System.out.println("true = "+sDFA.simularDFA("1", a));
        System.out.println("false = "+sDFA.simularDFA("abc", a));
        System.out.println("true = "+sDFA.simularDFA("", a));
        System.out.println("false = "+sDFA.simularDFA("1111111ε1111ε11ε", a));
        System.out.println("");
        a =davisHace2.REtoDFA("(a|ε)b(a+)c?"); //  YES
        //a.tostring();
        System.out.println("false = "+sDFA.simularDFA("abc", a));
        System.out.println("false = "+sDFA.simularDFA("ced", a));
        System.out.println("true = "+sDFA.simularDFA("ba", a));
        System.out.println("true = "+sDFA.simularDFA("abaaaaac", a));
        System.out.println("");
        a =davisHace2.REtoDFA("(1|0)+001"); //   YES
        System.out.println("true = "+sDFA.simularDFA("1001", a));
        System.out.println("false = "+sDFA.simularDFA("001", a));
        System.out.println("true = "+sDFA.simularDFA("101010101001010001", a));
        System.out.println("false = "+sDFA.simularDFA("00101101010000", a));
        System.out.println("");
        a =davisHace2.REtoDFA("(a|ε)b(a+)c?");//  YES
        System.out.println("false = "+sDFA.simularDFA("abaaccccccc", a));
        System.out.println("false = "+sDFA.simularDFA("101", a));
        System.out.println("");
        a =davisHace2.REtoDFA("(εa|εb)*abb");  //  YES
        System.out.println("true = "+sDFA.simularDFA("abb", a));
        System.out.println("false = "+sDFA.simularDFA("ab123", a));
        System.out.println("true = "+sDFA.simularDFA("ababbaabb", a));
        System.out.println("false = "+sDFA.simularDFA("ab", a));*/
    }
}
