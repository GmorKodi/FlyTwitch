package com.example.domains;

import java.util.ArrayList;

import com.example.enums.Quality;

public class Feeds {
    private ArrayList<String> feeds;        //String arraylist which holds the links of all of the feeds.
    private ArrayList<Quality> qualities;   //Quality arraylist which holds the corresponding qualities.
    //The feeds and qualities arraylist are corresponding,
    // the value at index 1 of one corresponds to the value at index 1 of the other.

    /**
     * Constructor of the feeds object
     * which initialises the feeds and
     * qualities arraylists.
     */
    public Feeds(){
        feeds=new ArrayList<String>();
        qualities=new ArrayList<Quality>();
    }

    /**
     * Mutator for the feeds arraylist which
     * sets the feeds arraylist to the
     * given arraylist.
     * @param f     String arraylist which represents what the feeds arraylist must be set to.
     */
    public void setFeeds(ArrayList<String> f){
        feeds=f;
    }

    /**
     * Mutator for the qualities arraylist which
     * sets the qualities arraylist to the
     * given arraylist.
     * @param q     Quality arraylist which represents what the qualities arraylist must be set to.
     */
    public void setQualities(ArrayList<Quality> q){
        qualities=q;
    }

    /**
     * Mutator for both the feeds and qualities arraylists,
     * adding an entry to both arraylists.
     * @param f     A string value which represents a feed URL to be added to the feeds list.
     * @param q     A quality enum which represents a quality to be added to the qualities list.
     */
    public void addEntry(String f, Quality q){
        feeds.add(f);
        qualities.add(q);
    }

    /**
     * Mutator for both the feeds and qualities arraylists,
     * adding an entry to both arraylists at one particular index.
     * @param f     A string value which represents a feed URL to be added to the feeds list.
     * @param q     A quality enum which represents a quality to be added to the qualities list.
     * @param i     An integer value representing the index at where to place the entry.
     */
    public void addEntryPos(String f, Quality q, int i){
        feeds.add(i, f);
        qualities.add(i, q);
    }

    /**
     * Mutator for the feeds arraylist whcih adds
     * the given string value to the feeds arraylist.
     * @param f     A string value which represents a feed URL to be added to the feeds list.
     */
    public void addFeed(String f){
        feeds.add(f);
    }

    /**
     * Mutator for the qualities arraylist which
     * adds the given Quality enum to the qualities arraylist.
     * @param q     A quality enum which represents a quality to be added to the qualities list.
     */
    public void addQuality(Quality q){
        qualities.add(q);
    }

    /**
     * An accessor for the feeds arraylist
     * which returns the URL of a feed at a
     * particular index of the feeds list.
     * @param i         Integer value representing the index of the feed to fetch.
     * @return String   String value representing the feed URL located at the given index.
     */
    public String getFeed(int i){
        return feeds.get(i);
    }

    /**
     * An accessor that returns the feed url
     * that corresponds to a particular
     * given quality.
     * @param q         Quality enum for which to get the corresponding feed url.
     * @return String   String value representing the feed url that corresponds to
     * the given quality or is null if the quality enum does not exist in the Feeds object.
     */
    public String getFeedQual(Quality q){
       for(int i=0; i<qualities.size(); i++){
           if(qualities.get(i)==q){
               return feeds.get(i);
           }
       }
       return null;     //If this point it reaches it means the quality wasn't present in the qualities list.
    }

    /**
     * An accessor for the qualiies
     * arraylist which returns the Quality
     * enum located at a particular enum.
     * @param i         Integer value representing the index of the quality enum to fetch.
     * @return Quality  Quality enum representing the quality of the corresponding feed located at the given index.
     */
    public Quality getQuality(int i){
        return qualities.get(i);
    }

    /**
     * An accessor that returns the quality
     * enum that corresponds to the quality
     * of a given feed URL.
     * @param feed      String value representing the feed value to find the corresponding quality of.
     * @return Quality  Quality enum which corresponds to the given feed URL
     * or is null if the feed URL does not exist in this Feeds object.
     */
    public Quality getQualityFeed(String feed){
        for(int i=0; i<feeds.size(); i++){
            if(feeds.get(i).equals(feed)){
                return qualities.get(i);
            }
        }
        return null;    //If it reaches this point, the feed url does not exist in this feed object.
    }
    /**
     * An accessor for the feeds arraylist
     * which returns the entire list of feeds.
     * @return ArrayList<String>    String arraylist representing the entire feeds arraylist.
     */
    public ArrayList<String> getFeeds(){
        return feeds;
    }

    /**
     * An accessor for the qualities arraylist
     * which returns the entire list of qualities.
     * @return ArrayList<Quality>   Quality arraylist which represents the entire qualities arraylist.
     */
    public ArrayList<Quality> getQualities(){
        return qualities;
    }
}