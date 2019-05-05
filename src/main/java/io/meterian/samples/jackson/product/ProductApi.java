package io.meterian.samples.jackson.product;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Request;
import spark.Response;

public class ProductApi {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductApi.class);

	private static final int HTTP_CODE_ACCEPTED = 201;
	private static final int HTTP_CODE_BAD_REQUEST = 400;
    
    private static ProductsDatabase products = new ProductsDatabase();
    
    private ProductApi() {
    	// Hide constructor
    }

	public static String addProduct(Request request, Response response) {
		Product product = ProductSerdes.deserialize(request);
		if (product != null) {
			Product res = products.add(product);
			response.status(HTTP_CODE_ACCEPTED);
			return ProductSerdes.serialize(res);
		} else {
			response.status(HTTP_CODE_BAD_REQUEST);
			return "Invalid content";
		}
	}

	public static String getProducts(Request request, Response response) {
		Collection<Product> res = products.list();
		LOGGER.info("/products -> {}", res);
		return ProductSerdes.serialize(res);
	}
	
}
