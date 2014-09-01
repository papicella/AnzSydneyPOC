package pivotal.au.fe.anzpoc.domain.query.server.impl;

import com.gemstone.gemfire.pdx.PdxReader;
import com.gemstone.gemfire.pdx.PdxWriter;
import pivotal.au.fe.anzpoc.domain.query.server.ServerProjection;

import java.util.ArrayList;
import java.util.List;

public class ProjectionList implements ServerProjection {

    private List<ServerProjection> elements = new ArrayList<ServerProjection>();

    protected ProjectionList() {
    }

    public ProjectionList create() {
        return new ProjectionList();
    }

    public ProjectionList add(ServerProjection proj) {
        elements.add(proj);
        return this;
    }

    @Override
    public String toOqlString() {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < getLength(); i++) {
            ServerProjection proj = getProjection(i);
            buf.append(proj.toOqlString());
            if (i < elements.size() - 1) buf.append(", ");
        }
        return buf.toString();
    }

    public String toGroupOqlString() {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < getLength(); i++) {
            ServerProjection proj = getProjection(i);
            if (proj.isGrouped()) {
                buf.append(proj.toGroupOqlString())
                        .append(", ");
            }
        }
        if (buf.length() > 2) buf.setLength(buf.length() - 2); //pull off the last ", "
        return buf.toString();
    }


    public ServerProjection getProjection(int i) {
        return (ServerProjection) elements.get(i);
    }

    public int getLength() {
        return elements.size();
    }

    @Override
    public boolean isGrouped() {
        for (int i = 0; i < getLength(); i++) {
            if (getProjection(i).isGrouped()) return true;
        }
        return false;
    }

    @Override
    public void fromData(PdxReader arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void toData(PdxWriter arg0) {
        // TODO Auto-generated method stub

    }


}
