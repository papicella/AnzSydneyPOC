package pivotal.au.fe.anzpoc.domain.query.server.impl;

import com.gemstone.gemfire.pdx.PdxReader;
import com.gemstone.gemfire.pdx.PdxWriter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import pivotal.au.fe.anzpoc.domain.query.server.impl.SimpleProjection;


/**
 * Projects the key from the cache.
 *
 */
public class KeyProjection extends SimpleProjection {
  
  private boolean grouped;
  private String propertyName;
  
  protected KeyProjection(boolean grouped) {
    this.grouped = grouped;
  }
  protected KeyProjection(String propertyName,boolean grouped) {
    this.propertyName = propertyName;
    this.grouped = grouped;
  }
  protected KeyProjection(String propertyName) {
    this.propertyName = propertyName;
    this.grouped = false;
  }

  protected KeyProjection() {
    this.grouped=false;
  }

    public String getPropertyName() {
        return propertyName;
    }

    @Override
  public String toOqlString()  {
    if (propertyName != null && propertyName.length()>0) {
      return "key." + propertyName;
    }
    return "key";
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
    this.propertyName = pdxReader.readString("propertyName");
    this.grouped = pdxReader.readBoolean("grouped");
    
  }
  @Override
  public void toData(PdxWriter pdxWriter) {
    pdxWriter.writeString("propertyName", this.propertyName);
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
