package pivotal.au.fe.anzpoc.domain.query.service;

import pivotal.au.fe.anzpoc.domain.query.common.DataStructure;
import pivotal.au.fe.anzpoc.domain.query.common.Expression;
import pivotal.au.fe.anzpoc.domain.query.common.Selection;
import pivotal.au.fe.anzpoc.domain.query.server.impl.ServerCriteriaImpl;

public interface CriteriaMessagePreProcessor {

  public Expression transformExpression(String dataType, Expression expression);

    DataStructure transformDataStructure(ServerCriteriaImpl serverCriteria, DataStructure dataStructure);

    Selection transformSelection(String dataType, Selection selection);
}
