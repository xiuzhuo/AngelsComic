package javax.zxiu.comic.apis;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * Created by Xiu on 2015/8/25.
 */
public abstract class BaseAPI {
    BaseAPI() {

    }
    public static String base64(byte[] target) {
        return Base64.encodeBase64URLSafeString(target).trim();
    }

    public static String hmac_sha1(String value, String key) {
        try {
            byte[] keyBytes = key.getBytes();
            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(value.getBytes());
            return new String(rawHmac).trim();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    protected static class Param {
        String name;
        Object value;

        public Param(String name) {
            this(name, null);
        }

        public Param(String name, Object value) {
            this.name = name;
            setValue(value);
        }

        public Param setValue(Object value) {
            this.value = value;
            return this;
        }

        public String getName() {
            return name;
        }

        public Param setName(String name) {
            this.name = name;
            return this;
        }

        public Object getValue() {
            return value;
        }
    }
}
