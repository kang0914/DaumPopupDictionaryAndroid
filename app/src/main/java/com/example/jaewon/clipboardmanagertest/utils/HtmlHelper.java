package com.example.jaewon.clipboardmanagertest.utils;

/**
 * Created by Jaewon on 2017-09-02.
 */

public class HtmlHelper {

    public static String encodeURIComponent(String s)
    {
        String result = null;

        try
        {
            result = java.net.URLEncoder.encode(s, "UTF-8")
                    .replaceAll("\\+", "%20")
                    .replaceAll("\\%21", "!")
                    .replaceAll("\\%27", "'")
                    .replaceAll("\\%28", "(")
                    .replaceAll("\\%29", ")")
                    .replaceAll("\\%7E", "~");
        }

        // This exception should never occur.
        catch (	java.io.UnsupportedEncodingException e)
        {
            result = s;
        }

        return result;
    }
}
