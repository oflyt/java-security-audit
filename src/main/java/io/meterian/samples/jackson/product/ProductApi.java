package io.meterian.samples.jackson.product;

import java.util.Arrays;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import spark.Request;
import spark.Response;

public class ProductApi {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductApi.class);

	private static final int HTTP_CODE_ACCEPTED = 201;
	private static final int HTTP_CODE_BAD_REQUEST = 400;
    
    private static ProductsDatabase products = new ProductsDatabase();
    private static ObjectMapper deserializer = new ObjectMapper().enableDefaultTyping();
    private static ObjectMapper serializer = new ObjectMapper();
    
    private ProductApi() {
    	// Hide constructor
    }

	public static String addProduct(Request request, Response response) {
		Product product = deserialize(request);
		if (product != null) {
			Product res = products.add(product);
			response.status(HTTP_CODE_ACCEPTED);
			return serialize(res);
		} else {
			response.status(HTTP_CODE_BAD_REQUEST);
			return "Invalid content";
		}
	}

	public static String getProducts(Request request, Response response) {
		Collection<Product> res = products.list();
		LOGGER.info("/products -> {}", res);
		return serialize(res);
	}
	
	private static Product deserialize(Request request) {
        try {
            return deserializer.readValue(request.body(), Product.class);
        } catch (Exception any) {
            LOGGER.warn("Unexpected exception deserializing content: {}", any.getClass());
            return null;
        }
    }
	
	private static String serialize(Product product) {
        try {
            return serializer.writeValueAsString(product);
        } catch (Exception any) {
            LOGGER.warn("Unexpected exception serializing content: {}", product);
            return null;
        }
    }
	
	private static String serialize(Collection<Product> products) {
        try {
            return serializer.writeValueAsString(products);
        } catch (Exception any) {
            LOGGER.warn("Unexpected exception serializing content: {}", Arrays.toString(products.toArray()));
            return null;
        }
    }
	
}
