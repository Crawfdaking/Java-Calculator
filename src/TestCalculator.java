import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TestCalculator {

	@Test
	public void testcalc() {      
		Calculator calc = new Calculator();
		assertEquals("7", calc.computeResult("3+4"));
		assertEquals("12", calc.computeResult("3+4+5"));
		assertEquals("5", calc.computeResult("1*5"));
		assertEquals("30", calc.computeResult("1*5*6"));
		assertEquals("5", calc.computeResult("10/2"));
		assertEquals("2", calc.computeResult("20/2/5"));
		assertEquals("3", calc.computeResult("5-2"));
		assertEquals("0", calc.computeResult("5-2-3"));
		assertEquals("298", calc.computeResult("3*100-2"));
		assertEquals("13", calc.computeResult("3+5*2"));
		assertEquals("8", calc.computeResult("(3+4)-1+2"));
		assertEquals("8", calc.computeResult("(3+4)+2-1"));
		assertEquals("0", calc.computeResult("(3+4)-(3+4)"));
		assertEquals("56", calc.computeResult("(3+5)*(3+4)"));
		assertEquals("35", calc.computeResult("5*(3+4)"));
		assertEquals("10", calc.computeResult("(3+(3+4))"));

	}
	//calc = new Calculator();
}

