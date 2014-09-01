package pivotal.au.fe.anzpoc.domain.query.server;

import com.gemstone.gemfire.pdx.PdxSerializable;
import pivotal.au.fe.anzpoc.domain.query.common.Criterion;

public interface ServerCriterion extends PdxSerializable, Criterion {
    /**
     * Render the OQL fragment
     *
     * @return The generated OQL fragment
     */
    public String toOqlString();
}
