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

public class POCDaoImpl implements POCDao {
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
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
                                                ""+tradeMetaDataList.get(i).getTradeId().toString());
                                        ps.setString(2, tradeMetaDataList.get(i).getKey());
                                        ps.setString(3, tradeMetaDataList.get(i).getValue());
                                    }

                                    public int getBatchSize() {
                                        return tradeMetaDataList.size();
                                    }
                                }
                        );
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
