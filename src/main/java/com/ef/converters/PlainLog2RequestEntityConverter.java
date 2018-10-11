package com.ef.converters;

import com.ef.entities.RequestsEntity;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Concrete implementation of a converter.
 * Transform an array of strings in a RequestEntity
 */
public class PlainLog2RequestEntityConverter implements ConverterInterface<RequestsEntity, String[]> {

    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * This method expects a string array representing the different properties of a RequestEntity
     * entity and returns the entity object.
     * @param input the string array
     * @return the RequestEntity
     * @throws ParseException
     */
    @Override
    public RequestsEntity convert(String[] input) throws ParseException {
        RequestsEntity e = new RequestsEntity();
        e.ip(input[1])
                .request_date(format1.parse(input[0]))
                .request(input[2])
                .request_status(input[3])
                .request_agent(input[4]);
        return e;
    }
}
