package com.ef;

import com.ef.entities.BannedEntity;
import com.ef.factories.PersistenceFactory;
import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.Date;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Parser {
    private static Logger logger = LogManager.getLogger(Parser.class.getName());
    public static void main(String[] args) {
        logger.info("Parser started!");
        EntityManager em = null;
        try {
            em = new PersistenceFactory().getEntityManager();
        } catch (IOException e) {
            logger.info("Exception occurred: " + e.getMessage());
            System.exit(0);
        }
        logger.info("Adding new entity");
        BannedEntity b = new BannedEntity().banned_ip("192.168.250.100")
                .comment("A banned ip test")
                .run("java -cp ...")
                .requests(1000)
                .start_date(new Date())
                .end_date(new Date());
        em.getTransaction().begin();
        em.persist(b);
        em.getTransaction().commit();
        em.close();
        logger.info("Entity added");
    }
}
