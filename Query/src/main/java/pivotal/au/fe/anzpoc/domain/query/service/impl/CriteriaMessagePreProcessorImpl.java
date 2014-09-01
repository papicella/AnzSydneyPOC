package pivotal.au.fe.anzpoc.domain.query.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pivotal.au.fe.anzpoc.domain.query.client.impl.ExpressionType;
import pivotal.au.fe.anzpoc.domain.query.common.DataStructure;
import pivotal.au.fe.anzpoc.domain.query.common.Expression;
import pivotal.au.fe.anzpoc.domain.query.common.Projection;
import pivotal.au.fe.anzpoc.domain.query.common.Selection;
import pivotal.au.fe.anzpoc.domain.query.common.impl.SelectionImpl;
import pivotal.au.fe.anzpoc.domain.query.server.impl.PropertyProjection;
import pivotal.au.fe.anzpoc.domain.query.server.impl.ServerCriteriaImpl;
import pivotal.au.fe.anzpoc.domain.query.service.CriteriaMessagePreProcessor;

import java.beans.Introspector;
import java.util.ArrayList;
import java.util.List;

public class CriteriaMessagePreProcessorImpl implements CriteriaMessagePreProcessor {

    private static final Logger logger = LoggerFactory.getLogger(CriteriaMessagePreProcessorImpl.class);
    private static final int DEPTH_LIMIT = 10;

    public Expression transformExpression(String dataType, Expression expression) {
        // There is a potential for infinite recursion adding depth meter to bail out if it gets too deep.
        return transformExpression(dataType, expression, 0);
    }

    @Override
    public DataStructure transformDataStructure(ServerCriteriaImpl criteria, DataStructure dataStructure) {
        for (int i = 0; i < dataStructure.getFieldNames().length; i++) {
            String fieldName = dataStructure.getFieldNames()[i];
            Projection projection = criteria.getCriteriaImpl().getProjectionEntries().get(i);
            String projectionPropertyName;
            if (projection instanceof PropertyProjection) {
                PropertyProjection propertyProjection = (PropertyProjection) projection;
                projectionPropertyName = propertyProjection.getPropertyName();
            } else {
                projectionPropertyName = fieldName;
            }
            dataStructure.getFieldNames()[i] = projectionPropertyName;
        }
        return dataStructure;
    }

    @Override
    public Selection transformSelection(String dataType, Selection selection) {
        return new SelectionImpl(selection.getProjectionType(), selection.getPropertyName(), selection.isGrouped());
    }

    private Expression transformExpression(String dataType, Expression expression, int depth) {
        if (depth > DEPTH_LIMIT) {
            throw new RuntimeException("CriteriaMessagePreProcessor has reached it's recursive depth limit");
        }
        // Process AND and OR statements
        if (expression.getExpressionType().equals(ExpressionType.AND)
                || expression.getExpressionType().equals(ExpressionType.OR)) {
            return processLogicalStatements(dataType, expression, depth);
        }
        expression = processExpression(dataType, expression);
        return expression;

    }

    private Expression processExpression(String dataType, Expression expression) {

        String serverPropertyName = null;
        serverPropertyName = expression.getPropertyName();
        expression.setPropertyName(serverPropertyName);

        return expression;
    }

    /**
     * API to convert method name to corresponding fieldName in javabean
     *
     * @param methodName
     * @return fieldName
     */
    private String resolveFieldName(String methodName) {
        String fieldName = methodName;

        if (methodName != null && methodName.contains("()")) {
            fieldName = methodName.trim();
            if (fieldName.length() > 3 && fieldName.startsWith("get"))
                fieldName = fieldName.substring(3, fieldName.length() - 2);

            if (fieldName.length() > 2 && fieldName.startsWith("is"))
                fieldName = fieldName.substring(2, fieldName.length() - 2);

            fieldName = Introspector.decapitalize(fieldName);
        }
        return fieldName;
    }

    private boolean isExplicitMethodCall(String serverPropertyName) {
        return serverPropertyName.contains("()");
    }

    private Expression processLogicalStatements(String dataType, Expression expression, int depth) {
        List<Object> transformedSubExpressions = new ArrayList<Object>();
        for (Object objectExpression : expression.getValues()) {
            // TODO: dangerous. come back to this.
            Expression subExpressionTransformed = transformExpression(dataType,
                    (Expression) objectExpression, depth + 1);
            transformedSubExpressions.add(subExpressionTransformed);

            logger.trace("subExpressionTransformed: " + subExpressionTransformed);

        }
        expression.setValues(transformedSubExpressions);
        return expression;
    }
}
