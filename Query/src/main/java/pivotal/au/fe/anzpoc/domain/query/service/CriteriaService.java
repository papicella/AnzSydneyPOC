package pivotal.au.fe.anzpoc.domain.query.service;

import pivotal.au.fe.anzpoc.domain.query.common.Criteria;
import pivotal.au.fe.anzpoc.domain.query.server.OqlResult;
import pivotal.au.fe.anzpoc.domain.query.server.impl.ServerCriteriaImpl;

import java.util.Collection;

public interface CriteriaService {

	ServerCriteriaImpl preProcessCriteria(Criteria criteria);

	OqlResult getOqlResult(ServerCriteriaImpl criteria);

    Collection postProcess(ServerCriteriaImpl serverCriteria, Collection results);

    OqlResult getSqlResult(ServerCriteriaImpl serverCriteria);
}
