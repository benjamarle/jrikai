/**
 * 
 */
package org.rikai;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.rikai.deinflector.DeinflectedWord;
import org.rikai.deinflector.Deinflector;

/**
 * @author Benjamin
 *
 */
public class DeinflectorTest {
	
	private Deinflector deinflector;

	
	@Before
	public void setUp() throws FileNotFoundException, IOException{
		deinflector = new Deinflector("C:\\deinflect.dat");
	}
	
	@Test
	public void test() {
		ArrayList<DeinflectedWord> deinflect = deinflector.deinflect("嬉しくありませんでした");
		
		assert deinflect.size() > 0;
	}

}
