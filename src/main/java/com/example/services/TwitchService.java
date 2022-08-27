package com.example.services;

import com.example.domains.Feeds;
import com.example.domains.Live;
import com.example.domains.VOD;
import com.example.enums.Quality;

import lombok.extern.slf4j.Slf4j;
@Slf4j 

public class TwitchService {
	
	public String getStream(String url)
	{
//		return retrieve(url); 
		return retrieveSource(url); 

	}
	
	public String getLive(String channel)
	{
		return retrieveLive(channel); 
	}
	
	
	private String retrieve(String url) {
		VOD vod=new VOD(false);
        vod.retrieveID(url);
        Feeds feeds=vod.getVODFeeds();
        int quality=feeds.getFeeds().size();
        return vod.getFeed(quality-1);
	}
	
	
	private String retrieveSource(String url) {
		VOD vod=new VOD(false);
        vod.retrieveID(url);
        Feeds feeds=vod.getVODFeeds();
        int quality=feeds.getFeeds().size();
        return feeds.getFeedQual(Quality.Source);
	}
	
	
	private String retrieveLive(String channel){
        Live live=new Live();
        live.setChannel(channel);
        Feeds feeds=live.retrieveFeeds();
        if(feeds.getFeeds().isEmpty()){
           return null; 
        }
        else{
            int quality=0;
            return live.getFeed(quality);
        }

    }

	public String getDeletedVod(String twitchTrackerLink, String url, String timeStamp) {
		
		return  recover(url,  twitchTrackerLink, timeStamp);
	}
	
	private String recover(String twitchTrackerLink, String html, String timeStamp){
		log.info("recover twitchTrackerLink:{}", twitchTrackerLink);
//        VODRecoveryMenu();
        VOD vod=new VOD(true);
        boolean wf;
        String url=twitchTrackerLink;
        vod.setHtmlCode(html);
        vod.setUrl(url);
        vod.setTime(timeStamp);
		log.info("recover url:{}", url);
        vod.retrieveVODURL("testurl");
        wf=false;
    
        
        vod.retrieveVOD(wf);
        Feeds feeds=vod.retrieveVODFeeds();
        if(feeds==null){
            return "none found"; 
        }
        else{
            int quality=feeds.getFeeds().size();
            return feeds.getFeed(quality-1);
        }

    }

}
