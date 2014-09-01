package pivotal.au.fe.anzpoc.domain.query.server.util;

public class ObjectOqlConverterUtil {
    public static String toOqlString(Object value) {
        if (value instanceof Long) {
            return Long.valueOf((Long) value) + "L";
        } else if (value instanceof Enum<?>) {
            return ((Enum) value).name();
        } else if (value instanceof Boolean) {
            value.toString();
        }
        return value.toString();
    }
}
