package pivotal.au.fe.anzpoc.client;

import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.client.PoolManager;
import com.gemstone.gemfire.cache.execute.FunctionService;
import com.gemstone.gemfire.cache.execute.ResultCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pivotal.au.fe.anzpoc.domain.query.CriteriaQueryMessage;
import pivotal.au.fe.anzpoc.domain.query.client.Restrictions;
import pivotal.au.fe.anzpoc.domain.query.client.impl.CriteriaImpl;
import pivotal.au.fe.anzpoc.domain.query.common.MatchMode;

import javax.sql.PooledConnection;
import java.util.List;


public class QueryClient {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private ClientCache cache = null;

    public QueryClient() {
        ClientCacheFactory ccf = new ClientCacheFactory();
        ccf.set("cache-xml-file", "client.xml");
        ccf.set("log-level", "config");
        ccf.set("mcast-port", "0");

        cache = ccf.create();
    }

    public static void main(String[] args) {
        QueryClient queryClient = new QueryClient();
        queryClient.run();
    }

    private void run() {
        CriteriaQueryMessage criteriaQueryMessage = new CriteriaQueryMessage();
        criteriaQueryMessage.setDataType("tradeRegion");
        criteriaQueryMessage.setDataStore("gemfire");
        CriteriaImpl criteria = new CriteriaImpl("tradeRegion");
        criteria.add(Restrictions.like("tradeAttributes['field2']", "field2_1", MatchMode.ANYWHERE));
//        criteria.add(Restrictions.like("tradeAttributes['field2']", "field2_3", MatchMode.ANYWHERE));
//        criteria.add(Restrictions.equal("tradeAttributes['field2']", "field2_1795"));
        criteriaQueryMessage.setCriteria(criteria);
        ResultCollector client = FunctionService.onServer(PoolManager.find("client")).withArgs(criteriaQueryMessage).execute("pivotal.au.fe.anzpoc.function.CriteriaQueryFunction");
        List result = (List) client.getResult();
        System.out.println("result = " + ((List)result.get(0)).size());
    }
}

