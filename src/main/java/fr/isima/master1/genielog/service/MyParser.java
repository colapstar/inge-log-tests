package fr.isima.master1.genielog.service;

import fr.isima.master1.genielog.domain.*;

public class MyParser implements RoleExpressionParser {

    @Override
    public RoleExpression parse(String string) {
        if ( string == null || string.isBlank() )
            return null;
        string = string.trim();
        if ( string.contains("|") ) {
            var operands = string.split("\\|", 2);
            if (operands.length != 2 || operands[0].isBlank() || operands[1].isBlank() )
                throw new RoleExpressionParseException("missing operand for AND");
            return new OrRoleOperator(parse(operands[0]), parse(operands[1]));
        }
        if ( string.contains("&") ) {
            var operands = string.split("&", 2);
            if (operands.length != 2 || operands[0].isBlank() || operands[1].isBlank() )
                throw new RoleExpressionParseException("missing operand for AND");
            return new AndRoleOperator(parse(operands[0]), parse(operands[1]));
        }
        if ( string.startsWith("!") ) {
            string = string.substring(1);
            if ( string.isBlank() )
                throw new RoleExpressionParseException("missing operand for NOT");
            return new NotRoleOperator(parse(string));
        }
        if ( string.contains(" ") )
            throw new RoleExpressionParseException("missing operator");

        return new RoleTerm(new Role(string));
    }
}
