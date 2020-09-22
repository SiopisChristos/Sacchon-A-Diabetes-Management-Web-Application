package com.pfizer.sacchon.repository.util;

import com.pfizer.sacchon.exception.BadEntityException;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.logging.Logger;

public class EntityUtil {

    /**
     * A generic method for finding an Entity Object by its id
     *
     * @param entity
     * @param entity_id
     * @param <T>
     * @return Optional of the Entity if it's not Null
     */
    public static  <T> Optional<T> findEntityById(T entity, EntityManager entityManager, long entity_id) {
        entity = (T) entityManager.find(entity.getClass(), entity_id);
        return (entity != null) ? Optional.of(entity) : Optional.empty();
    }


    /**
     * @param entity         can be any Optional Model Object (Carb, Doctor, Glucose, Note, Patient)
     * @param serverResource
     * @param LOGGER
     * @param <T>   the class of the entity
     * @return the Model Object if it's not Null
     * @throws BadEntityException if there is not such an Entity
     */
    public static <T> T getFromOptionalEntity(Optional<T> entity, ServerResource serverResource, Logger LOGGER) throws BadEntityException {
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
