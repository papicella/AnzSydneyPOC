package pivotal.au.fe.anzpoc.domain.query;

import com.gemstone.gemfire.pdx.PdxReader;
import com.gemstone.gemfire.pdx.PdxSerializable;
import com.gemstone.gemfire.pdx.PdxWriter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import pivotal.au.fe.anzpoc.domain.query.client.impl.CriteriaImpl;
import pivotal.au.fe.anzpoc.domain.query.common.Criteria;

public class CriteriaQueryMessage implements PdxSerializable {

  private String dataType;
	// this needs to be the client criterion
	private Criteria criteria;

	public CriteriaQueryMessage() {
		super();
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Criteria getCriteria() {
		return criteria;
	}

	public void setCriteria(Criteria clientCriteria) {
		this.criteria = clientCriteria;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public void fromData(PdxReader reader) {
		this.dataType = reader.readString("dataType");
		this.criteria = (CriteriaImpl) reader
				.readObject("criteria");
	}

	@Override
	public void toData(PdxWriter writer) {
		writer.writeString("dataType", dataType);
		writer.writeObject("criteria", criteria);
	}

}
