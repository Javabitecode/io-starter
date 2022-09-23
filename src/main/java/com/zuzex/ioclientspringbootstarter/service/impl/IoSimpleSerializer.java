package com.zuzex.ioclientspringbootstarter.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuzex.ioclientspringbootstarter.exception.IoDeserializationException;
import com.zuzex.ioclientspringbootstarter.service.IoSerializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.zuzex.ioclientspringbootstarter.service.impl.IoCacheManager.ioReferenceList;

@Slf4j
@RequiredArgsConstructor
public class IoSimpleSerializer implements IoSerializer {
    private final ObjectMapper objectMapper;

    public Object deserialization(String obj) {
        for (var ref : ioReferenceList) {
            try {
                var successDeserialization = objectMapper.readValue(obj, ref);
                log.info("Success deserialization of the value: {}", successDeserialization);
                return successDeserialization;
            } catch (Exception ex) {
                log.debug("Incorrect TypeReference. Exception: ", ex);
                log.info("Stub in deserialization");
            }
        }
        throw new IoDeserializationException("TypeReference do not found");
    }

    public String writeValueAsString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
