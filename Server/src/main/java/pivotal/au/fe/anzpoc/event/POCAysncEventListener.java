package pivotal.au.fe.anzpoc.event;

import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.asyncqueue.AsyncEvent;
import com.gemstone.gemfire.cache.asyncqueue.AsyncEventListener;
import com.gemstone.gemfire.pdx.PdxInstance;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pivotal.au.fe.anzpoc.dao.POCDao;
import pivotal.au.fe.anzpoc.domain.TradeObject;
import pivotal.au.fe.anzpoc.main.ApplicationContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class POCAysncEventListener implements AsyncEventListener, Declarable
{
    private Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    private static ClassPathXmlApplicationContext applicationContext;

    @Override
    public boolean processEvents(List<AsyncEvent> asyncEvents)
    {
        logger.log (Level.INFO, String.format("Size of List<AsyncEvent> = %s", asyncEvents.size()));
        applicationContext = ApplicationContextHolder.getInstance();
        POCDao pocDaoImpl = (POCDao) applicationContext.getBean(POCDao.class);

        List<TradeObject> tradeEntries = new ArrayList<TradeObject>();

        for (AsyncEvent event: asyncEvents)
        {
            if (event.getOperation().isCreate())
            {
                Object deserializedValue = event.getDeserializedValue();
                if (deserializedValue instanceof TradeObject) {
                    TradeObject value = (TradeObject) deserializedValue;
                    tradeEntries.add(value);
                }
                else if (deserializedValue instanceof PdxInstance) {
                    PdxInstance value = (PdxInstance) deserializedValue;
                    tradeEntries.add((TradeObject) value.getObject());
                }

            }
        }

        pocDaoImpl.storeTradeObjectBatch(tradeEntries);

        logger.log (Level.INFO, String.format("Stored %s Trade Objects", tradeEntries.size()));

        return true;
    }

    @Override
    public void close()
    {
    }

    @Override
    public void init(Properties properties)
    {
    }
}
