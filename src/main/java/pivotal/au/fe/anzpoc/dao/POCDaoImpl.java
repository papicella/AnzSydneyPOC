package pivotal.au.fe.anzpoc.dao;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import pivotal.au.fe.anzpoc.domain.TradeMetadata;
import pivotal.au.fe.anzpoc.domain.TradeObject;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class POCDaoImpl implements POCDao
{
    private Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private List<TradeMetadata> tradeMetaDataList = new ArrayList<TradeMetadata>();
    private Map<String, String> tradeMetaDataMap = new HashMap<String, String>();

    public void setDataSource(DataSource dataSource)
    {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void storeTradeObjectBatch(List<TradeObject> tradeEntries)
    {

      tradeMetaDataMap = new HashMap<String, String>();

       for (TradeObject trade: tradeEntries)
       {
           tradeMetaDataList = new ArrayList<TradeMetadata>();
           tradeMetaDataMap = new HashMap<String, String>();
           tradeMetaDataMap = trade.getTradeAttributes();
           TradeMetadata tradeMetaData = null;
           for (Map.Entry<String,String> entry : tradeMetaDataMap.entrySet())
           {
               tradeMetaData =
                       new TradeMetadata(trade.getTradeId(), entry.getKey(), entry.getValue());

               tradeMetaDataList.add(tradeMetaData);
           }

           // insert single trade
           jdbcTemplate.update
                   (Constants.INSERT_TRADE_SQL,
                           new BigDecimal(trade.getTradeId()),
                                          trade.getPayloadDigest(),
                                          trade.getPayload(),
                                          new String("" + trade.getCreatedTimeStamp()));

           // insert trade meta data entries
           int[] updateCounts =
                   jdbcTemplate.batchUpdate
                           (Constants.INSERT_TRADE_METADATA_SQL,
                                   new BatchPreparedStatementSetter()
                                   {
                                       public void setValues(PreparedStatement ps, int i) throws SQLException
                                       {
                                           ps.setBigDecimal(1,
                                                   new BigDecimal(tradeMetaDataList.get(i).getTradeId().toString()));
                                           ps.setString(2, tradeMetaDataList.get(i).getKey());
                                           ps.setString(3, tradeMetaDataList.get(i).getValue());
                                       }

                                       public int getBatchSize()
                                       {
                                           return tradeMetaDataList.size();
                                       }
                                   }
                           );
       }

    }
}
