package com.brantapps.justeatsearch.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.SearchView;

import com.brantapps.justeatsearch.R;
import com.brantapps.justeatsearch.api.FragmentChanger;
import com.brantapps.justeatsearch.api.OutcodeValidator;
import com.brantapps.justeatsearch.core.BaseActivity;
import com.brantapps.justeatsearch.fragment.ErrorMessageFragment;
import com.brantapps.justeatsearch.fragment.PlaceholderFragment;
import com.brantapps.justeatsearch.fragment.RestaurantListingFragment;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Landing activity providing the facility to search for
 * restaurants by "outcode".
 */
public class RestaurantSearchActivity extends BaseActivity implements FragmentChanger {
  @Inject SearchManager searchManager;
  @Inject OutcodeValidator outcodeValidator;
  @InjectView(R.id.background_image) ImageView backgroundImage;
  private SearchView searchView;
  private Fragment currentFragment;

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_restaurant_search);
    ButterKnife.inject(this);
    Picasso.with(this).load(R.drawable.bg)
                      .fit()
                      .into(backgroundImage);
    changeFragment(PlaceholderFragment.newInstance(), PlaceholderFragment.TAG, true);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public boolean onCreateOptionsMenu(final Menu menu) {
    // Inflate the options menu from XML
    final MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_restaurant_search, menu);

    searchView = (SearchView) menu.findItem(R.id.search).getActionView();
    // Assumes current activity is the searchable activity
    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
    searchView.clearFocus();
    searchView.setFocusable(false);

    return true;
  }


  /**
   * Manage the backstack according to the currently displayed fragment.
   */
  @Override
  public void onBackPressed() {
    final String tag = currentFragment.getTag();

    if (PlaceholderFragment.TAG.equalsIgnoreCase(tag)) {
      finish();
    } else {
      popBackStackUpTo(PlaceholderFragment.TAG);
      currentFragment = getFragmentManager().findFragmentById(R.id.container);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onNewIntent(final Intent intent) {
    setIntent(intent);
    handleIntent(intent);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  protected List<Object> getModules() {
    return new ArrayList<Object>();
  }


  /**
   * Handle the search entry.
   *
   * @param intent The search intent action.
   */
  private void handleIntent(Intent intent) {
    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
      final String query = intent.getStringExtra(SearchManager.QUERY);

      if (StringUtils.isBlank(searchView.getQuery()) && StringUtils.isNotBlank(query)) {
        // Voice activated query - show the query as recognised by G-Voice.
        searchView.setQuery(query, false);
      }

      final Pair<Boolean, String> validationResults = outcodeValidator.isValid(query);
      if (validationResults.first) {
        // Switch content area with listing...
        changeFragment(RestaurantListingFragment.newInstance(query), RestaurantListingFragment.TAG, true);
      } else {
        // Display an error.
        changeFragment(ErrorMessageFragment.newInstance(validationResults.second), ErrorMessageFragment.TAG, true);
      }

      // Revoke focus.
      searchView.clearFocus();
    }
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void changeFragment(final Fragment target, final String tag, final boolean addToBackStack) {
    final FragmentTransaction transaction = getFragmentManager().beginTransaction();
    transaction.setCustomAnimations(android.R.animator.fade_in,
                                    android.R.animator.fade_out,
                                    android.R.animator.fade_in,
                                    android.R.animator.fade_out);
    transaction.replace(R.id.container, target, tag);

    // Single activity application - exercise control of the user's navigation.
    if (addToBackStack) {
      transaction.addToBackStack(tag);
    }

    transaction.commit();
    currentFragment = target;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void popBackStackUpTo(final String tag) {
    getFragmentManager().popBackStackImmediate(tag, 0);
    currentFragment = getFragmentManager().findFragmentById(R.id.container);
  }
}
