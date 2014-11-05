package com.brantapps.justeatsearch.request.model;

import com.brantapps.justeatsearch.request.api.Rateable;
import com.google.common.collect.Sets;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Set;

import javax.annotation.Nullable;

/**
 * A representation of a restaurant venue.
 *
 * Created by davidb on 03/11/2014.
 */
public class Restaurant implements Rateable {
  private final long identifier;
  private final String name;
  private Float rating;
  private Set<CuisineType> cuisineTypes;
  private boolean isOpen;
  private boolean isOpenForDelivery;
  private boolean isOpenForColletion;


  /**
   * Constructs a representation of a restaurant.
   *
   * @param identifier The restaurant identifier.
   * @param name The restaurant name.
   */
  public Restaurant(final long identifier, final String name) {
    this.identifier = identifier;
    this.name = name;
    this.cuisineTypes = Sets.newLinkedHashSet();
  }


  /**
   * @return the restaurant identifier.
   */
  public long getIdentifier() {
    return identifier;
  }


  /**
   * @return the restaurant name.
   */
  public String getName() {
    return name;
  }


  /**
   * @return the cuisine types associated with this restaurant.
   */
  public Set<CuisineType> getCuisineTypes() {
    return cuisineTypes;
  }


  /**
   * @return the restaurant rating or <code>null</code> if no ratings have been made.
   */
  @Override
  @Nullable
  public float getRating() {
    return rating;
  }


  /**
   * Assign the restaurant rating.
   *
   * @param rating The rating to assign to this restaurant.
   */
  @Override
  public void setRating(float rating) {
    this.rating = rating;
  }


  /**
   * @return <code>true</code> if the restaurant is open.
   */
  public boolean isOpen() {
    return isOpen;
  }


  /**
   * @param isOpen Assign whether this restaurant is open.
   */
  public void setOpen(final boolean isOpen) {
    this.isOpen = isOpen;
  }


  /**
   * @return <code>true</code> if the restaurant is open <strong>for delivery</strong>.
   */
  public boolean isOpenForDelivery() {
    return isOpenForDelivery;
  }


  /**
   * @param isOpenForDelivery Assign whether this restaurant is open for delivery.
   */
  public void setOpenForDelivery(final boolean isOpenForDelivery) {
    this.isOpenForDelivery = isOpenForDelivery;
  }


  /**
   * @return <code>true</code> if the restaurant is open <strong>for collection</strong>.
   */
  public boolean isOpenForColletion() {
    return isOpenForColletion;
  }


  /**
   * @param isOpenForColletion Assign whether this restaurant is open for collection.
   */
  public void setOpenForColletion(final boolean isOpenForColletion) {
    this.isOpenForColletion = isOpenForColletion;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(final Object object) {
    if(object instanceof Restaurant) {
      final Restaurant comparisonDevice = (Restaurant) object;
      return new EqualsBuilder()
          .append(identifier, comparisonDevice.identifier)
          .append(name, comparisonDevice.name)
          .isEquals();
    }

    return false;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return new HashCodeBuilder()
        .append(identifier)
        .append(name)
        .toHashCode();
  }
}
