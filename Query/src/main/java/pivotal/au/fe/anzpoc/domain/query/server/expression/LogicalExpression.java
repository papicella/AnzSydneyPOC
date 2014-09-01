package pivotal.au.fe.anzpoc.domain.query.server.expression;

import com.gemstone.gemfire.pdx.PdxReader;
import com.gemstone.gemfire.pdx.PdxWriter;
import pivotal.au.fe.anzpoc.domain.query.server.ServerCriterion;

public class LogicalExpression implements ServerCriterion {

	private  ServerCriterion lhs;
	private  ServerCriterion rhs;
	private  String op;
	
	

	public LogicalExpression() {
		super();
	}

	public LogicalExpression(ServerCriterion lhs, ServerCriterion rhs, String op) {
		this.lhs = lhs;
		this.rhs = rhs;
		this.op = op;
	}

	// public String toOqlString(Criteria criteria, CriteriaQuery
	// criteriaQuery){
	public String toOqlString() {
		return '(' + lhs.toOqlString() + ' ' + getOp() + ' '
				+ rhs.toOqlString() + ')';
	}

	public String getOp() {
		return op;
	}

	public String toString() {
		return lhs.toString() + ' ' + getOp() + ' ' + rhs.toString();
	}

	@Override
	public void fromData(PdxReader reader) {
		   this.lhs = (ServerCriterion)reader.readObject("lhs");
           this.rhs = (ServerCriterion)reader.readObject("rhs");
           this.op = reader.readString("op");

	}

	@Override
	public void toData(PdxWriter writer) {
	     writer.writeObject("lhs", lhs);
         writer.writeObject("rhs", rhs);
         writer.writeString("op", this.op);
	}

}
