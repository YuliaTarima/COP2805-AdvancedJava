//----------------------------------------------------------------//
// Create two linked hash sets -----------------------------------//
// { "George" , "Jim" , "John" , "Blake" , "Kevin" , "Michael" } -//
// and -----------------------------------------------------------//
// { "George" , "Katie" , "Kevin" , "Michelle" , "Ryan" } --------//
// Find their union, difference, and intersection. ---------------//
// (You can clone the sets to preserve the original sets ---------//
// from being changed by these set methods.) ---------------------//
// ---------------------------------------------------------------//

package com.assignments.week10;

import java.util.Arrays;
import java.util.LinkedHashSet;

public class YuliaTarima_21_01 {
    public static void main(String[] args) {

        // Create and initialize the first linked hash set
        LinkedHashSet<String> set1 = new LinkedHashSet<>(Arrays.asList(
                "George", "Jim", "John", "Blake", "Kevin", "Michael"
        ));
        System.out.println("Set1: " + set1);

        // Create and initialize the second linked hash set
        LinkedHashSet<String> set2 = new LinkedHashSet<>(Arrays.asList(
                "George", "Katie", "Kevin", "Michelle", "Ryan"
        ));
        System.out.println("Set2: " + set2);

        // Clone to preserve original sets
        LinkedHashSet<String> unionSet = new LinkedHashSet<>(set1);
        LinkedHashSet<String> differenceSet = new LinkedHashSet<>(set1);
        LinkedHashSet<String> intersectionSet = new LinkedHashSet<>(set1);

        // Find the union of the two sets
        unionSet.addAll(set2);
        System.out.println("\nUnion: " + unionSet);
        System.out.println("Provides combined unduplicated results of both sets: set1 + set2");

        // Find the difference
        differenceSet.removeAll(set2);
        System.out.println("\nDifference: " + differenceSet);
        System.out.println("Provides unique results of set1: set1 - shared elements present in both sets");

        // Find the intersection of the two sets
        intersectionSet.retainAll(set2);
        System.out.println("\nIntersection: " + intersectionSet);
        System.out.println("Provides elements present in both sets: set1 and set2");
    }
}