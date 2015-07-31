package com.hnb.hello.utils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TitleExtractor
{

    private static final Pattern TITLE_TAG = Pattern.compile("\\<title>(.*)\\</title>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    // return data if nothing found, or exception
    private static final String STR_CAN_NOT_FIND_TITLE = "Cannot find title!";


    //   get url title of a website
    //   param url: link of a website
    public static String getPageTitle(String url) throws IOException
    {
        URL u = new URL(url);
        URLConnection conn = u.openConnection();

        // ContentType is an inner class defined below
        ContentType contentType = getContentTypeHeader(conn);
        if (contentType != null) // if normal website use this one it must be faster, if some special website, use "else"
        {
            try
            {
                if (!contentType.contentType.equals("text/html"))
                {
                    return STR_CAN_NOT_FIND_TITLE; // don't continue if not HTML
                }
                else
                {
                    // determine the charset, or use the default
                    Charset charset = getCharset(contentType);
                    if (charset == null)
                    {
                        charset = Charset.defaultCharset();
                    }

                    // read the response body
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
                    int n = 0, totalRead = 0;
                    char[] buf = new char[1024];
                    StringBuilder content = new StringBuilder();

                    // read until EOF or first 8192 characters
                    while (totalRead < 8192 && (n = reader.read(buf, 0, buf.length)) != -1)
                    {
                        content.append(buf, 0, n);
                        totalRead += n;
                    }
                    reader.close();

                    // extract the title
                    Matcher matcher = TITLE_TAG.matcher(content);
                    if (matcher.find())
                    {
                        //replace any occurrences of whitespace (which may include line feeds and other uglies)as well as HTML brackets with a space
                        return matcher.group(1).replaceAll("[\\s\\<>]+", " ").trim();
                    }
                    else
                    {
                        return STR_CAN_NOT_FIND_TITLE;
                    }
                }
            }
            catch (Exception ex)
            {
                return STR_CAN_NOT_FIND_TITLE;
            }
        }
        else // special website
        {
            String result = getTittleInCaseException(url);
            if (result != null)
            {
                return result;
            }
            else
            {
                return STR_CAN_NOT_FIND_TITLE;
            }

        }

    }


    // some special website, get title this way
    private static String getTittleInCaseException(String url)
    {
        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            //get all headers
            Header[] headers = response.getAllHeaders();


            for (Header header : headers)
            {
                if (header != null && header.getName().equalsIgnoreCase("Content-Type"))
                {
                    ContentType contentType = new ContentType(header.getValue());
                    Charset charset = getCharset(contentType);
                    if (charset == null) charset = Charset.defaultCharset();
                    HttpEntity entity = response.getEntity();

                    InputStream in = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
                    int n = 0, totalRead = 0;
                    char[] buf = new char[1024];
                    StringBuilder content = new StringBuilder();

                    // read until EOF or first 8192 characters
                    while (totalRead < 8192 && (n = reader.read(buf, 0, buf.length)) != -1)
                    {
                        content.append(buf, 0, n);
                        totalRead += n;
                    }
                    reader.close();

                    Matcher matcher = TITLE_TAG.matcher(content);
                    if (matcher.find())
                    {
                        return matcher.group(1).replaceAll("[\\s\\<>]+", " ").trim();
                    }


                }
            }
        }
        catch (Exception ex)
        {
            return STR_CAN_NOT_FIND_TITLE;
        }

        return STR_CAN_NOT_FIND_TITLE;
    }


    //Loops through response headers until Content-Type is found.
    private static ContentType getContentTypeHeader(URLConnection conn)
    {
        int i = 0;
        boolean moreHeaders = true;
        do
        {
            conn.getHeaderFields();
            String headerName = conn.getHeaderFieldKey(i);
            String headerValue = conn.getHeaderField(i);
            if (headerName != null && headerName.equals("Content-Type"))
                return new ContentType(headerValue);

            i++;
            moreHeaders = headerName != null || headerValue != null;
        }
        while (moreHeaders);

        return null;
    }


    // get charset of the content type
    private static Charset getCharset(ContentType contentType)
    {
        if (contentType != null && contentType.charsetName != null && Charset.isSupported(contentType.charsetName))
            return Charset.forName(contentType.charsetName);
        else return null;
    }


    // Class holds the content type and charset (if present)
    private static final class ContentType
    {
        private static final Pattern CHARSET_HEADER = Pattern.compile("charset=([-_a-zA-Z0-9]+)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

        private String contentType;
        private String charsetName;

        private ContentType(String headerValue)
        {
            if (headerValue == null)
                throw new IllegalArgumentException("ContentType must be constructed with a not-null headerValue");
            int n = headerValue.indexOf(";");
            if (n != -1)
            {
                contentType = headerValue.substring(0, n);
                Matcher matcher = CHARSET_HEADER.matcher(headerValue);
                if (matcher.find()) charsetName = matcher.group(1);
            }
            else contentType = headerValue;
        }
    }
}