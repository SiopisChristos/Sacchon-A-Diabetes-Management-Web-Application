package com.pfizer.sacchon.resource.constant;

public class Constants {
    public static final String RESPONSE_204 = "The server successfully processed the request, and is not returning any content.";
    public static final String RESPONSE_400 = "The server cannot or will not process the request due to an apparent client error.";
    public static final String RESPONSE_403 = "The request contained valid data and was understood "
    + "by the server, but the server is refusing action. This may be due to the user not having the "
    + "necessary permissions for a resource or needing an account of some sort, or attempting a prohibited action.";
    public static final String RESPONSE_500 = "An unexpected condition was encountered";

    public static final String CODE_200 = "OK";
    public static final String CODE_204 = "No Content";
    public static final String CODE_400 = "Bad Request";
    public static final String CODE_403 = "FORBIDDEN";
    public static final String CODE_500 = "Internal Server Error";
}
