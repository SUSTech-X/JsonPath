package com.jayway.jsonpath;


import org.junit.Test;

/**
 * test for issue_806
 */
//CS304 (manually written) Issue link: https://github.com/json-path/JsonPath/issues/806
public class Issue_806 {

    public static final Configuration conf = Configuration.builder().options(Option.FILTER_SLICE_AS_ARRAY).build();

    public static final String json = "[\n" +
            "    [0, 1, 2], \n" +
            "    [3, 4, 5],\n" +
            "    [6, 7, 8],\n" +
            "    [9, 10, 11],\n" +
            "    [12, 13, 14]\n" +
            "]";


    @Test
    public void test_1(){
        // find the first array that its first element is greater than 4
        Object res_ori = JsonPath.parse(json).read("$[?(@[0] > 4)][0]");
        Object res_new = JsonPath.using(conf).parse(json).read("$[?(@[0] > 4)][0]");
        assert (res_ori.toString().equals("[6,9,12]")); // origin mode will use INDEX_AT(0) operation on [6, 7, 8], [9, 10, 11], [12, 13, 14] respectively;
        assert (res_new.toString().equals("[[6,7,8]]")); // new mode use INDEX_AT(0) operation on ([6, 7, 8], [9, 10, 11], [12, 13, 14])
    }

    @Test
    public void test_2(){
        // find the count of the elements whose first element is greater than 4
        Object res_ori = JsonPath.parse(json).read("$[?(@[0] > 4)].length()");
        Object res_new = JsonPath.using(conf).parse(json).read("$[?(@[0] > 4)].length()");
        assert (res_ori.toString().equals("[3,3,3]")); // origin mode will use length() function on [6, 7, 8], [9, 10, 11], [12, 13, 14] respectively;
        assert (res_new.toString().equals("[3]")); // new mode use length() function on ([6, 7, 8], [9, 10, 11], [12, 13, 14])
    }

    @Test
    public void test_3(){
        // slice operation
        Object res_ori = JsonPath.parse(json).read("$[1:4][0]");
        Object res_new = JsonPath.using(conf).parse(json).read("$[1:4][0]");
        assert (res_ori.toString().equals("[3,6,9]")); // origin mode will use INDEX_AT(0) operation on [3, 4, 5], [6, 7, 8], [9, 10, 11] respectively;
        assert (res_new.toString().equals("[[3,4,5]]")); // new mode use INDEX_AT(0) operation on ([3, 4, 5], [6, 7, 8], [9, 10, 11])
    }

    @Test
    public void test_4(){
        // slice operation
        Object res_ori = JsonPath.parse(json).read("$[1:4].length()");
        Object res_new = JsonPath.using(conf).parse(json).read("$[1:4].length()");
        assert (res_ori.toString().equals("[3,3,3]")); // origin mode will use length() function on [3, 4, 5], [6, 7, 8], [9, 10, 11] respectively;
        assert (res_new.toString().equals("[3]")); // new mode use length() function on ([3, 4, 5], [6, 7, 8], [9, 10, 11])
    }

}
