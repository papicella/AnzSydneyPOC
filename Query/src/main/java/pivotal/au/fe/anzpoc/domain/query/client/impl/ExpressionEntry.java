package pivotal.au.fe.anzpoc.domain.query.client.impl;

import com.gemstone.gemfire.pdx.PdxReader;
import com.gemstone.gemfire.pdx.PdxSerializable;
import com.gemstone.gemfire.pdx.PdxWriter;
import pivotal.au.fe.anzpoc.domain.query.common.Criteria;
import pivotal.au.fe.anzpoc.domain.query.common.Criterion;
import pivotal.au.fe.anzpoc.domain.query.common.Expression;

import java.io.Serializable;

/**
 * container to reference Criteria for method chaining
 */
public class ExpressionEntry implements PdxSerializable {

    private Expression expression;
    private Criteria criteria;

    public ExpressionEntry() {
        super();
    }

    public ExpressionEntry(Criterion expression, Criteria criteria) {
        this.criteria = criteria;
        this.expression = (Expression) expression;
    }

    public Expression getExpression() {
        return expression;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    public String toString() {
        return expression.toString();
    }

    @Override
    public void fromData(PdxReader reader) {
        this.expression = (Expression) reader.readObject("expression");
    }

    @Override
    public void toData(PdxWriter writer) {
        writer.writeObject("expression", this.expression);
    }

}
