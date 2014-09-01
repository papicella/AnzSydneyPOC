package pivotal.au.fe.anzpoc.domain.query.server.impl;

import com.gemstone.gemfire.pdx.PdxReader;
import com.gemstone.gemfire.pdx.PdxWriter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PropertyProjection extends SimpleProjection {
  
  private String propertyName;
  private boolean grouped;
  
  protected PropertyProjection(String prop, boolean grouped) {
    this.propertyName = prop;
    this.grouped = grouped;
  }
  
  protected PropertyProjection(String prop) {
    this(prop, false);
  }

  public String getPropertyName() {
    return propertyName;
  }

  @Override
  public String toOqlString()  {
    return propertyName;
  }

  public boolean isGrouped() {
    return grouped;
  }
  
  public String toGroupOqlString() {
    if (!grouped) {
      return super.toGroupOqlString();
    }
    else {
      return ", " + propertyName;
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
