package com.example.domains;

public class LiveAPI {
    /**
     * This method gets the live feeds and
     * qualities of a live stream from
     * the channel name.
     * @param channel   String value representing the channel name to get the live feeds of.
     * @return Feeds    Feeds object holding the list of live feeds and corresponding qualities.
     */
    public static Feeds getLiveFeeds(String channel){
        String[] auth=getLiveToken(channel);    //0: Token; 1: Signature.
        return API.getPlaylist("https://usher.ttvnw.net/api/channel/hls/"+channel+".m3u8?sig="+auth[1]+"&token="+auth[0]+"&allow_source=true&allow_audio_only=true");
    }

    /**
     * This method retrieves the
     * token and signature for a
     * live channel.
     * @param channel   String value representing the channel to get the access tokens for.
     * @return String[] String array holding the token in the first position and the signature in the second.
     * String[2]: 0: Token; 1: Signature.
     */
    private static String[] getLiveToken(String channel){
        return API.getToken(channel, false);
    }
}
