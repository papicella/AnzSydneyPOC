package pivotal.au.fe.anzpoc.domain.query.common.impl;

import com.gemstone.gemfire.pdx.PdxReader;
import com.gemstone.gemfire.pdx.PdxWriter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import pivotal.au.fe.anzpoc.domain.query.common.DataStructure;

/**
 * Wrapper for "dynamic" data structure when the full domain
 * object is not required (e.g. 'Selectors' have been utilized.)
 */
public class DataStructureImpl implements DataStructure, Comparable<DataStructureImpl> {

    //  private String[] fieldTypes;

    // Map easier to work with but more bloated and not as language-neutral
    // so revert to array.
    //  private Map<String,Object> entries;
    //  private Map<String,String> fieldTypes;

    private String originalType;

    private String[] fieldNames;
    private Object[] fieldValues;
    private String[] fieldTypes;


    public DataStructureImpl() {

    }

    public DataStructureImpl(String originalType, String[] fieldNames, Object[] fieldValues, String[] fieldTypes) {
        this(fieldNames, fieldValues, fieldTypes);
        this.originalType = originalType;
    }

    public DataStructureImpl(final String[] fieldNames,
                             final Object[] fieldValues, final String[] fieldTypes) {
        this.fieldNames = fieldNames;
        this.fieldValues = fieldValues;
        this.fieldTypes = fieldTypes;
    }

    @Override
    public Object get(String fieldName) {
        // requires sorting. no good.
        // int index = Arrays.binarySearch(fieldNames, fieldName);
        int index = -1;
        for (int i = 0; i < fieldNames.length; i++) {
            if (fieldNames[i].equals(fieldName)) {
                index = i;
            }
        }
        if (index == -1) {
            return null;
        }
        return fieldValues[index];
    }

    @Override
    public String getType(String fieldName) {

        int index = -1;
        for (int i = 0; i < fieldNames.length; i++) {
            if (fieldNames[i].equals(fieldName)) {
                index = i;
            }
        }
        if (index == -1) {
            return null;
        }
        return fieldTypes[index];
    }

    @Override
    public Object[] getFieldValues() {
        return this.fieldValues;
    }

    @Override
    public String[] getFieldNames() {
        return this.fieldNames;
    }

    @Override
    public void fromData(PdxReader pdxReader) {
        this.fieldNames = pdxReader.readStringArray("fieldNames");
        this.fieldValues = pdxReader.readObjectArray("fieldValues");
        this.fieldTypes = pdxReader.readStringArray("fieldTypes");

    }

    @Override
    public void toData(PdxWriter pdxWriter) {
        pdxWriter.writeStringArray("fieldNames", this.fieldNames);
        pdxWriter.writeObjectArray("fieldValues", this.fieldValues);
        pdxWriter.writeStringArray("fieldTypes", this.fieldTypes);

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

    /**
     * NB Primarily used for unit tests. verify if using for actual implementation.
     */
    @Override
    public int compareTo(DataStructureImpl dataStructureImpl) {
        if (dataStructureImpl == null) {
            return 1;
        }
        if (dataStructureImpl.getFieldValues() == null) {
            if (this.fieldValues == null) {
                return 0;
            } else {
                return 1;
            }
        }
        if (this.fieldValues == null) {
            return -1;
        }
        StringBuilder fieldNamesBuilder = new StringBuilder();
        for (Object fieldValue : this.fieldValues) {
            fieldNamesBuilder.append(fieldValue == null ? "" : fieldValue.toString());
        }
        int hashCode = fieldNamesBuilder.toString().hashCode();

        StringBuilder fieldNamesBuilderOther = new StringBuilder();
        for (Object fieldValue : dataStructureImpl.fieldValues) {
            fieldNamesBuilderOther.append(fieldValue == null ? "" : fieldValue.toString());
        }
        int otherHashCode = fieldNamesBuilderOther.toString().hashCode();
        return (hashCode > otherHashCode) ? 1 : (hashCode == otherHashCode) ? 0 : -1;
    }


    /*
     * Pending. see ETP-258. We should really return the original type request
     * but a) need the orig type (it's been converted to server type and b) 
     * (non-Javadoc)
     * @see com.anz.markets.luxor.domain.EnterpriseData#getDataType()
     */
   @Override
   public String getDataType() {
      return originalType;
   }


}
