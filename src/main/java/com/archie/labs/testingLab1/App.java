package com.archie.labs.testingLab1;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class App 
{    
    public static void main(String[] args) {
        Scanner myReader = new Scanner(System.in);
		
		Automata a = new Automata(myReader);
		a.minimize().write(System.out);       
    }
}
