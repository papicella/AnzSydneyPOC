package pivotal.au.fe.anzpoc.domain.query.client.impl;

public enum ExpressionType {
  EQUAL,
  NOT_EQUAL,
  LIKE,
  IN,
  BETWEEN, 
  GREATER_THAN, 
  LESS_THAN, 
  LESS_THAN_EQUAL, 
  GREATER_THAN_EQUAL, 
  AND, // really an expression type?
  OR  // really an expression type?
}
