//package pivotal.au.fe.anzpoc.client;
//
//import com.gemstone.gemfire.cache.Region;
//import com.gemstone.gemfire.cache.client.ClientCache;
//import com.gemstone.gemfire.cache.client.ClientCacheFactory;
//import pivotal.au.fe.anzpoc.domain.TradeObject;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import java.util.zip.Deflater;
//
//public class InsertTrades {
//    private Logger logger = Logger.getLogger(this.getClass().getSimpleName());
//    private ClientCache cache = null;
//    private final int BATCH_SIZE = 1000;
//    private final int SAMPLE_SIZE = 10000;
//
//    public InsertTrades() {
//        ClientCacheFactory ccf = new ClientCacheFactory();
//        ccf.set("cache-xml-file", "client.xml");
//        ccf.set("log-level", "config");
//
//        cache = ccf.create();
//    }
//
//    public void run() throws IOException {
//        Region<String, TradeObject> tradeRegion = cache.getRegion("tradeRegion");
//        Map<String, TradeObject> buffer = new HashMap<String, TradeObject>();
//
//        long start = System.currentTimeMillis();
//
//        TradeObject trade = null;
//        Map<String, Object> tradeMetaDataMap = null;
//
//        for (int i = 1; i <= SAMPLE_SIZE; i++) {
//            trade = null;
//            trade = new TradeObject();
//            trade.setTradeId(i + "");
//            trade.setCreatedTimeStamp(new java.util.Date().getTime());
//            String payloadDigest = "<trade><trade-id>" + i + "</trade-id></trade>";
//
//            trade.setPayloadDigest(payloadDigest);
//            //byte[] mybyte_array = "Pas demo".getBytes();
//            //trade.setPayload(mybyte_array);
//
//            trade.setPayload(compressBytes(payloadDigest));
//
//            tradeMetaDataMap = new HashMap();
//            tradeMetaDataMap.put("accountingDate", System.currentTimeMillis());
//            tradeMetaDataMap.put("accountingSection", "accountingSection");
//            tradeMetaDataMap.put("active", true);
//            tradeMetaDataMap.put("bookValue", "123.45");
//            tradeMetaDataMap.put("bookValueCurrency", "AUS");
//            tradeMetaDataMap.put("correctiveActionType", "correctiveActionType");
//            tradeMetaDataMap.put("counterParty", "counterParty");
//            tradeMetaDataMap.put("createdMillis", System.currentTimeMillis());
//            tradeMetaDataMap.put("dataType", "TradeMessage");
//            tradeMetaDataMap.put("entity", "entity");
//            tradeMetaDataMap.put("eventType", "eventType");
//            tradeMetaDataMap.put("expiryDate", System.currentTimeMillis());
//            tradeMetaDataMap.put("identifier", i+"");
//            tradeMetaDataMap.put("instrumentId", "instrumentId");
//            tradeMetaDataMap.put("internalParty", "internalParty");
//            tradeMetaDataMap.put("internalPortfolio", "internalPortfolio");
//            tradeMetaDataMap.put("legalEntity", "legalEntity");
//            tradeMetaDataMap.put("portfolio", "portfolio");
//            tradeMetaDataMap.put("salesDealerAnzDealerId", "salesDealerAnzDealerId");
//            tradeMetaDataMap.put("salesDealerName", "salesDealerName");
//            tradeMetaDataMap.put("sourceSystem", "MUREX");
//            tradeMetaDataMap.put("sourceSystemOffset", "10");
//            tradeMetaDataMap.put("strategy", "strategy");
//            tradeMetaDataMap.put("subEventType", "subEventType");
//            tradeMetaDataMap.put("swapswire", "swapswire");
//            tradeMetaDataMap.put("systemDate", System.currentTimeMillis());
//            tradeMetaDataMap.put("timeStampLastAudit", System.currentTimeMillis());
//            tradeMetaDataMap.put("timeStampLastAuditUtc", "2014/06/06T10:10:10:000");
//            tradeMetaDataMap.put("timeToPurgeInMillis", 0L);
//            tradeMetaDataMap.put("tradeCreatorId", "tradeCreatorId");
//            tradeMetaDataMap.put("tradeDate", System.currentTimeMillis());
//            tradeMetaDataMap.put("tradeExternalId", "tradeExternalId");
//            tradeMetaDataMap.put("tradeFamily", "tradeFamily");
//            tradeMetaDataMap.put("tradeGlobalId", "tradeGlobalId");
//            tradeMetaDataMap.put("tradeGroup", "tradeGroup");
//            tradeMetaDataMap.put("tradeId", i+"");
//            tradeMetaDataMap.put("tradeIdReference", i+"");
//            tradeMetaDataMap.put("tradeMessageIdentifier", "A2342135ASDWQER");
//            tradeMetaDataMap.put("tradeOriginId", "tradeOriginId");
//            tradeMetaDataMap.put("tradePhysicalStatus", "live");
//            tradeMetaDataMap.put("tradeType", "tradeType");
//            tradeMetaDataMap.put("typology", "typology");
//            tradeMetaDataMap.put("userName", "James Bob");
//            tradeMetaDataMap.put("usi1", "Usi1");
//            tradeMetaDataMap.put("usi2", "Usi2");
//            tradeMetaDataMap.put("validationLevel", "validationLevel");
//            tradeMetaDataMap.put("validFrom", System.currentTimeMillis());
//            tradeMetaDataMap.put("validFromSourceSystem", System.currentTimeMillis());
//            tradeMetaDataMap.put("validTo", System.currentTimeMillis());
//            tradeMetaDataMap.put("validToSourceSystem", System.currentTimeMillis());
//            tradeMetaDataMap.put("key.tradeid", i + "");
//            tradeMetaDataMap.put("key.sourceSystem", "source_" + i);
//            tradeMetaDataMap.put("key.validFrom ", "validFrom_" + i);
//
//            trade.setTradeAttributes(tradeMetaDataMap);
//
//            // place person into temp buffer
//            buffer.put(String.valueOf(i), trade);
//
//            if ((i % BATCH_SIZE) == 0) {
//                // ready to insert a batch into our region
//                tradeRegion.putAll(buffer);
//                buffer.clear();
//            }
//        }
//
//        // there may be existing records to flush so this takes care of it
//        if (!buffer.isEmpty()) {
//            tradeRegion.putAll(buffer);
//            buffer.clear();
//        }
//
//        long end = System.currentTimeMillis() - start;
//
//        float elapsedTimeSec = end / 1000F;
//
//        logger.log(Level.INFO, String.format("Inserted %s TRADES", SAMPLE_SIZE));
//        logger.log(Level.INFO, String.format("Elapsed time in seconds %f", elapsedTimeSec));
//
//    }
//
//    private byte[] compressBytes(String data) throws UnsupportedEncodingException, IOException {
//        byte[] input = data.getBytes("UTF-8");
//        Deflater df = new Deflater();
//        //df.setLevel(Deflater.BEST_COMPRESSION);
//        df.setInput(input);
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream(input.length);
//        df.finish();
//        byte[] buff = new byte[1024];
//        while (!df.finished()) {
//            int count = df.deflate(buff);
//            baos.write(buff, 0, count);
//        }
//        baos.close();
//        byte[] output = baos.toByteArray();
//        return output;
//    }
//
//    public static void main(String[] args) throws IOException {
//        InsertTrades test = new InsertTrades();
//        test.run();
//    }
//}
package pivotal.au.fe.anzpoc.client;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import pivotal.au.fe.anzpoc.domain.TradeObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.Deflater;

