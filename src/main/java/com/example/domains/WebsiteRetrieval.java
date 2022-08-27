package com.example.domains;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * This class contains the core method for website data
 * recovery and all of its necessary methods to retrieve
 * necessary information from the Twitch analytics websites
 * Twitch recover supports.
 */
@Slf4j 
public class WebsiteRetrieval {
    //Core methods:

    /**
     * Core method which retrieves the 4 principal values (streamer's name, stream ID, timestamp, duration)
     * of a stream and returns in a string array in that order.
     * @param url URL to retrieve the values from.
     * @return String[4]    String array containing the 4 principal values (streamer's name, stream ID,
     * timestamp of the start of the stream and the duration) in that respective order. If all values of the
     * array are null, the URL is invalid.
     */
    public static String[] getData(String html, String url, String timeStamp) {
        String[] results = new String[4];     //0: streamer's name; 1: Stream ID; 2: Timestamp; 3: Duration.
//        log.info("getdata url: {}", url);
//        int source = checkURL(url);
//        log.info("check url returned: {} ", source);
//        if(source == -1) {         //Invalid URL.
//            return results;
//        }
//        else if(source == 1) {     //Twitch Tracker URL.
            try {
                results = getTTData(html, url, timeStamp);
            }
            catch(IOException ignored) {
            	log.error("Failed getting TTData: {}", ignored);
            }
//            return results;
//        }
//        else if(source == 2) {     //Stream Charts URL.
//            try {
//                results = getSCData(url);
//            }
//            catch(IOException ignored) {}
//            return results;
//        }
        log.info("getData results: ", results);
        return results;
    }

    /**
     * This method checks if a URL is a stream URL
     * from one of the supported analytics websites.
     * @param url URL to be checked.
     * @return int      Integer that is either -1 if the URL is invalid or
     * a value that represents which analytics service the stream link is from.
     */
    private static int checkURL(String url) {
        if(url.contains("twitchtracker.com/") && url.contains("/streams/")) {
            return 1;   //Twitch Tracker URL.
        }
        else if(url.contains("streamscharts.com/twitch/channels/") && url.contains("/streams/")) {
            return 2;   //Streams Charts URL.
        }
        return -1;
    }

