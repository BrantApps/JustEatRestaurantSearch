package com.brantapps.justeatsearch.request.service;

import com.brantapps.justeatsearch.api.ResponseCallback;
import com.brantapps.justeatsearch.request.api.RestaurantSearchCommand;
import com.brantapps.justeatsearch.request.core.RetrofitWebService;
import com.brantapps.justeatsearch.request.model.CuisineType;
import com.brantapps.justeatsearch.request.model.Restaurant;
import com.brantapps.justeatsearch.request.template.RestaurantsTemplate;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.util.Set;

import javax.annotation.Nullable;
import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;

/**
 * Default implementation of the {@link com.brantapps.justeatsearch.request.api.RestaurantSearchCommand}.
 *
 * Created by davidb on 03/11/2014.
 */
public class RestaurantSearchService extends RetrofitWebService<RestaurantSearchCommand> {
  private Gson gson;

  /**
   * Constructs the search service.
   *
   * @param restAdapterBuilder The Retrofit REST adapter.
   */
  @Inject
  RestaurantSearchService(final RestAdapter.Builder restAdapterBuilder) {
    super(restAdapterBuilder, RestaurantSearchCommand.class);
  }


  /**
   * Retrieving restaurant for a specific outcode.
   *
   * @param outcode     The provided outcode.
   * @param restaurants The restaurants in this outcode.
   * @param callback    The callback back to the UI layer.
   */
  public void populateRestaurantsForOutcode(final String outcode,
                                            final Set<Restaurant> restaurants,
                                            final ResponseCallback callback) {
    getService().searchByOutcode(outcode, new Callback<RestaurantsTemplate>() {

      /**
       * {@inheritDoc}
       *
       * @param venues The retrieved restaurants.
       * @param response The raw response.
       */
      @Override
      public void success(final RestaurantsTemplate venues, final Response response) {
        // Move the template API reflection to our models.
        restaurants.addAll(transformRestaurantTemplates(venues));
        callback.onSuccess("OK");
      }


      /**
       * {@inheritDoc}
       *
       * @param error What went wrong.
       */
      @Override
      public void failure(final RetrofitError error) {
        callback.onFailure(StringUtils.defaultString(error.getMessage()));
      }
    });
  }


  /**
   * Retrieving restaurant for a specific outcode.
   *
   * @param outcode     The provided outcode.
   * @param restaurants The restaurants in this outcode.
   * @param callback    The callback back to the UI layer.
   */
  public void populateOpenRestaurantsForOutcode(final String outcode,
                                                final Set<Restaurant> restaurants,
                                                final ResponseCallback callback) {
    getService().searchByOutcode(outcode, new Callback<RestaurantsTemplate>() {

      /**
       * {@inheritDoc}
       *
       * @param venues The retrieved restaurants.
       * @param response The raw response.
       */
      @Override
      public void success(final RestaurantsTemplate venues, final Response response) {
        // Move the template API reflection to our models.
        final Set<Restaurant> allOutcodeRestaurants = transformRestaurantTemplates(venues);

        final Iterable<Restaurant> openRestaurants = Iterables.filter(allOutcodeRestaurants, new Predicate<Restaurant>() {

          /**
           * @param input the restaurant to inspect.
           * @return <code>true</code> if the item is to be filtered and returned.
           */
          @Override
          public boolean apply(@Nullable Restaurant input) {
            return input.isOpen();
          }
        });

        restaurants.addAll(Sets.newLinkedHashSet(openRestaurants));
        callback.onSuccess("OK");
      }


      /**
       * {@inheritDoc}
       *
       * @param error What went wrong.
       */
      @Override
      public void failure(final RetrofitError error) {
        callback.onFailure(StringUtils.defaultString(error.getMessage()));
      }
    });
  }


  /**
   * @return the serialisation/deserialisation converter to use during the service
   *         response parsing.
   */
  @Override
  public Converter getConverter() {
    this.gson = new GsonBuilder().registerTypeAdapter(RestaurantsTemplate.class, new RestaurantDeserializer()).create();
    return new GsonConverter(gson);
  }


  private Set<Restaurant> transformRestaurantTemplates(final RestaurantsTemplate venues) {
    // Move the template API reflection to our models.
    final Iterable<Restaurant> restaurantsToAdd = Iterables.transform(venues.restaurants, new Function<RestaurantsTemplate.RestaurantTemplate, Restaurant>() {

      /**
       * Move from a {@link com.brantapps.justeatsearch.request.template.RestaurantsTemplate}
       * and a {@link com.brantapps.justeatsearch.request.model.Restaurant}.
       *
       * @param input the template.
       * @return the model.
       */
      @Nullable
      @Override
      public Restaurant apply(@Nullable RestaurantsTemplate.RestaurantTemplate input) {
        final Restaurant restaurant = new Restaurant(input.id, input.name);
        restaurant.setOpen(input.isOpenNow);
        restaurant.setOpenForColletion(input.isOpenNowForCollection);
        restaurant.setOpenForDelivery(input.isOpenNowForDelivery);
        restaurant.setRating(input.ratingStars);

        for (RestaurantsTemplate.RestaurantTemplate.CuisineTypeTemplate typeTemplate : input.cuisineTypes) {
          restaurant.getCuisineTypes().add(new CuisineType(typeTemplate.id, typeTemplate.name));
        }

        return restaurant;
      }
    });

    // Return a set removing potential for duplicate restaurants (by id).
    return Sets.newLinkedHashSet(restaurantsToAdd);
  }


  public class RestaurantDeserializer implements JsonDeserializer<RestaurantsTemplate> {

    /**
     * Move between the API representation of a restaurant list corresponding
     * to the search terms and the model, POJO class.
     *
     * {@inheritDoc}
     */
    @Override
    public RestaurantsTemplate deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
      final Gson gson = new GsonBuilder().create();
      return gson.fromJson(json.toString(), RestaurantsTemplate.class);
    }
  }
}