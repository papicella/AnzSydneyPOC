package pivotal.au.fe.anzpoc.service.impl;

import pivotal.au.fe.anzpoc.dao.POCDao;
import pivotal.au.fe.anzpoc.dao.POCDaoImpl;
import pivotal.au.fe.anzpoc.domain.query.common.Criteria;
import pivotal.au.fe.anzpoc.domain.query.server.OqlResult;
import pivotal.au.fe.anzpoc.domain.query.server.impl.ServerCriteriaImpl;
import pivotal.au.fe.anzpoc.domain.query.service.CriteriaService;
import pivotal.au.fe.anzpoc.domain.query.service.impl.JDBCCriteriaServiceImpl;
import pivotal.au.fe.anzpoc.service.GenericDataService;

import java.util.Collection;
import java.util.List;

public class JDBCGenericDataServiceImpl implements GenericDataService {
    private POCDao pocDao = new POCDaoImpl();
    private CriteriaService criteriaService = new JDBCCriteriaServiceImpl();

    @Override
    public Object findByKey(String regionName, Object key) {
        return null;
    }

    @Override
    public List findAll(String regionName) {
        return null;
    }

    @Override
    public Collection findValues(Criteria criteria) {
        String sqlString = generateSQLStringFromCriteria(criteria);
        return pocDao.getResult(sqlString);
    }

    private String generateSQLStringFromCriteria(Criteria criteria) {
        ServerCriteriaImpl serverCriteria = (ServerCriteriaImpl) criteria;
        OqlResult oqlResult = null;
        oqlResult = criteriaService.getSqlResult(serverCriteria);

        return oqlResult.getOql();
    }

    @Override
    public Object save(String regionName, Object value) {
        return null;
    }
}
