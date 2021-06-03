package org.davis.principal;

import org.davis.bean.Tree.Nodo;
import org.davis.bean.automata.Automata;
import org.davis.bean.grammar.Grammar;
import org.davis.bean.grammar.Production;
import org.davis.bean.grammar.TokenD;
import org.davis.dfa.DirectAutomataBuilder;
import org.davis.nfa.AutomataConverter;
import org.davis.nfa.ThompsonAutomataBuilder;
import org.davis.parser.Parser;
import org.davis.simulator.LexicalAnalyzer;
import org.davis.simulator.SimulateDFA;
import org.davis.simulator.SimulateNFA;
import org.davis.utility.ATGManager;
import org.davis.utility.FileManager;

import java.util.ArrayList;

public class Menu {

    public void probarGrammar(){
        Parser pro = new Parser();
        pro.parsear();
    }

    public void probarTokens(){
        LexicalAnalyzer LA = new LexicalAnalyzer();
        String name= "CoCoL";
        LA.analizar("ATG/"+name+".ATG", "ATG/"+name+".txt");
    }

    public void probaralgoritmos(){
        ThompsonAutomataBuilder davisHace = new ThompsonAutomataBuilder();
        AutomataConverter optimus = new AutomataConverter();
        DirectAutomataBuilder davisHace2 = new DirectAutomataBuilder();

        //String re = "(0|1|2|3|4|5|6|7|8|9|A|B|C|D|E|F)·(0|1|2|3|4|5|6|7|8|9|A|B|C|D|E|F)*·H";
        String re = "(a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z|a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z)·((a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z|a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z)|(0|1|2|3|4|5|6|7|8|9))*";

        long startTime;
        //Thompson simulación de NFA
        System.out.println("Thompson:");
        startTime = System.currentTimeMillis();
        Automata a = davisHace.armarMcNaughtonYamadaThompson(re,false);
        System.out.println(System.currentTimeMillis() - startTime + "ms");


        //NFA->DFA simulación de DFA
        System.out.println("NFA->DFA:");
        startTime = System.currentTimeMillis();
        a = davisHace.armarMcNaughtonYamadaThompson(re, false);
        a = optimus.NFAtoDFA(a);
        System.out.println(System.currentTimeMillis() - startTime + "ms");

        //RE->DFA simulación de DFA
        System.out.println("RE->DFA:");
        startTime = System.currentTimeMillis();
        a =davisHace2.REtoDFA(re, false);//  YES
        System.out.println(System.currentTimeMillis() - startTime + "ms");

    }

    public void probarThompson(){
        ThompsonAutomataBuilder davisHace = new ThompsonAutomataBuilder();
        SimulateNFA sNFA = new SimulateNFA();
        long startTime;
        //Thompson simulación de NFA
        startTime = System.currentTimeMillis();
        Automata a =davisHace.armarMcNaughtonYamadaThompson("(a|b)*a(a|b)(a|b)+", true);
        System.out.println(System.currentTimeMillis() - startTime + "ms");
        System.out.println("true = "+sNFA.simularNFA("bbbabbb", a));// YES
        System.out.println("false = "+sNFA.simularNFA("abc", a));
        //System.out.println("false = "+sNFA.simularNFA("ab", a));
        //System.out.println("true = "+sNFA.simularNFA("baababbabaa", a));
        System.out.println("");
        startTime = System.currentTimeMillis();
        a =davisHace.armarMcNaughtonYamadaThompson("((1?)*)*", true);  // YES
        System.out.println(System.currentTimeMillis() - startTime + "ms");
        System.out.println("true = "+sNFA.simularNFA("1", a));
        System.out.println("false = "+sNFA.simularNFA("abc", a));
        //System.out.println("true = "+sNFA.simularNFA("", a));
        //System.out.println("false = "+sNFA.simularNFA("1111111ε1111ε11ε", a));
        System.out.println("");
        startTime = System.currentTimeMillis();
        a =davisHace.armarMcNaughtonYamadaThompson("(a|ε)b(a+)c?", true); // YES
        System.out.println(System.currentTimeMillis() - startTime + "ms");
        System.out.println("false = "+sNFA.simularNFA("abc", a));
        System.out.println("false = "+sNFA.simularNFA("ced", a));
        //System.out.println("true = "+sNFA.simularNFA("ba", a));
        //System.out.println("true = "+sNFA.simularNFA("abaaaaac", a));
        System.out.println("");
        startTime = System.currentTimeMillis();
        a =davisHace.armarMcNaughtonYamadaThompson("(1|0)+001", true); // YES
        System.out.println(System.currentTimeMillis() - startTime + "ms");
        System.out.println("true = "+sNFA.simularNFA("1001", a));
        System.out.println("false = "+sNFA.simularNFA("001", a));
        // System.out.println("true = "+sNFA.simularNFA("101010101001010001", a));
        //System.out.println("false = "+sNFA.simularNFA("00101101010000", a));
        System.out.println("");
        startTime = System.currentTimeMillis();
        a =davisHace.armarMcNaughtonYamadaThompson("(a|ε)b(a+)c?", true);
        System.out.println(System.currentTimeMillis() - startTime + "ms");
        System.out.println("false = "+sNFA.simularNFA("abaaccccccc", a));
        System.out.println("false = "+sNFA.simularNFA("101", a));
        System.out.println("");
        startTime = System.currentTimeMillis();
        a =davisHace.armarMcNaughtonYamadaThompson("(εa|εb)*abb", true);  // YES
        System.out.println(System.currentTimeMillis() - startTime + "ms");
        System.out.println("true = "+sNFA.simularNFA("abb", a));
        System.out.println("false = "+sNFA.simularNFA("ab123", a));
        //System.out.println("true = "+sNFA.simularNFA("ababbaabb", a));
        //System.out.println("false = "+sNFA.simularNFA("ab", a));
    }

