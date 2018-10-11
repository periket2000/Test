package com.ef.services.logparse.pipedfile;

import com.ef.converters.PlainLog2RequestEntityConverter;
import com.ef.entities.RequestsEntity;
import com.ef.services.logparse.LogParseInterface;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Concrete implementation of the parser.
 */
public class PipedFileLogParseRequestService implements LogParseInterface<List<RequestsEntity>, InputStream> {

    private static Logger logger = LogManager.getLogger(PipedFileLogParseRequestService.class.getName());

    /**
     * Parse a file passed as parameter
     * @param input the input file.
     * @return the list of entities.
     */
    public List<RequestsEntity> parse(InputStream input) {
        List<RequestsEntity> result;
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        result = br.lines().collect(Collectors.toList()).stream().map(line -> {
            String[] fields = line.split("\\|");
            try {
                return new PlainLog2RequestEntityConverter().convert(fields);
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
    public List<RequestsEntity> parse(InputStream input, Map<String, Object> criteria) {
        final String startDateLiteral = "startDate";
        return this.parse(input).stream().filter(element -> {
            Date startDate = (Date) criteria.get(startDateLiteral);
            Date endDate = (Date) criteria.get("endDate");
            return element.getRequest_date().after(startDate) && element.getRequest_date().before(endDate);
        }).collect(Collectors.toList());
    }
}
