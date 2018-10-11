package com.ef;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ParserTest {

    @Test
    void run() throws IOException {
        Parser p = new Parser();
        Assertions.assertNotNull(p);
    }
}