    public void probarNFAtoDFA(){
        ThompsonAutomataBuilder davisHace = new ThompsonAutomataBuilder();
        AutomataConverter optimus = new AutomataConverter();
        SimulateDFA sDFA = new SimulateDFA();

        long startTime;
        //NFA->DFA simulación de DFA
        startTime = System.currentTimeMillis();
        Automata a =davisHace.armarMcNaughtonYamadaThompson("(a|b)*a(a|b)(a|b)+", true);
        a = optimus.NFAtoDFA(a);
        System.out.println(System.currentTimeMillis() - startTime + "ms");
        System.out.println("true = "+sDFA.simularDFA("bbbabbb", a));//
        System.out.println("false = "+sDFA.simularDFA("abc", a));
        System.out.println("false = "+sDFA.simularDFA("ab", a));
        System.out.println("true = "+sDFA.simularDFA("baababbabaa", a));
        System.out.println("");
        startTime = System.currentTimeMillis();
        a =davisHace.armarMcNaughtonYamadaThompson("((1?)*)*", true);  //
        a = optimus.NFAtoDFA(a);
        System.out.println(System.currentTimeMillis() - startTime + "ms");
        System.out.println("true = "+sDFA.simularDFA("1", a));
        System.out.println("false = "+sDFA.simularDFA("abc", a));
        System.out.println("true = "+sDFA.simularDFA("", a));
        System.out.println("false = "+sDFA.simularDFA("1111111ε1111ε11ε", a));
        System.out.println("");
        startTime = System.currentTimeMillis();
        a =davisHace.armarMcNaughtonYamadaThompson("(a|ε)b(a+)c?", true); //
        a = optimus.NFAtoDFA(a);
        System.out.println(System.currentTimeMillis() - startTime + "ms");
        System.out.println("false = "+sDFA.simularDFA("abc", a));
        System.out.println("false = "+sDFA.simularDFA("ced", a));
        System.out.println("true = "+sDFA.simularDFA("ba", a));
        System.out.println("true = "+sDFA.simularDFA("abaaaaac", a));
        System.out.println("");
        startTime = System.currentTimeMillis();
        a =davisHace.armarMcNaughtonYamadaThompson("(1|0)+001", true); //
        a = optimus.NFAtoDFA(a);
        System.out.println(System.currentTimeMillis() - startTime + "ms");
        System.out.println("true = "+sDFA.simularDFA("1001", a));
        System.out.println("false = "+sDFA.simularDFA("001", a));
        System.out.println("true = "+sDFA.simularDFA("101010101001010001", a));
        System.out.println("false = "+sDFA.simularDFA("00101101010000", a));
        System.out.println("");
        startTime = System.currentTimeMillis();
        a =davisHace.armarMcNaughtonYamadaThompson("(a|ε)b(a+)c?", true);
        a = optimus.NFAtoDFA(a);
        System.out.println(System.currentTimeMillis() - startTime + "ms");
        System.out.println("false = "+sDFA.simularDFA("abaaccccccc", a));
        System.out.println("false = "+sDFA.simularDFA("101", a));
        System.out.println("");
        startTime = System.currentTimeMillis();
        a =davisHace.armarMcNaughtonYamadaThompson("(εa|εb)*abb", true);  //
        a = optimus.NFAtoDFA(a);
        System.out.println(System.currentTimeMillis() - startTime + "ms");
        System.out.println("true = "+sDFA.simularDFA("abb", a));
        System.out.println("false = "+sDFA.simularDFA("ab123", a));
        System.out.println("true = "+sDFA.simularDFA("ababbaabb", a));
        System.out.println("false = "+sDFA.simularDFA("ab", a));
    }

