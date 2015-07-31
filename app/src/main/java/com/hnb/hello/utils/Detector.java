package com.hnb.hello.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by USER on 7/28/2015.
 */
public class Detector
{

    // detect emoticons function
    public static List<String> detectEmoticons(String input)
    {
        String strPatten = "\\(([a-zA-Z]{1,15})\\)";
        Pattern p = Pattern.compile(strPatten);
        Matcher m = p.matcher(input);

        List<String> results = new ArrayList<String>();
        while (m.find())
        {
            String a = m.group();
            a = a.substring(1, a.length() - 1);
            results.add(a);
        }

        return results;
    }

    // detect mentions function
    public static List<String> detectMentions(String input)
    {
        String strPatten = "@{1,1}[a-zA-Z]{1,50}[^a-zA-Z\\d]";
        Pattern p = Pattern.compile(strPatten);
        Matcher m = p.matcher(input);

        List<String> results = new ArrayList<String>();
        while (m.find())
        {
            String a = m.group();
            a = a.substring(1, a.length() - 1);
            results.add(a);
        }

        return results;
    }


    // detect links function
    public static ArrayList<String> detectLinks(String input)
    {
        // delimeter 1st
        ArrayList<String> links = new ArrayList();
        String strPatten = "\\(?\\b(http://|https://|ftp://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.]*[-A-Za-z0-9+&@#/%=~_()|]";
        Pattern p = Pattern.compile(strPatten);

        String[] parts = input.split("\\s+");
        for (String item : parts)
        {
            Matcher m = p.matcher(item);
            while (m.find())
            {
                String urlStr = m.group();
                if (urlStr.startsWith("(") && urlStr.endsWith(")"))
                {
                    urlStr = urlStr.substring(1, urlStr.length() - 1);
                }
                else
                {
                    if (urlStr.startsWith("("))
                    {
                        urlStr = urlStr.substring(1, urlStr.length());
                    }
                    else if (urlStr.endsWith(")"))
                    {
                        urlStr = urlStr.substring(0, urlStr.length() - 1);
                    }
                }
                links.add(urlStr);
            }
        }

        return links;
    }
}
