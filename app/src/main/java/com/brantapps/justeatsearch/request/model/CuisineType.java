package com.brantapps.justeatsearch.request.model;

/**
 * The available cusine types.
 * <p>
 * TODO: This should be changed to be an enumerated type.
 * </p>
 *
 * Created by davidb on 03/11/2014.
 */
public class CuisineType {
  private final long identifier;
  private final String type;


  /**
   * Constructs a cuisine type.
   *
   * @param identifier The cuisine identifier.
   * @param type The type of cuisine.
   */
  public CuisineType(final long identifier, final String type) {
    this.identifier = identifier;
    this.type = type;
  }


  /**
   * @return the cusine identifier.
   */
  public long getIdentifier() {
    return identifier;
  }


  /**
   * @return the cusine type.
   */
  public String getType() {
    return type;
  }

}
