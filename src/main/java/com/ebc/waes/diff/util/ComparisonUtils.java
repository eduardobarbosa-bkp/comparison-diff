package com.ebc.waes.diff.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Base64;

/**
 * This class is responsible to provide helper methods to handle string
 * @version 1.0
 * @author eduardo.costa
 * @since 06/10/2017
 */
public class ComparisonUtils {

    private ComparisonUtils() {
        throw new IllegalAccessError("Utility class");
    }

    /**
     * Decodes a Base64 encoded String
     * @param src the string to decode
     * @return a new string decoded
     */
    public static String decodeBase64Text(String src){
        if(StringUtils.isEmpty(src)){
            return src;
        }
        return new String(Base64.getDecoder().decode(src));
    }

    /**
     * Encodes the string to Base64
     * @param src the string to encode
     * @return a new string encoded
     */
    public static String encodeBase64Text(String src){
        if(StringUtils.isEmpty(src)){
            return src;
        }
        return Base64.getEncoder().encodeToString(src.getBytes());
    }

}
