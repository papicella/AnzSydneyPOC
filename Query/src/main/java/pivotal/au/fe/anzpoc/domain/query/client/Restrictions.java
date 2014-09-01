package pivotal.au.fe.anzpoc.domain.query.client;

import pivotal.au.fe.anzpoc.domain.query.client.impl.ExpressionImpl;
import pivotal.au.fe.anzpoc.domain.query.client.impl.ExpressionType;
import pivotal.au.fe.anzpoc.domain.query.common.Criterion;
import pivotal.au.fe.anzpoc.domain.query.common.Expression;
import pivotal.au.fe.anzpoc.domain.query.common.MatchMode;

import java.util.Collection;

public class Restrictions {
  
  /**
   * Apply an "equal" constraint to the named property
   * @param propertyName
   * @param value
   * @return Criterion
   */
  public static Criterion equal(String propertyName, Object value) {
    return new ExpressionImpl(propertyName, ExpressionType.EQUAL, value);
  }
  
  /**
   * Apply a "not equal" constraint to the named property
   * @param propertyName
   * @param value
   * @return Criterion
   */
  public static Criterion notEqual(String propertyName, Object value) {
    return new ExpressionImpl(propertyName, ExpressionType.NOT_EQUAL, value);
  }
  /**
   * Apply a "like" constraint to the named property
   * @param propertyName
   * @param value
   * @return Criterion
   */
  
  public static Criterion like(String propertyName, String value, MatchMode matchMode) {
    return new ExpressionImpl(propertyName, ExpressionType.LIKE, value, matchMode);
  }
  /**
   * A case-insensitive "like", similar to Postgres <tt>ilike</tt>
   * operator
   *
   * @param propertyName
   * @param value
   * @return Criterion
   */
  public static Criterion caseInsensitiveLike(String propertyName, String value, MatchMode matchMode) {
    return new ExpressionImpl(propertyName, ExpressionType.LIKE, value, true, matchMode);
  }
  /**
   * A case-insensitive "like", similar to Postgres <tt>ilike</tt>
   * operator
   *
   * @param propertyName
   * @param value
   * @return Criterion
   */
  public static Criterion caseInsensitiveLike(String propertyName, Object value) {
    return new ExpressionImpl(propertyName, ExpressionType.LIKE, value, true);
  }
  /**
   * Apply a "greater than" constraint to the named property
   * @param propertyName
   * @param value
   * @return Criterion
   */
  public static Criterion greaterThan(String propertyName, Object value) {
    return new ExpressionImpl(propertyName, ExpressionType.GREATER_THAN, value);
  }
  /**
   * Apply a "less than" constraint to the named property
   * @param propertyName
   * @param value
   * @return Criterion
   */
  public static Criterion lessThan(String propertyName, Object value) {
    return new ExpressionImpl(propertyName, ExpressionType.LESS_THAN, value);
  }
  /**
   * Apply a "less than or equal" constraint to the named property
   * @param propertyName
   * @param value
   * @return Criterion
   */
  public static Criterion lessThanEqual(String propertyName, Object value) {
    return new ExpressionImpl(propertyName, ExpressionType.LESS_THAN_EQUAL, value);
  }
  /**
   * Apply a "greater than or equal" constraint to the named property
   * @param propertyName
   * @param value
   * @return Criterion
   */
  public static Criterion greaterThanEqual(String propertyName, Object value) {
    return new ExpressionImpl(propertyName, ExpressionType.GREATER_THAN_EQUAL, value);
  }
  
  /**
   * Return the conjuction of two expressions
   *
   * @param lhs
   * @param rhs
   * @return Criterion
   */
  public static Criterion and(Criterion lhs, Criterion rhs) {
    return new ExpressionImpl(ExpressionType.AND, lhs, rhs);
  }
  /**
   * Return the disjuction of two expressions
   *
   * @param lhs
   * @param rhs
   * @return Criterion
   * @deprecated
   */
  public static Criterion or(Expression lhs, Expression rhs) {
    return new ExpressionImpl(ExpressionType.OR, lhs, rhs);
  }
  
  /**
   * Return the disjuction of two Criterion
   *
   * @param lhs
   * @param rhs
   * @return Criterion
   */
  public static Criterion or(Criterion lhs, Criterion rhs) {
    return new ExpressionImpl(ExpressionType.OR, lhs, rhs);
  }
  
  /**
   * Apply a "between" constraint to the named property
   * @param propertyName
   * @param lo value
   * @param hi value
   * @return ExpressionImpl
   */
  public static Expression between(String propertyName, Object lo, Object hi) {
    return new ExpressionImpl(propertyName, ExpressionType.BETWEEN, lo, hi);
  }
  /**
   * Apply an "in" constraint to the named property
   * @param propertyName
   * @param values
   * @return Criterion
   */
  public static Criterion in(String propertyName, Object[] values) {
    return new ExpressionImpl(propertyName, ExpressionType.IN,values);
  }
  /**
   * Apply an "in" constraint to the named property
   * @param propertyName
   * @param values
   * @return Criterion
   */
  public static Criterion in(String propertyName, Collection values) {
    return new ExpressionImpl( propertyName, ExpressionType.IN,values.toArray());
  }

}
