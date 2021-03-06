package com.ef;

import com.ef.entities.BannedEntity;
import com.ef.entities.RequestsEntity;
import com.ef.factories.PersistenceFactory;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;
import com.ef.services.logparse.pipedfile.PipedFileLogParseBannedService;
import com.ef.services.logparse.pipedfile.PipedFileLogParseRequestService;
import com.ef.utils.CommandLineParser;
import com.ef.utils.PropertiesLoader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * This class configure the environment and run the parsing and loading process.
 */
public class Parser {
    private Properties properties;
    private static Logger logger = LogManager.getLogger(Parser.class.getName());
    private InputStream input;
    private InputStream input2;
    private String propertiesFile;

    /**
     * Constructor. Build and initialize the parser.
     * @throws IOException
     */
    public Parser() throws IOException {
        try {
            // Dynamic configuration file location (remember add the directory to the classpath)
            this.propertiesFile = System.getenv("WALLETHUB_PARSER_PROPERTIES_FILE");
            final String inputFileProp = "parser.input.file";
            if (null == this.propertiesFile) {
                this.propertiesFile = "META-INF/config.properties";
                this.properties = PropertiesLoader.loadProperties(this.propertiesFile);
                this.input = Parser.class.getClassLoader().getResourceAsStream(properties.getProperty(inputFileProp));
                this.input2 = Parser.class.getClassLoader().getResourceAsStream(properties.getProperty(inputFileProp));
            } else {
                this.properties = new Properties();
                this.properties.load(new FileInputStream(this.propertiesFile));
                this.input = new FileInputStream(properties.getProperty(inputFileProp));
                this.input2 = new FileInputStream(properties.getProperty(inputFileProp));
            }

            // Update the log file destination
            String logFile = properties.getProperty("logging.file");
            if (null != logFile) {
                InputStream configStream = getClass().getResourceAsStream("/log4j.properties");
                Properties props = new Properties();
                props.load(configStream);
                props.setProperty("log4j.appender.file.File", logFile);
                PropertyConfigurator.configure(props);
            }
        } catch (Exception e) {
            logger.info(e);
            throw e;
        }
    }

    /**
     * Extract, parse and load the entities in the DB.
     * @param options Command line options.
     * @throws IOException
     */
    public void run(Map<String, Object> options) throws IOException{
        logger.info("Parser started!");
        final EntityManager em = new PersistenceFactory(this.properties).getEntityManager();
        PipedFileLogParseBannedService banService = new PipedFileLogParseBannedService();
        PipedFileLogParseRequestService rawService = new PipedFileLogParseRequestService();
        String logPath = (String)options.get("accesslog");
        if(logPath != null) {
            this.input = new FileInputStream(logPath);
            this.input2 = new FileInputStream(logPath);
        }
        try {
            em.getTransaction().begin();

            if(Boolean.valueOf(properties.getProperty("database.truncate.table"))) {
                // Clear table on each run
                CriteriaBuilder builder = em.getCriteriaBuilder();
                CriteriaDelete<BannedEntity> q = builder.createCriteriaDelete(BannedEntity.class);
                q.from(BannedEntity.class);
                em.createQuery(q).executeUpdate();
                CriteriaDelete<RequestsEntity> q2 = builder.createCriteriaDelete(RequestsEntity.class);
                q2.from(RequestsEntity.class);
                em.createQuery(q2).executeUpdate();
            }

            // Run the parsing and aggregation
            logger.info("Writing banned IPs");
            banService.parse(this.input, options).stream().forEach(a ->
                em.persist(a)
            );

            // Run the parsing and raw store
            logger.info("Writing raw requests");
            rawService.parse(this.input2, options).stream().forEach(a ->
                    em.persist(a)
            );

            em.getTransaction().commit();
        } catch (Exception e) {
            logger.info(e);
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
        logger.info("Entities added");
    }

    /**
     * Main entry point.
     * @param args
     * @throws IOException
     * @throws ParseException
     */
    public static void main(String[] args) throws IOException, ParseException {
        Parser p = new Parser();
        Map<String, Object> options = CommandLineParser.parseCommandLine(args);
        p.run(options);
    }
}
