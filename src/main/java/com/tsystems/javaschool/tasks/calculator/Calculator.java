package com.tsystems.javaschool.tasks.calculator;

import java.text.DecimalFormat;
import java.util.Stack;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        if (statement == null || statement.equals("")) {
            return null;
        }
        if (statement.contains(",") || statement.contains("**") || statement.contains("++") || statement.contains("--") || statement.contains("//")
                || !Character.isDigit(statement.charAt(0)) || statement.contains("..")) return null;
        expressionToRPN(statement);
        return rpnToAnswer(expressionToRPN(statement));
    }

    public static String expressionToRPN(String str) {
        if (str == null) {
            return null;
        }
        String current = "";
        Stack<Character> stack = new Stack<>();
        int priority;
        for (int i = 0; i < str.length(); i++) {
            priority = getPriority(str.charAt(i));
            if (priority == 0) {
                current += str.charAt(i);
            }
            if (priority == 1) {
                stack.push(str.charAt(i));
            }
            if (priority > 1) {
                current += " ";
                while (!stack.empty()) {
                    if (getPriority(stack.peek()) >= priority) {
                        current += stack.pop();
                    } else break;
                }
                stack.push(str.charAt(i));
            }
            if (priority == -1) {
                current += " ";
                while (getPriority(stack.peek()) != 1) {
                    current += stack.pop();
                }
                stack.pop();
            }
        }
        while (!stack.empty()) {
            current += stack.pop();
        }
        return current;
    }

    public static String rpnToAnswer(String rpn) {
        if (rpn == null) {
            return null;
        }
        String operand = "";
        Stack<Double> stack = new Stack<>();
        for (int i = 0; i < rpn.length(); i++) {
            if (rpn.charAt(i) == ' ') {
                continue;
            }
            if (getPriority(rpn.charAt(i)) == 0) {
                while (rpn.charAt(i) != ' ' && getPriority(rpn.charAt(i)) == 0) {
                    operand += rpn.charAt(i++);
                    if (i == rpn.length()) {
                        break;
                    }
                }
                stack.push(Double.parseDouble(operand));
                operand = "";

            }
            if (getPriority(rpn.charAt(i)) > 1) {
                double a = stack.pop();
                double b = stack.pop();
                if (rpn.charAt(i) == '+') {
                    stack.push(b + a);
                }
                if (rpn.charAt(i) == '-') {
                    stack.push(b - a);
                }
                if (rpn.charAt(i) == '*') {
                    stack.push(b * a);
                }
                if (rpn.charAt(i) == '/') {
                    if (a == 0) {
                        return null;
                    }
                    stack.push(b / a);
                }
            }
        }
        DecimalFormat df = new DecimalFormat("#.####");
        double d = stack.pop();
        String total = df.format(d);
        total = total.replace(",", ".");
        // return Double.toString(stack.pop());
        return total;
    }

    public static int getPriority(char ch) {
        if (ch == '*' || ch == '/') {
            return 3;
        } else if (ch == '+' || ch == '-') {
            return 2;
        } else if (ch == '(') {
            return 1;
        } else if (ch == ')') {
            return -1;
        } else return 0;
    }

    }


