package io.meterian.samples.jackson;

import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.port;
import static spark.Spark.post;

import org.slf4j.MDC;

import io.meterian.samples.jackson.logging.CommunicationLogger;
import io.meterian.samples.jackson.product.ProductApi;
import io.meterian.samples.jackson.security.Authorizer;

public class Main {

	private static final int HTTP_PORT = 8888;

	public static void main(String[] args) {
    	path("/products", () -> {
    		port(HTTP_PORT);
    		
    		before("", (req, resp) -> MDC.put("sessionId", req.session().id()));
    		before("", CommunicationLogger::before);
    		before("", Authorizer::authorize);
    		
    		get("", ProductApi::getProducts);
    		post("", ProductApi::addProduct);
    		post("/:id", ProductApi::updateData);
    		
    		after("", CommunicationLogger::after);
    		after("", (req, resp) -> MDC.clear());
    	});
    }

	

}
