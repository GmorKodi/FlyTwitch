package com.example.domains;


import com.example.enums.ContentType;
import com.example.enums.FileExtension;

/**
 * The object class for
 * the handling of live streams.
 */
public class Live {
    private String channel;     //String value representing the channel to process.
    private Feeds feeds;        //Feeds object containing all of the various feeds of a stream.
    private String fp;          //String value representing the user's desired download file path.
    private String fn;          //String value representing the file name of the output file.
    private String fFP;         //String value which represents the final file path of the downloaded object.

    /**
     * Constructor for the live
     * object which simply
     * instantiates the live object.
     */
    public Live(){
    }

    /**
     * Method which retrieves
     * all of the live feeds
     * of a channel.
     * @return Feeds    Feeds object containing all of the feeds of the live stream.
     */
    public Feeds retrieveFeeds(){
        feeds= LiveAPI.getLiveFeeds(channel);
        return feeds;
    }

    public void download(String feed){
        //TODO: Insert live stream downloading components using youtube-dl and FFMPEG.
    }

    /**
     * This method exports the feeds
     * object of the object class.
//     */
//    public void exportFeed(){
//        computeFN();
//        fFP=fp+fn+ FileExtension.TXT.fileExtension;
//        FileIO.exportFeeds(feeds, fFP);
//    }

    /**
//     * This  method computes the
//     * file name of the output file.
//     */
//    private void computeFN(){
//        fn=FileIO.computeFN(ContentType.Stream, channel);
//    }

    /**
     * Accessor for the channel variable.
     * @return String   String value representing the channel to process.
     */
    public String getChannel(){
        return channel;
    }

    /**
     * Mutator for the feeds variable.
     * @return Feeds    Feeds object containing all of the feeds of the stream.
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
     * Accessor to retrieve one
     * particular feed.
     * @param id        Integer value representing the list value of the feed to retrieve.
     * @return String   String value representing the desired feed URL.
     */
    public String getFeed(int id){
        return feeds.getFeed(id);
    }

    /**
     * Mutator for the channel variable.
     * @param channel   String value representing the channel to process.
     */
    public void setChannel(String channel){
        this.channel=channel;
    }

    /**
     * Mutator for the file path variable.
     * @param fp    String value representing the desired output file path.
     */
    public void setFP(String fp){
        this.fp=FileIO.adjustFP(fp);
    }
}