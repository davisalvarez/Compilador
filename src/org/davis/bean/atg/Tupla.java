package org.davis.bean.atg;

public class Tupla {

    private String name;
    private String content;
    private boolean check;
    private boolean ignore;

    public Tupla(){
        this.check =false;
        this.ignore =false;
    }

    public Tupla(String name, String content){
        this.name = name;
        this.content = content;
        this.check =false;
        this.ignore =false;
    }
    public Tupla(String name, String content, boolean check){
        this.name = name;
        this.content = content;
        this.check =check;
        this.ignore =false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }
}
