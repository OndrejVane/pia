package com.vane.pia.utils;

import com.vane.pia.domain.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RoleConverter implements Converter<String, Role> {
    @Override
    public Role convert(String s) {
        log.info("Converter");
        return null;
    }
}
