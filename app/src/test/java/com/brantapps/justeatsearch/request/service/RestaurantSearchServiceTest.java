package com.brantapps.justeatsearch.request.service;

import com.brantapps.justeatsearch.api.ResponseCallback;
import com.brantapps.justeatsearch.request.model.Restaurant;
import com.google.common.collect.Sets;
import com.google.common.io.CharStreams;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Test to ensure the correct operation of the
 * {@link com.brantapps.justeatsearch.request.service.RestaurantSearchService}.
 *
 * Created by davidb on 03/11/2014.
 */
public class RestaurantSearchServiceTest {
  private MockWebServer server;

  /**
   * Common test set-up.
   */
  @Before
  public void setUp() throws IOException {
    server = new MockWebServer();
  }


  /**
   * Check the end point the search service targets and with what method.
   */
  @Test
  public void checkRequestEndpoint() throws IOException, InterruptedException {
    // Given.
    server.enqueue(new MockResponse().setResponseCode(HttpURLConnection.HTTP_OK));
    server.play();
    final URL baseUrl = server.getUrl("");
    final CountDownLatch latch = new CountDownLatch(1);
    // Some dummy data.
    final String testOutcode = "BS8";
    final Set<Restaurant> venues = Sets.newHashSet();
    final RestAdapter.Builder dummyRestAdapter = new RestAdapter.Builder()
        .setEndpoint(baseUrl.toExternalForm())
        .setLogLevel(RestAdapter.LogLevel.FULL);
    final RestaurantSearchService service = new RestaurantSearchService(dummyRestAdapter);

    // When.
    service.populateRestaurantsForOutcode(testOutcode, venues, new ResponseCallback() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void onSuccess(String successMessage) {
        latch.countDown();
      }


      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(String failureMessage) {
        latch.countDown();
      }
    });

    latch.await(5, TimeUnit.SECONDS);

