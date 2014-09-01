package pivotal.au.fe.anzpoc.domain.query.common.impl;

import com.gemstone.gemfire.pdx.PdxReader;
import com.gemstone.gemfire.pdx.PdxSerializable;
import com.gemstone.gemfire.pdx.PdxWriter;
import org.apache.commons.lang.builder.ToStringBuilder;
import pivotal.au.fe.anzpoc.domain.query.client.impl.ProjectionType;
import pivotal.au.fe.anzpoc.domain.query.common.Selection;

public class SelectionImpl implements Selection,PdxSerializable {
  
  private ProjectionType projectionType;
  private String propertyName;
  private boolean grouped;
  
  public SelectionImpl() {
    
  }

  public SelectionImpl(ProjectionType projectionType, String propertyName,
                       boolean grouped) {
    super();
    this.projectionType = projectionType;
    this.propertyName = propertyName;
    this.grouped = grouped;
  }

  @Override
  public ProjectionType getProjectionType() {
    return this.projectionType;
  }

  public String getPropertyName() {
    return this.propertyName;
  }

  @Override
  public boolean isGrouped() {
    return this.grouped;
  }

  @Override
  public void fromData(PdxReader pdxReader) {
    this.projectionType = ProjectionType.valueOf(pdxReader.readString("projectionType"));
    this.propertyName = pdxReader.readString("propertyName");
    this.grouped = pdxReader.readBoolean("grouped");

    
  }

  @Override
  public void toData(PdxWriter pdxWriter) {
    pdxWriter.writeString("projectionType", this.projectionType.toString());
    pdxWriter.writeString("propertyName", this.propertyName);
    pdxWriter.writeBoolean("grouped", this.grouped);
    
  }
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
