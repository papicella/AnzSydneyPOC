package pivotal.au.fe.anzpoc.domain.query.client;


import pivotal.au.fe.anzpoc.domain.query.client.impl.ProjectionType;
import pivotal.au.fe.anzpoc.domain.query.common.Selection;
import pivotal.au.fe.anzpoc.domain.query.common.impl.SelectionImpl;

public final class Selections {
  /**
   * Constructs a Selection that will select the entire value from the
   * cache entry
   * @return Selection constructed
   */
  public static Selection value() {
    return new SelectionImpl(ProjectionType.VALUE, null, false);
  }
  /**
   * Constructs a Selection that will select the specified property on the value from the
   * cache entry
   * @return Selection constructed
   */
  public static Selection property(String propertyName) {
    return new SelectionImpl(ProjectionType.VALUE_PROPERTY,propertyName, false);
  }
  /**
   * Constructs a Selection that will select the entire key from the
   * cache entry
   * @return Selection constructed
   */
  public static Selection key() {
    return new SelectionImpl(ProjectionType.KEY,null, false);
  }
  /**
   * Constructs a Selection that will select the specified property on the key from the
   * cache entry
   * @return Selection constructed
   */
  public static Selection key(String keyPropertyName) {
    return new SelectionImpl(ProjectionType.KEY_PROPERTY,keyPropertyName, false);
  }
}