    // Then.
    assertThat(server.getRequestCount(), is(equalTo(1)));
    final RecordedRequest takeRequest = server.takeRequest();
    assertThat(takeRequest.getMethod(), is(equalTo("GET")));
    assertThat(takeRequest.getPath(), is(equalTo(String.format("/restaurants?q=%s", testOutcode))));
  }


  /**
   * Check that the requests contain the appropriate
   * header attributes.
   */
  @Test
  public void checkRequestHeaders() throws IOException, InterruptedException {
    // Given.
    final String testResponse = CharStreams.toString(new InputStreamReader(new FileInputStream("src/test/res/response/no_results_response.json"), "UTF-8"));
    server.enqueue(new MockResponse().setBody(testResponse));
    server.play();
    final URL baseUrl = server.getUrl("");
    final CountDownLatch latch = new CountDownLatch(1);
    // Some dummy data.
    final String testOutcode = "BS8";
    final Set<Restaurant> venues = Sets.newHashSet();
    final RestAdapter.Builder dummyRestAdapter = new RestAdapter.Builder()
        .setEndpoint(baseUrl.toExternalForm())
        .setLogLevel(RestAdapter.LogLevel.FULL);
    final RestaurantSearchService service = new RestaurantSearchService(dummyRestAdapter);

    // When.
    service.populateRestaurantsForOutcode(testOutcode, venues, new ResponseCallback() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void onSuccess(String successMessage) {
        latch.countDown();
      }


      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(String failureMessage) {
        latch.countDown();
      }
    });

    latch.await(5, TimeUnit.SECONDS);

    // Then.
    assertThat(server.takeRequest().getHeaders(), hasItems("Accept-Tenant: uk",
                                                           "Accept-Language: en-GB",
                                                           "Authorization: Basic VGVjaFRlc3RBUEk6dXNlcjI="));
  }


  /**
   * Check the handling of a web request failure.
   */
  @Test
  public void checkRequestFailureHandling() throws IOException, InterruptedException {
    // Given.
    server.enqueue(new MockResponse().setResponseCode(HttpURLConnection.HTTP_UNAVAILABLE));
    server.play();
    final URL baseUrl = server.getUrl("");
    final CountDownLatch latch = new CountDownLatch(1);
    // Some dummy data.
    final String testOutcode = "BS8";
    final Set<Restaurant> venues = Sets.newHashSet();
    final RestAdapter.Builder dummyRestAdapter = new RestAdapter.Builder()
        .setEndpoint(baseUrl.toExternalForm())
        .setLogLevel(RestAdapter.LogLevel.FULL);
    final RestaurantSearchService service = new RestaurantSearchService(dummyRestAdapter);

    // When.
    service.populateRestaurantsForOutcode(testOutcode, venues, new ResponseCallback() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void onSuccess(final String successMessage) {
        fail("Should have executed the failure path.");
      }


      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(final String failureMessage) {
        latch.countDown();
      }
    });

    latch.await(5, TimeUnit.SECONDS);
  }


  /**
   * Check the restaurants returned from a successful query.
   */
  @Test
  public void checkPositiveRestaurantsResponse() throws IOException, InterruptedException {
    // Given.
    final String testResponse = CharStreams.toString(new InputStreamReader(new FileInputStream("src/test/res/response/bs8_outcode_response.json"), "UTF-8"));
    server.enqueue(new MockResponse().setBody(testResponse));
    server.play();
    final URL baseUrl = server.getUrl("");
    final CountDownLatch latch = new CountDownLatch(1);
    // Some dummy data.
    final String testOutcode = "BS8";
    final Set<Restaurant> venues = Sets.newHashSet();
    final RestAdapter.Builder dummyRestAdapter = new RestAdapter.Builder()
        .setEndpoint(baseUrl.toExternalForm())
        .setLogLevel(RestAdapter.LogLevel.FULL);
    final RestaurantSearchService service = new RestaurantSearchService(dummyRestAdapter);

    // When.
    service.populateRestaurantsForOutcode(testOutcode, venues, new ResponseCallback() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void onSuccess(String successMessage) {
        latch.countDown();
      }


      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(String failureMessage) {
        latch.countDown();
      }
    });

    latch.await(5, TimeUnit.SECONDS);

    // Then.
    assertThat(venues, hasSize(106));
    assertThat(venues, hasItem(withRestaurant(7851, "Spice Out", 4.78f, true, true, true)));
  }


  /**
   * Check that only open restaurants are returned from the
   * {@link com.brantapps.justeatsearch.request.service.RestaurantSearchService#populateOpenRestaurantsForOutcode(String, java.util.Set, com.brantapps.justeatsearch.api.ResponseCallback)}
   * web-service call.
   */
  @Test
  public void checkOpenRestaurantsResponse() throws IOException, InterruptedException {
    // Given.
    final String testResponse = CharStreams.toString(new InputStreamReader(new FileInputStream("src/test/res/response/bs8_outcode_response.json"), "UTF-8"));
    server.enqueue(new MockResponse().setBody(testResponse));
    server.play();
    final URL baseUrl = server.getUrl("");
    final CountDownLatch latch = new CountDownLatch(1);
    // Some dummy data.
    final String testOutcode = "BS8";
    final Set<Restaurant> venues = Sets.newHashSet();
    final RestAdapter.Builder dummyRestAdapter = new RestAdapter.Builder()
        .setEndpoint(baseUrl.toExternalForm())
        .setLogLevel(RestAdapter.LogLevel.FULL);
    final RestaurantSearchService service = new RestaurantSearchService(dummyRestAdapter);

    // When.
    service.populateOpenRestaurantsForOutcode(testOutcode, venues, new ResponseCallback() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void onSuccess(String successMessage) {
        latch.countDown();
      }


      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(String failureMessage) {
        latch.countDown();
      }
    });

    latch.await(5, TimeUnit.SECONDS);

    // Then.
    assertThat(venues, hasSize(93));
  }


  /**
   * Check the restaurants returned from a successful query which had no results.
   */
  @Test
  public void checkNegativeRestaurantsResponse() throws IOException, InterruptedException {
    // Given.
    final String testResponse = CharStreams.toString(new InputStreamReader(new FileInputStream("src/test/res/response/no_results_response.json"), "UTF-8"));
    server.enqueue(new MockResponse().setBody(testResponse));
    server.play();
    final URL baseUrl = server.getUrl("");
    final CountDownLatch latch = new CountDownLatch(1);
    // Some dummy data.
    final String testOutcode = "BS8";
    final Set<Restaurant> venues = Sets.newHashSet();
    final RestAdapter.Builder dummyRestAdapter = new RestAdapter.Builder()
        .setEndpoint(baseUrl.toExternalForm())
        .setLogLevel(RestAdapter.LogLevel.FULL);
    final RestaurantSearchService service = new RestaurantSearchService(dummyRestAdapter);

    // When.
    service.populateRestaurantsForOutcode(testOutcode, venues, new ResponseCallback() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void onSuccess(String successMessage) {
        latch.countDown();
      }


      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(String failureMessage) {
        latch.countDown();
      }
    });

    latch.await(5, TimeUnit.SECONDS);

    // Then.
    assertThat(venues, hasSize(0));
  }


  /**
   * Disconnect the mock web-server.
   *
   * @throws java.io.IOException
   */
  @After
  public void tearDown() throws IOException {
    server.shutdown();
  }


  private TypeSafeMatcher<Restaurant> withRestaurant(final long id,
                                                     final String name,
                                                     final float rating,
                                                     final boolean isOpen,
                                                     final boolean isOpenForDelivery,
                                                     final boolean isOpenForCollection) {
    return new TypeSafeMatcher<Restaurant>() {

      /**
       * Safe matching criteria.
       *
       * @param item The item to match against.
       * @return <code>true</code> if a match is made.
       */
      @Override
      protected boolean matchesSafely(final Restaurant item) {
        return id == item.getIdentifier() &&
               name.equals(item.getName()) &&
               rating == item.getRating() &&
               isOpen == item.isOpen() &&
               isOpenForDelivery == item.isOpenForDelivery() &&
               isOpenForCollection == item.isOpenForColletion();
      }


      /**
       * {@inheritDoc}
       */
      @Override
      public void describeTo(Description description) {
        description.appendText(String.format("a restaurant with id [%s], name [%s] & rating [%f]", id, name, rating));
      }
    };
  }
}
