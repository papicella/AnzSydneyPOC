package pivotal.au.fe.anzpoc.domain.query.server;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class OqlResult {
  
  private final OqlType oqlType;
  private final String oql;
  
  public OqlType getOqlType() {
    return oqlType;
  }

  public String getOql() {
    return oql;
  }

  public OqlResult(OqlType oqlType, String oql) {
    this.oqlType = oqlType;
    this.oql = oql;
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
    return "OqlResult [oqlType=" + oqlType + ", oql=" + oql + "]";
  }
  
}
