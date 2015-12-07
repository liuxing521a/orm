// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Utils.java

package org.itas.common.collection;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

// Referenced classes of package daff.util:
//            HashMap, Map, ArrayList, List, 
//            ByteBuffer, ClipboardAccessor

public final class Utils
{

    public static Map line2Args(String s, String s1, String s2, Map map)
    {
        if(map == null)
            map = new HashMap(4);
        if(s2 == null)
            s2 = " ";
        if(s1 == null)
            s1 = "=";
        int i = 0;
        int j = s.indexOf(s1);
        boolean flag = false;
        Object obj = null;
        Object obj1 = null;
        for(; j > 0; j = s.indexOf(s1, i))
        {
            int k = s.indexOf(s2, j + s1.length());
            if(k < 0)
                k = s.length();
            String s3 = s.substring(i, j);
            String s4;
            if(j < k)
                s4 = s.substring(j + s1.length(), k);
            else
                s4 = "";
            if(s4.length() > 0 && s4.charAt(0) == '"' && s4.charAt(s4.length() - 1) == '"')
                s4 = s4.substring(1, s4.length() - 1);
            if(s4.length() > 0)
                map.put(s3, s4);
            i = k + s2.length();
        }

        return map;
    }

    public static final byte[] readURL(URL url)
        throws IOException
    {
        URLConnection urlconnection = url.openConnection();
        InputStream inputstream = urlconnection.getInputStream();
        int i = urlconnection.getContentLength();
        if(i <= 0)
            i = inputstream.available();
        if(i <= 0)
            return null;
        byte abyte0[] = new byte[i];
        for(int j = 0; j < i; j += inputstream.read(abyte0, j, i - j));
        return abyte0;
    }

    public static void sleep(int i)
    {
        try
        {
            Thread.sleep(i);
        }
        catch(InterruptedException interruptedexception)
        {
            interruptedexception.printStackTrace();
        }
    }

    public static boolean createDirs(String s)
    {
        if(s.indexOf('\\') > -1)
            s = s.replace('\\', '/');
        int i = s.lastIndexOf('/');
        if(JAVA_VERSION == 1)
        {
            String as[] = split(s, "/");
            String s2 = as[0] + "/";
            for(int j = 1; j < as.length - 1; j++)
            {
                s2 = s2 + as[j] + "/";
                File file1 = new File(s2);
                if(!file1.exists() && !file1.mkdir())
                    return false;
            }

            return true;
        } else
        {
            String s1 = s.substring(0, i + 1);
            File file = new File(s1);
            return file.mkdirs();
        }
    }

    public static ClipboardAccessor getClipboardAccessor()
    {
        return localAccessor;
    }

    public static void setClipboardAccessor(ClipboardAccessor clipboardaccessor)
    {
        localAccessor = clipboardaccessor;
    }

    public static String md5String(String s)
    {
        try
        {
            MessageDigest messagedigest = MessageDigest.getInstance("MD5");
            byte abyte0[] = s.getBytes();
            abyte0 = messagedigest.digest(abyte0);
            StringBuffer stringbuffer = new StringBuffer(32);
            for(int i = 0; i < abyte0.length; i++)
            {
                String s1 = Integer.toHexString(abyte0[i] & 0xff);
                if(s1.length() == 1)
                    stringbuffer.append('0');
                stringbuffer.append(s1);
            }

            return stringbuffer.toString();
        }
        catch(NoSuchAlgorithmException _ex)
        {
            return "";
        }
    }

    public static String getStackTrace(Throwable throwable)
    {
        if(throwable == null)
        {
            return "no exception";
        } else
        {
            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
            PrintWriter printwriter = new PrintWriter(new OutputStreamWriter(bytearrayoutputstream));
            throwable.printStackTrace(printwriter);
            printwriter.flush();
            printwriter.close();
            return bytearrayoutputstream.toString();
        }
    }

    public static int indexOf(byte abyte0[], byte abyte1[])
    {
        int i = abyte0.length - abyte1.length;
        for(int j = 0; j < i; j++)
        {
            boolean flag = true;
            for(int k = 0; k < abyte1.length; k++)
            {
                flag &= abyte0[j + k] == abyte1[k];
                if(!flag)
                    break;
            }

            if(flag)
                return j;
        }

        return -1;
    }

