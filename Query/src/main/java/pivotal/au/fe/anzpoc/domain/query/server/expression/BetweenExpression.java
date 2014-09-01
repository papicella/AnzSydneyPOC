package pivotal.au.fe.anzpoc.domain.query.server.expression;

import com.gemstone.gemfire.pdx.PdxReader;
import com.gemstone.gemfire.pdx.PdxWriter;
import pivotal.au.fe.anzpoc.domain.query.server.ServerCriterion;
import pivotal.au.fe.anzpoc.domain.query.server.util.ObjectOqlConverterUtil;

public class BetweenExpression implements ServerCriterion {

	private final String propertyName;
	private final Object lo;
	private final Object hi;

	public BetweenExpression(String propertyName, Object lo, Object hi) {
		this.propertyName = propertyName;
		this.lo = lo;
		this.hi = hi;
	}

	@Override
	public String toOqlString() {

		// TODO come back.. why multiple prop names?
		// need bind parameters?

		// String[] propertyNames = new String[1];
		// propertyNames[0] = propertyName;
		return " (" + propertyName + " >= " + ObjectOqlConverterUtil.toOqlString(lo) + " AND " + propertyName
				+ " <= " + ObjectOqlConverterUtil.toOqlString(hi) + ") ";

	}

	@Override
	public void fromData(PdxReader pdxReader) {
	  pdxReader.readString("propertyName");
	  pdxReader.readObject("lo");
	  pdxReader.readObject("hi");

	}

	@Override
	public void toData(PdxWriter pdxWriter) {
		pdxWriter.writeString("propertyName", this.propertyName);
		pdxWriter.writeObject("lo",this.lo);
		pdxWriter.writeObject("hi", this.hi);

	}

}
