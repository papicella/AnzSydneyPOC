package pivotal.au.fe.anzpoc.domain.query.common;

import com.gemstone.gemfire.pdx.PdxSerializable;
import pivotal.au.fe.anzpoc.domain.query.common.impl.Order;

public interface Criteria extends PdxSerializable {

	public Criteria setProjection(Projection projection);

	public Criteria add(Projection projection);

	public Criteria add(Criterion criterion);

	public Criteria addOrder(Order order);
}
