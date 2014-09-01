package pivotal.au.fe.anzpoc.domain.query.server.impl;

import com.gemstone.gemfire.pdx.PdxReader;
import com.gemstone.gemfire.pdx.PdxWriter;
import com.springsource.vfabric.licensing.log.Logger;
import org.apache.commons.lang.builder.ToStringBuilder;
import pivotal.au.fe.anzpoc.domain.query.server.CriteriaException;
import pivotal.au.fe.anzpoc.domain.query.server.ServerCriterion;

public class SimpleExpression implements ServerCriterion {

    private static final Logger logger = Logger.getLogger(SimpleExpression.class);

    private String propertyName;
    private Object value;
    private boolean ignoreCase;
    private String op;

    public SimpleExpression() {

    }

    public SimpleExpression(String propertyName, Object value, String op) {
        super();
        this.propertyName = propertyName;
        this.value = value;
        this.op = op;
    }

    public SimpleExpression(String propertyName, Object value, boolean ignoreCase, String op) {
        super();
        this.propertyName = propertyName;
        this.value = value;
        this.ignoreCase = ignoreCase;
        this.op = op;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Object getValue() {
        return value;
    }

    public String getOp() {
        return op;
    }

    @Override
    public String toOqlString() {
        if (propertyName != null) {
            // extract the property from the object and
            // get the type.
        } else {
            throw new CriteriaException("PropertyName can not be null.");
        }

        // for now...
        boolean eliminateQuote = false;
        boolean isBoolean = false;
        logger.debug("value: " + value);

        if (value != null) {
            if (value instanceof Number) {
                value = value + "L";
                eliminateQuote = true;
            } else if (value instanceof Boolean) {
                eliminateQuote = true;
                isBoolean = true;
            } else if (value instanceof Enum<?>) {
                propertyName += ".toString()";
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        if (eliminateQuote) {
            if (!isBoolean) {
                stringBuilder.append(propertyName).append(" ");
                stringBuilder.append(op);
                stringBuilder.append(" ").append(value);
            } else {
                stringBuilder.append((Boolean) value ? "" : "NOT ").append(propertyName);
            }
            return stringBuilder.toString();
        }
        return stringBuilder.append(propertyName).append(" ").append(op).append(" '").append(value)
                .append("'").toString();
    }

    @Override
    public String toSqlString() {
        if (propertyName != null) {
            // extract the property from the object and
            // get the type.
        } else {
            throw new CriteriaException("PropertyName can not be null.");
        }

        // for now...
        boolean eliminateQuote = false;
        boolean isBoolean = false;
        logger.debug("value: " + value);

        if (value != null) {
            if (value instanceof Number) {
                value = value + "L";
                eliminateQuote = true;
            } else if (value instanceof Boolean) {
                eliminateQuote = true;
                isBoolean = true;
            } else if (value instanceof Enum<?>) {
                propertyName += ".toString()";
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        if (eliminateQuote) {
            if (!isBoolean) {
                stringBuilder.append(propertyName).append(" ");
                stringBuilder.append(op);
                stringBuilder.append(" ").append(value);
            } else {
                stringBuilder.append((Boolean) value ? "" : "NOT ").append(propertyName);
            }
            return stringBuilder.toString();
        }
        return stringBuilder.append(propertyName).append(" ").append(op).append(" '").append(value)
                .append("'").toString();
    }

    @Override
    public void fromData(PdxReader reader) {
        this.propertyName = reader.readString("propertyName");

        Object valueObject = reader.readObject("value");

        if (valueObject instanceof Number) {
            if (((Number) valueObject).longValue() > Integer.MAX_VALUE) {
                //this.value=valueObject+"L";
                this.value = valueObject;
            } else {
                this.value = valueObject;
            }
        } else {
            this.value = valueObject;
        }

        this.ignoreCase = reader.readBoolean("ignoreCase");
        this.op = reader.readString("op");
    }

    @Override
    public void toData(PdxWriter writer) {
        writer.writeString("propertyName", this.propertyName);
        writer.writeObject("value", this.value);
        writer.writeBoolean("ignoreCase", this.ignoreCase);
        writer.writeString("op", this.op);
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
