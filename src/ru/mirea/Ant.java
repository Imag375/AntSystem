package ru.mirea;

import java.util.LinkedList;

public class Ant {

    private LinkedList route = new LinkedList();

    public void addNode(int node) {
        if(route.contains(node)) {
            while(route.contains(node)) {
                route.removeLast();
            }
        }
        route.addLast(node);
    }

    public int getNode(int index) {
        return (int)route.get(index);
    }

    public int getSizeRoute() {
        return route.size();
    }
    //public int get
}
