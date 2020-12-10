
public class Calculator implements CalcInterface{
	private int op;
	private String formula;
	private int lastop;
	private int nextop;
	
	/**
	 * Constructs the calculator object with no arguments (Does nothing)
	 */
	public Calculator() {
	}
	
	/**
	 * Constructs the calculator object with arguments (Use this one)
	 * @param formula - current equation
	 */
	public Calculator(String formula) {
		this.formula = formula;
	}

	/**
	 * Finds the index of the first operator in PEMDAS order
	 * 
	 * @param String formula (contains the equation in question)
	 * @return index of operator
	 *
	 */
	private int findop(String formula) {
		int powerop = formula.indexOf('^', 0);
		if(powerop == -1) {
			powerop = 999999;
		}
		int multop = formula.indexOf('*', 0);
		if(multop == -1) {
			multop = 999999;
		}
		int divop = formula.indexOf('/', 0);
		if(divop == -1) {
			divop = 999999;
		}
		int addop = formula.indexOf('+', 0);
		if(addop == -1) {
			addop = 999999;
		}
		int subop = formula.indexOf('-', 0);
		if (subop == -1) {
			subop = 999999;
		}
		op = powerop;
		if(op == 999999) {
			op = Math.min(multop, divop);
		}
		if(op == 999999) {
			op = Math.min(addop, subop);
		}
		if(op == 999999) {
			op = -1;
		}
		return op;
	}

	/**
	 * Finds the index of the operator directly before current operator 
	 * 
	 * @param String - formula (contains the equation in question)
	 * @return Integer - index of last operator
	 */

	private int findlastop(String formula) {
		int powerop = formula.lastIndexOf('^', op-1);
		int multop = formula.lastIndexOf('*', op-1);
		int divop = formula.lastIndexOf('/', op-1);
		int addop = formula.lastIndexOf('+', op-1);
		int subop = formula.lastIndexOf('-', op-1);
		int bigger = Math.max(multop, divop);
		int Bigger = Math.max(addop, subop);
		int Biggest = Math.max(bigger, Bigger);
		lastop = Math.max(powerop, Biggest);
		if(lastop == -1) {
			lastop = 0;
		}
		return lastop;
	}


	/**
	 * Finds the operator directly after current operator
	 * 
	 * @param String - formula (contains the equation in question)
	 * @return Integer - index of next operator
	 */

	private int findnextop(String formula) {
		//char[] operators = new char[] {'^', '*', '/', '+', '-'};
		//finds the next operator and if its -1 the sets it to 999999
		int powerop = (formula.indexOf('^', op+1) == -1) ? 999999 : formula.indexOf('^', op+1);

		int multop = (formula.indexOf('*', op+1) == -1) ? 999999 : formula.indexOf('*', op+1);

		int divop = (formula.indexOf('/', op+1) == -1) ? 999999 : formula.indexOf('/', op+1);

		int addop = (formula.indexOf('+', op+1) == -1) ? 999999 : formula.indexOf('+', op+1);

		int subop = (formula.indexOf('-', op+1) == -1) ? 999999 : formula.indexOf('-', op+1);

		int nextop = Math.min(powerop, Math.min(Math.min(multop,divop), Math.min(addop, subop)));
		if(nextop == 999999) {
			nextop = formula.length();
		}
		return nextop;

	}

	/**
	 * Finds left side of current formula
	 * @param formula - current math equation
	 * @return Integer - value to calculate
	 */
	private int findleft(String formula) {
		String left = formula.substring(lastop, op);
		return Integer.parseInt(left);
	}
	
	/**
	 * Finds right side of current formula
	 * @param formula - current math equation
	 * @return Integer - value to calculate
	 */
	private int findright(String formula) {
		String right = formula.substring(op+1, nextop);
		return Integer.parseInt(right);
	}

	/**
	 * Does the actual math of the equation
	 * @param formula - current formula
	 * @param left - number left of current math operator
	 * @param right - right of current math operator
	 * @return Integer - the result of left and right calculated together
	 */
	private int getresult(String formula, int left, int right) {
		char operator = formula.charAt(op);
		int result = 0;
		switch(operator) {
		case '^':
			result = pow(left, right);
			break;
		case '*':
			result = mult(left, right);
			break;
		case '/':
			result = div(left, right);
			break;
		case '+':
			result = add(left, right);
			break;
		case '-':
			result = sub(left, right);
			break;
		}
		return result;
	}

	/**
	 * Computates everything inside parentheses 
	 * @param formula - current math equation
	 * @param openparen - left parenthesis
	 * @param closeparen - right parenthesis
	 * @return Integer - result of everything computed inside the parentheses 
	 */
	private String computeParen(String formula, int openparen, int closeparen) {
		int result = 0;
		String paren = formula.substring(openparen+1, closeparen);
		paren:while(paren.contains("^") || paren.contains("*") || paren.contains("/") || paren.contains("+") || paren.contains("-")) {
			op = findop(paren);
			lastop = findlastop(paren);
			nextop =  findnextop(paren);
			int left = findleft(paren);
			int right =  findright(paren);
			result = getresult(paren, left, right);
			paren = Substitute(result, paren, openparen, closeparen);
		}
		result = Integer.parseInt(paren);
		formula = Substitute(result, formula, openparen, closeparen);
		return formula;
	}



