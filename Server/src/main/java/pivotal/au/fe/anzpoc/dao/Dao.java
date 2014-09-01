package pivotal.au.fe.anzpoc.dao;

import java.util.List;
import java.util.Map;

/**
 * Might have parent interface with only value
 *
 * @param <K>
 * @param <V>
 */
public interface Dao<K, V> {

    public V save(V value);

    public void saveAll(List<V> values);

    public void saveAll(Map<K, V> values);
}
