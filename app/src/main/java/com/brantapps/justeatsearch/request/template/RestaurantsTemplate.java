package com.brantapps.justeatsearch.request.template;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * API reflection of a restaurant.
 *
 * Created by davidb on 03/11/2014.
 */
public class RestaurantsTemplate {
  @SerializedName("ShortResultText") public String outcode;
  @SerializedName("Restaurants") public List<RestaurantTemplate> restaurants;

  public class RestaurantTemplate {
    @SerializedName("Id") public int id;
    @SerializedName("Name") public String name;
    @SerializedName("RatingStars") public float ratingStars;
    @SerializedName("CuisineTypes") public List<CuisineTypeTemplate> cuisineTypes;
    @SerializedName("IsOpenNow") public boolean isOpenNow;
    @SerializedName("IsOpenNowForDelivery") public boolean isOpenNowForDelivery;
    @SerializedName("IsOpenNowForCollection") public boolean isOpenNowForCollection;

    public class CuisineTypeTemplate {
      @SerializedName("Id") public int id;
      @SerializedName("Name") public String name;
      @SerializedName("SeoName") public float searchName;
    }
  }
}
