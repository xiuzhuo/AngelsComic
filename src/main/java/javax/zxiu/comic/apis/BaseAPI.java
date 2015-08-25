package javax.zxiu.comic.apis;

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
    private BaseAPI(){

    };

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
