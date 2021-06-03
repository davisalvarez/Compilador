package org.davis.bean.grammar;

import org.davis.bean.Tree.NodoTK;
import org.davis.bean.Tree.TreeBuilder;
import org.davis.utility.Postfix;

import java.util.ArrayList;

public class Production {

    private TokenD head;
    private ArrayList<TokenD> body;
    private NodoTK arbol;

    public Production(){}

    // Crear arbol desde String-Array
    public Production(TokenD head, ArrayList<TokenD> body){
        Postfix p = new Postfix();
        TreeBuilder bobC = new TreeBuilder();

        this.head = head;
        this.body = body;

        body = p.tokenToPostfix(this.body);
        this.arbol = bobC.postfixToTreeToken(body);
        //this.arbol.printFirst();

    }

    public TokenD getHead() {
        return head;
    }

    public void setHead(TokenD head) {
        this.head = head;
    }

    public ArrayList<TokenD> getBody() {
        return body;
    }

    public void setBody(ArrayList<TokenD> body) {
        this.body = body;
    }

    public NodoTK getArbol() {
        return arbol;
    }

    public void setArbol(NodoTK arbol) {
        this.arbol = arbol;
    }
}
