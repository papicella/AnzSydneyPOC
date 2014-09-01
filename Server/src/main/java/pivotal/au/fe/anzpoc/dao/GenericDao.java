package pivotal.au.fe.anzpoc.dao;

import com.gemstone.gemfire.cache.GemFireCache;
import pivotal.au.fe.anzpoc.domain.query.common.Criteria;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * use generics or not? we're not saving state one GenericDao
 * so think no.. come back to this.
 * <p/>
 * or maybe we use use with Identifier,Entity<Identifier>
 * but see issues with projections...
 *
 * @param <K>
 * @param <V>
 * @author bonnerc
 */
public interface GenericDao<K, V> {

    public GemFireCache getCache();

    /**
     * Creates the given value directly to the supplied region. If the value
     * already exists it will throw an exception.
     *
     * @param regionName
     * @param value
     * @return
     */
    public V create(String regionName, V value);

    /**
     * Creates the given value directly to the region on the supplied datametadata.
     * If the value already exists it will throw an exception.
     *
     * @param value the value to save.
     * @return the saved value.
     */
//    public V create(DataMetaData dataMetaData, V value);

//    /**
//     * returns all values for the region associated with the datametadata
//     *
//     * @param dataMetaData
//     * @return
//     */
//    public List<V> findAll(DataMetaData dataMetaData);

    /**
     * returns all values for the region
     *
     * @param regionName
     * @return list of values
     */
    public List<V> findAll(String regionName);

//    /**
//     * The value matching the supplied key for the data metadata
//     *
//     * @param dataMetaData
//     * @param key
//     * @return
//     */
//    public V findByKey(DataMetaData dataMetaData, K key);

    /**
     * Finds the value with the given key and region name.
     *
     * @param key    - the key.
     * @return the value.
     */

    public V findByKey(String regionName, K key);

    /**
     * Saves the value to the region
     *
     * @param regionName
     * @param value
     * @return
     */
    public V save(String regionName, V value);

    /**
     * Saves the value to the region associated with the
     * given dataMetaData
     *
     * @param dataMetaData
     * @param value
     * @return
     */
//    public V save(DataMetaData dataMetaData, V value);

    /**
     * Saves the list values to the region in the datametadata
     * suppliedl
     *
     * @param dataMetaData
     * @param values
     */
//    public void saveAll(DataMetaData dataMetaData, List<V> values);

    /**
     * Saves the list values to the regionName supplied
     *
     * @param regionName
     * @param values
     */
    public void saveAll(String regionName, List<V> values);

    /**
     * Saves the map of values to the region in the datametadata
     * supplied.
     *
     * @param dataMetaData
     * @param values
     */
//    public void saveAll(DataMetaData dataMetaData, Map<K, V> values);

    /**
     * Saves the map of values to the region defined in regionName
     *
     * @param regionName
     * @param values
     */
    void saveAll(String regionName, Map<K, V> values);


    /**
     * @param criteria the servercriteria that specifieds the query
     *                 details including regioname, projections, values, etc.
     *                 <p/>
     *                 TODO: if projections are used it is not a 'V' (entity) that is returned....
     * @return
     */
    public List<V> findValues(Criteria criteria);

    /**
     * The keys in the region.
     *
     * @param regionName
     * @return the key set present on the  server.
     * @see com.gemstone.gemfire.cache.Region#keySetOnServer() ()
     */
    public Set<K> keySet(String regionName);

    /**
     * @param regionName
     * @return true if there is at least one entity present in the server
     * for the region supplied
     */
    public boolean hasAny(String regionName);

}
