package com.brantapps.justeatsearch.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brantapps.justeatsearch.R;
import com.brantapps.justeatsearch.api.ResponseCallback;
import com.brantapps.justeatsearch.core.BaseListFragment;
import com.brantapps.justeatsearch.fragment.adapter.RestaurantsAdapter;
import com.brantapps.justeatsearch.request.model.Restaurant;
import com.brantapps.justeatsearch.request.service.RestaurantSearchService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.Set;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Listing displaying the restaurants near
 * to the provided outcode.
 */
public class RestaurantListingFragment extends BaseListFragment implements ResponseCallback {
  public static final String TAG = "RestaurantListingFragment";
  private static final String QUERY = "query";
  private Set<Restaurant> restaurants;
  @InjectView(android.R.id.list) ListView listView;
  @InjectView(android.R.id.empty) RelativeLayout emptyLayout;
  @InjectView(R.id.no_results) TextView noResultsMessage;
  @InjectView(R.id.progress) ProgressBar progressBar;
  @Inject RestaurantSearchService searchService;


  /**
   * Creates a new instance of the fragment.
   *
   * @param query the outcode query to run.
   * @return a new fragment.
   */
  public static RestaurantListingFragment newInstance(final String query){
    final RestaurantListingFragment fragment = new RestaurantListingFragment();
    final Bundle bundle = new Bundle();
    bundle.putSerializable(QUERY, query);
    fragment.setArguments(bundle);
    return fragment;
  }


  /**
   * Create the listing view.
   *
   * {@inheritDoc}
   */
  @Override
  public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    final View listing = inflater.inflate(R.layout.fragment_restaurant_listing, null);
    ButterKnife.inject(this, listing);
    return listing;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void onActivityCreated(final Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    final String query = getArguments().getString(QUERY);
    this.restaurants = Sets.newLinkedHashSet();
    searchService.populateOpenRestaurantsForOutcode(query, restaurants, this);
    listView.setEmptyView(emptyLayout);
    progressBar.setVisibility(View.VISIBLE);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void onSuccess(final String successMessage) {
    // Display the results.
    if (getActivity() != null) {
      final RestaurantsAdapter adapter = new RestaurantsAdapter(getActivity(), Lists.newLinkedList(restaurants));
      listView.setVisibility(View.VISIBLE);
      listView.setAdapter(adapter);
      if (restaurants.isEmpty()) {
        progressBar.setVisibility(View.GONE);
        emptyLayout.setVisibility(View.VISIBLE);
        noResultsMessage.setVisibility(View.VISIBLE);
      } else {
        emptyLayout.setVisibility(View.GONE);
      }
    }
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void onFailure(final String failureMessage) {
    // Display an appropriate error message.
    listView.setVisibility(View.GONE);
  }
}