    /**
     * This method gets the JSON return from a URL.
     * @param url String representing the URL to get the JSON response from.
     * @return String   String response representing the JSON response of the URL.
     * @throws IOException
     */
    private static String getJSON(String url) throws IOException {
        String json = "";
        URL jsonFetch = new URL(url);
        HttpURLConnection httpcon = (HttpURLConnection) jsonFetch.openConnection();
        httpcon.setRequestMethod("GET");
        httpcon.setRequestProperty("User-Agent", "Mozilla/5.0");
        String readLine = null;
        if(httpcon.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));
            StringBuffer response = new StringBuffer();
            while((readLine = br.readLine()) != null) {
                response.append(readLine);
            }
            br.close();
            json = response.toString();
        }
        return json;
    }

    //Individual website retrieval:

    //Twitch Tracker retrieval:
    /**
     * This method gets the 4 principal values (streamer's name, stream ID, timestamp and the duration)
     * from a Twitch Tracker stream URL.
     * @param url String value representing the Twitch Tracker stream URL.
     * @return String[4]    String array containing the 4 principal values (streamer's name, stream ID,
     * timestamp of the start of the stream and the duration) in that respective order.
     * @throws IOException
     */
    private static String[] getTTData(String html, String url, String timeStamp) throws IOException {
        String[] results = new String[4];
//        log.info("getTTData url: {}", url);
//        URL obj = new URL(url);
//        HttpURLConnection httpcon = (HttpURLConnection) obj.openConnection();
//        httpcon.setRequestMethod("GET");
//        httpcon.setRequestProperty("User-Agent", "Mozilla/5.0");
//        	System.setProperty("http.proxyHost", "<Proxy host>"); 
//        	System.setProperty("http.proxyPort", "<proxy port>");
//        if(httpcon.getResponseCode() == HttpURLConnection.HTTP_OK) {
            //Get the timestamp:
        
	        String apiResponse = URLDecoder.decode(html, "UTF-8");
//	        log.info("html decoded: {}", apiResponse);
//	        System.out.println("html decoded: " + apiResponse);
	        log.info("timestamp: {}", timeStamp);
          results[2] = timeStamp;


	        Reader inputString = new StringReader(apiResponse);
	        BufferedReader brt = new BufferedReader(inputString);
	        
//	        Pattern p = Pattern.compile(".*STREAM ON (.*)<span.*");
//	        Matcher ma = p.matcher(apiResponse);
//	        String timestapString = ma.group(1);
//	        log.info("ma: {}", ma);
//	        log.info("timestamp: {}", timestapString);
        
        	log.info("TT api responsed");
//            BufferedReader brt = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));
            String response;
            String responseD = "";
            for(int i = 0; i < 300; i++) {
                response = brt.readLine();
//                if(i == 111) {
//                	log.info("line 111: {}", response);
////                if(response.contains("stream on ")) {
//                    int tsIndex = response.indexOf(" on ") + 4;
////                    results[2] = response.substring(tsIndex, tsIndex + 19);
//                    results[2] = timeStamp;
//                }
                //Stream duration fetcher:
                if(response.contains("stats-value to-time-lg")) {
                    responseD = response;
                }
            }

            //Get the stream duration:
            String durationPattern = "<div class=\"stats-value to-time-lg\">(\\d*)</div>";
            Pattern dr = Pattern.compile(durationPattern);
            Matcher dm = dr.matcher(responseD);
            if(dm.find()) {
                results[3] = dm.group(1);
            }
            //Get the streamer's name and the VOD ID:
            String pattern = "twitchtracker\\.com\\/([a-zA-Z0-9-_]*)\\/streams\\/(\\d*)";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(url);
            if(m.find()) {
                results[0] = m.group(1);
                results[1] = m.group(2);
            }
            //Return the array:
            log.info("results array: {}", results);
            return results;
//        }
//        throw new IOException();
    }
    
    
    private static String getApiCall(String url) {
		// TODO Auto-generated method stub
		final String uri = url;

	    RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.getForObject(uri, String.class);

	    System.out.println(result);
		return result;
	}

    //Stream Charts retrieval:
    /**
     * This method gets the 4 principal values (streamer's name, stream ID, timestamp and the duration)
     * from a Stream Charts stream URL.
     * @param url String value representing the Stream Charts stream URL.
     * @return String[4]    String array containing the 4 principal values (streamer's name, stream ID,
     * timestamp of the start of the stream and the duration) in that respective order.
     * @throws IOException
     */
    private static String[] getSCData(String url) throws IOException {
        String[] results = new String[4];     //0: streamer's name; 1: Stream ID; 2: Timestamp; 3: Duration.
        String userID;
        double duration = 0.0;
        //Retrieve initial values:
        String pattern = "streamscharts\\.com\\/twitch\\/channels\\/([a-zA-Z0-9_-]*)\\/streams\\/(\\d*)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(url);
        if(m.find()) {
            results[0] = m.group(1);
            results[1] = m.group(2);
        }

        //Retrieve user ID:
        String idJSON = getJSON("https://api.twitch.tv/v5/users/?login=" + results[0] + "&client_id=ohroxg880bxrq1izlrinohrz3k4vy6");
        JSONObject joID = new JSONObject(idJSON);
        JSONArray users = joID.getJSONArray("users");
        JSONObject user = users.getJSONObject(0);
        userID = user.getString("_id");

        //Retrieve stream values:
        String dataJSON = getJSON("https://alla.streamscharts.com/api/free/streaming/platforms/1/channels/" + userID + "/streams/" + results[1] + "/statuses");
        JSONObject joD = new JSONObject(dataJSON);
        JSONArray items = joD.getJSONArray("items");
        for(int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            if(i == 0) {
                results[2] = item.getString("stream_created_at");
            }
            duration += item.getDouble("air_time");
        }
        results[3] = String.valueOf(duration * 60);
        return results;
    }
}
