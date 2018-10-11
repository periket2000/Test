package com.ef.services.logparse;

/**
 * Generic Interface
 * @param <T1> Return object
 * @param <T2> Input object
 */
@FunctionalInterface
public interface LogParseInterface <T1 extends Object, T2 extends Object> {
    public T1 parse(T2 input);
}
