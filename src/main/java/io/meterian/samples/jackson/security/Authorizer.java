package io.meterian.samples.jackson.security;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Request;
import spark.Response;
import spark.Spark;

public class Authorizer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Authorizer.class);
	private static final int UNATHORIZED_HTTP_CODE = 401;
	private static final List<String> VALID_TOKENS = Arrays.asList(
			"iamvalid",
			"whydoyoutrustme",
			"justanotheradmin",
			"heythere"
			);
	
	private Authorizer() {
		// Hide constructor
	}

	public static void authorize(Request req, Response resp) {
		switch(req.requestMethod()) {
		case "POST":
		case "PUT":
		case "DELETE":
			auth(req);
			break;
		default:
			break;
		}
	}

	private static void auth(Request req) {
		String auth = req.headers("Authorization");
		if(auth != null) {			
			byte[] decoded = Base64.getDecoder().decode(auth);
			String token = new String(decoded, StandardCharsets.UTF_8);
			if(VALID_TOKENS.contains(token)) {
				return;
			}
		}
		LOGGER.warn("Unathorized user");
		Spark.halt(UNATHORIZED_HTTP_CODE, "User unathorized");
	}
	
}
