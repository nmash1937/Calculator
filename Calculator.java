
import java.util.Scanner;
import java.util.Stack;
/**
 * 
 * @author Nick Masciandaro
 *	Written for Data Structures then extended upon for multi digit numbers
 */
public class Calculator {
	public static void main(String[] args){
		System.out.println("Type Mathematical equation:");
		Scanner in = new Scanner(System.in);
		String input = in.nextLine();
		String postfix = infixToPostfix(input);
		System.out.println(postfix);
		int answer = solve(postfix);
		System.out.println(answer);
		
	}
	
	public static int solve(String postfix){
		int ans = 0;
		Stack<Integer> stack = new Stack<Integer>();
		Stack<Integer> number = new Stack<Integer>();
		boolean wasDigit = false;
		int num = 0;
		int count = 0;
		int power = 0;
		for(char c : postfix.toCharArray()){
			//checks if last char was a digit and current one is not
			if(wasDigit && !isDigit(c)) {
				int a = 0;
				power = count;
				while(power!=0) {
					a += (int) (number.pop()*Math.pow(10, count-power));
					power--;
				}
				stack.push(Integer.parseInt("" +a));
				num = 0;
				count = 0;
				wasDigit = false;
			}
			//Checks if Digit
			if(isDigit(c)){
				number.push(Integer.parseInt("" +c));
				count++;
				wasDigit = true;
				
			}
			else if(isOp(c)){
				wasDigit = false;
				int b = stack.pop();
				int a = stack.pop();
				if(c == '*'){
					int res = a*b;
					stack.push(res);
				}
				else if(c=='/'){
					int res = a/b;
					stack.push(res);
				}
				else if(c == '-'){
					int res = a-b;
					stack.push(res);
				}
				else if(c =='+'){
					int res = a+b;
					stack.push(res);
					System.out.println(res);
				}
				wasDigit = false;
			}
			
		}
		return stack.pop();
	}
	
	public static String infixToPostfix(String infix){
		Stack<Character> stack = new Stack<Character>();
		String postfix = "";
		boolean out = true;
		boolean wasDigit = false;
		String num = "";
		
		for(char c : infix.toCharArray()){
			if(wasDigit && !isDigit(c)){		
						postfix+=' ';
						postfix+=num;
						num = "";	
			}
			//Checks if digit
			if(isDigit(c)){
				num+=c;
				wasDigit = true;
			}
			//Checks if )
			else if(c == ')'){
				wasDigit=false;
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
			else if(c == '(') {
				stack.push(c);
				wasDigit=false;
			}
			//left with operator
			else{
				wasDigit=false;
				while(!stack.empty() && checkPriority(c, stack.peek()) && !(stack.peek()=='(')  ){
					postfix+= ' ';
					postfix+=stack.pop();
				}
				stack.push(c);
			}
		}
		if(num!="") {
			postfix+=" ";
			postfix+=num;
		}
		while(!stack.isEmpty()){
			postfix+= ' ';
			postfix+=stack.pop();
		}
		return postfix;
		
	}
	
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
	
	public static boolean isDigit(char c){
		if(c == '0' || c == '1' ||c == '2' ||c == '3' ||
				c == '4' ||c == '5' ||c == '6' ||c == '7' ||
				c == '8' ||c == '9') return true;
		return false;
	}
	
	public static boolean isOp(char c) {
		if(c=='*'||c=='/'||c=='-'||c=='+')return true;
		return false;
	}
}
