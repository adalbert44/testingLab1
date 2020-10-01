package com.archie.labs.testingLab1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AppTest
{
	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
			{"2\n" + 
			"3\n" + 
			"0\n" + 
			"1 2\n" + 
			"0 a 1", 
			"2\n" + 
			"1\n" + 
			"0\n" + 
			"1 1\n" + 
			"0 a 1\n"}, 
			{"4\n" + 
			"5\n" + 
			"0\n" + 
			"2 3 4\n" + 
			"0 a 1\n" + 
			"0 b 2\n" + 
			"2 c 2\n" + 
			"1 c 1\n" + 
			"1 d 3\n" + 
			"2 c 4\n" + 
			"1 a 1", 
			"4\n" +
			"3\n" +
			"0\n" + 
			"1 3\n" + 
			"0 a 1\n" + 
			"0 b 2\n" + 
			"1 a 1\n" + 
			"1 c 1\n" + 
			"1 d 3\n" + 
			"2 c 3\n"}
		});
	}
	
	private String fInput;
	private String fExpected;
	

	public AppTest(String input, String expected) {
		fInput = input;
		fExpected = expected;
	}
	
	@Test
	public void test() {
		try {			
			PrintStream rout = System.out;
			System.setIn(new ByteArrayInputStream(fInput.getBytes()));
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			PrintStream out=new PrintStream(stream); 
			System.setOut(out);
			String[] args = {};
			App.main(args);
			rout.println(stream.toString());
			assertEquals(fExpected, stream.toString());
		}
		catch(Exception e)  
		{
			fail();
		}
	}
}
