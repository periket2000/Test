package com.ef.converters;

import java.text.ParseException;

/**
 * Generic Interface
 * @param <T1> Return object
 * @param <T2> Input object
 */
public interface ConverterInterface <T1 extends Object, T2 extends Object> {
    public T1 convert(T2 input) throws ParseException;
}
