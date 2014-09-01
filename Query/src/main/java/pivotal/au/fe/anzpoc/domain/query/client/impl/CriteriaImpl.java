package pivotal.au.fe.anzpoc.domain.query.client.impl;

import com.gemstone.gemfire.pdx.PdxReader;
import com.gemstone.gemfire.pdx.PdxWriter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pivotal.au.fe.anzpoc.domain.query.common.Criteria;
import pivotal.au.fe.anzpoc.domain.query.common.Criterion;
import pivotal.au.fe.anzpoc.domain.query.common.Projection;
import pivotal.au.fe.anzpoc.domain.query.common.Selection;
import pivotal.au.fe.anzpoc.domain.query.common.impl.Order;

import java.util.ArrayList;
import java.util.List;

public class CriteriaImpl implements Criteria {

  private String dataType;
  

  private List<Selection> projectionEntries = new ArrayList<Selection>();
  private List<ExpressionEntry> expressionEntries = new ArrayList<ExpressionEntry>();
  private List<Order> orders = new ArrayList<Order>();
  
  private static final Logger logger = LoggerFactory.getLogger(CriteriaImpl.class);

  public CriteriaImpl() {
  }
  public CriteriaImpl(String dataType) {
    this.dataType = dataType;
  }


  public Criteria add(Criterion expression) {
    add( this, expression );
    return this;
  }
  public Criteria add(Criteria criteriaInst, Criterion expression) {
    expressionEntries.add( new ExpressionEntry(expression, criteriaInst) );
    return this;
  }
  
  @Override
  public Criteria add(Projection projection) {
    projectionEntries.add((Selection) projection);
    return this;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  public List<ExpressionEntry> getExpressionEntries() {
    return this.expressionEntries;
  }
  


  public String getDataType() {
    return dataType;
  }
  public List<Selection> getProjectionEntries() {
    return projectionEntries;
  }
  public void fromData(PdxReader reader) {
    this.dataType = reader.readString("dataType");
    Object expressionEntryArrayAsObjects[]=reader.readObjectArray("expressionEntryArray");
    if(expressionEntryArrayAsObjects!=null){
      
      for (int i = 0; i < expressionEntryArrayAsObjects.length; i++) {
        expressionEntries.add((ExpressionEntry)expressionEntryArrayAsObjects[i]);
      }
    }
    Object projectionEntriesArrayAsObjects[]=reader.readObjectArray("projectionEntriesArray");
    if(projectionEntriesArrayAsObjects!=null){
      for (int i = 0; i < projectionEntriesArrayAsObjects.length; i++) {
        Selection selection=(Selection)projectionEntriesArrayAsObjects[i];
        this.projectionEntries.add(selection);
      }
    }
    Object orderArrayAsObjects[]=reader.readObjectArray("ordersArray");
    if(orderArrayAsObjects!=null){
      for (int i = 0; i < orderArrayAsObjects.length; i++) {
        Order order=(Order)orderArrayAsObjects[i];
        this.orders.add(order);
      }
    }
  }
  
  private List<Object> convertToObjectArray(List<ExpressionEntry> listEntry) {

    List<Object> listObject = new ArrayList<Object>();
    for(ExpressionEntry entry : listEntry )
    {
        listObject.add(entry);
    }
    return listObject;
}


  public void toData(PdxWriter writer) {
    writer.writeString("dataType", this.getDataType());
    writer.writeObjectArray("expressionEntryArray", convertToObjectArray(expressionEntries).toArray());
    Selection[] projectionEntriesArray = null;

    if (this.projectionEntries!= null && this.projectionEntries.size()>0) {
      
      Object[] entries = this.projectionEntries.toArray();
      Selection[] selections = new Selection[this.projectionEntries.size()];
      for (int i=0;i<this.projectionEntries.size();i++) {
        Object object = entries[i];
        selections[i] = (Selection) object;
        
      }
      
      projectionEntriesArray = selections;
      writer.writeObjectArray("projectionEntriesArray", selections);  
      
    }else{
      writer.writeObjectArray("projectionEntriesArray", null);  
    }
    writer.writeObjectArray("ordersArray", convertOrdersToObjectArray(orders).toArray());
    
  }
  
  private List<Object> convertOrdersToObjectArray(List<Order> orders) {
    List<Object> listObject = new ArrayList<Object>();
    for(Order order : orders )
    {
        listObject.add(order);
    }
    return listObject;
  }
  public String toString(){
	  return ToStringBuilder.reflectionToString(this);
  }
  @Override
  public Criteria setProjection(Projection projection) {
    logger.warn("setProjection is NOT implemented. Please use add(Projection) for now" );
    return null;
  }
  @Override
  public Criteria addOrder(Order order) {
    orders.add( order );
    return this;
  }
  public List<Order> getOrders() {
    return this.orders;
  }

}
