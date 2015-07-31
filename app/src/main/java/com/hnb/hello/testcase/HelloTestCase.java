package com.hnb.hello.testcase;

import android.test.InstrumentationTestCase;

import com.hnb.hello.utils.Detector;

import java.util.List;

/**
 * Created by USER on 7/30/2015.
 */

// test cases for detect functions
public class HelloTestCase extends InstrumentationTestCase
{

    public void test_mention_sie_1() throws Exception
    {
        String input = "@Chris how are you?";
        List<String> result = Detector.detectMentions(input);
        int resultSize = 1;

        assertEquals(result.size(), resultSize);
    }

    public void test_mention_sie_2() throws Exception
    {
        String input = "hey @Chris, @Ben how are you?";
        List<String> result = Detector.detectMentions(input);
        int resultSize = 2;

        assertEquals(result.size(), resultSize);
    }

    public void test_mention_sie_3() throws Exception
    {
        String input = "@Chris @Ben, @Cassey how are you?";
        List<String> result = Detector.detectMentions(input);
        int resultSize = 3;

        assertEquals(result.size(), resultSize);
    }

    public void test_mention_sie_4() throws Exception
    {
        String input = "@@Chris how are you?";
        List<String> result = Detector.detectMentions(input);
        int resultSize = 1;

        assertEquals(result.size(), resultSize);
    }

    public void test_mention_sie_5() throws Exception
    {
        String input = "@@@@@Chris how are you?";
        List<String> result = Detector.detectMentions(input);
        int resultSize = 1;

        assertEquals(result.size(), resultSize);
    }

    public void test_mention_sie_6() throws Exception
    {
        String input = "@Chris@Ben how are you?";
        List<String> result = Detector.detectMentions(input);
        int resultSize = 1;

        assertEquals(result.size(), resultSize);
    }


    public void test_mention_value_1() throws Exception
    {
        String input = "@Chris how are you?";
        List<String> result = Detector.detectMentions(input);

        String testValue1 = "Chris";

        String value1 = result.get(0);
        assertEquals(value1, testValue1);

    }


    public void test_mention_value_2() throws Exception
    {
        String input = "hey @Chris, @Ben how are you?";
        List<String> result = Detector.detectMentions(input);

        String testValue1 = "Chris";
        String testValue2 = "Ben";

        String value1 = result.get(0);
        String value2 = result.get(1);
        assertEquals(value1, testValue1);
        assertEquals(value2, testValue2);

    }

    public void test_mention_value_3() throws Exception
    {
        String input = "@Chris @Ben, @Cassey how are you?";
        List<String> result = Detector.detectMentions(input);

        String testValue1 = "Chris";
        String testValue2 = "Ben";
        String testValue3 = "Cassey";

        String value1 = result.get(0);
        String value2 = result.get(1);
        String value3 = result.get(2);
        assertEquals(value1, testValue1);
        assertEquals(value2, testValue2);
        assertEquals(value3, testValue3);

    }


    public void test_mention_value_4() throws Exception
    {
        String input = "@@Chris how are you?";
        List<String> result = Detector.detectMentions(input);
        String testValue1 = "Chris";

        String value1 = result.get(0);
        assertEquals(value1, testValue1);


    }

    public void test_mention_value_5() throws Exception
    {
        String input = "@@@@@Chris how are you?";
        List<String> result = Detector.detectMentions(input);
        String testValue1 = "Chris";

        String value1 = result.get(0);
        assertEquals(value1, testValue1);

    }

    public void test_mention_value_6() throws Exception
    {
        String input = "@Chris@Ben how are you?";
        List<String> result = Detector.detectMentions(input);
        String testValue1 = "Chris";

        String value1 = result.get(0);
        assertEquals(value1, testValue1);

    }


    public void test_emoticon_size_1() throws Exception
    {
        String input = "(megusta)";
        List<String> result = Detector.detectEmoticons(input);
        int resultSize = 1;

        assertEquals(result.size(), resultSize);
    }


