package pivotal.au.fe.anzpoc.domain.query.server.expression;

import com.gemstone.gemfire.pdx.PdxReader;
import com.gemstone.gemfire.pdx.PdxWriter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import pivotal.au.fe.anzpoc.domain.query.server.ServerCriterion;

/**
 * 
 * Bind parameters not yet supported.
 * http://www.vmware.com/support/developer/vfabric
 * -gemfire/661-api/com/gemstone/gemfire/cache/query/QueryService.html
 * 
 */
public class InExpression implements ServerCriterion {

	private  String propertyName;
	private  Object[] values;
	

	public InExpression() {
		super();
	}

	public InExpression(String propertyName, Object[] values) {
		this.propertyName = propertyName;
		this.values = values;
	}

	public String getPropertyName() {
		return propertyName;
	}

	@Override
	public String toOqlString() {

		StringBuilder stringBuilder = new StringBuilder();

		

		boolean first = true;
		int length = values.length;
		boolean appendComma = (length > 1);
		for (int i = 0; i < values.length; i++) {
			Object object = values[i];
		
			if (object instanceof Number) {
			  if (first) {
			    stringBuilder.append(propertyName + ".toString() IN SET (");
			    first = false;
			  }
			  
				// not working. could pull type and do this.
				// stringBuilder.append("(java.math.BigDecimal) " + object);
				// stringBuilder.append("new java.math.BigDecimal(\"" + object +
				// "\")");
				// works for long but not scalable.
				// stringBuilder.append(object + "L");
				stringBuilder.append("'" + object + "'");
			} else {
			  if (first) {
			    stringBuilder.append(propertyName + " IN SET (");
          first = false;
        }
			  
				stringBuilder.append("'" + object + "'");
			}
			if (appendComma && (length != i + 1)) {
				stringBuilder.append(",");
			}
		}
		stringBuilder.append(") ");
		return stringBuilder.toString();

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
	public void fromData(PdxReader reader) {
		this.propertyName=reader.readString("propertyName");
		this.values=reader.readObjectArray("values");
	}

	@Override
	public void toData(PdxWriter writer) {
		writer.writeString("propertyName", this.propertyName);
		writer.writeObjectArray("values", this.values);

	}

}
