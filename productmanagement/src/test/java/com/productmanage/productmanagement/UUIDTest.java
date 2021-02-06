package com.productmanage.productmanagement;

import java.util.Random;
import java.util.UUID;

public class UUIDTest {
    public static void main(String[] args) {
    	String password = "5H5S5v5k5T";
    	StringBuffer mima = new StringBuffer("");
        for(int j = 0; j < password.length(); j = j + 2) {
            int jie = (int)password.charAt(j) - 4;
            mima.append((char)jie);
        }
        System.out.println(mima);
    }
}
