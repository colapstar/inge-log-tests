package fr.isima.master1.genielog.service;

import static org.junit.jupiter.api.Assertions.*;

class MyParserTest extends RoleExpressionParserTest {

    @Override
    RoleExpressionParser getParser() {
        return new MyParser();
    }
}