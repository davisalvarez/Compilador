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


        for (String cadena: script){
            String tkn = "";
            char symbol;
            int bl=0;
            int fw=0;
            cadena = cadena + "$";
            boolean ws;
            for(int i=0; i<cadena.length(); ++i) {
                fw = i;
                symbol = cadena.charAt(i);
                //tkn = cadena.substring(bl, fw+1);

                ws = Character.isWhitespace(symbol);
                System.out.println("->"+symbol);
                if (symbol == '\\'){
                    System.out.println("linea rara");
                }

                if (ws || symbol == '$'){
                    tkn = cadena.substring(bl, fw);
                    //System.out.println(i+"-"+tkn+"");
                    System.out.println(tkn);
                    boolean pass=false;
                    for (Automata androide17: fighterZ){
                        pass = sDFA.simularDFA(tkn, androide17);
                        //System.out.println(pass);
                        if (pass){
                            System.out.println("<"+androide17.getToken().getName()+">");
                            break;

                        }
                    }
                    if (!pass) System.out.println("No se identifico: '" + tkn+"'");
                    bl = fw+1;
                    ++i;

                }


            }
        }

/*        for(int i=0; i<cadena.length(); ++i) {
            fw = i;
            symbol = cadena.charAt(i);
            //tkn = cadena.substring(bl, fw+1);

            if (symbol == ' ' || symbol == '$'){
                tkn = cadena.substring(bl, fw);
                //System.out.println(i+"-"+tkn+"");
                System.out.println(tkn);
                boolean pass=false;
                for (Automata androide17: fighterZ){
                    pass = sDFA.simularDFA(tkn, androide17);
                    //System.out.println(pass);
                    if (pass){
                        System.out.println("<"+androide17.getToken().getName()+">");
                        break;

                    }
                }
                if (!pass) System.out.println("No se identifico: '" + tkn+"'");
                bl = fw+1;
                ++i;
            }
        }*/




    }
}
