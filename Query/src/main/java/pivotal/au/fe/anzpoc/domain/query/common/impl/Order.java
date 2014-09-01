package pivotal.au.fe.anzpoc.domain.query.common.impl;

import com.gemstone.gemfire.pdx.PdxReader;
import com.gemstone.gemfire.pdx.PdxSerializable;
import com.gemstone.gemfire.pdx.PdxWriter;
import pivotal.au.fe.anzpoc.domain.query.common.Criterion;

/**
 * Huh... what happens if two different orders have different ascening, etc. it's as
 * thought it's at the wrong level... copied from hibernate..
 */
public class Order implements Criterion, PdxSerializable {


    private String propertyName;
    private boolean ascending;

    public Order() {

    }

    public String toString() {
        return propertyName + ' ' + (ascending ? "asc" : "desc");
    }

    /**
     * Constructor for Order.
     */
    protected Order(String propertyName, boolean ascending) {
        this.propertyName = propertyName;
        this.ascending = ascending;
    }

    /**
     * Render the SQL fragment
     */
    public String toOqlString() {
        return propertyName;
    }

    /**
     * Ascending order
     *
     * @param propertyName
     * @return Order
     */
    public static Order asc(String propertyName) {
        return new Order(propertyName, true);
    }


    public boolean isAscending() {
        return ascending;
    }

    /**
     * Descending order
     *
     * @param propertyName
     * @return Order
     */
    public static Order desc(String propertyName) {
        return new Order(propertyName, false);
    }

    @Override
    public void fromData(PdxReader reader) {
        this.propertyName = reader.readString("propertyName");
        this.ascending = reader.readBoolean("ascending");
    }

    @Override
    public void toData(PdxWriter writer) {
        writer.writeString("propertyName", this.propertyName);
        writer.writeBoolean("ascending", this.ascending);
    }
}

