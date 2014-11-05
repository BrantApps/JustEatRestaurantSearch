package com.brantapps.justeatsearch;

import android.app.SearchManager;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.brantapps.justeatsearch.activity.RestaurantSearchActivity;
import com.brantapps.justeatsearch.api.OutcodeValidator;
import com.brantapps.justeatsearch.core.ForApplication;
import com.brantapps.justeatsearch.fragment.RestaurantListingFragment;
import com.brantapps.justeatsearch.model.GbOutcodeValidator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

/**
 * The application level DI module.
 */
@Module(injects = {JustEatSearchApplication.class,
                   RestaurantSearchActivity.class,
                   RestaurantListingFragment.class},
        complete = true,
        library = true)
public class JustEatSearchModule {
  private final Context context;


  /**
   * Constructs the Dagger module backing application
   * wide components.
   *
   * @param context The application mContext to use.
   */
  public JustEatSearchModule(final Context context) {
    this.context = context;
  }


  /**
   * Provide the application context to use throughout the app.
   *
   * @return The application context.
   */
  @Provides
  @Singleton
  @ForApplication
  Context provideApplicationContext() {
    return context;
  }


  /**
   * Provide access to the display metrics backing a screen.
   *
   * @return the display metrics.
   */
  @Provides
  @Singleton
  DisplayMetrics provideDisplayMetrics() {
    final Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    final DisplayMetrics metrics = new DisplayMetrics();
    display.getMetrics(metrics);
    return metrics;
  }


  /**
   * Provides the instance (Singleton) of the search manager.
   *
   * @return The search manager.
   */
  @Provides
  @Singleton
  SearchManager provideSearchManager() {
    return (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);
  }


  /**
   * Provide the rest adapter builder to use within services.
   *
   * @return the {@link retrofit.RestAdapter.Builder}.
   */
  @Provides
  RestAdapter.Builder provideRestAdapterBuilder() {
    return new RestAdapter.Builder().setEndpoint(BuildConfig.API_ENDPOINT);
  }


  /**
   * Provide a validator for the outcode based off the target locale.
   *
   * @return The outcode validator implementation.
   */
  @Provides
  OutcodeValidator provideOutcodeValidator() {
    return new GbOutcodeValidator(context);
  }
}