public class InsertTrades
{
    private Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    private ClientCache cache = null;
    private final int BATCH_SIZE = 1000;
    private final int SAMPLE_SIZE = 100000;

    public InsertTrades()
    {
        ClientCacheFactory ccf = new ClientCacheFactory();
        ccf.set("cache-xml-file", "client.xml");
        ccf.set("log-level", "config");

        cache = ccf.create();
    }

    public void run() throws IOException
    {
        Region<String,TradeObject> tradeRegion = cache.getRegion("tradeRegion");
        Map<String, TradeObject> buffer = new HashMap<String, TradeObject>();

        long start = System.currentTimeMillis();

        TradeObject trade = null;
        Map<String, String> tradeMetaDataMap = null;

        for (int i = 1; i <= SAMPLE_SIZE; i++)
        {
            trade = null;
            trade = new TradeObject();
            trade.setTradeId(i + "");
            trade.setCreatedTimeStamp(new java.util.Date().getTime());
            String payloadDigest = "<trade><trade-id>" + i + "</trade-id></trade>";

            trade.setPayloadDigest(payloadDigest);
            //byte[] mybyte_array = "Pas demo".getBytes();
            //trade.setPayload(mybyte_array);

            trade.setPayload(compressBytes(payloadDigest));

            tradeMetaDataMap = null;
            tradeMetaDataMap = new HashMap<String, String>();
            tradeMetaDataMap.put("field1", "field1_" + i);
            tradeMetaDataMap.put("field2", "field2_" + i);
            tradeMetaDataMap.put("key.tradeid", i + "");
            tradeMetaDataMap.put("key.sourceSystem", "source_" + i);
            tradeMetaDataMap.put("key.validFrom ", "validFrom_" + i);

            trade.setTradeAttributes(tradeMetaDataMap);

            // place person into temp buffer
            buffer.put(String.valueOf(i), trade);

            if ((i % BATCH_SIZE) == 0)
            {
                // ready to insert a batch into our region
                tradeRegion.putAll(buffer);
                buffer.clear();
            }
        }

        // there may be existing records to flush so this takes care of it
        if (!buffer.isEmpty())
        {
            tradeRegion.putAll(buffer);
            buffer.clear();
        }

        long end = System.currentTimeMillis() - start;

        float elapsedTimeSec = end/1000F;

        logger.log (Level.INFO, String.format("Inserted %s TRADES", SAMPLE_SIZE));
        logger.log (Level.INFO, String.format("Elapsed time in seconds %f", elapsedTimeSec));

    }

    private byte[] compressBytes(String data) throws UnsupportedEncodingException, IOException {
        byte[] input = data.getBytes("UTF-8");
        Deflater df = new Deflater();
        //df.setLevel(Deflater.BEST_COMPRESSION);
        df.setInput(input);

        ByteArrayOutputStream baos = new ByteArrayOutputStream(input.length);
        df.finish();
        byte[] buff = new byte[1024];
        while(!df.finished())
        {
            int count = df.deflate(buff);
            baos.write(buff, 0, count);
        }
        baos.close();
        byte[] output = baos.toByteArray();
        return output;
    }

    public static void main(String[] args) throws IOException
    {
        InsertTrades test = new InsertTrades();
        test.run();
    }
}
