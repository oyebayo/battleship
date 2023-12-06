package com.findcomputerstuff.apps.battleship;

public interface InputScanner{
    String next();
    String nextLine();
    boolean hasNext();
    int nextInt();
    void close();
}