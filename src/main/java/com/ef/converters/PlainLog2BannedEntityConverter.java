package com.ef.converters;

import com.ef.entities.BannedEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Concrete implementation of a converter.
 * Transform an array of strings in a BannedEntity
 */
public class PlainLog2BannedEntityConverter implements ConverterInterface<BannedEntity, String[]> {

    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * This method expects a string array representing the different properties of a BannedEntity
     * entity and returns the entity object.
     * @param input the string array
     * @return the BannedEntity
     * @throws ParseException
     */
    @Override
    public BannedEntity convert(String[] input) throws ParseException {
        BannedEntity e = new BannedEntity();
        e.banned_ip(input[1])
                .start_date(format1.parse(input[0]))
                .requests(1)
                .comment(input[2] + "-" + input[3] + "-" + input[4])
                .run("")
                .end_date(new Date());
        return e;
    }
}
