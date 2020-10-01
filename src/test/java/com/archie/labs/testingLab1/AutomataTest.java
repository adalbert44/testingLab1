package com.archie.labs.testingLab1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.StringContains.containsString;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AutomataTest {
	static int A;
	static int S;
	static int s0;
	static Set<Integer> finals;
	ArrayList<HashMap<Character, Integer>> edges;

	@BeforeClass
	public static void init() {
		A = 2;
		S = 3;
		s0 = 0;
		finals = new HashSet<Integer>();
		finals.add(2);
	}

	
	@Before
	public void initEdges() {
		A = 2;
		S = 3;
		s0 = 0;
		finals = new HashSet<Integer>();
		finals.add(2);
		edges = new ArrayList<HashMap<Character, Integer>>();
		edges.add(new HashMap<Character, Integer>());
		edges.add(new HashMap<Character, Integer>());
		edges.add(new HashMap<Character, Integer>());
		edges.get(0).put('a', 1);
	}
	
	@Test
	public void constructorWithAllArguments() {
		Automata a = new Automata(A, S, s0,  edges, finals);
		
		assertEquals(a.A, A);
		assertEquals(a.S, S);
		assertEquals(a.s0, s0);
		assertEquals(a.edges, edges);
		assertEquals(a.finals, finals);
	}
	
	@Test
	public void constructorFromFile() {
		Automata a = new Automata(new Scanner("2\n" + 
				"3\n" + 
				"0\n" + 
				"1 2\n" + 
				"0 a 1"));
		
		assertEquals(a.A, A);
		assertEquals(a.S, S);
		assertEquals(a.s0, s0);
		assertEquals(a.edges, edges);
		assertEquals(a.finals, finals);
	}
	
	@Test
	public void minimize() {
		Automata a = new Automata(A, S, s0, edges, finals);
		Automata aMinimized = a.minimize();
		assertSame(a, a);
		assertTrue(aMinimized instanceof Automata);
		assertNotNull(aMinimized);
		Set<Integer> newFinals = new HashSet<Integer>();
		newFinals.add(1);
		assertThat(aMinimized, equalTo(new Automata(A, 1, s0, edges, newFinals)));
	}
	
	@Test
	public void write() {
		String st = "2\n" + 
				"3\n" + 
				"0\n" + 
				"1 2\n" + 
				"0 a 1";
		Automata a = new Automata(new Scanner(st));

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		PrintStream out=new PrintStream(stream); 
		a.write(out);
		assertThat(stream.toString(), containsString(st));
	}
}