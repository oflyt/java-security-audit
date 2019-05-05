package io.meterian.samples.jackson.product;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.meterian.samples.jackson.logging.LogMarkers;
import spark.Request;

public class ProductSerdes {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductSerdes.class);
	
	private static ObjectMapper deserializer = new ObjectMapper().enableDefaultTyping();
    private static ObjectMapper serializer = new ObjectMapper();
    
    private ProductSerdes() {
    	// Hide constructor
    }
	
	public static Product deserialize(Request request) {
        try {
            return deserializer.readValue(request.body(), Product.class);
        } catch (IOException e) {
            LOGGER.warn("Unable to deserialize product");
            LOGGER.warn(LogMarkers.STACKTRACE, "Unable to deserialize product", e);
		}
        return null;
    }
	
	public static String serialize(Product product) {
        try {
            return serializer.writeValueAsString(product);
        } catch (IOException e) {
            LOGGER.warn("Unable to serialize product: {}", product);
            LOGGER.warn(LogMarkers.STACKTRACE, "Unable to serialize product: {}", product, e);
        }
        return null;
    }
	
	public static String serialize(Collection<Product> products) {
        try {
            return serializer.writeValueAsString(products);
        } catch (IOException e) {
            String productsStr = Arrays.toString(products.toArray());
			LOGGER.warn("Unable to serialize products: {}", productsStr);
            LOGGER.warn(LogMarkers.STACKTRACE, "Unable to serialize products: {}", productsStr, e);
        }
        return null;
    }
}
