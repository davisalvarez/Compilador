package org.davis.bean.automata;

import java.util.ArrayList;

public class FollowposTable {
    private int id;
    private ArrayList<Integer> followpos;

    public FollowposTable(int id){
        this.id=id;
        this.followpos = new ArrayList<Integer>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public ArrayList<Integer> getFollowpos() {
        return followpos;
    }

    public void setFollowpos(ArrayList<Integer> followpos) {
        this.followpos = followpos;
    }
}
