package com.jayway.jsonpath;
import org.junit.Assert;
import org.junit.Test;

/**
 * test for issue 784
 */
public class Issue_784 {
    @Test
    public void test_set_empty(){
        String inputJson1="{}",inputJson2="{\"root\":{}}";
        DocumentContext context1=JsonPath.parse(inputJson1),context2=JsonPath.parse(inputJson2);
        boolean hasBug=false;
        //Try to put an empty key
        try{
            context1.put("$","","test1");
            context2.put("$","","test2");
        }catch (Exception e){
            hasBug=true;
        }
        //If there are no exception, it's right
        Assert.assertFalse(hasBug);
    }
    @Test
    public void test_renameKey(){
        String input="{\"name\":1}";
        boolean hasBug=false;
        DocumentContext context=JsonPath.parse(input);
        //Try to rename a key
        try{
            context.renameKey("$","name","new");
        }catch (Exception e){
            hasBug=true;
        }
        //If there are no exception, it's right
        Assert.assertFalse(hasBug);
    }
}
