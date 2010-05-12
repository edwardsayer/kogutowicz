/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.style.parser;

import net.anzix.kogutowicz.style.filter.Filter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import net.anzix.kogutowicz.style.*;

/**
 *
 * @author elek
 */
public class FilterParser {

    private List<Operator> operators = new ArrayList();

    private List<Function> functions = new ArrayList();

    public FilterParser() {
        operators.add(new EqualOperator());
        operators.add(new OrOperator());
        operators.add(new NotEqualOperator());
        operators.add(new AndOperator());
        functions.add(new NotFunction());
    }

    public Filter parse(String str) {
        str = str.replaceAll("=", " = ");
        str = str.replaceAll("<>", " <> ");
        str = str.replaceAll("\\(", " ( ");
        str = str.replaceAll("\\)", " ) ");
        str = str.replaceAll("\\s+", " ");
        List rpn = shuttingYard(str);
        Stack stack = new Stack();
        for (Object o : rpn) {
            if (o instanceof String) {
                stack.push(o);
            } else if (o instanceof Function) {
                Function op = (Function) o;
                Object[] parameters = new Object[1];
                parameters[0] = stack.pop();
                Filter f = op.createFilter(parameters);
                stack.push(f);
            } else {
                Operator op = (Operator) o;
                Object[] parameters = new Object[2];
                parameters[1] = stack.pop();
                parameters[0] = stack.pop();
                Filter f = op.createFilter(parameters);
                stack.push(f);
            }
        }
        Object o = stack.pop();
        if (!(o instanceof Filter)) {
            throw new IllegalArgumentException("Unknown operator : " + o);
        }
        return (Filter) o;
    }

    public List shuttingYard(String str) {
        String[] tokens = str.split(" ");
        List output = new ArrayList();
        Stack<Object> stack = new Stack();

        tok:
        for (String token : tokens) {
            if (token.equals(")")) {
                Object t;
                while (!(t = stack.pop()).equals("(") && !stack.empty()) {
                    if (!t.equals("(")) {
                        output.add(t);
                    }
                    if (stack.size() > 9 && stack.peek() instanceof Function) {
                        output.add(stack.pop());
                    }
                }
            } else if (token.equals("(")) {
                stack.push(token);
            } else {
                for (Function function : functions) {
                    if (function.is(token)) {
                        stack.push(function);
                        continue tok;
                    }

                }
                for (Operator op : operators) {
                    if (op.is(token)) {
                        if (stack.size() > 0 && stack.peek() instanceof Operator) {
                            Operator op2 = (Operator) stack.peek();
                            if (op2.getPrecendece() < op.getPrecendece()) {
                                output.add(stack.pop());
                            }
                        }
                        stack.push(op);
                        continue tok;
                    }

                }

                output.add(token);


            }
        }
        while (stack.size() > 0) {
            output.add(stack.pop());
        }
        return output;


    }
}
