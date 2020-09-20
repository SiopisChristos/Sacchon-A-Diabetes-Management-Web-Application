package com.pfizer.sacchon.resource.util;

import com.pfizer.sacchon.exception.NotAuthorizedException;
import com.pfizer.sacchon.model.Doctor;
import org.restlet.Request;
import org.restlet.security.User;

import javax.print.Doc;

public class ResourceAuthorization {

    public static void equalsUsername(String username1, String username2) throws NotAuthorizedException{
        if (!username1.equals(username2)){
            throw new NotAuthorizedException("Only patient can alter the notification");
        }
    }


    public static String currentUserToUsername(){
        User currentSystemUsername;
        Request request = Request.getCurrent();
        currentSystemUsername = request.getClientInfo().getUser();
        return currentSystemUsername.toString();
    }


    /**
     * Checks if the connected user, it's the same with a username
     * @param username
     * @return
     */
    public static boolean userAuthorization(String username) {
        if (currentUserToUsername().equals(username))
            return true;
        return false;
    }

    /**
     * A method for throwing a NotAuthorizedException in the case which
     * the input_username it's not equal with the connected user
     * @param input_username
     * @throws NotAuthorizedException
     */
    public static void checkUserAuthorization(String input_username) throws NotAuthorizedException{
        boolean authorized =
                ResourceAuthorization.userAuthorization(input_username);
        if (!authorized) {
            throw new NotAuthorizedException("Access: DENIED!");
        }
    }






}
