package com.brantapps.justeatsearch.request.api;

import com.brantapps.justeatsearch.request.template.RestaurantsTemplate;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

/**
 * Contract for classes wishing to provide restaurant search facilities.
 *
 * Created by davidb on 03/11/2014.
 */
public interface RestaurantSearchCommand {

  /**
   * Perform an (asynchronous) search for venues using an outcode.
   *
   * @param outcode The outcode for the target region, for example "SE19".
   * @param responseCallback The callback to invoke upon successfully completing the request.
   */
  @GET("/restaurants")
  @Headers({
      "Accept-Tenant: uk",
      "Accept-Language: en-GB",
      "Authorization: Basic VGVjaFRlc3RBUEk6dXNlcjI="
  })
  void searchByOutcode(@Query("q") final String outcode, final Callback<RestaurantsTemplate> responseCallback);
}
