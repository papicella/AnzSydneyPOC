package pivotal.au.fe.anzpoc.domain.query.server.expression;

import com.gemstone.gemfire.pdx.PdxReader;
import com.gemstone.gemfire.pdx.PdxWriter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import pivotal.au.fe.anzpoc.domain.query.common.MatchMode;
import pivotal.au.fe.anzpoc.domain.query.server.CriteriaException;
import pivotal.au.fe.anzpoc.domain.query.server.ServerCriterion;

/**
 * Only supporting strings as doesn't really make sense for booleans or number
 */
public class LikeExpression implements ServerCriterion {

    private String propertyName;
    private String value;
    private MatchMode matchMode;

    public LikeExpression(String propertyName, String value, MatchMode matchMode) {
        this.propertyName = propertyName;
        if (value == null) {
            throw new CriteriaException("Value can not be null. propertyname: " + propertyName);
        }
        this.value = value;
        this.matchMode = (matchMode == null ? MatchMode.ANYWHERE : matchMode);
    }

    protected LikeExpression(String propertyName, String value) {
        this(propertyName, value, MatchMode.ANYWHERE);
    }

    /**
     * TODO: bind params? utilize meta data.
     */
    @Override
    public String toOqlString() {
        return propertyName + " LIKE '" + matchMode.toMatchString(value.toString()) + "'";

    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {

        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public void fromData(PdxReader pdxReader) {
        this.propertyName = pdxReader.readString("propertyName");
        this.value = pdxReader.readString("value");
        this.matchMode = (MatchMode) pdxReader.readObject("matchMode");
    }

    @Override
    public void toData(PdxWriter pdxWriter) {
        pdxWriter.writeString("propertyName", this.propertyName);
        pdxWriter.writeString("value", this.value);
        pdxWriter.writeObject("matchMode", this.matchMode);
    }
}
