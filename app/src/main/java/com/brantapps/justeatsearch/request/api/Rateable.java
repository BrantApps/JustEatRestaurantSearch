package com.brantapps.justeatsearch.request.api;

/**
 * Contract for rating venues.
 *
 * Created by davidb on 03/11/2014.
 */
public interface Rateable {

  /**
   * Retrieve the rating.
   */
  float getRating();

  /**
   * Assign a rating.
   *
   * @param rating The rating to assign.
   */
  void setRating(final float rating);
}
