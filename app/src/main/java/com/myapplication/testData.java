package com.myapplication;

import java.util.ArrayList;

/**
 * Created by LJM on 2017/8/7 15:35.
 * Description:
 */

public class testData {
    private String text1;
    private String text2;
    private String text3;
    private String text4;
    private ArrayList<dataCollection> fire_list;

    public class dataCollection {

        public dataCollection(String text_one, String text_two) {
            this.text_one = text_one;
            this.text_two = text_two;
        }

        private String text_one;
        private String text_two;
    }

    public testData() {
        text1 = "1";
        text2 = "2";
        text3 = "3";
        text4 = "4";

        fire_list = new ArrayList<>();
        fire_list.add(new dataCollection("1", "2"));
        fire_list.add(new dataCollection("3", "4"));
        fire_list.add(new dataCollection("5", "6"));
        fire_list.add(new dataCollection("7", "8"));
    }
}
