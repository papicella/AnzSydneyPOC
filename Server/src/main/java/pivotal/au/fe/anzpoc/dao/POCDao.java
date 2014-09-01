package pivotal.au.fe.anzpoc.dao;

import pivotal.au.fe.anzpoc.domain.TradeObject;

import java.util.Collection;
import java.util.List;

public interface POCDao
{
    public void storeTradeObjectBatch(List<TradeObject> tradeEntries);

    Collection getResult(String sqlString);
}
