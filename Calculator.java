import java.util.Scanner;
import java.util.Stack;
/**
 * Calculator: solves infix notated expression involving +-/*()
 * @author Nick Masciandaro
 *CSCI 258
 */
public class Calculator {
	/**
	 * Runs program
	 * @param args
	 */
	public static void main(String[] args){
		System.out.println("Type Mathematical Expression:");
		Scanner in = new Scanner(System.in);
		String input = in.nextLine();
		String[] split = input.split(" ");
		input = "";
		for(String a : split){
			input += a;
		}
		String postfix = infixToPostfix(input);
		System.out.println(postfix);
		Double answer = solve(postfix);
		System.out.println(answer);
		
	}
	
	/**
	 * Solves the postfix equation into an integer
	 * @param postfix expression yet to be solved
	 * @return answer of mathematic postfix expression
	 */
	public static Double solve(String postfix){
		double ans = 0;
		Stack<Double> stack = new Stack<Double>();
		for(char c : postfix.toCharArray()){
			//checks if digit
			if(isDigit(c)){
				stack.push( Double.parseDouble("" +c));
			}
			else{
				Double b = stack.pop();
				Double a = stack.pop();
				if(c == '*'){
					double res = a*b;
					stack.push(res);
				}
				else if(c=='/'){
					double res = a/b;
					stack.push(res);
				}
				else if(c == '-'){
					double res = a-b;
					stack.push(res);
				}
				else if(c =='+'){
					double res = a+b;
					stack.push(res);
				}
			}
		}
		return stack.pop();
	}
	
	/**
	 * Converts a infix equation into a postfix equation
	 * @param infix original mathematical expression
	 * @return converted postfix mathematical expression
	 */
	public static String infixToPostfix(String infix){
		Stack<Character> stack = new Stack<Character>();
		String postfix = "";
		boolean out = true;
		for(char c : infix.toCharArray()){
			//Checks if digit
			if(isDigit(c)){
				postfix+=c;
			}
			//Checks if )
			else if(c == ')'){
				while(!stack.isEmpty()&& out){
					if(!(stack.peek() == '(')){
						postfix += stack.pop();
					}else{
						stack.pop();
						out=false;
					}
				}
				out=true;
			}
			//Checks if (
			else if(c == '(') stack.push(c);
			//left with operator
			else{
				while(!stack.empty() && checkPriority(c, stack.peek()) && !(stack.peek()=='(')  ){
					postfix+=stack.pop();
				}
				stack.push(c);
			}
		}
		while(!stack.isEmpty()){
			postfix+=stack.pop();
		}
		return postfix;
		
	}
	
	/**
	 * Determines if first operator is higher or lower than the second operator in terms of priority
	 * @param c next operator
	 * @param d: top of stack operator
	 * @return if operator d is lower than c
	 */
	public static boolean checkPriority(char c, char d){
		int rankC = 0;
		int rankD = 0;
		if(c=='*' || c=='/') rankC=2;
		else rankC=1;
		
		if(d=='*' || d=='/') rankD=2;
		else rankD=1;
		
		if(rankC>rankD)return false;
		else return true;
		
	}
	
	/**
	 * Determines if the character given is a digit
	 * @param c character to be examined
	 * @return true if digit, false if not
	 */
	public static boolean isDigit(char c){
		if(c == '0' || c == '1' ||c == '2' ||c == '3' ||
				c == '4' ||c == '5' ||c == '6' ||c == '7' ||
				c == '8' ||c == '9') return true;
		return false;
	}
}
