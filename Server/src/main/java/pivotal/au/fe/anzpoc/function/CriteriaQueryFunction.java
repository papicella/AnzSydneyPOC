package pivotal.au.fe.anzpoc.function;

import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.execute.FunctionAdapter;
import com.gemstone.gemfire.cache.execute.FunctionContext;
import org.springframework.stereotype.Service;

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

//	private static Logger logger = LoggerFactory.getLogger(CriteriaQueryFunction.class);
//	private Integer chunkSize = Integer.MAX_VALUE - 1;
//
	@Override
	public void execute(FunctionContext fc) {

    }
//		try {
//			if (fc.getArguments() instanceof CriteriaQueryMessage) {
//				CriteriaQueryMessage criteriaQueryMessage = (CriteriaQueryMessage) fc.getArguments();
//				logger.trace("criteriaQueryMessage: " + criteriaQueryMessage);
//
//				Criteria criteria = criteriaQueryMessage.getCriteria();
//
//				CriteriaService criteriaService = ApplicationContextProvider
//						.getBean(CriteriaService.class);
//
//				/**
//				 * Step 1. Transform the Criteria to ServerCriteria
//				 */
//				ServerCriteriaImpl serverCriteria = criteriaService.preProcessCriteria(criteria);
//
//				/**
//				 * Step 2. Run Query for the transformed server criteria
//				 */
//				GenericDataService genericDataService = ApplicationContextProvider
//						.getBean(GenericDataService.class);
//
//				// perform post-transformation? probably not here.. wouldn't iterate through
//				// maybe on cache listener is better location? but would need to know target
//				// data type, etc.
//				Collection results = handleRegionSpecificSearches(serverCriteria, genericDataService);
//
//				/**
//				 * Step 3. Post-Process the return results.
//				 */
//				results = criteriaService.postProcess(serverCriteria,results);
//
//                /**
//                 * Step 4. Send results in a "chunked"/streaming fashion
//                 */
//                logger.debug("Return result Size: "+results.size());
//				ResultSender.sendResults(fc, results, chunkSize);
//			}
//		} catch (GemfireQueryException e) {
//			// This would need to be replaced with sendException once the bug between server and
//			// NC is resolved.
//			logger.error("CriteriaQueryFunction ", e);
//			fc.getResultSender().sendException(new LuxorMessageException(e));
//		} catch (InvalidDateFormatException e) {
//			logger.error("InvalidDateFormatException ", e);
//			fc.getResultSender().sendException(new LuxorMessageException(e));
//		}
//		catch (CriteriaQueryException e) {
//			logger.error("CriteriaQueryException ", e);
//			fc.getResultSender().sendException(new LuxorMessageException(e));
//		}catch (Throwable e) {
//			logger.error("Error occured", e);
//		}
//	}
//
//	private Collection handleRegionSpecificSearches(ServerCriteriaImpl serverCriteria,
//			GenericDataService genericDataService) {
//		Collection results;
//		if (serverCriteria.getRegionName().equals("PackageTradeMessage")) {
//			results = searchPackageTradeMessage(serverCriteria);
//		} else if (serverCriteria.getRegionName().equals("ContractTradeMessage")) {
//			results = searchContractTrades(serverCriteria);
//		} // todo need specific behavior for ReferenceData? Shouldnt be
//        else {
//			results = genericDataService.findValues(serverCriteria);
//		}
//		return results;
//	}
//
//	private Collection searchContractTrades(ServerCriteriaImpl serverCriteria) {
//		ContractService contractService = ApplicationContextProvider.getBean(ContractService.class);
//		if (serverCriteria.getCriteriaImpl().getProjectionEntries() != null && serverCriteria.getCriteriaImpl().getProjectionEntries().size() > 0) {
//			return contractService.getContractDataStructureForCriteria(serverCriteria);
//		} else {
//			return contractService.getContractsForCriteria(serverCriteria);
//		}
//	}
//
//	private Collection searchPackageTradeMessage(ServerCriteriaImpl serverCriteria) {
//		// workaround for now. if there are selections, we're going to get back
//		// a DataStructure so we construct the PackageTrade differently then.
//		PackageTradeService packageTradeService = ApplicationContextProvider
//				.getBean(PackageTradeService.class);
//		if (serverCriteria.getCriteriaImpl().getProjectionEntries() != null
//				&& serverCriteria.getCriteriaImpl().getProjectionEntries().size() > 0) {
//			return packageTradeService.getPackageDataStructureForCriteria(serverCriteria);
//		} else {
//			Map<PackageIdentifier, PackageTrade> packageTradesForCriteria = packageTradeService
//					.getPackageTradesForCriteria(serverCriteria);
//			return new ArrayList(packageTradesForCriteria.values());
//		}
//	}

	@Override
	public String getId() {
		return getClass().getName();
	}

	@Override
	public void init(Properties properties) {

	}
}
