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
import java.util.List;
import java.util.Map;

public class POCDaoImpl implements POCDao
{
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource)
    {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void storeTradeObjectBatch(List<TradeObject> tradeEntries)
    {

       for (TradeObject trade: tradeEntries)
       {

           final List<TradeMetadata> tradeMetaDataList = createTradeMetaDataList(trade.getTradeAttributes(), trade.getTradeId());

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

    private List<TradeMetadata> createTradeMetaDataList (Map<String, String> tradeMetaDataMap, String tradeId)
    {
        List<TradeMetadata> tradeMetaDataList = new ArrayList<TradeMetadata>();
        TradeMetadata tradeMetaData = null;

        for (Map.Entry<String,String> entry : tradeMetaDataMap.entrySet())
        {
            tradeMetaData =
                    new TradeMetadata(tradeId, entry.getKey(), entry.getValue());

            tradeMetaDataList.add(tradeMetaData);
        }

        return tradeMetaDataList;
    }
}
