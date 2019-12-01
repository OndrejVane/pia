package com.vane.pia.utils.generator;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.nio.file.LinkOption;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class GeneratorTest {

    @Test
    void generateUsername() {
        String generatedUsername = Generator.generateUsername();
        assertEquals(8, generatedUsername.length());
        assertTrue(Pattern.matches("[a-zA-Z0-9]+", generatedUsername));
    }

    @Test
    void generatePassword() {
        String generatedPassword = Generator.generatePassword();
        assertEquals(4, generatedPassword.length());
        assertTrue(isNumeric(generatedPassword));
    }
}