    public void probarDirecto(){
        DirectAutomataBuilder davisHace2 = new DirectAutomataBuilder();
        SimulateDFA sDFA = new SimulateDFA();
        long startTime;

        //RE->DFA simulación de DFA
        startTime = System.currentTimeMillis();
        Automata a =davisHace2.REtoDFA("(a|b)*a(a|b)(a|b)+", true);//  YES
        System.out.println(System.currentTimeMillis() - startTime + "ms");
        System.out.println("true = "+sDFA.simularDFA("bbbabbb", a));
        System.out.println("false = "+sDFA.simularDFA("abc", a));
        System.out.println("false = "+sDFA.simularDFA("ab", a));
        System.out.println("true = "+sDFA.simularDFA("baababbabaa", a));
        System.out.println("");
        startTime = System.currentTimeMillis();
        a =davisHace2.REtoDFA("((1?)*)*", true);  //  YES
        System.out.println(System.currentTimeMillis() - startTime + "ms");
        System.out.println("true = "+sDFA.simularDFA("1", a));
        System.out.println("false = "+sDFA.simularDFA("abc", a));
        System.out.println("true = "+sDFA.simularDFA("", a));
        System.out.println("false = "+sDFA.simularDFA("1111111ε1111ε11ε", a));
        System.out.println("");
        startTime = System.currentTimeMillis();
        a =davisHace2.REtoDFA("(a|ε)b(a+)c?", true); //  YES
        System.out.println(System.currentTimeMillis() - startTime + "ms");
        //a.tostring();
        System.out.println("false = "+sDFA.simularDFA("abc", a));
        System.out.println("false = "+sDFA.simularDFA("ced", a));
        System.out.println("true = "+sDFA.simularDFA("ba", a));
        System.out.println("true = "+sDFA.simularDFA("abaaaaac", a));
        System.out.println("");
        startTime = System.currentTimeMillis();
        a =davisHace2.REtoDFA("(1|0)+001", true); //   YES
        System.out.println(System.currentTimeMillis() - startTime + "ms");
        System.out.println("true = "+sDFA.simularDFA("1001", a));
        System.out.println("false = "+sDFA.simularDFA("001", a));
        System.out.println("true = "+sDFA.simularDFA("101010101001010001", a));
        System.out.println("false = "+sDFA.simularDFA("00101101010000", a));
        System.out.println("");
        startTime = System.currentTimeMillis();
        a =davisHace2.REtoDFA("(a|ε)b(a+)c?", true);//  YES
        System.out.println(System.currentTimeMillis() - startTime + "ms");
        System.out.println("false = "+sDFA.simularDFA("abaaccccccc", a));
        System.out.println("false = "+sDFA.simularDFA("101", a));
        System.out.println("");
        startTime = System.currentTimeMillis();
        a =davisHace2.REtoDFA("(εa|εb)*abb", true);  //  YES
        System.out.println(System.currentTimeMillis() - startTime + "ms");
        System.out.println("true = "+sDFA.simularDFA("abb", a));
        System.out.println("false = "+sDFA.simularDFA("ab123", a));
        System.out.println("true = "+sDFA.simularDFA("ababbaabb", a));
        System.out.println("false = "+sDFA.simularDFA("ab", a));
    }

    public void probarTostringAutomata(){

        DirectAutomataBuilder davisHace2 = new DirectAutomataBuilder();
        FileManager crudA = new FileManager();

        Automata a = davisHace2.REtoDFA("(a|b)*abb", true);
        crudA.writeAutomata("primero", a);
        a.tostring();
    }

}
