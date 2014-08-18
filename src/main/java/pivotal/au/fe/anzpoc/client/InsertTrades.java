package pivotal.au.fe.anzpoc.client;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import pivotal.au.fe.anzpoc.domain.TradeObject;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InsertTrades
{
    private Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    private ClientCache cache = null;
    private final int BATCH_SIZE = 10000;
    private final int SAMPLE_SIZE = 100000;

    public InsertTrades()
    {
        ClientCacheFactory ccf = new ClientCacheFactory();
        ccf.set("cache-xml-file", "client.xml");
        ccf.set("log-level", "config");

        cache = ccf.create();
    }

    public void run()
    {
        Region<String,TradeObject> tradeRegion = cache.getRegion("tradeRegion");
        Map<String, TradeObject> buffer = new HashMap<String, TradeObject>();

        long start = System.currentTimeMillis();

        TradeObject trade = new TradeObject();
        trade.setTradeId("1");
        trade.setTradeType("apples");
        trade.setPayloadDigest("payload");

        byte[] mybyte_array = "Pas demo".getBytes();
        trade.setPayload(mybyte_array);

        long createDate = 46474747;

        trade.setCreatedTimeStamp(createDate);

        Map<String, String> tradeMetaDataMap = new HashMap<String, String>();
        tradeMetaDataMap.put("field1", "field1value");
        tradeMetaDataMap.put("field2", "field2value");
        tradeMetaDataMap.put("key.tradeid", "1");
        tradeMetaDataMap.put("key.sourceSystem", "source");
        tradeMetaDataMap.put("key.validFrom ", "validFrom");

        trade.setTradeAttributes(tradeMetaDataMap);

        tradeRegion.put(trade.getTradeId(), trade);

        long end = System.currentTimeMillis() - start;

        float elapsedTimeSec = end/1000F;

        logger.log (Level.INFO, String.format("Elapsed time in seconds %f", elapsedTimeSec));

    }

    public static void main(String[] args)
    {
        InsertTrades test = new InsertTrades();
        test.run();
    }
}
