package com.jayway.jsonpath;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *  Test for Issue 777
 */
// CS304 (manually written) Issue link: https://github.com/json-path/JsonPath/issues/777 //NOPMD - suppressed CommentSize
public class Issue_777 { //NOPMD - suppressed AtLeastOneConstructor //NOPMD - suppressed ClassNamingConventions
    /**
     *  The default configuration for testing
     */
    public static final Configuration CONFIGURATION = Configuration.defaultConfiguration();

    @Test
    public void test01NestedPathInFilterValue() { //NOPMD - suppressed MethodNamingConventions
        // prepare the json to test
        final String json = "{"
                + "    \"store\": {"
                + "        \"book\": ["
                + "            {" //NOPMD - suppressed AvoidDuplicateLiterals
                + "                \"category\": \"reference\","
                + "                \"author\": \"Nigel Rees\","
                + "                \"title\": \"Sayings of the Century\","
                + "                \"price\": 8.95"
                + "            },"
                + "            {"
                + "                \"category\": \"fiction\","
                + "                \"author\": \"Evelyn Waugh\","
                + "                \"title\": \"Sword of Honour\","
                + "                \"price\": 12.99"
                + "            },"
                + "            {"
                + "                \"category\": \"fiction\","
                + "                \"author\": \"Herman Melville\","
                + "                \"title\": \"Moby Dick\","
                + "                \"isbn\": \"0-553-21311-3\","
                + "                \"price\": 8.99"
                + "            },"
                + "            {"
                + "                \"category\": \"fiction\","
                + "                \"author\": \"J. R. R. Tolkien\","
                + "                \"title\": \"The Lord of the Rings\","
                + "                \"isbn\": \"0-395-19395-8\","
                + "                \"price\": 22.99"
                + "            }"
                + "        ],"
                + "        \"bicycle\": {"
                + "            \"color\": \"red\","
                + "            \"price\": 19.95"
                + "        }"
                + "    },"
                + "    \"expensive\": 10"
                + "}";
        // parse the json string with the default Configuration
        final ParseContext parseContext = JsonPath.using(CONFIGURATION);
        final DocumentContext documentContext = parseContext.parse(json); //NOPMD - suppressed LawOfDemeter
        // try to read the jsonpath and get the result
        final String result = documentContext.read("$.store.book[" //NOPMD - suppressed LawOfDemeter
                + "?(@.price == $.max($.store.book[*].price))].author").toString();

        assertEquals("test_01 successful", "[\"J. R. R. Tolkien\"]", result);
    }

    @Test
    public void test02NestedPathInFilterValue() { //NOPMD - suppressed MethodNamingConventions
        // prepare the json to test
        final String json = "{\"list\": "
                + "[{\"val\": 1, \"name\": \"val=1\"},"
                + " {\"val\": 2, \"name\": \"val=2\"}, "
                + "{\"val\": 3,\"name\": \"val=3\"}]"
                + "}"; //NOPMD - suppressed AvoidFinalLocalVariable
        // parse the json string with the default Configuration
        final ParseContext parseContext = JsonPath.using(CONFIGURATION);
        final DocumentContext documentContext = parseContext.parse(json); //NOPMD - suppressed LawOfDemeter
        // try to read the jsonpath and get the result
        final String result = documentContext.read("$.list[" //NOPMD - suppressed LawOfDemeter
                + "?(@.val == $.max($.list[*].val))]"
                + ".name").toString();
        assertEquals("test_02 successful", "[\"val=3\"]", result);
    }
}
