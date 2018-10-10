package com.ef.factories;

import com.ef.utils.PropertiesLoader;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.Properties;

public class PersistenceFactory {
    private EntityManagerFactory emFactory;
    public PersistenceFactory() throws IOException {
        Properties properties = PropertiesLoader.loadProperties("META-INF/config.properties");
        emFactory = Persistence.createEntityManagerFactory(properties.getProperty("database.persistence.unit"));
    }

    public EntityManager getEntityManager() {
        return emFactory.createEntityManager();
    }

    public void close() {
        emFactory.close();
    }
}
