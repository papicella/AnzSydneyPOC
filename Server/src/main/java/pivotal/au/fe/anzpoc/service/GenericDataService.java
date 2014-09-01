package pivotal.au.fe.anzpoc.service;

import pivotal.au.fe.anzpoc.domain.query.common.Criteria;

import java.util.Collection;
import java.util.List;

public interface GenericDataService<K,V> {
  
  public V findByKey(String regionName, K key);
  /**
   * 
   * @return list of all values for the region associated with
   * the data metadata. empty List if none found.
   */
  public List<V> findAll(String regionName);
  /**
   * 
   * @param criteria
   * @return
   */
  public Collection<V> findValues(Criteria criteria);
  /**
   *
   * @param regionName
   * @param value
   * @return value (original - need to confirm)
   */
  public V save(String regionName, V value);

}
