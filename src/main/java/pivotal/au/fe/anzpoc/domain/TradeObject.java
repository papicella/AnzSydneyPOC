package pivotal.au.fe.anzpoc.domain;

import java.util.Arrays;
import java.util.Map;

public class TradeObject
{
    private String tradeId;
    private Map tradeAttributes;
    private long createdTimeStamp;
    private byte[] payload;
    private String payloadDigest;

    public TradeObject()
    {
    }

    public TradeObject(String tradeId, Map tradeAttributes, long createdTimeStamp, byte[] payload, String payloadDigest) {
        this.tradeId = tradeId;
        this.tradeAttributes = tradeAttributes;
        this.createdTimeStamp = createdTimeStamp;
        this.payload = payload;
        this.payloadDigest = payloadDigest;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public Map getTradeAttributes() {
        return tradeAttributes;
    }

    public void setTradeAttributes(Map tradeAttributes) {
        this.tradeAttributes = tradeAttributes;
    }

    public long getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(long createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    public String getPayloadDigest() {
        return payloadDigest;
    }

    public void setPayloadDigest(String payloadDigest) {
        this.payloadDigest = payloadDigest;
    }

    @Override
    public String toString() {
        return "TradeObject{" +
                "tradeId='" + tradeId + '\'' +
                ", tradeAttributes=" + tradeAttributes +
                ", createdTimeStamp=" + createdTimeStamp +
                ", payload=" + Arrays.toString(payload) +
                ", payloadDigest='" + payloadDigest + '\'' +
                '}';
    }
}