    public void test_emoticon_size_2() throws Exception
    {
        String input = "(megusta)(happy)";
        List<String> result = Detector.detectEmoticons(input);
        int resultSize = 2;

        assertEquals(result.size(), resultSize);
    }

    public void test_emoticon_size_3() throws Exception
    {
        String input = "(megusta)(happy) (dance)";
        List<String> result = Detector.detectEmoticons(input);
        int resultSize = 3;

        assertEquals(result.size(), resultSize);
    }

    public void test_emoticon_size_4() throws Exception
    {
        String input = "hello (megusta) (happy) how are you! ";
        List<String> result = Detector.detectEmoticons(input);
        int resultSize = 2;

        assertEquals(result.size(), resultSize);
    }

    public void test_emoticon_size_5() throws Exception
    {
        String input = "hello(megusta)(happy)how are you! ";
        List<String> result = Detector.detectEmoticons(input);
        int resultSize = 2;

        assertEquals(result.size(), resultSize);
    }

    public void test_emoticon_size_6() throws Exception
    {
        String input = "hello (lonely girl over 15) (happy) how are you! ";
        List<String> result = Detector.detectEmoticons(input);
        int resultSize = 1;

        assertEquals(result.size(), resultSize);
    }


    public void test_emoticon_size_7() throws Exception
    {
        String input = "hello () (happy) how are you! ";
        List<String> result = Detector.detectEmoticons(input);
        int resultSize = 1;

        assertEquals(result.size(), resultSize);
    }

    public void test_emoticon_size_8() throws Exception
    {
        String input = "hello () (happy) how are you! ";
        List<String> result = Detector.detectEmoticons(input);
        int resultSize = 1;

        assertEquals(result.size(), resultSize);
    }


    public void test_emoticon_size_9() throws Exception
    {
        String input = "hello (((megusta))) (happy) how are you! ";
        List<String> result = Detector.detectEmoticons(input);
        int resultSize = 2;

        assertEquals(result.size(), resultSize);
    }


    public void test_emoticon_size_10() throws Exception
    {
        String input = "hello (((megu*sta))) (happy) how are you! ";
        List<String> result = Detector.detectEmoticons(input);
        int resultSize = 1;

        assertEquals(result.size(), resultSize);
    }


    public void test_emoticon_value_1() throws Exception
    {
        String input = "(megusta)";
        List<String> result = Detector.detectEmoticons(input);

        String value1 = result.get(0);

        String valueResult1 = "megusta";

        assertEquals(value1, valueResult1);
    }


    public void test_emoticon_value_2() throws Exception
    {
        String input = "(megusta)(happy)";
        List<String> result = Detector.detectEmoticons(input);

        String value1 = result.get(0);
        String value2 = result.get(1);

        String valueResult1 = "megusta";
        String valueResult2 = "happy";

        assertEquals(value1, valueResult1);
        assertEquals(value2, valueResult2);
    }

    public void test_emoticon_value_3() throws Exception
    {
        String input = "hello (megusta) (happy) how are you! ";
        List<String> result = Detector.detectEmoticons(input);

        String value1 = result.get(0);
        String value2 = result.get(1);

        String valueResult1 = "megusta";
        String valueResult2 = "happy";

        assertEquals(value1, valueResult1);
        assertEquals(value2, valueResult2);
    }


    public void test_emoticon_value_4() throws Exception
    {
        String input = "hello(megusta) (happy)how are you! ";
        List<String> result = Detector.detectEmoticons(input);

        String value1 = result.get(0);
        String value2 = result.get(1);

        String valueResult1 = "megusta";
        String valueResult2 = "happy";

        assertEquals(value1, valueResult1);
        assertEquals(value2, valueResult2);
    }

    public void test_emoticon_value_5() throws Exception
    {
        String input = "hello(megusta) (happy)how are you! ";
        List<String> result = Detector.detectEmoticons(input);

        String value1 = result.get(0);
        String value2 = result.get(1);

        String valueResult1 = "megusta";
        String valueResult2 = "happy";

        assertEquals(value1, valueResult1);
        assertEquals(value2, valueResult2);
    }

