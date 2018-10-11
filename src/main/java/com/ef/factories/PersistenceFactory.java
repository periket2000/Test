package com.ef.factories;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.Properties;

/**
 * Persistence Factory. This class is for getting the EntityManager in charge
 * of persisting the Entities.
 */
public class PersistenceFactory {
    private EntityManagerFactory emFactory;
    public PersistenceFactory(Properties properties) throws IOException {
        emFactory = Persistence.createEntityManagerFactory(properties.getProperty("database.persistence.unit"));
    }

    public EntityManager getEntityManager() {
        return emFactory.createEntityManager();
    }

    public void close() {
        emFactory.close();
    }
}
