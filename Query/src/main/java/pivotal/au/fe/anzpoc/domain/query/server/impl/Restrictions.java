package pivotal.au.fe.anzpoc.domain.query.server.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pivotal.au.fe.anzpoc.domain.query.client.impl.ExpressionType;
import pivotal.au.fe.anzpoc.domain.query.common.Criterion;
import pivotal.au.fe.anzpoc.domain.query.common.Expression;
import pivotal.au.fe.anzpoc.domain.query.common.MatchMode;
import pivotal.au.fe.anzpoc.domain.query.server.ServerCriterion;
import pivotal.au.fe.anzpoc.domain.query.server.expression.BetweenExpression;
import pivotal.au.fe.anzpoc.domain.query.server.expression.InExpression;
import pivotal.au.fe.anzpoc.domain.query.server.expression.LikeExpression;
import pivotal.au.fe.anzpoc.domain.query.server.expression.LogicalExpression;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Restrictions implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(Restrictions.class);

    Restrictions() {
        //cannot be instantiated
    }

    /**
     * Apply an "equal" constraint to the named property
     *
     * @param propertyName
     * @param value
     * @return ServerCriterion
     */
    public static SimpleExpression equal(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, "=");
    }

    /**
     * Apply a "not equal" constraint to the named property
     *
     * @param propertyName
     * @param value
     * @return ServerCriterion
     */
    public static SimpleExpression notEqual(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, "<>");
    }

    /**
     * Apply a "like" constraint to the named property
     *
     * @param propertyName
     * @param value
     * @return ServerCriterion
     */

    public static SimpleExpression like(String propertyName, String value, MatchMode matchMode) {
        return new SimpleExpression(propertyName, matchMode.toMatchString(value), " like ");
    }

    /**
     * Apply a "greater than" constraint to the named property
     *
     * @param propertyName
     * @param value
     * @return ServerCriterion
     */
    public static SimpleExpression greaterThan(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, ">");
    }

    /**
     * Apply a "less than" constraint to the named property
     *
     * @param propertyName
     * @param value
     * @return ServerCriterion
     */
    public static SimpleExpression lessThan(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, "<");
    }

    /**
     * Apply a "less than or equal" constraint to the named property
     *
     * @param propertyName
     * @param value
     * @return ServerCriterion
     */
    public static SimpleExpression lessThanEqual(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, "<=");
    }

    /**
     * Apply a "greater than or equal" constraint to the named property
     *
     * @param propertyName
     * @param value
     * @return ServerCriterion
     */
    public static SimpleExpression greaterThanEqual(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, ">=");
    }
    /**
     * Apply a "between" constraint to the named property
     * @param propertyName
     * @param lo value
     * @param hi value
     * @return ServerCriterion
     */


    /**
     * Return the conjuction of two expressions
     *
     * @param lhs
     * @param rhs
     * @return ServerCriterion
     */
    public static LogicalExpression and(ServerCriterion lhs, ServerCriterion rhs) {
        return new LogicalExpression(lhs, rhs, "AND");
    }

    /**
     * Return the disjuction of two expressions
     *
     * @param lhs
     * @param rhs
     * @return ServerCriterion
     */
    public static LogicalExpression or(ServerCriterion lhs, ServerCriterion rhs) {
        return new LogicalExpression(lhs, rhs, "OR");
    }

    /**
     * Apply a "between" constraint to the named property
     *
     * @param propertyName
     * @param lo           value
     * @param hi           value
     * @return ServerCriterion
     */
    public static ServerCriterion between(String propertyName, Object lo, Object hi) {
        return new BetweenExpression(propertyName, lo, hi);
    }

    /**
     * Apply an "in" constraint to the named property
     *
     * @param propertyName
     * @param values
     * @return ServerCriterion
     */
    public static ServerCriterion in(String propertyName, Object[] values) {
        return new InExpression(propertyName, values);
    }

    /**
     * Apply an "in" constraint to the named property
     *
     * @param propertyName
     * @param values
     * @return ServerCriterion
     */
    public static Criterion in(String propertyName, Collection values) {
        return new InExpression(propertyName, values.toArray());
    }

    /**
     * Add
     *
     * @param expression Client expression. Might be better to transform
     *                   prior to calling so server-side restrictions does not interact
     *                   with client model.
     * @return
     */
    public static Criterion add(Expression expression) {

        ExpressionClassification expressionClassification = classifications.get(expression.getExpressionType());


        switch (expressionClassification) {
            case SIMPLE:
                return new SimpleExpression(expression.getPropertyName(),
                        expression.getValues().get(0),
                        expression.isIgnoreCase(),
                        expressionSyntax.get(expression.getExpressionType())
                );

            case IN:
                return new InExpression(expression.getPropertyName(),
                        expression.getValues().toArray());

            case LIKE:
                return new LikeExpression(expression.getPropertyName(),
                        (String) expression.getValues().get(0),
                        expression.getMatchMode()
                );
            case BETWEEN:
                return new BetweenExpression(expression.getPropertyName(),
                        expression.getValues().get(0),
                        expression.getValues().get(1)
                );
            default:
                Criterion lhs = add((Expression) expression.getValues().get(0));
                logger.debug("lhs: " + lhs);
                Criterion rhs = add((Expression) expression.getValues().get(1));
                logger.debug("rhs: " + rhs);
                return new LogicalExpression(
                        (ServerCriterion) add((Expression) expression.getValues().get(0)),
                        (ServerCriterion) add((Expression) expression.getValues().get(1)),
                        expression.getExpressionType().toString()
                );
        }
    }

    enum ExpressionClassification {
        SIMPLE,
        IN,
        LIKE,
        BETWEEN,
        LOGICAL
    }

    private static Map<ExpressionType, ExpressionClassification> classifications = new HashMap<ExpressionType, ExpressionClassification>();

    static {
        classifications.put(ExpressionType.AND, ExpressionClassification.LOGICAL);
        classifications.put(ExpressionType.OR, ExpressionClassification.LOGICAL);
        classifications.put(ExpressionType.BETWEEN, ExpressionClassification.BETWEEN);
        classifications.put(ExpressionType.EQUAL, ExpressionClassification.SIMPLE);
        classifications.put(ExpressionType.GREATER_THAN, ExpressionClassification.SIMPLE);
        classifications.put(ExpressionType.GREATER_THAN_EQUAL, ExpressionClassification.SIMPLE);
        classifications.put(ExpressionType.LESS_THAN, ExpressionClassification.SIMPLE);
        classifications.put(ExpressionType.LESS_THAN_EQUAL, ExpressionClassification.SIMPLE);
        classifications.put(ExpressionType.IN, ExpressionClassification.IN);
        classifications.put(ExpressionType.LIKE, ExpressionClassification.LIKE);
        classifications.put(ExpressionType.NOT_EQUAL, ExpressionClassification.SIMPLE);

    }

    private static Map<ExpressionType, String> expressionSyntax = new HashMap<ExpressionType, String>();

    static {
        expressionSyntax.put(ExpressionType.EQUAL, "=");
        expressionSyntax.put(ExpressionType.GREATER_THAN, ">");
        expressionSyntax.put(ExpressionType.GREATER_THAN_EQUAL, ">=");
        expressionSyntax.put(ExpressionType.LESS_THAN, "<");
        expressionSyntax.put(ExpressionType.LESS_THAN_EQUAL, "<=");
        expressionSyntax.put(ExpressionType.NOT_EQUAL, "<>");


    }


}
