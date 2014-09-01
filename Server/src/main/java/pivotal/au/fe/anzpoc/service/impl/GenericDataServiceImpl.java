package pivotal.au.fe.anzpoc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import pivotal.au.fe.anzpoc.domain.query.common.Criteria;
import pivotal.au.fe.anzpoc.service.GenericDataService;

import java.util.Collection;
import java.util.List;

@Repository
public class GenericDataServiceImpl<K, V> implements GenericDataService<K, V> {

    @Autowired
    @Qualifier(value = "GENERICDAO")
//  private GenericDao<K,V> genericDao;

    /**
     * {@inheritDoc}
     */
//  @Override
//  public V findByKey(DataMetaData dataMetaData, K k) {
//    return genericDao.findByKey(dataMetaData, k);
//  }
//
//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public List<V> findAll(DataMetaData dataMetaData) {
//    return genericDao.findAll(dataMetaData);
//  }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<V> findValues(Criteria criteria) {

        // perform potential mapping from dataType to region(s).. append criteria if needed, etc.
        // potentially generic... potentially not (i.e. have specific service) but prob best
        // to use generic mapping.

        // for now leave untouched.
//    return genericDao.findValues(criteria);
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<V> findAll(String regionName) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V findByKey(String regionName, K key) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V save(String regionName, V value) {
//    return genericDao.save(regionName, value);
        return value;
    }
}
