package pivotal.au.fe.anzpoc.domain.query.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pivotal.au.fe.anzpoc.domain.query.client.impl.CriteriaImpl;
import pivotal.au.fe.anzpoc.domain.query.client.impl.ExpressionEntry;
import pivotal.au.fe.anzpoc.domain.query.common.*;
import pivotal.au.fe.anzpoc.domain.query.common.impl.Order;
import pivotal.au.fe.anzpoc.domain.query.server.OqlResult;
import pivotal.au.fe.anzpoc.domain.query.server.OqlType;
import pivotal.au.fe.anzpoc.domain.query.server.ServerCriterion;
import pivotal.au.fe.anzpoc.domain.query.server.ServerProjection;
import pivotal.au.fe.anzpoc.domain.query.server.impl.CriterionEntry;
import pivotal.au.fe.anzpoc.domain.query.server.impl.Projections;
import pivotal.au.fe.anzpoc.domain.query.server.impl.Restrictions;
import pivotal.au.fe.anzpoc.domain.query.server.impl.ServerCriteriaImpl;
import pivotal.au.fe.anzpoc.domain.query.server.util.ServerCriteriaUtil;
import pivotal.au.fe.anzpoc.domain.query.service.CriteriaMessagePreProcessor;
import pivotal.au.fe.anzpoc.domain.query.service.CriteriaService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CriteriaServiceImpl implements CriteriaService {

    private static final Logger logger = LoggerFactory.getLogger(CriteriaServiceImpl.class);
    private CriteriaMessagePreProcessor criteriaMessagePreProcessor;

    private ServerCriteriaUtil serverCriteriaUtil;

    private static final String SEPARATOR = System.getProperty("line.separator");

    @Override
    public ServerCriteriaImpl preProcessCriteria(Criteria clientCriteria) {
        CriteriaImpl criteriaImpl = (CriteriaImpl) clientCriteria;

        logger.trace("client criteria: " + criteriaImpl);
        ServerCriteriaImpl serverCriteriaImpl = new ServerCriteriaImpl(clientCriteria);

        logger.debug("serverCriteriaUtil: " + serverCriteriaUtil);
        String regionName = serverCriteriaUtil.getRegion(((CriteriaImpl)
                clientCriteria).getDataType());

        serverCriteriaImpl.setRegionName(regionName);
        logger.trace("server criteria: " + serverCriteriaImpl);

        // Transform expressions to restrictions and add in.
        for (ExpressionEntry expressionEntry : criteriaImpl.getExpressionEntries()) {

            Expression expression = expressionEntry.getExpression();

            logger.trace("expression: " + expression);
            // TODO: Need pre-processor based on the type of message. at moment only have
            // trade message

            Expression transformedExpression = criteriaMessagePreProcessor.transformExpression(criteriaImpl.getDataType(), expression);

            serverCriteriaImpl.add(Restrictions.add(transformedExpression));

        }
        // Add i selections.
        // TODO: these should be transformed as well.
        for (Selection selection : criteriaImpl.getProjectionEntries()) {

            logger.debug("adding selection: " + selection);

            switch (selection.getProjectionType()) {
                case KEY:
                    serverCriteriaImpl.add(Projections.keyProjection());
                    break;
                case KEY_PROPERTY:
                    serverCriteriaImpl.add(Projections.keyProjection(criteriaMessagePreProcessor
                            .transformSelection(((CriteriaImpl) clientCriteria).getDataType(), selection).getPropertyName()));
                    break;
                case VALUE_PROPERTY:
                    serverCriteriaImpl.add(Projections.property(criteriaMessagePreProcessor
                            .transformSelection(((CriteriaImpl) clientCriteria).getDataType(), selection).getPropertyName()));
                    break;
                case VALUE:
                    serverCriteriaImpl.add(Projections.value());
                    break;
            }

        }
        // Add in orders
        for (Order order : criteriaImpl.getOrders()) {
            serverCriteriaImpl.addOrder(order);
        }

        return serverCriteriaImpl;
    }

    public ServerCriteriaImpl getServerCriteriaImplFromDataType(String dataType) {
        return new ServerCriteriaImpl(serverCriteriaUtil.getRegion(dataType));
    }


    @Override
    public Collection postProcess(ServerCriteriaImpl serverCriteria,
                                  Collection results) {
        Collection<EnterpriseData> postProcessedResults = new ArrayList<EnterpriseData>();
        for (Object oject : results) {
            if (oject instanceof DataStructure) {
                DataStructure dataStructure = (DataStructure) oject;
                dataStructure = criteriaMessagePreProcessor
                        .transformDataStructure(serverCriteria, dataStructure);
                postProcessedResults.add(dataStructure);
            } else {
                return results;
            }
        }
        return postProcessedResults;
    }

    @Override
    public OqlResult getOqlResult(ServerCriteriaImpl criteria) {

        // full select query
        if (criteria.getCriteriaImpl().getProjectionEntries() != null
                && criteria.getCriteriaImpl().getProjectionEntries().size() > 0) {

            logger.debug("projection query...");
            StringBuilder oql = new StringBuilder();
            oql.append("SELECT ");
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
            oql.append("FROM /" + criteria.getRegionName());

            List<CriterionEntry> criterionEntries = criteria.getCriteriaImpl().getCriterionEntries();

            boolean first = true;
            for (CriterionEntry criterionEntry : criterionEntries) {
                if (first) {
                    oql.append(" WHERE ");
                } else {
                    oql.append(" AND ");
                }
                first = false;
                oql.append(((ServerCriterion) criterionEntry.getCriterion()).toOqlString());
            }
            int oCount = 1;
            for (Order order : criteria.getCriteriaImpl().getOrders()) {
                if (oCount == 1) {
                    oql.append(" ORDER BY ");
                }
                oql.append(order.toOqlString());
                if (oCount == criteria.getCriteriaImpl().getOrders().size()) {

                    // take last... need to come back to this.
                    oql.append(order.isAscending() ? " ASC" : " DESC");
                } else if (criteria.getCriteriaImpl().getOrders().size() > 1) {
                    oql.append(", ");
                }
                oCount++;
            }
            logger.debug("oql string: " + oql);
            return new OqlResult(OqlType.FULL_QUERY, oql.toString());

        } else {
            logger.debug("standard criterion query..." + criteria);

            List<CriterionEntry> criterionEntries = criteria.getCriteriaImpl().getCriterionEntries();
            if (criterionEntries.size() == 0 && criteria.getCriteriaImpl().getOrders().size() == 0) {
                return new OqlResult(OqlType.ALL, null);
            }
            logger.debug("criterion entries.." + criterionEntries);
            StringBuilder whereClause = new StringBuilder();
            int size = criterionEntries.size();
            int i = 1;
            for (CriterionEntry criterionEntry : criterionEntries) {

                whereClause.append(((ServerCriterion) criterionEntry.getCriterion()).toOqlString());
                if (i != size) {
                    whereClause.append(" AND ");
                }
                i++;
            }

            logger.debug("whereclause: " + whereClause);
            int oCount = 1;
            for (Order order : criteria.getCriteriaImpl().getOrders()) {
                if (oCount == 1) {
                    whereClause.append(" ORDER BY ");
                }
                whereClause.append(order.toOqlString());
                logger.debug("whereclause2: " + whereClause);
                if (oCount == criteria.getCriteriaImpl().getOrders().size()) {
                    logger.debug("whereclause3: " + whereClause);
                    whereClause.append(order.isAscending() ? " ASC" : " DESC");
                    logger.debug("whereclause4: " + whereClause);
                } else if (criteria.getCriteriaImpl().getOrders().size() > 1) {
                    whereClause.append(", ");
                }

                oCount++;
            }
            return new OqlResult(OqlType.WHERE_CLAUSE, whereClause.toString());

        }
    }

}
