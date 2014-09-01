package pivotal.au.fe.anzpoc.domain.query.server.impl;


import com.gemstone.gemfire.pdx.PdxReader;
import com.gemstone.gemfire.pdx.PdxWriter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pivotal.au.fe.anzpoc.domain.query.client.impl.CriteriaImpl;
import pivotal.au.fe.anzpoc.domain.query.common.Criteria;
import pivotal.au.fe.anzpoc.domain.query.common.Criterion;
import pivotal.au.fe.anzpoc.domain.query.common.Projection;
import pivotal.au.fe.anzpoc.domain.query.common.impl.Order;
import pivotal.au.fe.anzpoc.domain.query.server.util.ServerCriteriaUtil;

public class ServerCriteriaImpl implements Criteria {

   private static Logger logger = LoggerFactory.getLogger(ServerCriteriaImpl.class);


	// set of region names? main region + additional imports?
	// for now support only 1 region.
	private String regionName;

	//This could be empty
	private String dataType;

	// probably don't need the composition.. was shared with client. let's see
	// what consultants think about lightweight client.
	private CriteriaContainerImpl criteriaImpl;


	public ServerCriteriaImpl() {}

	public ServerCriteriaImpl(String regionName) {
		this.criteriaImpl = new CriteriaContainerImpl();
		this.regionName = regionName;
	}

	public ServerCriteriaImpl(final String regionName, final String dataType) {
		this.criteriaImpl = new CriteriaContainerImpl();
		this.regionName = regionName;
		this.dataType = dataType;
	}

	public ServerCriteriaImpl(final Criteria clientCriteria) {
		this.criteriaImpl = new CriteriaContainerImpl();
        ServerCriteriaUtil serverCriteriaUtil = new ServerCriteriaUtil();
        this.dataType = ((CriteriaImpl)clientCriteria).getDataType();
		this.regionName = serverCriteriaUtil.getRegion(dataType);
	}


    public static ServerCriteriaImpl getServerCriteriaImplFromDataType(final String dataType) {
        ServerCriteriaUtil serverCriteriaUtil = new ServerCriteriaUtil();
        return new ServerCriteriaImpl(serverCriteriaUtil.getRegion(dataType), dataType);
    }


	public ServerCriteriaImpl(final String regionName, final CriteriaContainerImpl criteriaContainer) {
		this.criteriaImpl = criteriaContainer;
		this.regionName = regionName;
	}

	@Override
	public Criteria setProjection(final Projection projection) {
		return criteriaImpl.setProjection(projection);
	}

	@Override
	public Criteria add(final Criterion criterion) {
		return criteriaImpl.add(criterion);
	}

	@Override
	public Criteria addOrder(final Order order) {
		return criteriaImpl.addOrder(order);
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(final String regionName) {
		this.regionName = regionName;
	}

	public CriteriaContainerImpl getCriteriaImpl() {
		return criteriaImpl;
	}

	public String getDataType() {
		return dataType;
	}

	/**
	 * In initial days Region name and dataype was interchangeable. DataType may not be available for all the clients.
	 * Hence this method would return dataType if it is available, otherwise for backward compatibility, this returns regionName.
	 *
	 * @return dataType
	 */
	public String getDataTypeOrRegionName() {
		if(dataType != null && StringUtils.isNotBlank(dataType)) {
			return dataType;
		}
		return regionName;
	}

	public void setCriteriaImpl(final CriteriaContainerImpl criteriaImpl) {
		this.criteriaImpl = criteriaImpl;
	}

	@Override
	public void fromData(PdxReader arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void toData(PdxWriter arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Criteria add(Projection projection) {
		return this.criteriaImpl.add(projection);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
