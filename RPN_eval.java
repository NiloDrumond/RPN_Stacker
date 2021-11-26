import java.util.*;
import java.io.*;

public class RPN_eval {

	public static void main(String[] args) throws UnexpectedCharacterException {
		try {
			Stack<Integer> stack = new Stack<>();
			LinkedList<Token> tokens = new LinkedList<>();

			File text = new File("Calc1.stk");
			Scanner scanner = new Scanner(text);

			while (scanner.hasNext()) {
				String inp = scanner.next();

				if (inp.matches("-?\\d+")) {
					tokens.add(new Token(TokenType.NUM, inp));
				} else {
					tokens.add(new Token(detectOp(inp.charAt(0)), inp));
				}
			}

			tokens.forEach((temp) -> {
				System.out.println(temp.toString());
				if (temp.type == TokenType.NUM) {
					stack.push(Integer.parseInt(temp.lexeme));
				} else {
					Integer y = stack.pop();
					Integer x = stack.pop();
					stack.push(op(x, y, temp.type));
				}
			});

			System.out.println("\nRPN evaluation result: " + stack.peek());
			scanner.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not readble.");
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

	public static TokenType detectOp(char symbol) throws UnexpectedCharacterException {
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
