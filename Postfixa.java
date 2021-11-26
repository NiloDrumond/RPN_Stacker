import java.util.*;
import java.io.*;

public class Postfixa {

	public static void main(String[] args) throws UnexpectedCharacterException {
		try {
			Stack<Integer> stack = new Stack<>();
			LinkedList<Token> tokens = new LinkedList<>();

			File input = new File("Calc1.stk");
			Scanner scanner = new Scanner(input);

			while (scanner.hasNext()) {
				String str = scanner.next();

				if (str.matches("-?\\d+")) {
					tokens.add(new Token(TokenType.NUM, str));
				} else {
					tokens.add(new Token(detectToken(str.charAt(0)), str));
				}
			}

			tokens.forEach((temp) -> {
				System.out.println(temp.toString());
				if (temp.type == TokenType.NUM) {
					stack.push(Integer.parseInt(temp.lexeme));
				} else {
					Integer a = stack.pop();
					Integer b = stack.pop();
					stack.push(op(b, a, temp.type));
				}
			});

			System.out.println("\nRPN result: " + stack.peek());
			scanner.close();

		} catch (FileNotFoundException e) {
			System.out.println("No file to read from.");
		}

	}

	public static Integer op(Integer a, Integer b, TokenType symbol) {
		switch (symbol) {
			case MINUS:
				return a - b;
			case PLUS:
				return a + b;
			case SLASH:
				return a / b;
			case STAR:
				return a * b;
			default:
				throw new Error("Error: Unexpected Symbol: " + String.valueOf(symbol));
		}
	}

	public static TokenType detectToken(char symbol) throws UnexpectedCharacterException {
		switch (symbol) {
			case '-':
				return TokenType.MINUS;
			case '+':
				return TokenType.PLUS;
			case '/':
				return TokenType.SLASH;
			case '*':
				return TokenType.STAR;
			default:
				throw new UnexpectedCharacterException("Error: Unexpected character: " + String.valueOf(symbol));
		}
	}

}
