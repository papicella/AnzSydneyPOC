package pivotal.au.fe.anzpoc.function;

import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.execute.FunctionAdapter;
import com.gemstone.gemfire.cache.execute.FunctionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pivotal.au.fe.anzpoc.domain.query.CriteriaQueryMessage;
import pivotal.au.fe.anzpoc.domain.query.common.Criteria;
import pivotal.au.fe.anzpoc.domain.query.server.impl.ServerCriteriaImpl;
import pivotal.au.fe.anzpoc.domain.query.service.CriteriaService;
import pivotal.au.fe.anzpoc.domain.query.service.impl.CriteriaServiceImpl;
import pivotal.au.fe.anzpoc.service.GenericDataService;
import pivotal.au.fe.anzpoc.service.impl.GenericDataServiceImpl;

import java.util.Collection;
import java.util.Properties;

/**
 *
 * Generic function to generate retrieve the data corresponding to the criteria.
 * Client supplies:
 *
 * + Desired Data Type + Criteria
 *
 * Function: a) performs mapping from exposed data type to internal
 * representation b) Generates the appropriate oql from mappings and supplied
 * criteria c) Retrieves the data d) Applies any post-transformation to the
 * returned result e) returns the formatted response. This may be a generic data
 * structure or format specific to client - TBD.
 *
 */
@Service
@SuppressWarnings({ "rawtypes", "serial", "unused", "unchecked" })
public class CriteriaQueryFunction extends FunctionAdapter implements Declarable {

	private static Logger logger = LoggerFactory.getLogger(CriteriaQueryFunction.class);

	@Override
	public void execute(FunctionContext fc) {

		try {
			if (fc.getArguments() instanceof CriteriaQueryMessage) {
				CriteriaQueryMessage criteriaQueryMessage = (CriteriaQueryMessage) fc.getArguments();
				logger.trace("criteriaQueryMessage: " + criteriaQueryMessage);

				Criteria criteria = criteriaQueryMessage.getCriteria();

				CriteriaService criteriaService = new CriteriaServiceImpl();

				/**
				 * Step 1. Transform the Criteria to ServerCriteria
				 */
				ServerCriteriaImpl serverCriteria = criteriaService.preProcessCriteria(criteria);

				/**
				 * Step 2. Run Query for the transformed server criteria
				 */
				GenericDataService genericDataService = new GenericDataServiceImpl();

				// perform post-transformation? probably not here.. wouldn't iterate through
				// maybe on cache listener is better location? but would need to know target
				// data type, etc.
				Collection results = genericDataService.findValues(serverCriteria);

				fc.getResultSender().lastResult(results);
			}
		} catch (Exception e) {
			// This would need to be replaced with sendException once the bug between server and
			// NC is resolved.
			logger.error("CriteriaQueryFunction ", e);
			fc.getResultSender().sendException(e);
		}
	}

	@Override
	public String getId() {
		return getClass().getName();
	}

	@Override
	public void init(Properties properties) {

	}
}
