package pivotal.au.fe.anzpoc.dao;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import pivotal.au.fe.anzpoc.domain.TradeMetadata;
import pivotal.au.fe.anzpoc.domain.TradeObject;
import pivotal.au.fe.anzpoc.main.ApplicationContextHolder;

import javax.sql.DataSource;
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
        List<TradeMetadata> tradeMetadatas = new ArrayList<TradeMetadata>();
        setDataSource((DataSource) ApplicationContextHolder.getInstance().getBean("dataSource"));
        tradeMetadatas = jdbcTemplate.query(sqlString, new BeanPropertyRowMapper<TradeMetadata>(TradeMetadata.class));
        if (tradeMetadatas.size() == 0) {
            return new ArrayList();
        }
        Map<String, String> queryField = new HashMap<String, String>();
        Map<String, Map<String, String>> tradeObjects = new TreeMap<String, Map<String, String>>();
        TreeSet<String> tradeIds = new TreeSet<String>();
        for (TradeMetadata tradeMetadata : tradeMetadatas) {
            tradeIds.add(tradeMetadata.getTradeId());
        }
        String metaSQL = generateSQLStringForTradeIds(tradeIds, "anz.trade_metadata");
        tradeMetadatas = jdbcTemplate.query(metaSQL, new BeanPropertyRowMapper<TradeMetadata>(TradeMetadata.class));
        String prevTradeId = "";
        for (TradeMetadata metadata : tradeMetadatas) {
            if (!prevTradeId.equals(metadata.getTradeId())) {
                if (!prevTradeId.isEmpty()) {
                    tradeObjects.put(prevTradeId, queryField);
                    queryField = new HashMap<String, String>();
                    queryField.put(metadata.getKey(), metadata.getValue());
                } else {
                    queryField.put(metadata.getKey(), metadata.getValue());
                }
            } else {
                queryField.put(metadata.getKey(), metadata.getValue());
            }
            prevTradeId = metadata.getTradeId();
        }
        tradeObjects.put(prevTradeId, queryField);

        String tradeSQL = generateSQLStringForTradeIds(tradeIds, "anz.trades");

        List<TradeObject> trades = new ArrayList<TradeObject>();
        trades = jdbcTemplate.query(tradeSQL, new BeanPropertyRowMapper<TradeObject>(TradeObject.class));
        for (TradeObject trade : trades) {
            trade.setTradeAttributes(tradeObjects.get(trade.getTradeId()));
        }
        return trades;
    }

    private String generateSQLStringForTradeIds(TreeSet<String> tradeIds, String tableName) {
        int count = 0;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("select * from " + tableName + " where trade_id in(");
        for (String tradeId : tradeIds) {
            count++;
            stringBuffer.append("'" + tradeId + "'");
            if (count < tradeIds.size()) {
                stringBuffer.append(",");
            }
        }
        stringBuffer.append(") order by trade_id");
        return stringBuffer.toString();
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
