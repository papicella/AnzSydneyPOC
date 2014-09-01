package pivotal.au.fe.anzpoc.dao;

import com.sun.jmx.remote.internal.ArrayQueue;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import pivotal.au.fe.anzpoc.domain.TradeMetadata;
import pivotal.au.fe.anzpoc.domain.TradeObject;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class POCDaoImpl implements POCDao {
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    @Transactional
    public void storeTradeObjectBatch(final List<TradeObject> tradeEntries) {

        final List<TradeMetadata> tradeMetaDataList = createTradeMetaDataList(tradeEntries);

        // insert single trade
        jdbcTemplate.batchUpdate
                (Constants.INSERT_TRADE_SQL,
                        new BatchPreparedStatementSetter() {
                            public void setValues(PreparedStatement ps, int i) throws SQLException {
                                ps.setString(1, "" + tradeEntries.get(i).getTradeId());
                                ps.setString(2, tradeEntries.get(i).getPayloadDigest());
                                ps.setBytes(3, tradeEntries.get(i).getPayload());
                                ps.setString(4, "" + tradeEntries.get(i).getCreatedTimeStamp());
                            }

                            public int getBatchSize() {
                                return tradeEntries.size();
                            }
                        }
                );

        // insert trade meta data entries
        int[] updateCounts =
                jdbcTemplate.batchUpdate
                        (Constants.INSERT_TRADE_METADATA_SQL,
                                new BatchPreparedStatementSetter() {
                                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                                        ps.setString(1,
                                                "" + tradeMetaDataList.get(i).getTradeId().toString());
                                        ps.setString(2, tradeMetaDataList.get(i).getKey());
                                        ps.setString(3, tradeMetaDataList.get(i).getValue());
                                    }

                                    public int getBatchSize() {
                                        return tradeMetaDataList.size();
                                    }
                                }
                        );
    }

    @Override
    public Collection getResult(String sqlString) {
        List<TradeObject> result = new ArrayList<TradeObject>();
        List<TradeMetadata> tradeMetadata = new ArrayList<TradeMetadata>();
        tradeMetadata = jdbcTemplate.query(sqlString, new BeanPropertyRowMapper<TradeMetadata>(TradeMetadata.class));
        String prevTradeId = "";
        Map<String, String> queryField = new HashMap<String, String>();
        Map<String, Map<String, String>> tradeObjects = new TreeMap<String, Map<String, String>>();
        for (TradeMetadata metadata : tradeMetadata) {
            if (!prevTradeId.equals(metadata.getTradeId())) {
                if (!prevTradeId.isEmpty()) {
                    tradeObjects.put(metadata.getTradeId(), queryField);
                    queryField.clear();
                } else {
                    queryField.put(metadata.getKey(), metadata.getValue());
                }
            }
            prevTradeId = metadata.getTradeId();
        }
        tradeObjects.put(prevTradeId,queryField);

        for (String tradeId : tradeObjects.keySet()) {
            TradeObject tradeObject =
            jdbcTemplate.queryForObject(Constants.QUERY_TRADE, new Object[]{tradeId}, new BeanPropertyRowMapper<TradeObject>(TradeObject.class));
            tradeObject.setTradeAttributes(tradeObjects.get(tradeId));
            result.add(tradeObject);
        }

        return result;
    }

    private List<TradeMetadata> createTradeMetaDataList(final List<TradeObject> tradeEntries) {
        List<TradeMetadata> tradeMetaDataList = new ArrayList<TradeMetadata>();
        TradeMetadata tradeMetaData = null;
        for (TradeObject tradeEntry : tradeEntries) {
            Map<String, String> tradeAttributes = tradeEntry.getTradeAttributes();

            for (Map.Entry<String, String> entry : tradeAttributes.entrySet()) {
                tradeMetaData =
                        new TradeMetadata(tradeEntry.getTradeId(), entry.getKey(), entry.getValue());

                tradeMetaDataList.add(tradeMetaData);
            }
        }
        return tradeMetaDataList;
    }
}
