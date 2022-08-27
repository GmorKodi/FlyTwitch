package com.example.domains;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j 
public class Compute {
    /**
     * Method which
     * computes a VOD URL from given values.
     * @param name          String value representing the streamer's name.
     * @param streamID      A long representing the stream ID of a stream.
     * @param timestamp     A long value representing the timestamp of the stream
     * in standard timestamp form.
     * @return String       String value representing the completed latter part of the URL.
     */
    protected static String URLCompute(String name, long streamID, long timestamp){
        String baseString=name+"_"+streamID+"_"+timestamp;
        String hash=hash(baseString);
        String finalString=hash+"_"+baseString;
        return "/"+finalString+"/chunked/index-dvr.m3u8";
    }

    /**
     * This method gets the UNIX time from a time value in a standard
     * timestamp format.
     * @param ts        String value representing the timestamp.
     * @return long     Long value which represents the UNIX timestamp.
     */
    protected static long getUNIX(String ts){
    	log.info("getUNIX ts:{}" , ts);
        String time = ts + " UTC";
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz");
        DateFormat df = new SimpleDateFormat("EEE, MMM d, HH:mm zzz");
        Date date=null;
        try{
            date=df.parse(time);
        }
        catch(ParseException ignored){log.error("error: {}", ignored);}
        return (long) date.getTime() / 1000;
    }

    /**
     * This method computers the SHA1 hash of the base
     * string computed in the URL compute method and
     * returns the first 20 characters of the hash.
     * @param baseString    Base string for which to compute the hash for.
     * @return String       First 20 characters of the SHA1 hash of the given base string.
     * @throws NoSuchAlgorithmException
     */
    private static String hash(String baseString){
        MessageDigest md= null;
        try {
            md = MessageDigest.getInstance("SHA1");
        }
        catch(NoSuchAlgorithmException ignored){}
        byte[] result=md.digest(baseString.getBytes());
        StringBuffer sb=new StringBuffer();
        for(byte val: result){
            sb.append(Integer.toString((val&0xff)+0x100, 16).substring(1));
        }
        String hash=sb.toString();
        return hash.substring(0, 20);
    }

    /**
     * This method computes the regex of a
     * given value and returns the value of
     * the first group, or if the pattern
     * did not match the given value, it
     * returns null.
     * @param pattern   String value representing the regex pattern to compile.
     * @param value     String value representing the value to apply the regex pattern to.
     * @return String   String value representing the first regex group or null if the regex did not compile.
     */
    public static String singleRegex(String pattern, String value){
        Pattern p=Pattern.compile(pattern);
        Matcher m=p.matcher(value);
        if(m.find()){
            return m.group(1);
        }
        return null;
    }

    /**
     * This method checks whether a string
     * has a null value or not.
     * @param string    String value to check.
     * @return boolean  Boolean value which is true if the string is null and false otherwise.
     */
    public static boolean checkNullString(String string){
        if(string==null){
            return true;
        }
        return false;
    }
}