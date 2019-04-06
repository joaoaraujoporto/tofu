package io.tofu.teprl.machines.er;

import java.util.Stack;

import io.tofu.teprl.machines.Machine;
import io.tofu.teprl.machines.exceptions.EditarMecanismoException;

public class ER extends Machine {
	
	private String expression;

	public ER(String name) {
		super(name);
		expression = "&";
	}
	
	public void setExpression(String expressao) throws EditarMecanismoException {
		if (!isCorrectExpression(expressao))
			throw new EditarMecanismoException("There are unbalanced parentheses in the expression");
		
		this.expression = expressao;
	}
	
	private boolean isCorrectExpression(String expression) {
		// TODO - verify operators
		
		Stack<Character> stack = new Stack<Character>();
		
		for (Character symbol : expression.toCharArray()) {
			if (symbol.equals('('))
				stack.push('(');
			
			if (symbol.equals(')')) {
				if (stack.empty())
					return false;
				
				stack.pop();
			}
		}
		
		if (stack.empty())
			return true;
		
		return false;
	}
	
	public String getExpression() {
		return expression;
	}
}

