package pivotal.au.fe.anzpoc.domain.query.server.util;

import java.util.Map;

public class ServerCriteriaUtil {

    private Map<String,String> clientDataTypeRegionMapping;
    // our current default behaviour is to use historical reference dao. if this changes the getDao
    // lookup shuld become smarter.

    public Map<String, String> getClientDataTypeRegionMapping() {
        return clientDataTypeRegionMapping;
    }

    public void setClientDataTypeRegionMapping(Map<String, String> clientDataTypeRegionMapping) {
        this.clientDataTypeRegionMapping = clientDataTypeRegionMapping;
    }

    public String getRegion(String dataType) {
        if (clientDataTypeRegionMapping.containsKey(dataType)) {
            return clientDataTypeRegionMapping.get(dataType);
        } else {
            return dataType;
        }
    }

}
