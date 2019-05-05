package io.meterian.samples.jackson.security;

import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Request;
import spark.Response;
import spark.Session;

public class AuditLogger {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuditLogger.class);
	
	private AuditLogger() {
		// Hide constructor
	}

	public static void mdcBefore(Request request, Response response) {
		if(LOGGER.isDebugEnabled()) {			
			Session session = request.session();
			LOGGER.debug("Request session creation time: {}", session.creationTime());
			LOGGER.debug("Request ip: {}", request.ip());
			LOGGER.debug("Request headers: {}", mapToString(headersMap(request)));
			LOGGER.debug("Request cookies: {}", mapToString(request.cookies()));
			LOGGER.debug("Request body: {}", request.body());
		}
    }

    
    public static void mdcAfter(Request request, Response response) {
    	if(LOGGER.isDebugEnabled()) {			
			LOGGER.debug("Response code: {}", response.status());
			LOGGER.debug("Response body: {}", response.body());
		}
    }

    private static String mapToString(Map<String, String> map) {
    	return map.entrySet().stream()
    			.map(entry -> String.format("\"%s\": \"%s\"", entry.getKey(), entry.getValue()))
    			.collect(Collectors.joining(", ", "{", "}"));
    }
    
    private static Map<String, String> headersMap(Request request) {
    	return request.headers().stream()
    			.collect(Collectors.toMap(key -> key, request::headers));
    }
	
}
