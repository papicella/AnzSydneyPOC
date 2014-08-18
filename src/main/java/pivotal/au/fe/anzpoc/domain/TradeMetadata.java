package pivotal.au.fe.anzpoc.domain;

public class TradeMetadata
{
    private String tradeId;
    private String key;
    private String value;

    public TradeMetadata()
    {
    }

    public TradeMetadata(String tradeId, String key, String value) {
        this.tradeId = tradeId;
        this.key = key;
        this.value = value;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "TradeMetadata{" +
                "tradeId='" + tradeId + '\'' +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