    public static String[] split(String s, String s1)
    {
        if(s.indexOf(s1) < 0)
            return (new String[] {
                s
            });
        ArrayList arraylist = new ArrayList();
        for(int i = s.indexOf(s1); i >= 0; i = s.indexOf(s1))
        {
            String s2 = s.substring(0, i);
            String s3 = s.substring(s1.length() + i);
            arraylist.add(s2);
            s = s3;
        }

        arraylist.add(s);
        String as[] = new String[arraylist.size()];
        arraylist.toArray(as);
        return as;
    }

    public static String urlDecode(String s)
    {
        byte abyte0[] = s.getBytes();
        ByteBuffer bytebuffer = new ByteBuffer();
        for(int i = 0; i < abyte0.length; i++)
            if(abyte0[i] == 43)
                bytebuffer.writeByte(32);
            else
            if(abyte0[i] == 37)
            {
                String s1 = new String(abyte0, i + 1, 2);
                int j = Integer.parseInt(s1, 16);
                bytebuffer.writeByte(j);
                i += 2;
            } else
            {
                bytebuffer.writeByte(abyte0[i]);
            }

        return bytebuffer.toString();
    }

    public static String strEncode(String s)
    {
        boolean flag = false;
        StringBuffer stringbuffer = new StringBuffer(s.length() * 6);
        for(int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if(c > '\377' || c == '%' || c == '+' || c == '"' || c == '\'' || c == '<' || c == '>')
            {
                stringbuffer.append('%');
                stringbuffer.append(Integer.toHexString(c));
                stringbuffer.append('%');
            } else
            if(c == ' ')
                stringbuffer.append('+');
            else
                stringbuffer.append(c);
        }

        return stringbuffer.toString();
    }

    public static final int getTimeDiffByCode(int i, int j)
    {
        Date date = getDate(i);
        Date date1 = getDate(j);
        long l = date.getTime();
        long l1 = date1.getTime();
        int k = (int)((l1 - l) / 1000L);
        return k;
    }

    public static final int getTimeDiff(int i, long l)
    {
        Date date = getDate(i);
        int j = (int)((l - date.getTime()) / 1000L);
        return j;
    }

    public static String getCurrentTimeDesc()
    {
        Date date = new Date();
        return dateFormat.format(date);
    }

    private Utils()
    {
    }

    public static String strDecode(String s)
    {
        if(s.indexOf('%') < 0 && s.indexOf('+') < 0)
            return s;
        StringBuffer stringbuffer = new StringBuffer(s.length());
        boolean flag = false;
        for(int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if(c == '%')
            {
                int j = s.indexOf('%', i + 1);
                String s1 = s.substring(i + 1, j);
                c = (char)Integer.parseInt(s1, 16);
                i = j;
            } else
            if(c == '+')
                c = ' ';
            stringbuffer.append(c);
        }

        return stringbuffer.toString();
    }

    public static final Date getDate(int i)
    {
        int j = (i >> 26) + 100;
        int k = i >> 22 & 0xf;
        int l = i >> 17 & 0x1f;
        int i1 = i >> 12 & 0x1f;
        int j1 = i >> 6 & 0x3f;
        int k1 = i & 0x3f;
        Date date = new Date(j, k, l, i1, j1, k1);
        return date;
    }

    public static int getCurrentTimeCode()
    {
        Date date = new Date();
        return getTimeCode(date);
    }

    public static byte[] readFile(String s)
    {
        return readFile(new File(s));
    }

    public static byte[] readFile(File file)
    {
        if(!file.exists() || file.isDirectory())
            return null;
        try
        {
            FileInputStream fileinputstream = new FileInputStream(file);
            byte abyte0[] = new byte[fileinputstream.available()];
            fileinputstream.read(abyte0);
            fileinputstream.close();
            return abyte0;
        }
        catch(IOException ioexception)
        {
            ioexception.printStackTrace();
        }
        return null;
    }

    public static void writeFile(String s, byte abyte0[])
    {
        writeFile(new File(s), abyte0);
    }

