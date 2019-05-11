package io.tofu.teprl.machines.grammar;

import java.util.ArrayList;
import java.util.Stack;

public class Expander {
	public static void split(Production p, G g) {
		removeUnPar(p);
		ArrayList<Symbol> body = p.getBody();
		
		int lastIndInit = 0;
		
		for (int i = 0; i < body.size(); i++) {
			if (body.get(i).getValue().equals("|") && body.get(i) instanceof AuxSym) {
				ArrayList<Symbol> exp = new ArrayList<Symbol>();
				
				for (int j = lastIndInit; j < i; j++)
					exp.add(body.get(j));
				
				if (isBalanced(exp)) {
					Symbol newNonTerminal = new Symbol(p.getHead().getValue() + "'");
					Production newP = new Production(newNonTerminal, (ArrayList<Symbol>) exp.clone());
					
					g.addProduction(newP);					
				
					for (int j = lastIndInit; j <= i; j++)
						body.remove(0);
					
					split(newP, g);
					split(p, g);
					
					return;
				}
			}
		}
	}
	
	public static void removeStar(Production p, G g) {
		ArrayList<Symbol> body = p.getBody();
		
		for (int i = 0; i < body.size(); i++)
			if (body.get(i).getValue().equals("*") && body.get(i) instanceof AuxSym) {
				body.remove(i);
				
				ArrayList<Symbol> exp = new ArrayList<Symbol>();
				Symbol newNonTerminal = new Symbol(p.getHead().getValue() + "'");
				int posOP = i - 1;
				ArrayList<Symbol> expAux = new ArrayList<Symbol>();

				if (body.get(i-1).getValue().equals(")")) {
					posOP = getPositionOtherPar(i-1, body);
					
					for (int j = posOP; j < i ; j++)
						expAux.add(body.get(j));
				} else {
					expAux.add(body.get(i-1));					
				}
				
				exp.addAll(expAux);
				exp.add(newNonTerminal);
				exp.add(new Symbol("|"));
				exp.addAll(expAux);
				
				g.addProduction(new Production(newNonTerminal, (ArrayList<Symbol>) exp.clone()));					
				
				exp.add(0 ,new Symbol("("));
				exp.add(new Symbol("|"));
				exp.add(new Symbol("&"));
				exp.add(new Symbol(")"));
				
				body.addAll(posOP, exp);

				for (int j = posOP + exp.size(); j < posOP + exp.size() + expAux.size(); j++)
					body.remove(posOP + exp.size());
				
				removeStar(p, g);
				
				break;
			}
	}
	
	public static void removeInterr(Production p, G g) {
		ArrayList<Symbol> body = p.getBody();
		
		for (int i = 0; i < body.size(); i++)
			if (body.get(i).getValue().equals("?") && body.get(i) instanceof AuxSym) {
				body.remove(i);
				
				ArrayList<Symbol> exp = new ArrayList<Symbol>();
				Symbol newNonTerminal = new Symbol(p.getHead().getValue() + "'");
				int posOP = i - 1;
				ArrayList<Symbol> expAux = new ArrayList<Symbol>();

				if (body.get(i-1).getValue().equals(")")) {
					posOP = getPositionOtherPar(i-1, body);
					
					for (int j = posOP; j < i ; j++)
						expAux.add(body.get(j));
				} else {
					expAux.add(body.get(i-1));					
				}
				
				exp.addAll(expAux);				
				exp.add(0 ,new Symbol("("));
				exp.add(new Symbol("|"));
				exp.add(new Symbol("&"));
				exp.add(new Symbol(")"));
				
				body.addAll(posOP, exp);

				for (int j = posOP + exp.size(); j < posOP + exp.size() + expAux.size(); j++)
					body.remove(posOP + exp.size());
				
				removeInterr(p, g);
				return;
			}
	}
	
	public static void removePlus(Production p, G g) {
		ArrayList<Symbol> body = p.getBody();
		
		for (int i = 0; i < body.size(); i++)
			if (body.get(i).getValue().equals("+")) {
				body.remove(i);
				
				int posOP = i - 1;
				ArrayList<Symbol> expAux = new ArrayList<Symbol>();

				if (body.get(i-1).getValue().equals(")")) {
					posOP = getPositionOtherPar(i-1, body);
					
					for (int j = posOP; j < i ; j++)
						expAux.add(body.get(j));
				} else {
					expAux.add(body.get(i-1));					
				}
				
				body.addAll(expAux);
				body.add(new Symbol("*"));
									
				removePlus(p, g);
				return;
			}
	}
	
	public static int getPositionOtherPar(int posAc, ArrayList<Symbol> exp) {
		Symbol ac = exp.get(posAc);
		Stack<String> s = new Stack<String>();
		
		if (ac.getValue().equals("(")) {
			for (int i = posAc; i < exp.size(); i--) {
				if (exp.get(i).getValue().equals("("))
					s.add("(");
				else if (exp.get(i).getValue().equals(")"))
					s.pop();
				
				if (s.empty())
					return i;
			}
		} else {
			for (int i = posAc; i >= 0; i--) {
				if (exp.get(i).getValue().equals(")"))
					s.add(")");
				else if (exp.get(i).getValue().equals("("))
					s.pop();
				
				if (s.empty())
					return i;
			}
		}
		
		return -1;
	}
	
	public static void removeUnPar(Production p) {
		ArrayList<Symbol> body = p.getBody();
		
		if (body.get(0).getValue().equals("(")) {
			body.remove(0);
			body.remove(body.size());
		}
	}
	
	public static boolean isBalanced(ArrayList<Symbol> exp) {
		Stack<Character> stack = new Stack<Character>();
		
		for (Symbol sym : exp) {
			if (sym.getValue().equals("("))
				stack.push('(');
			
			if (sym.getValue().equals(")")) {
				if (stack.empty())
					return false;
				
				stack.pop();
			}
		}
		
		if (stack.empty())
			return true;
		
		return false;		
	}
}
