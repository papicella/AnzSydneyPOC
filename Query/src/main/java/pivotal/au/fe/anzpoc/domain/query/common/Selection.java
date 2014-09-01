package pivotal.au.fe.anzpoc.domain.query.common;


import pivotal.au.fe.anzpoc.domain.query.client.impl.ProjectionType;

public interface Selection extends Projection {
  public String getPropertyName();
  public ProjectionType getProjectionType();
  public boolean isGrouped();

}
