package pivotal.au.fe.anzpoc.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Abstract
 *
 * @param <K>
 * @param <V>
 */
@Service
@SuppressWarnings("unchecked")
public abstract class AbstractDaoImpl<K, V> extends GenericDaoImpl implements
		Dao<K, V> {

	private static final Logger logger = LoggerFactory.getLogger(AbstractDaoImpl.class);

	@Override
	public void initialise() {

	}

}
