//CS304 (manually written) Issue link: https://github.com/json-path/JsonPath/issues/806
package com.jayway.jsonpath;


import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * test for issue_806
 */
 //NOPMD - suppressed CommentSize
// - TODO explain reason for suppression
@SuppressWarnings("checkstyle:RegexpSingleline")
public class Issue_806 { //NOPMD - suppressed AtLeastOneConstructor - TODO explain reason for suppression
    // NOPMD - suppressed ClassNamingConventions - TODO explain reason for suppression

    /**
     * CONF. configuration with FILTER_SLICE_AS_ARRAY
     */
    public static final Configuration CONF = Configuration.builder().options(Option.FILTER_SLICE_AS_ARRAY).build();

    /**
     * JSON. given json
     */
    public static final String JSON =
        "[\n"
        + "    [0, 1, 2], \n"
        + "    [3, 4, 5],\n"
        + "    [6, 7, 8],\n"
        + "    [9, 10, 11],\n"
        + "    [12, 13, 14]\n"
        + "]";


    @Test
    public void test1() { //NOPMD - suppressed JUnitTestsShouldIncludeAssert - TODO explain reason for suppression
        // find the first array that its first element is greater than 4
        Object resOri = JsonPath.parse(JSON).read("$[?(@[0] > 4)][0]"); //NOPMD - suppressed LocalVariableCouldBeFinal -
        // TODO explain reason for suppression
        Object resNew = JsonPath.using(CONF).parse(JSON).read("$[?(@[0] > 4)][0]"); //NOPMD - suppressed LocalVariableCouldBeFinal -
        // TODO explain reason for suppression
        assertEquals("wrong answer", "[6,9,12]", resOri.toString()); //NOPMD - suppressed AvoidDuplicateLiterals -
        // TODO explain reason for suppression //NOPMD - suppressed LawOfDemeter - TODO explain reason for suppression
        // origin mode will use INDEX_AT(0) operation on [6, 7, 8], [9, 10, 11], [12, 13, 14] respectively; //NOPMD - suppressed CommentSize -
        // TODO explain reason for suppression
        assertEquals("wrong answer", "[[6,7,8]]", resNew.toString()); //NOPMD - suppressed LawOfDemeter -
        // TODO explain reason for suppression
        // new mode use INDEX_AT(0) operation on ([6, 7, 8], [9, 10, 11], [12, 13, 14])
    }

    @Test
    public void test2() { //NOPMD - suppressed JUnitTestContainsTooManyAsserts - TODO explain reason for suppression
        // find the count of the elements whose first element is greater than 4
        Object resOri = JsonPath.parse(JSON).read("$[?(@[0] > 4)].length()"); //NOPMD - suppressed LocalVariableCouldBeFinal
        // - TODO explain reason for suppression
        Object resNew = JsonPath.using(CONF).parse(JSON).read("$[?(@[0] > 4)].length()"); //NOPMD - suppressed LocalVariableCouldBeFinal
        // - TODO explain reason for suppression
        assertEquals("wrong answer", "[3,3,3]", resOri.toString()); //NOPMD - suppressed LawOfDemeter
        // - TODO explain reason for suppression
        // origin mode will use length() function on [6, 7, 8], [9, 10, 11], [12, 13, 14] respectively; //NOPMD - suppressed CommentSize
        // - TODO explain reason for suppression
        assertEquals("wrong answer", "[3]", resNew.toString()); //NOPMD - suppressed LawOfDemeter
        // - TODO explain reason for suppression
        // new mode use length() function on ([6, 7, 8], [9, 10, 11], [12, 13, 14])
    }

    @Test
    public void test3() { //NOPMD - suppressed JUnitTestContainsTooManyAsserts
        // - TODO explain reason for suppression
        // slice operation
        Object resOri = JsonPath.parse(JSON).read("$[1:4][0]"); //NOPMD - suppressed LocalVariableCouldBeFinal
        // - TODO explain reason for suppression
        Object resNew = JsonPath.using(CONF).parse(JSON).read("$[1:4][0]"); //NOPMD - suppressed LocalVariableCouldBeFinal
        // - TODO explain reason for suppression
        assertEquals("wrong answer", "[3,6,9]", resOri.toString()); //NOPMD - suppressed LawOfDemeter
        // - TODO explain reason for suppression
        // origin mode will use INDEX_AT(0) operation on [3, 4, 5], [6, 7, 8], [9, 10, 11] respectively; //NOPMD - suppressed CommentSize
        // - TODO explain reason for suppression
        assertEquals("wrong answer", "[[3,4,5]]", resNew.toString()); //NOPMD - suppressed LawOfDemeter
        // - TODO explain reason for suppression
        // new mode use INDEX_AT(0) operation on ([3, 4, 5], [6, 7, 8], [9, 10, 11])
    }

    @Test
    public void test4() { //NOPMD - suppressed JUnitTestContainsTooManyAsserts - TODO explain reason for suppression
        // slice operation
        Object resOri = JsonPath.parse(JSON).read("$[1:4].length()"); //NOPMD - suppressed LocalVariableCouldBeFinal
        // - TODO explain reason for suppression
        Object resNew = JsonPath.using(CONF).parse(JSON).read("$[1:4].length()"); //NOPMD - suppressed LocalVariableCouldBeFinal
        // - TODO explain reason for suppression
        assertEquals("wrong answer", "[3,3,3]", resOri.toString()); //NOPMD - suppressed LawOfDemeter
        // - TODO explain reason for suppression
        // origin mode will use length() function on [3, 4, 5], [6, 7, 8], [9, 10, 11] respectively; //NOPMD - suppressed CommentSize
        // - TODO explain reason for suppression
        assertEquals("wrong answer", "[3]", resNew.toString()); //NOPMD - suppressed LawOfDemeter
        // - TODO explain reason for suppression
        // new mode use length() function on ([3, 4, 5], [6, 7, 8], [9, 10, 11])
    }

}
