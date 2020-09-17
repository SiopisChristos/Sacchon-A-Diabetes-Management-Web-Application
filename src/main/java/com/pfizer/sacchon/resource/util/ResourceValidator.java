package com.pfizer.sacchon.resource.util;

import com.pfizer.sacchon.exception.BadEntityException;
import org.restlet.Request;
import org.restlet.resource.ServerResource;
import org.restlet.security.User;

import java.util.Optional;
import java.util.logging.Logger;

public class ResourceValidator {


    public static boolean userAuthorization(String username) {
        User currentSystemUsername;
        Request request = Request.getCurrent();
        currentSystemUsername = request.getClientInfo().getUser();
        System.out.println("USer" + currentSystemUsername);
        if (currentSystemUsername.toString().equals(username))
            return true;

        return false;
    }


    public static <T> T getFromOptionalEntityById(Optional<T> entity, ServerResource serverResource, Logger LOGGER) throws BadEntityException {
        Class<?> type = null;
        serverResource.setExisting(entity.isPresent());
        if (!serverResource.isExisting()) {
            type = entity.get().getClass();
            System.out.println(type.toString() + " id does not exist");
            LOGGER.config(type.toString() + " id does not exist");
            throw new BadEntityException(type.toString() + " id does not exist");
        }
        return entity.get();
    }

}
