package com.restermans;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        SingleLinkedList<Integer> singleLinkedList = new SingleLinkedList<>();
        ArrayList<Integer> arrayList = new ArrayList<>();
        for(int i = 0 ; i < 10; ++i)
        {
            singleLinkedList.add(i);
            arrayList.add(i);
        }

        singleLinkedList.appendAnything(arrayList);
        System.out.println(singleLinkedList.reverseToString());
    }
}
