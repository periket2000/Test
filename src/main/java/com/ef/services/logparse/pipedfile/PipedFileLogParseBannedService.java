package com.ef.services.logparse.pipedfile;

import com.ef.converters.PlainLog2BannedEntityConverter;
import com.ef.entities.BannedEntity;
import com.ef.services.logparse.LogParseInterface;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

/**
 * Concrete implementation of the parser.
 */
public class PipedFileLogParseBannedService implements LogParseInterface<List<BannedEntity>, InputStream> {

    private static Logger logger = LogManager.getLogger(PipedFileLogParseBannedService.class.getName());

    /**
     * Parse a file passed as parameter
     * @param input the input file.
     * @return the list of entities.
     */
    public List<BannedEntity> parse(InputStream input) {
        List<BannedEntity> result;
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        result = br.lines().collect(Collectors.toList()).stream().map(line -> {
            String[] fields = line.split("\\|");
            try {
                return new PlainLog2BannedEntityConverter().convert(fields);
            } catch (ParseException e) {
                logger.info(e);
                return null;
            }
        }).collect(Collectors.toList());
        return result;
    }

    /**
     * Parse a file passed as parameter
     * @param input the input file.
     * @param criteria the criteria coming from the command line options.
     * @return
     */
    public List<BannedEntity> parse(InputStream input, Map<String, Object> criteria) {
        final String startDateLiteral = "startDate";
        int threshold = (Integer) criteria.get("threshold");
        return this.parse(input).stream().filter(element -> {
            Date startDate = (Date) criteria.get(startDateLiteral);
            Date endDate = (Date) criteria.get("endDate");
            return element.getStart_date().after(startDate) && element.getStart_date().before(endDate);
        }).collect(Collectors.toList()).stream()
                .collect(groupingBy(BannedEntity::getBanned_ip, summingInt(BannedEntity::getRequests)))
                .entrySet().stream()
                .filter(x -> x.getValue() > threshold)
                .map(x -> {
                    System.out.println("Banned: " + x.getKey());
                    return new BannedEntity().banned_ip(x.getKey())
                            .requests(x.getValue())
                            .start_date((Date) criteria.get(startDateLiteral))
                            .run("--startDate=" + ((Date) criteria.get(startDateLiteral)).toString()
                                    + " --duration=" + criteria.get("duration")
                                    + " --threshold=" + threshold)
                            .comment("IP requested more than "
                                    + threshold
                                    + " requests "
                                    + " on a " + criteria.get("duration") + " basis.")
                            .end_date(new Date());
                }).collect(Collectors.toList());
    }
}
