package fr.isima.master1.genielog.service;

import fr.isima.master1.genielog.domain.*;

import java.util.function.Predicate;

public class InfixParser implements RoleExpressionParser {

    private String expression;

    private static final String SPECIAL_CHARS = "!&|()";

    public InfixParser() {
        this(null);

    }

    private InfixParser(String expression) {
        this.expression = expression;
    }

    @Override
    public RoleExpression parse(String string) {
        if (string == null || string.isBlank() ) return null;

        return new InfixParser(string).parse();
    }

    private RoleExpression parse() {
        RoleExpression result = parseOr();
        if (!expression.isBlank())
            throw new RoleExpressionParseException("Expected end of expression");
        return result;
    }

    private RoleExpression parseOr() {
        RoleExpression result = parseAnd();

        while (isNext("|")) {
            if (!hasNext()) {
                throw new RoleExpressionParseException("Expected right operand for OR");
            }
            result = new OrRoleOperator(result, parseOr());
        }
        return result;

    }

    private RoleExpression parseAnd() {
        RoleExpression result = parseTerm();

        while (isNext("&")) {
            if (!hasNext()) {
                throw new RoleExpressionParseException("Expected right operand for AND");
            }
            result = new AndRoleOperator(result, parseAnd());
        }
        return result;

    }

    private RoleExpression parseTerm() {
        expression = expression.trim();

        if (isNext("!")) {
            return new NotRoleOperator(parseTerm());
        }


        if (isNext("(")) {
            RoleExpression result = parseOr();
            if (!isNext(")"))
                throw new RoleExpressionParseException("expected closing bracket");
            return result;
        }
        if (isNext(")")) {
            throw new RoleExpressionParseException("unexpected closing bracket");
        }

        int i = 0;
        while (i < expression.length()) {
            char c = expression.charAt(i);
            if (Character.isWhitespace(c) || SPECIAL_CHARS.indexOf(c) >= 0 ) {
                break;
            }
            i++;
        }
        String roleName = expression.substring(0, i).trim();
        expression = expression.substring(i).trim();

        try {
            return new RoleTerm(new Role(roleName));
        } catch (IllegalArgumentException e) {
            throw new RoleExpressionParseException("Invalid role term : \"" + roleName + "\"", e);
        }

    }

    private boolean isNext(String string) {
        if (expression.startsWith(string)) {
            expression = expression.substring(string.length()).trim();
            return true;
        }
        return false;
    }

    private boolean hasNext() {
        return expression != null && !expression.isBlank();
    }
}
