package com.example.hansangwon.mutoyou.Data;

/**
 * Created by JoJaeDeok on 2016-11-08.
 */

public class GradeData {

    public static double gradechange(String Sgrade) {
        switch (Sgrade) {
            case "A+":
                return 4.5;
            case "A":
                return 4.0;
            case "B+":
                return 3.5;
            case "B":
                return 3.0;
            case "C+":
                return 2.5;
            case "C":
                return 2.0;
            case "D":
                return 1.0;
            case "F":
                return 0.0;
            default:
                return 0.0;
        }
    }
}