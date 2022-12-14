package com.example.domains;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;
@Slf4j 
public class VODRetrieval {
    /**
     * This method retrieves the VOD M3U8
     * URLs from given String values.
     * @param name                  String value representing the streamer's name.
     * @param sID                   String value representing the stream ID.
     * @param ts                    String value representing the timestamp of the stream.
     * @param bf                    Boolean value which represents whether a VOD brute force should be carried out.
     * @return ArrayList<String>    String arraylist which represents all of the working VOD M3U8 URLs.
     */
    public static ArrayList<String> retrieveVOD(String name, String sID, String ts, boolean bf){
    	log.info("retrieve vod inputs: name:{}, sID: {}, ts:{}, bf:{}", name,sID,ts,bf);
        ArrayList<String> results=new ArrayList<String>();
        long timestamp=Compute.getUNIX(ts);
        long streamID=Long.parseLong(sID);
        if(bf){
            results=Fuzz.BFURLs(name, streamID, timestamp);
        }
        else{
            String url=Compute.URLCompute(name, streamID, timestamp);
            results=Fuzz.verifyURL(url);
        }
        return results;
    }

    /**
     * This method retrieves all
     * of the possible feeds of a
     * VOD and returns a Feeds object
     * containing them.
     * @param baseURL   String value representing the base, source URL to check all of the qualities for.
     * @return Feeds    Feeds object containing all of the possible feeds for that particular VOD.
     */
    public static Feeds retrieveVODFeeds(String baseURL){
        String coreURL=Compute.singleRegex("(https:\\/\\/[a-z0-9\\-]*.[a-z_]*.[net||com||tv]*\\/[a-z0-9_]*\\/)chunked\\/index-dvr.m3u8", baseURL);
        return Fuzz.fuzzQualities(coreURL, "/index-dvr.m3u8");
    }

    /**
     * This method retrieves the VODID
     * from a complete VOD link.
     * @param url   Twitch VOD link (or raw ID) of a VOD.
     */
    public static Long retrieveID(String url){
        if(Compute.singleRegex("(twitch.tv\\/[a-z0-9]*\\/v\\[0-9]*)", url)!=null){
            return Long.parseLong(Compute.singleRegex("twitch.tv\\/[a-zA-Z0-9]*\\/v\\/([0-9]*)", url));
        }
        else if(Compute.singleRegex("(twitch.tv\\/[a-z0-9]*\\/videos\\/[0-9]*)", url)!=null){
            return Long.parseLong(Compute.singleRegex("twitch.tv\\/[a-z0-9]*\\/videos\\/([0-9]*)", url));
        }
        else if(Compute.singleRegex("(twitch.tv\\/videos\\/[0-9]*)", url)!=null){
            return Long.parseLong(Compute.singleRegex("twitch.tv\\/videos\\/([0-9]*)", url));
        }
        else{
            return Long.parseLong(url);
        }
    }

    /**
     * This method returns a boolean
     * value depending on whether or
     * not an M3U8 has muted segments.
     * @param url       String value representing the URL of the M3U8 to check.
     * @return boolean  Boolean value which is true if the M3U8 contains muted segments and false if otherwise.
     */
//    public static boolean hasMuted(String url){
//        File m3u8=null;
//        try {
//            m3u8= Download.tempDownload(url);
//        }
//        catch(IOException ignored){}
//        ArrayList<String> contents=FileIO.read(m3u8.getAbsolutePath());
//        for(String line: contents){
//            if(line.contains("unmuted")){
//                return true;
//            }
//        }
//        return false;
//    }

    /**
     * This method unmutes an M3U8.
     * @param value     String value representing either the file absolute path or the M3U8 URL.
     * @param isFile    Boolean value representing whether or not the M3U8 input value is a file (true value) or is a URL (false value).
     * @param outFP     String value representing the user's desired complete output file path.
//     */
//    public static void unmute(String value, boolean isFile, String outFP){
//        File m3u8=null;
//        String url="";
//        if(isFile){
//            m3u8=new File(value);
//        }
//        else{
//            try{
//                m3u8=Download.tempDownload(value);
//            }
//            catch(Exception ignored){}
//            url=value.substring(0, value.indexOf("index-dvr.m3u8"));
//        }
//        FileIO.write(unmuteContents(FileIO.read(m3u8.getAbsolutePath()), url), outFP);
//    }

    /**
     * This method unmutes the contents of
     * an arraylist representing the values
     * of an M3U8 file.
     * @param contents              String arraylist representing the contents of the raw M3U8 file.
     * @param url                   String value representing the URL to add to each TS part if applicable. Can be empty.
     * @return ArrayList<String>    String arraylist containing the unmuted contents of the M3U8 file.
     */
    private static ArrayList<String> unmuteContents(ArrayList<String> contents, String url) {
        ArrayList<String> unmutedContents=new ArrayList<String>();
        for(String line: contents){
            if(line.contains("-unmuted.ts") && !line.startsWith("#")){
                unmutedContents.add(url+line.substring(0, line.lastIndexOf("unmuted.ts"))+"muted.ts");
            }
            else if(!line.startsWith("#")){
                unmutedContents.add(url+line);
            }
            else{
                unmutedContents.add(line);
            }
        }
        return unmutedContents;
    }
}
