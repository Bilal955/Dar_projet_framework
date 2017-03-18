package types;

public enum HeaderRequest {

    ACCEPT("Accept"),
    ACCEPT_CHARSET("Accept-Charset"),
    ACCEPT_ENCODING("Accept-Encoding"),
    ACCEPT_DATETIME("Accept-Datetime"),
    ACCEPT_LANGUAGE("Accept-Language"),
    USER_AGENT("User-Agent"), 
    AUTHORIZATION("Authorization"),
    CONNECTION("Connection"),
    EXPECT("Expect"),
    FORWARDED("Forwarded"),
    FROM("From"),
    IF_MODIFIED_SINCE("If-Modified-Since"),
    IF_RANGE("If-Range"),
    IF_UNMODIFIED_SINCE("If-Unmodified-Since"),
    IF_NONE_MATCH("If-None-Match"),
    CACHE_CONTROL("Cache-Control"),
    VIA("Via"),
    WARNING("Warning"),
    HOST("Host"),
    COOKIE("Cookie"),
    RANGE("Range"),
    DNT("DNT"),
    UNKNOWN("Unknown");

    private String name;

    private HeaderRequest(String name) {
    	this.name = name;
    }

    public String getName() {
    	return name;
    }
    
}