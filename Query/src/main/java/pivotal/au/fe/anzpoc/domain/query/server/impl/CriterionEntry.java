package pivotal.au.fe.anzpoc.domain.query.server.impl;

import com.gemstone.gemfire.pdx.PdxReader;
import com.gemstone.gemfire.pdx.PdxSerializable;
import com.gemstone.gemfire.pdx.PdxWriter;
import pivotal.au.fe.anzpoc.domain.query.common.Criteria;
import pivotal.au.fe.anzpoc.domain.query.common.Criterion;

import java.io.Serializable;

public class CriterionEntry implements Serializable, PdxSerializable {

    private Criterion criterion;
    private Criteria criteria;


    public CriterionEntry() {
        super();
        // TODO Auto-generated constructor stub
    }

    public CriterionEntry(Criterion criterion, Criteria criteria) {
        this.criteria = criteria;
        this.criterion = criterion;
    }

    public Criterion getCriterion() {
        return criterion;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    public String toString() {
        return criterion.toString();
    }

    @Override
    public void fromData(PdxReader reader) {
        this.criterion = (Criterion) reader.readObject("criterion");

    }

    @Override
    public void toData(PdxWriter writer) {

        writer.writeObject("criterion", this.criterion);
    }

}
