package pivotal.au.fe.anzpoc.domain.query.client.impl;

import com.gemstone.gemfire.pdx.PdxReader;
import com.gemstone.gemfire.pdx.PdxWriter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import pivotal.au.fe.anzpoc.domain.query.common.Criterion;
import pivotal.au.fe.anzpoc.domain.query.common.Expression;
import pivotal.au.fe.anzpoc.domain.query.common.MatchMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Lightweight client expression to hold state of expressions
 * requested.
 *
 */
public class ExpressionImpl implements Expression {
  
  
  /**
   * would rather these be final.
   */
  private String propertyName;
  private ExpressionType expressionType;
  private List<Object> values = new ArrayList<Object>(); // array?
  private boolean ignoreCase;
  private MatchMode matchMode;
  
  public ExpressionImpl() {
    
  }
  
  public ExpressionImpl(String propertyName, ExpressionType expressionType, Object value) {
    this.propertyName = propertyName;
    this.expressionType = expressionType;
    this.values.add(value);
    this.ignoreCase = false;
    this.matchMode = null;
  }
  public ExpressionImpl(String propertyName, ExpressionType expressionType, Object[] values) {
    this.propertyName = propertyName;
    this.expressionType = expressionType;
    this.values.addAll(Arrays.asList(values));
    this.ignoreCase = false;
    this.matchMode = null;
  }

  public ExpressionImpl(String propertyName, ExpressionType expressionType,
      Object value, MatchMode matchMode
      ) {
    this.propertyName = propertyName;
    this.expressionType = expressionType;
    this.values.add(value);
    this.ignoreCase = false;
    this.matchMode = matchMode;
  }

  public ExpressionImpl(String propertyName, ExpressionType expressionType, Object value,
      boolean ignoreCase,MatchMode matchMode) {
    this.propertyName = propertyName;
    this.expressionType = expressionType;
    this.values.add(value);
    this.ignoreCase = false;
    this.matchMode = null;
  }

  public ExpressionImpl(String propertyName, ExpressionType expressionType, Object value,
      boolean ignoreCase) {
    this.propertyName = propertyName;
    this.expressionType = expressionType;
    this.values.add(value);
    this.ignoreCase = ignoreCase;
    this.matchMode = null;
  }

  public ExpressionImpl(ExpressionType expressionType, Criterion lhs, Criterion rhs) {
    this.propertyName = null;
    this.expressionType = expressionType;
    this.values.add(lhs);
    this.values.add(rhs);
    this.ignoreCase = false;
    this.matchMode = null;
  }

  public ExpressionImpl(String propertyName, ExpressionType expressionType, Object lo,
      Object hi) {
    this.propertyName = propertyName;
    this.expressionType = expressionType;
    this.values.add(lo);
    this.values.add(hi);
    this.ignoreCase = false;
    this.matchMode = null;
  }
  public String getPropertyName() {
    return propertyName;
  }
  public ExpressionType getExpressionType() {
    return expressionType;
  }
  public List<Object> getValues() {
    return values;
  }
  public boolean isIgnoreCase() {
    return ignoreCase;
  }
  public MatchMode getMatchMode() {
    return matchMode;
  }
  @Override
  public void setPropertyName(String propertyName) {
    this.propertyName = propertyName;
  }
  @Override
  public void setValues(List<Object> values) {
    this.values = values;
  }
  
  public void setExpressionType(ExpressionType expressionType) {
    this.expressionType = expressionType;
  }

  public void setIgnoreCase(boolean ignoreCase) {
    this.ignoreCase = ignoreCase;
  }

  public void setMatchMode(MatchMode matchMode) {
    this.matchMode = matchMode;
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
  @Override
  public void fromData(PdxReader pdxReader) {
    this.propertyName = pdxReader.readString("propertyName");
    this.expressionType = (ExpressionType) pdxReader.readObject("expressionType");
    Object[] valuesArray = pdxReader.readObjectArray("values");
    this.values = Arrays.asList(valuesArray);
    this.ignoreCase = pdxReader.readBoolean("ignoreCase");
    this.matchMode = (MatchMode) pdxReader.readObject("matchMode");
  }
  
  @Override
  public void toData(PdxWriter pdxWriter) {
    pdxWriter.writeString("propertyName",this.propertyName);
    pdxWriter.writeObject("expressionType", this.expressionType);
    Object[] values = null;
    if (this.values!= null) {
      values = this.values.toArray();
    }
    pdxWriter.writeObjectArray("values", values);
    pdxWriter.writeBoolean("ignoreCase", this.ignoreCase);
    pdxWriter.writeObject("matchMode", this.matchMode);    
    
  }

}
