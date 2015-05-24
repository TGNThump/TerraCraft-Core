package uk.co.terragaming.code.terracraft.utils;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import com.google.common.base.Charsets;

public class AttributeUtil {
	public static UUID computeUUID(String id){
		try {
		    final byte[] input = ("5jNT9hjG64C431gOmv0l5hqQaEdueX3lnnUW1Rm2" + id).getBytes(Charsets.UTF_8);
		    final ByteBuffer output = ByteBuffer.wrap(MessageDigest.getInstance("MD5").digest(input));
		     
		    return new UUID(output.getLong(), output.getLong());
		     
	    } catch (NoSuchAlgorithmException e) {
	    throw new IllegalStateException("Current JVM doesn't support MD5.", e);
	    }
	}
}
