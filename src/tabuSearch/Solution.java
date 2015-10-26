package tabuSearch;

import java.util.ArrayList;

public class Solution {
    private int id;
    private int objetiveValue;
    //private ArrayList<Bin> itemMap;

    public Solution() {

    }

    public Solution(Solution solution) {

        this.id=solution.id;
        this.objetiveValue=solution.objetiveValue;
        //this.itemMap=solution.itemMap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getObjValue() {
        return objetiveValue;
    }

    public void setObjValue(int objetiveValue) {
        this.objetiveValue = objetiveValue;
    }

//    public ArrayList<Bin> getBins() {
//        return itemMap;
//    }
//
//    public void setBins(ArrayList<Bin> itemMap) {
//        this.itemMap = itemMap;
//    }

    public void printSolutionContent() {
        System.out.println("Solution ID =" + id  + " | " + "fo = " + objetiveValue);
    }
}
