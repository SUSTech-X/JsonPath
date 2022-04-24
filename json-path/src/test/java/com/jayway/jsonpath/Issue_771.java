package com.jayway.jsonpath;

import com.jayway.jsonpath.internal.JsonContext;
import org.junit.Assert;
import org.junit.Test;
/**
 * test for issue 771
 */
//CS304 (manually written) Issue link: https://github.com/json-path/JsonPath/issues/771 //NOPMD - suppressed CommentSize
public class Issue_771 { //NOPMD - suppressed AtLeastOneConstructor //NOPMD - suppressed ClassNamingConventions
    /**
     * Test a short equation swap left and right
     */
    @Test
    public void testReverseExpression1() {
        //Put the @ at the equation's right
        String inJson = "{\"authors\":[{\"first\":[1,2],\"last\":2}]}"; //NOPMD - suppressed LocalVariableCouldBeFinal
        final DocumentContext documentContext = JsonPath.parse(inJson);
        final JsonContext context = documentContext.read("$.authors[?([1,2]==@.first)]"); //NOPMD - suppressed LawOfDemeter
        final String outJson = context.toString(); //NOPMD - suppressed LawOfDemeter
        //Judge whether the result is right
        Assert.assertEquals(outJson, "[{\"first\":[1,2],\"last\":2}]", "The answer is wrong");
    }
    /**
     * Test a long equation swap left and right
     */
    @Test
    public void testReverseExpression2() {
        //Using a bigger filter and put the @ at the equation's right
        final String inJson = "{\n" + "\"store\": {\n" + "\"book\": [\n" //NOPMD - suppressed AvoidDuplicateLiterals
                + "{\n" + "\"category\": \"reference\",\n" //NOPMD - suppressed AvoidDuplicateLiterals
                + "\"authors\": [\n"
                + "{\n" + "\"firstName\": \"Nigel\",\n" + "\"lastName\": \"Rees\"\n" + "},\n" + "{\n" //NOPMD - suppressed AvoidDuplicateLiterals
                + "\"firstName\": \"Evelyn\",\n" + "\"lastName\": \"Waugh\"\n" + "}\n" + "],\n"
                + "\"title\": \"Sayings of the Century\",\n" + "\"price\": 8.95\n" + "},\n"
                + "{\n" + "\"category\": \"fiction\",\n" + "\"authors\": [\n" + "{\n" + "\"firstName\": \"A\",\n"
                + "\"lastName\": \"B\"\n" + "},\n" + "{\n" + "\"firstName\": \"C\",\n" + "\"lastName\": \"D\"\n"
                + "}\n" + "],\n" + "\"title\": \"Sword of Honour\",\n" + "\"price\": 12.99\n" + "}\n" + "]\n" + "},\n"
                + "\"expensive\": 10\n" + "}";
        final DocumentContext documentContext = JsonPath.parse(inJson);
        final JsonContext context = documentContext.//NOPMD - suppressed LawOfDemeter
                read("$.store.book[?(['Rees','Waugh'] == @.authors[*].lastName)]"); //NOPMD - suppressed LawOfDemeter
        final String outJson = context.toString(); //NOPMD - suppressed LawOfDemeter
        //Judge whether the result is right
        Assert.assertEquals(outJson, "[{\"category\":\"reference\",\"authors\":"
                + "[{\"firstName\":\"Nigel\",\"lastName\":\"Rees\"},{\"firstName\":"
                + "\"Evelyn\",\"lastName\":\"Waugh\"}],\"title\":\"Sayings of the Century\",\"price\":8.95}]\n",
                "The answer is wrong");
    }
}
