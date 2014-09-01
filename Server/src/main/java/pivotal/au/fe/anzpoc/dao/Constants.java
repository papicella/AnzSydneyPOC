package pivotal.au.fe.anzpoc.dao;

public interface Constants
{
    // trades table and trade_metadata

    // trade_id, trade_type, payload
    public final String INSERT_TRADE_SQL = "insert into anz.trades values (?, ?, ?, ?)";

    // trade_id, key, value
    public final String INSERT_TRADE_METADATA_SQL = "insert into anz.trade_metadata values (?, ?, ?)";

    public final String QUERY_TRADE = "select * from anz.trades where trade_id=?";
}