    public void test_emoticon_value_6() throws Exception
    {
        String input = "hello (lonely girl over 15) (happy) how are you!";
        List<String> result = Detector.detectEmoticons(input);

        String value1 = result.get(0);

        String valueResult1 = "happy";
        assertEquals(value1, valueResult1);
    }

    public void test_emoticon_value_7() throws Exception
    {
        String input = "hello () (happy) how are you!";
        List<String> result = Detector.detectEmoticons(input);

        String value1 = result.get(0);

        String valueResult1 = "happy";
        assertEquals(value1, valueResult1);
    }

    public void test_emoticon_value_8() throws Exception
    {
        String input = "hello (((megusta))) (happy) how are you! ";
        List<String> result = Detector.detectEmoticons(input);

        String value1 = result.get(0);
        String value2 = result.get(1);

        String valueResult1 = "megusta";
        String valueResult2 = "happy";

        assertEquals(value1, valueResult1);
        assertEquals(value2, valueResult2);
    }

    public void test_emoticon_value_9() throws Exception
    {
        String input = "hello (((megu*sta))) (happy) how are you!";
        List<String> result = Detector.detectEmoticons(input);

        String value1 = result.get(0);

        String valueResult1 = "happy";

        assertEquals(value1, valueResult1);

    }

    public void test_link_size_0() throws Exception
    {
        String input = "hello (((megu*sta))) (happy) how are you! ";
        List<String> result = Detector.detectLinks(input);
        int resultSize = 0;

        assertEquals(result.size(), resultSize);
    }


    public void test_link_size_1() throws Exception
    {
        String input = "https://www.google.com/?gws_rd=ssl";
        List<String> result = Detector.detectLinks(input);
        int resultSize = 1;

        assertEquals(result.size(), resultSize);
    }

    public void test_link_size_2() throws Exception
    {
        String input = "https://www.google.com/?gws_rd=ssl https://www.facebook.com/";
        List<String> result = Detector.detectLinks(input);
        int resultSize = 2;

        assertEquals(result.size(), resultSize);
    }

    public void test_link_size_3() throws Exception
    {
        String input = "https://www.google.com/?gws_rd=sslhttps://www.facebook.com/";
        List<String> result = Detector.detectLinks(input);
        int resultSize = 1;

        assertEquals(result.size(), resultSize);
    }

    public void test_link_size_4() throws Exception
    {
        String input = "hello this is a new link guy https://www.google.com/?gws_rd=ssl";
        List<String> result = Detector.detectLinks(input);
        int resultSize = 1;

        assertEquals(result.size(), resultSize);
    }

    public void test_link_size_5() throws Exception
    {
        String input = "hello this is a new link guy (https://www.google.com/?gws_rd=ssl)";
        List<String> result = Detector.detectLinks(input);
        int resultSize = 1;

        assertEquals(result.size(), resultSize);
    }

    public void test_link_size_6() throws Exception
    {
        String input = "hello this is a new link guy (https://www.google.com/?gws_rd=ssl;)";
        List<String> result = Detector.detectLinks(input);
        int resultSize = 1;

        assertEquals(result.size(), resultSize);
    }


    public void test_link_size_7() throws Exception
    {
        String input = "hello this is a new link guy (abchttps://www.google.com/?gws_rd=ssl;)";
        List<String> result = Detector.detectLinks(input);
        int resultSize = 1;

        assertEquals(result.size(), resultSize);
    }


    public void test_link_size_8() throws Exception
    {
        String input = "hello this is a new link guy (https://www.google.com/?gws_rd=ssl;https://www.facebook.com/) https://www.facebook.com/";
        List<String> result = Detector.detectLinks(input);
        int resultSize = 3;

        assertEquals(result.size(), resultSize);
    }

    public void test_link_size_9() throws Exception
    {
        String input = "https://www.google.com/?gws_rd=ssl;https://www.facebook.com/,https://www.facebook.com/";
        List<String> result = Detector.detectLinks(input);
        int resultSize = 3;

        assertEquals(result.size(), resultSize);
    }

}


