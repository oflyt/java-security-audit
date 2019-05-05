package io.meterian.samples.jackson.logging;

import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Request;
import spark.Response;
import spark.Session;

public class CommunicationLogger {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CommunicationLogger.class);
	
	private CommunicationLogger() {
		// Hide constructor
	}

	public static void before(Request request, Response response) {
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("{} {}", request.requestMethod(), request.pathInfo());
		} 
		if (LOGGER.isDebugEnabled()) {			
			Session session = request.session();
			LOGGER.debug(LogMarkers.AUDIT, "Request session creation time: {}", session.creationTime());
			LOGGER.debug(LogMarkers.AUDIT, "Request ip: {}", request.ip());
			LOGGER.debug(LogMarkers.AUDIT, "Request headers: {}", mapToString(headersMap(request)));
			LOGGER.debug(LogMarkers.AUDIT, "Request cookies: {}", mapToString(request.cookies()));
			LOGGER.debug(LogMarkers.AUDIT, "Request body: {}", request.body());
		}
    }

    
    public static void after(Request request, Response response) {
    	if(LOGGER.isInfoEnabled()) {
			LOGGER.info("Response code: {}", response.status());
		} 
    	if(LOGGER.isDebugEnabled()) {			
			LOGGER.debug(LogMarkers.AUDIT, "Response body: {}", response.body());
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
