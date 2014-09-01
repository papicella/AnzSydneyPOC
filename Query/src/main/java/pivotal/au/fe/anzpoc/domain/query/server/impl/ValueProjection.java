package pivotal.au.fe.anzpoc.domain.query.server.impl;

import com.gemstone.gemfire.pdx.PdxReader;
import com.gemstone.gemfire.pdx.PdxWriter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * Projects the value from the cache.
 * 
 * this is NOT working but seems like gemfire would support it (coherence does).
 * is there a way?
 *
 */
public class ValueProjection extends SimpleProjection {
  
  private boolean grouped;
  
  protected ValueProjection(boolean grouped) {
    this.grouped = grouped;
  }

  public ValueProjection() {
    this.grouped=false;
  }

  @Override
  public String toOqlString()  {
    return "value";
  }

  public boolean isGrouped() {
    return grouped;
  }
  
  public String toGroupOqlString() {
    if (!grouped) {
      return super.toGroupOqlString();
    }
    else {
      return ", key";
    }
  }
  @Override
  public void fromData(PdxReader pdxReader) {
    this.grouped = pdxReader.readBoolean("grouped");
    
  }
  @Override
  public void toData(PdxWriter pdxWriter) {
    pdxWriter.writeBoolean("grouped", this.grouped);
    
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
}
