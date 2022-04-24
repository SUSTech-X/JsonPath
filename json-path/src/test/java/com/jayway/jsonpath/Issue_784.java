package com.jayway.jsonpath;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * test for issue 784
 */
//CS304 (manually written) Issue link: https://github.com/json-path/JsonPath/issues/784 //NOPMD - suppressed CommentSize
public class Issue_784 { //NOPMD - suppressed AtLeastOneConstructor
    @Test
    public void testSetEmpty() { //NOPMD - suppressed CommentRequired
        final String inputJson1 = "{}"; //NOPMD - suppressed AvoidFinalLocalVariable
        final String inputJson2 = "{\"root\":{}}"; //NOPMD - suppressed AvoidFinalLocalVariable
        final DocumentContext context1 = JsonPath.parse(inputJson1);
        final DocumentContext context2 = JsonPath.parse(inputJson2);


        //Try to put an empty key
        try {
            context1.put("$", "", "test1");
            context2.put("$", "", "test2");
            assertEquals("context1 equals {=test1}", context1.json().toString(), "{=test1}"); //NOPMD - suppressed LawOfDemeter
            assertEquals("context2 equals {root={}, =test2}", context2.json().toString(), "{root={}, =test2}"); //NOPMD - suppressed LawOfDemeter
        } catch (Exception e) { //NOPMD - suppressed AvoidCatchingGenericException
            fail("should be no exception");
        }
    }

    @Test
    public void testRenameKey() { //NOPMD - suppressed CommentRequired
        final String input = "{\"name\":1}"; //NOPMD - suppressed AvoidFinalLocalVariable
        final DocumentContext context = JsonPath.parse(input);
        //Try to rename a key
        try {
            context.renameKey("$", "name", "new");
            assertEquals("context.json().toString() equals {new=1}", context.json().toString(), "{new=1}"); //NOPMD - suppressed LawOfDemeter
        } catch (Exception e) { //NOPMD - suppressed AvoidCatchingGenericException
            fail("should be no exception");
        }

    }
}
