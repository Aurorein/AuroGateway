package hiwth.cxn.gateway.core.type;

import javax.lang.model.element.TypeParameterElement;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SimpleTypeRegistry {

    private final static Set<String> TYPE_SET = new HashSet<>();

    static{
        TYPE_SET.add(String.class.getName());
        TYPE_SET.add(Integer.class.getName());
        TYPE_SET.add(Short.class.getName());
        TYPE_SET.add(Character.class.getName());
        TYPE_SET.add(Integer.class.getName());
        TYPE_SET.add(Long.class.getName());
        TYPE_SET.add(Float.class.getName());
        TYPE_SET.add(Double.class.getName());
        TYPE_SET.add(Boolean.class.getName());
        TYPE_SET.add(Date.class.getName());
        TYPE_SET.add(Class.class.getName());
        TYPE_SET.add(BigInteger.class.getName());
        TYPE_SET.add(BigDecimal.class.getName());

    }

    public static boolean isSimpleType(String type) {
        return TYPE_SET.contains(type);
    }

    private SimpleTypeRegistry() {}


}