    public static void writeFile(File file, byte abyte0[])
    {
        String s = file.getParent();
        if(s != null)
        {
            File file1 = new File(file.getParent());
            if(!file1.exists())
                createDirs(file.getAbsolutePath());
        }
        try
        {
            FileOutputStream fileoutputstream = new FileOutputStream(file);
            fileoutputstream.write(abyte0);
            fileoutputstream.close();
        }
        catch(IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    public static void countFiles(File file, List list, String s, boolean flag)
    {
        String as[] = file.list();
        if(as == null)
            return;
        for(int i = 0; i < as.length; i++)
        {
            File file1 = new File(file, as[i]);
            if(file1.isDirectory() && flag)
                countFiles(file1, list, s, flag);
            else
            if(as[i].toLowerCase().endsWith(s))
                list.add(file1.getPath());
        }

    }

    public static final String getTimeDesc(int i)
    {
        Date date = getDate(i);
        return dateFormat.format(date);
    }

    public static final String getTimeDesc(long l)
    {
        Date date = new Date(l);
        return dateFormat.format(date);
    }

    public static int getTimeCode(long l)
    {
        Date date = new Date(l);
        return getTimeCode(date);
    }

    public static int getTimeCode(long l, int ai[])
    {
        Date date = new Date(l);
        int i = date.getYear() - 100;
        int j = date.getMonth();
        int k = date.getDate();
        int i1 = date.getHours();
        int j1 = date.getMinutes();
        int k1 = date.getSeconds();
        ai[0] = i;
        ai[1] = j;
        ai[2] = k;
        ai[3] = i1;
        ai[4] = j1;
        ai[5] = k1;
        ai[6] = date.getDay();
        return i << 26 | j << 22 | k << 17 | i1 << 12 | j1 << 6 | k1;
    }

    public static int getTimeCode(int i, int j, int k, int l, int i1, int j1)
    {
        return i << 26 | j << 22 | k << 17 | l << 12 | i1 << 6 | j1;
    }

    public static final int getTimeCode(Date date)
    {
        int i = date.getYear() - 100;
        int j = date.getMonth();
        int k = date.getDate();
        int l = date.getHours();
        int i1 = date.getMinutes();
        int j1 = date.getSeconds();
        return i << 26 | j << 22 | k << 17 | l << 12 | i1 << 6 | j1;
    }

    public static String getClipboardText()
    {
        if(localAccessor != null)
            return localAccessor.getClipboardText();
        try
        {
            Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
            Object obj = transferable.getTransferData(DataFlavor.stringFlavor);
            if(obj instanceof String)
                return (String)obj;
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return "";
    }

    public static boolean isValidPassword(String s)
    {
        int i = s.length();
        for(int j = 0; j < i; j++)
        {
            char c = s.charAt(j);
            if(c <= ' ' || c == '"' || c == '\'' || c == '/' || c == '>' || c == '<')
                return false;
        }

        return true;
    }

    public static void setClipboardText(String s)
    {
        if(localAccessor != null)
        {
            localAccessor.setClipboardText(s);
        } else
        {
            StringSelection stringselection = new StringSelection(s);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselection, stringselection);
        }
    }

    public static String replaceAll(String s, String s1, String s2)
    {
        for(int i = s.indexOf(s1); i >= 0; i = s.indexOf(s1))
            s = s.substring(0, i) + s2 + s.substring(i + s1.length());

        return s;
    }

    public static void intersection(Rectangle rectangle, Rectangle rectangle1, Rectangle rectangle2)
    {
        int i = rectangle.x;
        int j = rectangle.y;
        int k = rectangle1.x;
        int l = rectangle1.y;
        int i1 = i;
        i1 += rectangle.width;
        int j1 = j;
        j1 += rectangle.height;
        int k1 = k;
        k1 += rectangle1.width;
        int l1 = l;
        l1 += rectangle1.height;
        if(i < k)
            i = k;
        if(j < l)
            j = l;
        if(i1 > k1)
            i1 = k1;
        if(j1 > l1)
            j1 = l1;
        i1 -= i;
        j1 -= j;
        if(i1 < 0x80000000)
            i1 = 0x80000000;
        if(j1 < 0x80000000)
            j1 = 0x80000000;
        rectangle2.setBounds(i, j, i1, j1);
    }

    @SuppressWarnings("unchecked")
	public static <T> T loadObject(String s) {
        try {
            Class<?> class1 = Class.forName(s);
            return (T)class1.newInstance();
        }
        catch(ClassNotFoundException classnotfoundexception) {
            classnotfoundexception.printStackTrace();
        }
        catch(IllegalAccessException illegalaccessexception) {
            illegalaccessexception.printStackTrace();
        }
        catch(InstantiationException instantiationexception) {
            instantiationexception.printStackTrace();
        }
        return null;
    }

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final int JAVA_VERSION = System.getProperty("java.version").charAt(2) - 48;
    private static ClipboardAccessor localAccessor;

}
