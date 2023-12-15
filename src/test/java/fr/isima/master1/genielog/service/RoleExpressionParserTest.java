package fr.isima.master1.genielog.service;

import fr.isima.master1.genielog.domain.*;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

abstract class RoleExpressionParserTest {

    abstract RoleExpressionParser getParser();

    @Test
    void mustReturnNullForNullOrBlankString() {
        assertThat(getParser().parse(null)).isNull();
        assertThat(getParser().parse("")).isNull();
        assertThat(getParser().parse("    ")).isNull();
    }

    @Nested
    class TermTests {

        @Test
        public void mustParseSimpleTerm() {
            mustParse("dev", RoleTerm.class, "dev");
        }

        @Test
        public void mustParseSimpleTermIgnoringSpaces() {
            mustParse("   dev   ", RoleTerm.class, "dev");
        }

    }


    @Nested
    class OperatorTest {

        @Test
        public void mustNotParseWithMissingOperator() {
            mustNotParse("dev admin");
        }

        @Nested
        class AndTests {
            @Test
            public void mustParseSimpleAndWithoutSpaces() {
                mustParse("dev&admin",
                        AndRoleOperator.class, "(dev & admin)");
            }

            @Test
            public void mustParseSimpleAnd() {
                mustParse("dev & admin",
                        AndRoleOperator.class,
                        "(dev & admin)");
            }



            @Test
            public void mustParseSimpleAndWithExtraSpaces() {
                mustParse( "   dev    &   admin  ",
                        AndRoleOperator.class,
                        "(dev & admin)");
            }

            @Test
            public void mustNotParseAndWithMissingOperands() {
                mustNotParse("&");
                mustNotParse("dev &");
                mustNotParse("& admin");
            }

            @Test
            public void mustParseMultipleAnd() {
                mustParse("dev & admin & rh",
                        AndRoleOperator.class,
                        "(dev & (admin & rh))", "((dev & admin) & rh)");
            }

        }

        @Nested
        class OrTests {

            @Test
            public void mustParseSimpleOr() {
                mustParse("dev | admin", OrRoleOperator.class, "(dev | admin)");
            }

            @Test
            public void mustParseSimpleOrWithoutSpacesd() {
                mustParse("dev|admin", OrRoleOperator.class, "(dev | admin)");
            }

            @Test
            public void mustNotParseOrWithMissingOperands() {
                mustNotParse("|");
                mustNotParse("dev |");
                mustNotParse("| admin");
            }

            @Test
            public void mustParseMultipleOr() {
                mustParse("dev | admin | rh", OrRoleOperator.class, "(dev | (admin | rh))", "((dev | admin) | rh)");
            }
        }

        @Nested
        @Order(4)
        class NotTests {
            @Test
            public void mustParseSimpleNot() {

                mustParse("!dev", NotRoleOperator.class, "(!dev)");
            }

            @Test
            public void mustParseSimpleNotWithSpaces() {

                mustParse(" !  dev ", NotRoleOperator.class, "(!dev)");
            }

            @Test
            public void mustParseMultipleNot() {
                mustParse("!!dev", NotRoleOperator.class, "(!(!dev))");
                mustParse("!!!dev", NotRoleOperator.class, "(!(!(!dev)))");
            }

            @Test
            public void mustNotParseNotWithoutOperand() {
                mustNotParse("!");
            }

        }

        @Nested
        @Order(5)
        class PriorityTests {
            @Test
            public void mustPrioritizeAndOverOr() {
                mustParse("dev & admin | rh",
                        OrRoleOperator.class,
                        "((dev & admin) | rh)");
                mustParse("dev | admin & rh",
                        OrRoleOperator.class,
                        "(dev | (admin & rh))");
            }


            public void mustPrioritizeNotOverAnd() {
                mustParse("!dev & admin", AndRoleOperator.class, "((!dev) & admin)");
                mustParse("dev & !admin", AndRoleOperator.class, "(dev & (!admin))");
            }

            public void mustPrioritizeNotOverOr() {
                mustParse("!dev | admin", OrRoleOperator.class, "((!dev) | admin)");
                mustParse("dev | !admin", OrRoleOperator.class, "(dev | (!admin))");
            }

        }
    }

    @Nested
    @Order(4)
    class BracketsTests {
        @Test
        public void mustParseSimpleBrackets() {
            mustParse("(user)", RoleTerm.class,
                    "user");
        }

        @Test
        public void mustParseBracketsWithOperator() {
            mustParse("(user & dev)", AndRoleOperator.class, "(user & dev)");
            mustParse("(user | dev)", OrRoleOperator.class, "(user | dev)");
        }

        @Test
        public void mustMultipleParseBracketsWithOperator() {
            mustParse("((user & dev))", "(user & dev)");
            mustParse("((user) & (dev))", "(user & dev)");
            mustParse("((user) | (dev))", "(user | dev)");
        }

        @Test
        public void mustPrioritizeBracketsOverNaturalOrder() {
            mustParse("(user | dev) & admin", "((user | dev) & admin)");
        }

        @Test
        public void mustPrioritizeBracketsAfterNot() {
            mustParse("!(user | dev) & admin", "((!(user | dev)) & admin)");
        }


        @Test
        public void mustParseMultipleBrackets() {
            mustParse("((user | dev) & admin)", "((user | dev) & admin)");
        }

        @Test
        public void mustNotParseWithoutClosingBracket() {
            mustNotParse("(admin | dev");
        }

        @Test
        public void mustNotParseWithoutOpeningBracket() {
            mustNotParse("admin | dev)");
        }

        @Test
        public void mustNotParseWithEmptyBrackets() {
            mustNotParse("()");
            mustNotParse("( )");
        }

    }

    private void mustParse(String string,
                           String... expectedStringifications) {
        mustParse(string, RoleExpression.class, expectedStringifications);
    }

    private void mustParse(String string,
                           Class<? extends RoleExpression> expectedClass,
                           String... expectedStringifications) {
        var expression = getParser().parse(string);
        assertThat(expression).isNotNull();
        //assertThat(expression).isInstanceOf(expectedClass);
        assertThat(expression.stringify()).isIn(expectedStringifications);
    }

    private void mustNotParse(String string) {
        assertThrows(RoleExpressionParseException.class, () -> getParser().parse(string));
    }
}