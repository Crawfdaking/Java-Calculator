import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import java.util.*;
/**
 *
 * @author Crawford Herbert
 * @version 3.0
 * 
 *
 */
public class main {
	/**
	 * Main Method deals with the user and then passes the input to calculator
	 * @param args - default java args
	 */
	public static void main(String[] args) {
		runTests();
		Scanner scan = new Scanner(System.in);
		System.out.println("Hello, I am a calculator.");
		while(true){
			System.out.println(" Please enter a math problem to begin.");
			String formula = scan.nextLine();
			if (formula.toLowerCase().equals("stop")) {
				System.out.println("Stopping");
				break;
			}
			else if(formula.equals("Exit") || formula.equals("exit")) {
				System.out.println("Exiting");
				break;
			}


			Calculator calc = new Calculator();
			String result = calc.computeResult(formula);
			if(result == "paren"){
				System.out.println("You have unfinished parentheses. Please try your equation again");	
			}
			else {
				int resultAsNum = Integer.parseInt(result);
				System.out.println("Result = " + resultAsNum);
			}
		}
	}
	/**
	 * Test the calculator class
	 */
	public static void runTests() {
		Result result = JUnitCore.runClasses(TestCalculator.class);

	}
}