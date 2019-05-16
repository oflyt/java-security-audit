package io.meterian.samples.jackson.product;

import java.util.Collection;

import spark.Request;
import spark.Response;

public class ProductApi {
	
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

	public static String updateData(Request request, Response response) {
    	String id = request.params(":id");
    	String data = request.body();
    	Object dataDeserialized = DataSerdes.deserialize(data);

		if (dataDeserialized != null) {
			Product product = products.findById(id);
			if (product != null) {
				product.setData(dataDeserialized);
				Object res = products.update(id, product);
				response.status(HTTP_CODE_ACCEPTED);
				return DataSerdes.serialize(res);
			}
		}
		response.status(HTTP_CODE_BAD_REQUEST);
		return "Invalid content";
	}

	public static String getProducts(Request request, Response response) {
		Collection<Product> res = products.list();
		return ProductSerdes.serialize(res);
	}
	
}
