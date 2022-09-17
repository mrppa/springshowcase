package com.mrppa.showcase;

import java.io.IOException;
import java.util.Map;

import javax.persistence.AttributeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HashMapConverter implements AttributeConverter<Map<String, Object>, String> {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(Map<String, Object> customerInfo) {

		String customerInfoJson = null;
		try {
			customerInfoJson = objectMapper.writeValueAsString(customerInfo);
		} catch (final JsonProcessingException e) {
			logger.error("JSON writing error", e);
		}

		return customerInfoJson;
	}

	@Override
	public Map<String, Object> convertToEntityAttribute(String customerInfoJSON) {

		Map<String, Object> customerInfo = null;
		try {
			customerInfo = objectMapper.readValue(customerInfoJSON, Map.class);
		} catch (final IOException e) {
			logger.error("JSON reading error", e);
		}

		return customerInfo;
	}

}