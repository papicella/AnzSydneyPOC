package pivotal.au.fe.anzpoc.domain.query.server.impl;

import com.gemstone.gemfire.pdx.PdxReader;
import com.gemstone.gemfire.pdx.PdxWriter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import pivotal.au.fe.anzpoc.domain.query.common.Criteria;
import pivotal.au.fe.anzpoc.domain.query.common.Criterion;
import pivotal.au.fe.anzpoc.domain.query.common.Projection;
import pivotal.au.fe.anzpoc.domain.query.common.impl.Order;
import pivotal.au.fe.anzpoc.domain.query.server.ServerProjection;

import java.util.ArrayList;
import java.util.List;

public class CriteriaContainerImpl implements Criteria {
  
  private ServerProjection serverProjection;
  private Criteria projectionCriteria;
  

  // simplify - but will this support subqueries, etc.?
  // let's go simple for now.
  private List<ServerProjection> projectionEntries = new ArrayList<ServerProjection>();
  
  
  private List<CriterionEntry> criterionEntries = new ArrayList<CriterionEntry>();
  private List<Order> orders = new ArrayList<Order>();
  
  public CriteriaContainerImpl() {
  }


  @Override
  public Criteria add(Criterion expression) {
    add( this, expression );
    return this;
  }
  public Criteria add(Criteria criteriaInst, Criterion expression) {
    criterionEntries.add( new CriterionEntry(expression, criteriaInst) );
    return this;
  }

  @Override
  public Criteria addOrder(Order order) {
    orders.add( order );
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

  public List<CriterionEntry> getCriterionEntries() {
    return this.criterionEntries;
  }
  

  public List<ServerProjection> getProjectionEntries() {
    return projectionEntries;
  }


  public void setProjectionEntries(List<ServerProjection> projectionEntries) {
    this.projectionEntries = projectionEntries;
  }


  @Override
  public Criteria setProjection(Projection projection) {
    this.serverProjection = (ServerProjection) projection;
    this.projectionCriteria = this;
//    setResultTransformer( PROJECTION );
    return this;
  }


  public ServerProjection getProjection() {
    return this.serverProjection;
  }

  public void fromData(PdxReader reader) {
    Object oeobj[]=reader.readObjectArray("ordersArray");
    if(oeobj!=null){
      
      for (int i = 0; i < oeobj.length; i++) {
        orders.add((Order)oeobj[i]);
      }
    }
	  Object obj[]=reader.readObjectArray("criterionEntryArray");
	  if(obj!=null){
		  for (int i = 0; i < obj.length; i++) {
			  criterionEntries.add((CriterionEntry)obj[i]);
		  }
	  }
	 
  }
  
  private List<Object> convertToObjectArray(List<CriterionEntry> listEntry)  {
      List<Object> listObject = new ArrayList<Object>();
      for(CriterionEntry entry : listEntry )
      {
          listObject.add(entry);
      }
      return listObject;
  }

  public void toData(PdxWriter writer) {
    writer.writeObjectArray("ordersArray", convertOrdersToObjectArray(orders).toArray());
	  writer.writeObjectArray("criterionEntryArray", convertToObjectArray(criterionEntries).toArray());
  }
  
  private List<Object> convertOrdersToObjectArray(List<Order> orders) {
    List<Object> listObject = new ArrayList<Object>();
    for(Order entry : orders ){
        listObject.add(entry);
    }
    return listObject;
  }


  public String toString(){
	  return ToStringBuilder.reflectionToString(this);
  }


  @Override
  public Criteria add(Projection projection) {
    projectionEntries.add((ServerProjection) projection);
    return this;
  }


  public List<Order> getOrders() {
    return this.orders;
  }
}
