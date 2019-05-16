package io.meterian.samples.jackson.product;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.core.net.SyslogOutputStream;
import net.jcip.annotations.Immutable;

@Immutable
public class Product {
 
    private int id;
    private String name;
    private String description;
    private Object data;
 
    protected Product() {
    }

    public Product(int id, String name, String description, Object data) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.data = data;
    }

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", description=" + description + ", data=" + data + "]";
    }
    
    public Product duplicate(int id) {
        return new Product(
            id,
            this.name,
            this.description,
            this.data);                
    }
    
}
