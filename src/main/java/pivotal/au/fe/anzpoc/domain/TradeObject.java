package pivotal.au.fe.anzpoc.domain;

import java.util.Arrays;
import java.util.Map;

public class TradeObject
{
    private String tradeId;
    private Map tradeAttributes;
    private String tradeType;
    private byte[] payload;
    private long createdTimeStamp;
    private String payloadDigest;

    public TradeObject()
    {
    }

    public TradeObject(String tradeId, Map tradeAttributes, String tradeType, byte[] payload, long createdTimeStamp, String payloadDigest) {
        this.tradeId = tradeId;
        this.tradeAttributes = tradeAttributes;
        this.tradeType = tradeType;
        this.payload = payload;
        this.createdTimeStamp = createdTimeStamp;
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

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    public long getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(long createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
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
                ", tradeType='" + tradeType + '\'' +
                ", payload=" + Arrays.toString(payload) +
                ", createdTimeStamp=" + createdTimeStamp +
                ", payloadDigest='" + payloadDigest + '\'' +
                '}';
    }
}
