package pivotal.au.fe.anzpoc.domain.query.common;

import com.gemstone.gemfire.pdx.PdxSerializable;
import pivotal.au.fe.anzpoc.domain.query.client.impl.ExpressionType;

import java.util.List;

public interface Expression extends Criterion, PdxSerializable {
  
  public String getPropertyName();
  public ExpressionType getExpressionType();
  public List<Object> getValues();
  public boolean isIgnoreCase();
  public MatchMode getMatchMode();
  void setPropertyName(String propertyName);
  void setValues(List<Object> values);

}
