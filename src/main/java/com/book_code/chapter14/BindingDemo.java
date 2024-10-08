package com.book_code.chapter14;

// https://liveexample.pearsoncmg.com/html/BindingDemo.html
// https://liveexample.pearsoncmg.com/LiveRun13e/faces/LiveExample.xhtml?programName=BindingDemo&programHeight=431&username=slide&header=on

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class BindingDemo {
    public static void main(String[] args) {
        DoubleProperty d1 = new SimpleDoubleProperty(1);
        DoubleProperty d2 = new SimpleDoubleProperty(2);
        d1.bind(d2); // Bind d1 with d2
        System.out.println("d1 is " + d1.getValue()
                + " and d2 is " + d2.getValue());
        d2.setValue(70.2);
        System.out.println("d1 is " + d1.getValue()
                + " and d2 is " + d2.getValue());
    }
}