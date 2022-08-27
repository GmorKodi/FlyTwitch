package com.example.domains;

import java.util.ArrayList;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j 
@Data
public class VOD {
    private boolean isDeleted;                  //Boolean value representing whether or not a VOD is still up.
    private Feeds feeds;                        //Feeds object corresponding to the VOD.
    private long VODID;                         //VOD ID of a VOD if it is still up.
    private String[] vodInfo;                   //String array containing the VOD info such as streamer, timestamp, etc.
    //0: Channel name; 1: Stream ID; 2. Timestamp of the start of the stream; 3: Brute force boolean.
    private ArrayList<String> retrievedURLs;    //Arraylist containing all of the VOD 'chunked' M3U8s of a particular VOD.
    private String fp;                          //String value representing the file path of the output file.
    private String fn;                          //String value representing the file name of the output file.
    private String fFP;                         //String value which represents the final file path of the downloaded object.
    private String htmlCode; 
    private String url;
    private String time;
    /**
     * The constructor of a
     * VOD object which initialises
     * two boolean values based on given inputs
     * and if necessary initialises the vodInfo
     * string array.
     * @param isDeleted     Boolean value representing whether or not the VOD has being deleted or not.
     */
    public VOD(boolean isDeleted){
        this.isDeleted=isDeleted;
        if(isDeleted){
            vodInfo=new String[4];
        }
    }


    
   
    /**
     * This method gets an arraylist
     * of chunked (source quality)
     * VOD feeds from given information.
     * @return ArrayList<String>    String arraylist containing all of the source VOD feeds.
     */
    public ArrayList<String> retrieveVOD(boolean wr){
    	log.info("retrieveVod vodInfo:{}", vodInfo);
        if(!wr){
            retrievedURLs=VODRetrieval.retrieveVOD(vodInfo[0], vodInfo[1], vodInfo[2], false);
        }
        else{
            retrievedURLs=VODRetrieval.retrieveVOD(vodInfo[0], vodInfo[1], vodInfo[2], Boolean.parseBoolean(vodInfo[3]));
        }
        return retrievedURLs;
    }

    /**
     * This method retrieves the list of
     * all possible feeds for a deleted VOD.
     * @return Feeds    Feeds object containing all possible feeds of a deleted VOD.
     */
    public Feeds retrieveVODFeeds(){
        if(retrievedURLs.isEmpty()){
            return null;
        }
        else{
            feeds=VODRetrieval.retrieveVODFeeds(retrievedURLs.get(0));
            return feeds;
        }
    }

    /**
     * This method uses a website analytics
     * link to get all the values of
     * the vodInfo array.
     * @param url   String value representing a website analytics URL.
     */
    public void retrieveVODURL(String url){
    	String html = getHtmlCode();
        vodInfo=WebsiteRetrieval.getData(html, getUrl(), getTime());
        log.info("vod info retrieved: ", vodInfo);
    }

    /**
     * This method gets the corresponding
     * Feeds object to a given VOD ID.
     * @return Feeds    Feeds object corresponding to the VOD of the VOD ID.
     */
    public Feeds getVODFeeds(){
        feeds=VideoAPI.getVODFeeds(VODID);
        if(feeds.getFeeds().isEmpty()){
            feeds= VideoAPI.getSubVODFeeds(VODID, false);
        }
        return feeds;
    }

    /**
     * Accessor for a single particular
     * feed of the Feeds object based
     * on a given integer ID.
     * @param id        Integer value representing the list value of the feed to fetch.
     * @return String   Feed URL corresponding to the given ID.
     */
    public String getFeed(int id){
        return feeds.getFeed(id);
    }

    /**
     * Accessor for the Feeds
     * object of the VOD object.
     * @return Feeds    Feeds object of the VOD object.
     */
    public Feeds getFeeds(){
        return feeds;
    }

    /**
     * Accessor for the fFP variable.
     * @return String   String value representing the final file path of the outputted object.
     */
    public String getFFP(){
        return fFP;
    }

    /**
     * Accessor for the retrievedURLs
     * arraylist.
     * @return ArrayList<String>    The retrievedURLs string arraylist.
     */
    public ArrayList<String> getRetrievedURLs(){
        return retrievedURLs;
    }

    /**
     * Mutator for the
     * VODID variable.
     * @param VODID     Long value which represents the VODID of the VOD.
     */
    public void setID(long VODID){
        this.VODID=VODID;
    }

   
    /**
     * Mutator for the VOD info
     * string array which contains
     * all of the information about a
     * VOD in order to compute the base URL.
     * @param info      String array containing the information about the VOD.
     */
    public void setVODInfo(String[] info){
        vodInfo=info;
    }

    /**
     * Mutator for the channel name
     * value of the vodInfo array.
     * @param channel   String value representing the channel the VOD is from.
     */
    public void setChannel(String channel){
        vodInfo[0]=channel;
    }

    /**
     * Mutator for the stream ID
     * value of the vodInfo array.
     * @param streamID  String value representing the stream ID of the stream of the VOD.
     */
    public void setStreamID(String streamID){
        vodInfo[1]=streamID;
    }

    /**
     * Mutator for the timestamp
     * value of the vodInfo array.
     * @param timestamp     String value representing the timestamp of the start of the VOD in 'YYYY-MM-DD HH:mm:ss' format.
     */
    public void setTimestamp(String timestamp){
        vodInfo[2]=timestamp;
    }

    /**
     * Mutator for the brute force
     * value of the vodInfo array.
     * @param bf    Boolean value representing whether or not the VOD start timestamp is to the second or to the minute.
     */
    public void setBF(boolean bf){
        vodInfo[3]=String.valueOf(bf);
    }

   
    public void retrieveID(String url){
        VODID=VODRetrieval.retrieveID(url);
    }

   
}