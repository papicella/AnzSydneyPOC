package pivotal.au.fe.anzpoc.domain.query.common;
/**
 * 
 * High-level representation of data in the Cache. May be a domain-specific structure
 * such as TradeMessage or PackageTrade or a dynamically generated option such as
 * a DataStructure
 *
 * <br><br>
 */
public interface EnterpriseData {
   public String getDataType();

}
