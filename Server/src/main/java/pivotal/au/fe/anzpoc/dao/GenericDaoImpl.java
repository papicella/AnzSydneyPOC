package pivotal.au.fe.anzpoc.dao;

import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.GemFireCache;
import com.gemstone.gemfire.cache.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pivotal.au.fe.anzpoc.domain.query.common.Criteria;
import pivotal.au.fe.anzpoc.domain.query.server.OqlResult;
import pivotal.au.fe.anzpoc.domain.query.server.impl.ServerCriteriaImpl;
import pivotal.au.fe.anzpoc.domain.query.service.CriteriaService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Not sure what is the best thing to pass in - DataMetaData as per current
 * implementation or something like region name.?
 * <p/>
 * Should we eliminate the generics if we're not typing the dao? probably makes
 * sense.
 *
 * @param <K>
 * @param <V>
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class GenericDaoImpl<K, V> implements GenericDao<K, V> {

    private String clientPoolName;
    @Autowired
    private BeanFactory beanFactory;
    @Autowired
    private CriteriaService criteriaService;
//	@Autowired
//    private EnterpriseDataImplUtil enterpriseDataImplUtil;

    private static final Logger logger = LoggerFactory.getLogger(GenericDaoImpl.class);

    @PostConstruct
    public void initialise() {
    }

    @Override
    public GemFireCache getCache() {
        return CacheFactory.getAnyInstance();
    }

    @Override
    public V create(String regionName, V value) {
        return null;
    }

    @Override
    public List<V> findAll(String regionName) {
        Region<Object, Object> region = getCache().getRegion(regionName);
        ArrayList<Object> objects = new ArrayList<Object>();
        objects.addAll(region.values());
        return (List<V>) objects;
    }

    @Override
    public V findByKey(String regionName, K key) {
        Region<Object, Object> region = getCache().getRegion(regionName);
        return (V) region.get(key);
    }

    @Override
    public V save(String regionName, V value) {
        return null;
    }

    @Override
    public void saveAll(String regionName, List<V> values) {

    }

    @Override
    public void saveAll(String regionName, Map<K, V> values) {

    }

    @Override
    public List<V> findValues(Criteria criteria) {
        ServerCriteriaImpl serverCriteria = (ServerCriteriaImpl) criteria;
        OqlResult oqlResult = null;
        oqlResult = criteriaService.getOqlResult(serverCriteria);

        logger.debug("RESULT: " + oqlResult);
        switch (oqlResult.getOqlType()) {
            case ALL:
                return findAll((serverCriteria).getRegionName());
            default:
                // where clause only
                return findBy(serverCriteria.getRegionName(), oqlResult.getOql());
        }
    }

    protected List<V> findBy(String regionName, String whereClause) {
        logger.debug("query: " + whereClause + " regionName: " + regionName);
        List result = null;
        try {
            result = (List) getCache().getQueryService().newQuery(whereClause).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Set<K> keySet(String regionName) {
        return null;
    }

    @Override
    public boolean hasAny(String regionName) {
        return false;
    }


}
