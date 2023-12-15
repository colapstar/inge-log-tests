package fr.isima.master1.genielog.service;

class InfixParserTest extends RoleExpressionParserTest {
    @Override
    RoleExpressionParser getParser() {
        return new InfixParser();
    }
}