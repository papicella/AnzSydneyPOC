package pivotal.au.fe.anzpoc.domain.query.server;

import com.gemstone.gemfire.pdx.PdxSerializable;
import pivotal.au.fe.anzpoc.domain.query.common.Projection;

public interface ServerProjection extends Projection, PdxSerializable {
  public String toOqlString();

  public boolean isGrouped();

  public String toGroupOqlString();

}
