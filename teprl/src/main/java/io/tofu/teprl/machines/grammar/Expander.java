package io.tofu.teprl.machines.grammar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

public class Expander {
	public static void split(Production p, Grammar g) {
		removeUnPar(p);
		ArrayList<Symbol> body = p.getBody();
		
		int lastIndInit = 0;
		
		for (int i = 0; i < body.size(); i++) {
			if (body.get(i).getValue().equals("|") && body.get(i) instanceof AuxSym) {
				ArrayList<Symbol> exp = new ArrayList<Symbol>();
				
				for (int j = lastIndInit; j < i; j++)
					exp.add(body.get(j));
				
				if (isBalanced(exp)) {
					NonTerminal newNonTerminal = new NonTerminal(p.getHead().getValue() + "'");
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
	
	public static void removeStar(Production p, Grammar g) {
		ArrayList<Symbol> body = p.getBody();
		
		for (int i = 0; i < body.size(); i++)
			if (body.get(i).getValue().equals("*") && body.get(i) instanceof AuxSym) {
				body.remove(i);
				
				ArrayList<Symbol> exp = new ArrayList<Symbol>();
				NonTerminal newNonTerminal = new NonTerminal(p.getHead().getValue() + "'");
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
	
	public static void removeInterr(Production p, Grammar g) {
		ArrayList<Symbol> body = p.getBody();
		
		for (int i = 0; i < body.size(); i++)
			if (body.get(i).getValue().equals("?") && body.get(i) instanceof AuxSym) {
				body.remove(i);
				
				ArrayList<Symbol> exp = new ArrayList<Symbol>();
				NonTerminal newNonTerminal = new NonTerminal(p.getHead().getValue() + "'");
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
	
	public static void removePlus(Production p, Grammar g) {
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
	
	public static void toFactor(Grammar g) {
		ArrayList<NonTerminal> nts = g.getNonTerminals();
		ArrayList<ArrayList<Production>> listsSameSym = new ArrayList<ArrayList<Production>>();
			
		for (Symbol nt : nts) {
			ArrayList<Production> prods = g.getProductions(nt);
			
			for (int i = 0; i < prods.size(); i++)
				for (int j = i + 1; j < prods.size(); j++) {
					if (!sameFirstSym(prods.get(i), prods.get(j)))
						continue;
					
					int k;
					for (k = 0; k < listsSameSym.size(); k++)
						if (sameFirstSym(listsSameSym.get(k).get(0), prods.get(i))) {
							listsSameSym.get(k).add(prods.get(j));
							break;
						}
					
					if (k < listsSameSym.size())
						continue;
					
					ArrayList<Production> l = new ArrayList<Production>();
					listsSameSym.add(l);
					l.add(prods.get(i));
					l.add(prods.get(j));
				}
		}
	}
	
	private static void toFactor(Grammar g, ArrayList<ArrayList<Production>> listsSameSym) {
		for (ArrayList<Production> l : listsSameSym) {
			int i;
			for (i = 0; sameNSymm(l, i+1); i++);
			
			ArrayList<Symbol> prev = subword(l.get(0).getBody(), 0, i);
			NonTerminal head = l.get(0).getHead();
			NonTerminal headn = new NonTerminal(head.getValue() + "\'");
			
			for (Production p : l) {
				ArrayList<Symbol> suc = subword(p.getBody(), i);
				
				g.addProduction(new Production(headn, suc));
				g.removeProduction(p);
			}
			
			prev.add(headn);
			g.addProduction(new Production(head, prev));
		}
	}
	
	public static ArrayList<Symbol> subword(ArrayList<Symbol> word, int beginIndex) {
		return subword(word, beginIndex, word.size() - 1);
	}
	
	public static ArrayList<Symbol> subword(ArrayList<Symbol> word, int beginIndex, int endIndex) {
		ArrayList<Symbol> subword = new ArrayList<Symbol>();
		
		int i;
		for (i = 0; i < beginIndex; i++);
		for (; i <= endIndex; i++)
			subword.add(word.get(i));
		
		return subword;
	}
	
	
	private static boolean sameFirstSym(Production p, Production q) {
		return sameFirstSym(p.getBody(), q.getBody());
	}
	
	private static boolean sameNSymm(ArrayList<Production> l, int n) {
		ArrayList<ArrayList<Symbol>> l1 = new ArrayList<ArrayList<Symbol>>();
		
		for (Production p : l)
			l1.add(p.getBody());
		
		return sameNSym(l1, n);
	}

	public static boolean sameNSym(ArrayList<Symbol> p, ArrayList<Symbol> q, int n) {
		return p.get(n).equals(q.get(n));
	}
	
	public static boolean sameFirstSym(ArrayList<Symbol> p, ArrayList<Symbol> q) {
		return sameNSym(p, q, 0);
	}
	
	public static boolean sameNSym(ArrayList<ArrayList<Symbol>> lists, int n) {
		ArrayList<Symbol> l1 = lists.get(0);
		
		for (ArrayList<Symbol> l : lists)
			if (!sameNSym(l1, l, n))
				return false;
		
		return true;
	}
}
