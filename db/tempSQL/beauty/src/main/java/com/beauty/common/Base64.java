package com.beauty.common;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import javax.xml.bind.DatatypeConverter;

public final class Base64 {

	public static Optional<InputStream> Base64InputStream(String base64String)throws IOException {
	    String imageDataBytes = base64String.substring(base64String.indexOf(",")+1);
	    return Optional.ofNullable(new ByteArrayInputStream(DatatypeConverter.parseBase64Binary(imageDataBytes)));
	}
	
}
