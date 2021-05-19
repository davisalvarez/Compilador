package org.davis.simulator;

import org.davis.bean.automata.Automata;
import org.davis.utility.ATGManager;
import org.davis.utility.FileManager;

import java.util.ArrayList;

public class LexicalAnalyzer {

    public void analizar(String pathCoCoL, String pathTest){
        FileManager flm = new FileManager();
        ATGManager atg = new ATGManager();
        SimulateDFA sDFA = new SimulateDFA();

        ArrayList<Automata>  fighterZ = atg.procesarATG(flm.readATG(pathCoCoL));

        ArrayList<String> script = flm.readLATest(pathTest);


        String tkn = "";
        char symbol;
        int bl=0;
        int fw=0;

        for (String cadena: script){
            if (cadena.length() == 0) continue;
            System.out.println("Cadena: "+cadena.trim());
            cadena = cadena + "$";
            boolean ws;
            bl=0;

            for(int i=0; i<cadena.length(); ++i) {
                fw = i;

                symbol = cadena.charAt(i);

                ws = Character.isWhitespace(symbol);

                //System.out.println("->"+symbol);

                if (ws || symbol == '$'){
                    tkn = cadena.substring(bl, fw);
                    System.out.print(tkn.trim());
                    boolean pass=false;
                    for (Automata androide17: fighterZ){
                        pass = sDFA.simularDFA(tkn.trim(), androide17);
                        if (pass){
                            System.out.println(" <"+androide17.getToken().getName()+">");
                            System.out.println("");
                            break;

                        }
                    }
                    if (!pass) System.out.println("No se identifico: '" + tkn.trim()+"'");

                    while (Character.isWhitespace(symbol)){
                        ++i;
                        symbol = cadena.charAt(i);
                    }
                    bl = fw+1;

                }


            }
        }

    }
}
