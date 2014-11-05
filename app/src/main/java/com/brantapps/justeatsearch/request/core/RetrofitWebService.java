package com.brantapps.justeatsearch.request.core;

import retrofit.RestAdapter;
import retrofit.converter.Converter;

/**
 * Base Retrofit web service.
 *
 * Created by davidb on 03/11/2014.
 */
public abstract class RetrofitWebService<T> {
  private final T serviceImpl;

  /**
   * Constructs a Retrofit driven web service.
   *
   * @param restAdapterBuilder The builder used to create the service adapter.
   * @param serviceClazz The service contract.
   */
  public RetrofitWebService(final RestAdapter.Builder restAdapterBuilder, final Class<T> serviceClazz) {
    this.serviceImpl =
        restAdapterBuilder.setConverter(getConverter())
                          .build()
                          .create(serviceClazz);
  }

  /**
   * @return an implementation of the web service.
   */
  public T getService() {
    return serviceImpl;
  }


  /**
   * @return the JSON converter to use under the service.
   */
  public abstract Converter getConverter();
}
