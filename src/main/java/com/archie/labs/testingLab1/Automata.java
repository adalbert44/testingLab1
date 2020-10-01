package com.archie.labs.testingLab1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.io.PrintStream;

public class Automata {
	public int A;
	public int S;
	public int s0;
	public List<HashMap<Character, Integer>> edges;
	public Set<Integer> finals;
	
	public Automata(Scanner myReader) {
        A = myReader.nextInt();
        S = myReader.nextInt();
        s0 = myReader.nextInt();
        int F = myReader.nextInt();
        finals = new HashSet<Integer>();
        for (int i = 0; i < F; i++) {
            int f = myReader.nextInt();
            finals.add(f);
        }
        edges = new ArrayList<HashMap<Character, Integer>>(S);
        for (int i = 0; i < S; i++) {
            edges.add(new HashMap<Character, Integer>());
        }

        while(myReader.hasNext()) {
            int s = myReader.nextInt();
            char a = myReader.next().charAt(0);
            int s_ = myReader.nextInt();
            edges.get(s).put(a, s_);
        }
	}
	
	public Automata(int A, int S, int s0, List<HashMap<Character, Integer>> edges, Set<Integer> finals) {
		this.A = A;
		this.S = S;
		this.s0 = s0;
		this.edges = edges;
		this.finals = finals;
	}
	
	public void write(PrintStream out) {
        out.println(A);
        out.println(S);
        out.println(s0);
        out.print(finals.size());
        for (int f:finals) {
            out.print(" " + f);
        }
        out.println();

        for (int s = 0; s < S; s++) {
            for (Map.Entry<Character, Integer> entry:edges.get(s).entrySet()) {
                out.println(s + " " + entry.getKey() + " " + entry.getValue());
            }
        }
	}
	
    public static HashMap<Integer, Integer> splitClasses(HashMap<Integer, Integer> eqClass, List<HashMap<Character, Integer>> edges) {
        HashMap<HashMap<Character, Integer>, Integer> edgesToEqClass = new HashMap<>();
        int lastUnusedEqClass = 0;
        HashMap<Integer, Integer> res = new HashMap<>();
        for (int i = 0; i < eqClass.size(); i++) {
            HashMap<Character, Integer> curEdges = new HashMap<>();
            for (Map.Entry<Character, Integer> entry:edges.get(i).entrySet()) {
                curEdges.put(entry.getKey(), eqClass.get(entry.getValue()));
            }

            if (!edgesToEqClass.containsKey(curEdges)) {
                edgesToEqClass.put(curEdges, lastUnusedEqClass);
                lastUnusedEqClass++;
            }
            res.put(i, edgesToEqClass.get(curEdges));
        }

        return res;
    }
	
	public Automata minimize() {
		HashMap<Integer, Integer> eqClass = new HashMap<>();
        for (int i = 0; i < S; i++) {
            if (finals.contains(i)) {
                eqClass.put(i, 1);
            } else {
                eqClass.put(i, 0);
            }
        }

        HashMap<Integer, Integer> oldEqClass;
        do {
            oldEqClass = eqClass;
            eqClass = splitClasses(eqClass, edges);
        } while(oldEqClass.equals(eqClass));
        
        int newS = 0;
        Set<Integer> newFinals = new HashSet<>();
        for (Map.Entry<Integer,Integer> entry :eqClass.entrySet()) {
            newS = Math.max(newS, entry.getValue());
            if(finals.contains(entry.getKey())) {
                newFinals.add(entry.getValue());
            }
        }
        
        List<HashMap<Character, Integer>> newEdges = new ArrayList<>(S);
        for (int i = 0; i < S; i++) {
            newEdges.add(new HashMap<Character, Integer>());
        }

        for (int s = 0; s < S; s++) {
            for (Map.Entry<Character, Integer> entry:edges.get(s).entrySet()) {
                newEdges.get(eqClass.get(s)).put(entry.getKey(), eqClass.get(entry.getValue()));
            }
        }

        return new Automata(A, newS, eqClass.get(s0), newEdges, newFinals);
	}
	
	@Override
	public boolean equals(Object o) {
			Automata a = (Automata) o;
			return this.A == a.A && this.S == a.S && this.edges.equals(a.edges) && this.finals.equals(a.finals);
	}
}