	/**
	 * Substitutes the result of the current computation back into the original equation
	 * @param result - TBD
	 * @param formula - current equation
	 * @param openparen - index of left parenthesis
	 * @param closeparen - index of right parenthesis
	 * @return String - the new equation after substitution
	 */
	private String Substitute(int result, String formula, int openparen, int closeparen) {
		String newFormula = "";
		if(findOpenParen(formula) >= 0 && findCloseParen(formula) >= 0) {
			//for things before paren
			if(openparen > 0) {
				newFormula = formula.substring(0, openparen) + Integer.toString(result) + formula.substring(closeparen+1, formula.length());
			}
			else {
				newFormula = Integer.toString(result) + formula.substring(closeparen+1, formula.length());
			}
		}
		else {
			//both if statements below are for non paren problems (do not change confirmed work)
			if(findlastop(formula) == 0) {
				newFormula = Integer.toString(result) + formula.substring(nextop, formula.length());
			}
			else if(findlastop(formula) > 0){
				newFormula = formula.substring(0, lastop+1) + Integer.toString(result) + formula.substring(nextop, formula.length());
			}
		}
		return newFormula;

		//all false
		//		if(findlastop(formula) == 0 && openparen == -1) {
		//			formula = Integer.toString(result) + formula.substring(nextop, formula.length());
		//		}
		//		//if just lastop true
		//		else if(findlastop(formula) > 0 && openparen == -1){
		//			formula = formula.substring(0, lastop+1) + Integer.toString(result) + formula.substring(nextop, formula.length());	 
		//		}
		//		//if just paren true but not inparen
		//		else if(findlastop(formula) == 0 && openparen > -1){
		//			formula = Integer.toString(result) + formula.substring(closeparen+1, formula.length());	 
		//		}
		//		//if lastop and paren both true but not inparen
		//		else if(findlastop(formula) > 0 && openparen > -1){
		//			formula = formula.substring(0, lastop+1) + Integer.toString(result) + formula.substring(closeparen, formula.length());
		//		}
		//		return formula;
	}


	private int findOpenParen(String formula) {
		int openparen = -1;
		for(int i =0; i < formula.length(); ++i) {
			if(formula.indexOf('(', openparen+1) >= 0) {
				openparen = formula.indexOf('(', openparen+1);
			}
			else {
				return openparen;
			}

		}
		return openparen;
	}
	
	/**
	 * Finds the closing parentheses
	 * @param formula - current equation
	 * @return Integer - index of the parenthesis
	 */
	private int findCloseParen(String formula) {
		int closeparen = -1;
		if(formula.indexOf(')') >= 0) {
			closeparen = formula.indexOf(')', closeparen);
		}
		if(formula.indexOf(')') < findOpenParen(formula)) {
			closeparen =  formula.indexOf(')', findOpenParen(formula));
		}
		return closeparen;
	}
	/**
	 * The first method called from main class. Determines which parts of the formal need to go where
	 * @param formula - current equation
	 * @return String - the answer
	 */
	public String computeResult(String formula){

		main:while(formula.contains("^") ||formula.contains("*") || formula.contains("/") || formula.contains("+") || formula.contains("-")) {
			int openparen = findOpenParen(formula);
			int closeparen = findCloseParen(formula);
			if(openparen >= 0 && closeparen == -1) {
				formula = "paren";
				break main;
			}
			else if(openparen == -1 && closeparen >= 0) {
				formula = "paren";
				break main;
			}
			if(openparen >= 0 && closeparen >= 0) {
				String parenResult = computeParen(formula, openparen, closeparen);
				formula = parenResult;
			}
			else {
				op = findop(formula);
				lastop = findlastop(formula);
				nextop =  findnextop(formula);
				int left = findleft(formula);
				int right =  findright(formula);
				int result = getresult(formula, left, right);
				formula = Substitute(result, formula, openparen, closeparen);
			}
		}


	return formula;
	}
	
	/**Multiples two numbers together
	 * @param a - left side number
	 * @param b - right side number
	 * @return Integer - result
	 */
	public int mult(int a, int b) {
		return a * b;
	}
	
	/**Divides two numbers
	 * @param a - left side number
	 * @param b - right side number
	 * @return Integer - result
	 */
	public int div(int a, int b) {
		return a / b;
	}
	
	/**Adds two numbers together
	 * @param a - left side number
	 * @param b - right side number
	 * @return Integer - result
	 */
	public int add(int a, int b) {
		return a + b;
	}
	
	/**Subtracts two numbers
	 * @param a - left side number
	 * @param b - right side number
	 * @return Integer - result
	 */
	public int sub(int a, int b) {
		return a - b;
	}
	
	/** Powers one number by another
	 * @param a - left side number
	 * @param b - right side number
	 * @return Integer - result
	 */
	public int pow(int a, int b) {
		int result = (int)Math.pow(Double.valueOf(a), Double.valueOf(b));
		return result;
	}

}

