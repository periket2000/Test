package com.ef;

import com.ef.entities.BannedEntity;
import com.ef.factories.PersistenceFactory;
import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.Date;

import com.ef.services.log_parse.piped_file_log_parse.PipedFileLogParseService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Parser {
    private static Logger logger = LogManager.getLogger(Parser.class.getName());
    public static void main(String[] args) throws IOException {
        logger.info("Parser started!");
        final EntityManager em = new PersistenceFactory().getEntityManager();
        logger.info("Adding new entries");
        PipedFileLogParseService service = new PipedFileLogParseService();
        try {
            em.getTransaction().begin();
            service.parse(null).stream().forEach(a -> {
                em.persist(a);
            });
            em.getTransaction().commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        logger.info("Entities added");
    }
}
