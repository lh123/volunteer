package com.yangtze.volunteer.utils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class StreamUtils
{
    public static String readFromStream(InputStream in,String charsetName)
    {
        InputStreamReader isr = null;
        try
        {
            isr = new InputStreamReader(in, charsetName);
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String temp = null;
        try
        {
            while ((temp = br.readLine()) != null)
            {
                sb.append(temp);
            }
            temp = sb.toString();
        }
        catch (IOException e)
        {
            temp = null;
            e.printStackTrace();
        }
        finally
        {
            closeStream(br);
            closeStream(isr);
            closeStream(in);
        }
        return temp;
    }

    public static void writeToStream(OutputStream ou, byte[] b)
    {
        try
        {
            ou.write(b);
            ou.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeStream(ou);
        }
    }

    private static void closeStream(Closeable c)
    {
        if (c != null)
        {
            try
            {
                c.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
