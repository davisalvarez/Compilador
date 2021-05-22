/*
    UVG
    Diseño de Lenguajes de Programación
    Davis Alvarez - 15842
 */
package org.davis.utility;

import org.davis.bean.automata.Automata;
import org.davis.bean.automata.Transition;
import org.davis.bean.atg.ATG;
import org.davis.bean.atg.Tupla;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {

    public void writeAutomata(String name, Automata atm){
        String info ="";
        try {
            File fileATM = new File(name+".txt");
            if (fileATM.createNewFile()) {
                FileWriter myWriter = new FileWriter(name+".txt");
                System.out.println("Archivo creado: " + fileATM.getName());

                info = info + ("ESTADOS = { ");
                for (int i = 0; i<atm.getEstados().size()-1; ++i){
                    info = info + (atm.getEstados().get(i).getId() +", ");
                }
                if (atm.getEstados().size()>=1) {
                    info = info + (atm.getEstados().get(atm.getEstados().size()-1).getId());
                    info = info + (" }\n");
                }

                info = info + ("SIMBOLOS = { ");
                for (int i = 0; i<atm.getSimbolos().size()-1; ++i){
                    info = info + (atm.getSimbolos().get(i) +", ");
                }
                if (atm.getSimbolos().size()>=1) {
                    info = info + (atm.getSimbolos().get(atm.getSimbolos().size()-1));
                    info = info + (" }\n");
                }

                /*myWriter.write(info);
                info ="";*/
                info = info + ("INICIO = { "+atm.getInidialD().getId()+" }\n");

                info = info + ("ACEPTACION = { ");
                for (int i = 0; i<atm.getFinalQ().size()-1; ++i){
                    info = info + (atm.getFinalQ().get(i).getId() +", ");
                }
                if (atm.getEstados().size()>=1) {
                    info = info + (atm.getFinalQ().get(atm.getFinalQ().size()-1).getId());
                    info = info + (" }\n");
                }

                info = info + ("TRANSICION = ");
                for(Transition f: atm.getTransiciones()){
                    info = info + ("( "+ f.getOrigen().getId());
                    info = info + (", "+f.getSimbolo() +", ");
                    info = info + (f.getDestino().getId()+") - ");
                }

                myWriter.write(info);
                myWriter.close();
            } else {
                System.out.println("El archivo "+name+" ya existe!");
            }
        } catch (IOException e) {
            System.out.println("No se pudo crear el archivo '" + name + "'!");
            e.printStackTrace();
        }
    }

    public ATG readATG(String filePath){
        ATG nCOCO =  new ATG();
        String[] info;
        int choose=0;
        boolean go =false;
        ArrayList<String> prePro = new ArrayList<>();

        Scanner myReader = null;
        try {
            File myObj = new File(filePath);
            myReader = new Scanner(myObj);
            filePath = "";
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                //System.out.println(data);
                if (!data.isEmpty()) {
                    go =true;
                    info = data.split(" ");
                    if (info[0].matches("COMPILER")){
                        choose = 1;
                        go =true;
                    }else if (info[0].matches("CHARACTERS")){
                        choose = 2;
                        go =false;
                    }else if (info[0].matches("KEYWORDS")){
                        choose = 3;
                        go =false;
                    }else if (info[0].matches("TOKENS")){
                        choose = 4;
                        go =false;
                    }else if (info[0].matches("END")){
                        choose = 5;
                        go =true;
                    }else if (info[0].matches("IGNORE")){
                        choose = 6;
                        go =true;
                    }else if (info[0].matches("PRODUCTIONS")){
                        choose = 7;
                        go =false;
                    }
                    if(go) {
                        try {


                            switch (choose) {
                                case 1:
                                    nCOCO.setCompailer(info[1].trim());
                                    //System.out.println(info[1]);
                                    choose = 0;
                                    go = false;
                                    break;
                                case 2:
                                    info = data.split("=", 2);
                                    //System.out.println(info[0]);
                                    //System.out.println(info[1]);
                                    nCOCO.addCaracteres(new Tupla(info[0].trim(), info[1].trim()));
                                    break;
                                case 3:
                                    info = data.split("=", 2);
                                    //System.out.println(info[0]);
                                    //System.out.println(info[1]);
                                    nCOCO.addKeyword(new Tupla(info[0].trim(), info[1].trim()));
                                    break;
                                case 4:
                                    info = data.split("=", 2);
                                    //System.out.println(info[0]);
                                    //System.out.println(info[1]);
                                    nCOCO.addTokenFake(new Tupla(info[0].trim(), info[1].trim()));
                                    break;
                                case 5:
                                    //System.out.println(info[1]);
                                    go = false;
                                    choose = 0;
                                    break;
                                case 6:
                                    info = data.split(" ");
                                    //System.out.println(info[0]);
                                    //System.out.println(info[1]);
                                    nCOCO.addTokenFake(new Tupla(info[0].trim(), info[2].trim()));
                                    break;
                                case 7:
                                    //System.out.println(info[0]);
                                    //System.out.println(info[1]);
                                    //nCOCO.addProductionFake(data);
                                    prePro.add(data);
                                    break;
                                default:
                                    // code block
                            }
                        }catch (ArrayIndexOutOfBoundsException e){}
                    }
                }

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("NO se encontro el archivos '" + filePath + "'.");
            e.printStackTrace();
        }

        nCOCO.setProductionFake(this.prepareProduction(prePro));
        return nCOCO;
    }

    public ArrayList<String> readLATest(String path){
        ArrayList<String> cadena = new ArrayList<>();
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                cadena.add(data);
            }
            myReader.close();
            //System.out.println(cadena);
        } catch (FileNotFoundException e) {
            System.out.println("NO se encontro el archivos '" + path + "'.");
            e.printStackTrace();
        }
        return cadena;
    }

    public ArrayList<String> prepareProduction(ArrayList<String> lista){

        ArrayList<String> productions = new ArrayList<>();
        String cadena="";
        for (String linea: lista) {
            char symbol;

            for (int i = 0; i < linea.length(); ++i) {
                symbol = linea.charAt(i);

                cadena = cadena + symbol;

                if (symbol == '.'){
                    try {
                        char cha = linea.charAt(i+1);
                    }catch (IndexOutOfBoundsException e){
                        //System.out.println(cadena);
                        productions.add(cadena);
                        cadena = "";
                    }
                }

            }
        }
        lista.clear();
        for (String linea: productions){
            linea = linea + "$";
            char symbol;
            cadena = "";
            for(int i=0; i<linea.length(); ++i) {
                symbol = linea.charAt(i);

                if (symbol =='$') break;

                if (symbol !='=' &&
                        symbol !='<' &&
                        symbol !='('){
                    cadena = cadena + symbol ;
                }

                if (symbol =='<'){

                    cadena = cadena + "█";
                    char n1 = symbol;
                    while (n1 !='>' ){
                        cadena = cadena + n1;
                        ++i;
                        n1 = linea.charAt(i);
                    }
                    cadena = cadena + ">█";
                    continue;
                }
                if (symbol =='(' && linea.charAt(i+1) =='.'){

                    cadena = cadena + "█";
                    char n1 = symbol;
                    char n2 = ' ';
                    while (n1 !='.' || n2 !=')'){
                        cadena = cadena + n1;
                        ++i;
                        n1 = linea.charAt(i);
                        n2 = linea.charAt(i+1);
                    }
                    ++i;
                    cadena = cadena + ".)█";
                    continue;
                }

                if (symbol =='='){
                        cadena = cadena + "█" + symbol;
                        cadena = cadena + "█";
                    continue;
                }

            }
            lista.add(cadena);
            //System.out.println("->"+cadena);
        }
        return lista;
    }

}
