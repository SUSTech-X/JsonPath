package com.jayway.jsonassert;

import org.junit.Test;

import static com.jayway.jsonassert.JsonAssert.with;

/**
 * Test for issue 744
 */
// CS304 (manually written)
// Issue link: https://github.com/json-path/JsonPath/issues/744
public class Issue_744 { //NOPMD - suppressed AtLeastOneConstructor
    @Test
    /**
     * test not defined path, it will not throw AssertionError
     */
    public void testAssertNotDefined() { //NOPMD - suppressed JUnitTestsShouldIncludeAssert
        final String json = "{\n"
                + "    \"array\": [\n"
                + "        { \"name\": \"object1\" },\n"
                + "        { \"name\": \"object2\" }\n"
                + "    ]\n"
                + "}";
        with(json).assertNotDefined("array.*.fake"); //NOPMD - suppressed LawOfDemeter
    }

    @Test(expected = AssertionError.class)
    /**
     * test defined path, it will throw AssertionError
     */
    public void testAssertDefinedThrowException() {
        final String json = "{\n"
                + "    \"array\": [\n"
                + "        { \"name\": \"object1\" },\n"
                + "        { \"name\": \"object2\" }\n"
                + "    ]\n"
                + "}";
        with(json).assertNotDefined("array.*.name"); //NOPMD - suppressed LawOfDemeter
    }
}
