package io.meterian.samples.jackson.product;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.meterian.samples.jackson.logging.LogMarkers;
import spark.Request;

public class DataSerdes {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataSerdes.class);
	private static final Charset CHARSET = StandardCharsets.UTF_8;
    
    private DataSerdes() {
    	// Hide constructor
    }
	
	public static Object deserialize(String base64) {
        try {
        	ByteArrayInputStream bais = new ByteArrayInputStream(base64.getBytes(CHARSET));
    		ObjectInputStream ois = new ObjectInputStream(bais);
    		return ois.readObject();
        } catch (IOException e) {
            LOGGER.warn("Unable to deserialize data");
            LOGGER.warn(LogMarkers.STACKTRACE, "Unable to deserialize data", e);
		} catch (ClassNotFoundException e) {
			LOGGER.warn("Unable to find class");
			LOGGER.warn(LogMarkers.STACKTRACE, "Unable to find class", e);
		}
        return null;
    }
	
	public static String serialize(Object data) {
        try {
        	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    		ObjectOutputStream oos = new ObjectOutputStream(baos);
    		oos.writeObject(data);
    		return new String(Base64.getEncoder().encode(baos.toByteArray()), CHARSET);
        } catch (IOException e) {
            LOGGER.warn("Unable to serialize data: {}", data);
            LOGGER.warn(LogMarkers.STACKTRACE, "Unable to serialize data: {}", data, e);
        }
        return null;
    }
	
}
