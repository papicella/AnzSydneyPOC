package pivotal.au.fe.anzpoc.domain.query.server.impl;

public final class Projections {
  
  private Projections() {
    //cannot be instantiated
  }
  
  /**
   * A projected property value
   */
  public static PropertyProjection property(String propertyName) {
    return new PropertyProjection(propertyName);
  }
  
  /**
   * A projected property value
   */
  public static KeyProjection keyProjection() {
    return new KeyProjection();
  }
  /**
   * Create a new projection list
   */
  public static ProjectionList projectionList() {
    return new ProjectionList();
  }

  public static KeyProjection keyProjection(String propertyName) {
    return new KeyProjection(propertyName);
  }

  public static ValueProjection value() {
    return new ValueProjection();
  }



}
