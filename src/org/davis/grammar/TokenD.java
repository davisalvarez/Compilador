package org.davis.grammar;

public class TokenD {
    public static final int P1 = 7;
    public static final int P2 = 8;
    public static final int C1 = 7;
    public static final int C2 = 8;
    public static final int L1 = 7;
    public static final int L2 = 8;

    private String lexeme;
    private String name;
    private int value;
    private boolean terminal;

    public TokenD(){}

    public TokenD(String name, String lexeme){
        this.lexeme = lexeme;
        this.name =  name;
        this.value = value;
    }

    public TokenD(String name, String lexeme, int value){
        this.lexeme = lexeme;
        this.name =  name;
        this.value = value;
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isTerminal() {
        return terminal;
    }

    public void setTerminal(boolean terminal) {
        this.terminal = terminal;
    }
}
