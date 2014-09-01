package pivotal.au.fe.anzpoc.domain.query.common;

import com.gemstone.gemfire.pdx.PdxSerializable;


/**
 * 
 * Generic structure to house data returned from the cache.
 * Initially this is targeted for dynamic select queries that do
 * not return entire object stored in the cache but long-term
 * all cache data may be returned in such a structure.
 *
 */
public interface DataStructure extends PdxSerializable, EnterpriseData {

  /**
   * 
   * @param fieldName
   * @return The value for the given field
   */
  public Object get(String fieldName);
  /**
   * 
   * @return String[] of fields contained in the DataStructure
   */
  public String[] getFieldNames();
  /**
   * 
   * @return Object[] of fields contained in the DataStructure
   */
  public Object[] getFieldValues() ;
  /**
   * 
   * @param fieldName
   * @return FieldType for the given fieldName
   * <br>Not sure how useful; can return things like
   * java.lang.String which is not Language-neutral. Need to 
   * think this through.
   */
  public String getType(String fieldName);
  
}
