package pivotal.au.fe.anzpoc.domain.query.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pivotal.au.fe.anzpoc.domain.query.common.impl.Order;
import pivotal.au.fe.anzpoc.domain.query.server.OqlResult;
import pivotal.au.fe.anzpoc.domain.query.server.OqlType;
import pivotal.au.fe.anzpoc.domain.query.server.ServerCriterion;
import pivotal.au.fe.anzpoc.domain.query.server.ServerProjection;
import pivotal.au.fe.anzpoc.domain.query.server.impl.CriterionEntry;
import pivotal.au.fe.anzpoc.domain.query.server.impl.ServerCriteriaImpl;
import pivotal.au.fe.anzpoc.domain.query.service.CriteriaService;

import java.util.List;

public class JDBCCriteriaServiceImpl extends CriteriaServiceImpl implements CriteriaService {

    private static final Logger logger = LoggerFactory.getLogger(JDBCCriteriaServiceImpl.class);
    @Override
    public OqlResult getOqlResult(ServerCriteriaImpl criteria) {
        // full select query
//        if (criteria.getCriteriaImpl().getProjectionEntries() != null
//                && criteria.getCriteriaImpl().getProjectionEntries().size() > 0) {

        logger.debug("projection query...");
        StringBuilder oql = new StringBuilder();
        oql.append("SELECT * ");
        if (criteria.getCriteriaImpl().getOrders().size() > 0) {
            oql.append("DISTINCT ");
        }

//      oql.append(criteria.getCriteriaImpl().getProjection().toOqlString());

        int size = criteria.getCriteriaImpl().getProjectionEntries().size();
        int count = 0;
        for (ServerProjection projection : criteria.getCriteriaImpl().getProjectionEntries()) {

            logger.debug("server projection: " + projection);
            oql.append(projection.toOqlString() + " ");
            count++;
            if (size > count) {
                oql.append(", ");
            }
        }
        // like this or past back select and criterion separately?
        oql.append("FROM anz.trade_metadata");

        List<CriterionEntry> criterionEntries = criteria.getCriteriaImpl().getCriterionEntries();

        boolean first = true;
        for (CriterionEntry criterionEntry : criterionEntries) {
            if (first) {
                oql.append(" WHERE ");
            } else {
                oql.append(" AND ");
            }
            first = false;
            String restriction = ((ServerCriterion) criterionEntry.getCriterion()).toSqlString();
            restriction = unwrapRestriction(restriction);
            oql.append(restriction);
        }
        int oCount = 1;
        logger.debug("oql string: " + oql);
        return new OqlResult(OqlType.FULL_QUERY, oql.toString());
    }

    private String unwrapRestriction(String restriction) {
        int start = restriction.indexOf("['");
        int end = restriction.indexOf("']");
        String field = restriction.substring(start+2,end);
        return field+restriction.substring(end+2);
    }
}
