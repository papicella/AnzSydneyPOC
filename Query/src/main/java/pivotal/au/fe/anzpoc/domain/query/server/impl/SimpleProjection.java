package pivotal.au.fe.anzpoc.domain.query.server.impl;

import pivotal.au.fe.anzpoc.domain.query.server.ServerProjection;

public abstract class SimpleProjection implements ServerProjection {
  
  @Override
  public String toOqlString() {
    return null;
  }

  public String toGroupOqlString() {
    throw new UnsupportedOperationException("not a grouping projection");
  }

}
