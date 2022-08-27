package com.example.enums;

public enum FileExtension {
    M3U8(".m3u8"),
    TS(".ts"),
    MPEG(".mpeg"),
    TXT(".txt"),
    MOV(".mov"),
    AVI(".avi"),
    MP4(".mp4");

    public String fileExtension;
    FileExtension(String fe){
        fileExtension=fe;
    }
}
