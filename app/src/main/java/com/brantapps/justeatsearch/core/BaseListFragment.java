package com.brantapps.justeatsearch.core;

import android.app.ListFragment;
import android.os.Bundle;

import com.brantapps.justeatsearch.api.Injectable;

/**
 * Non-support menu requiring base class encapsulating listing behaviour.
 */
public abstract class BaseListFragment extends ListFragment {

  /**
   * After the activity has been created, leverage it's object
   * graph to inject the fragment's dependencies.
   *
   * @param savedInstanceState The fragment's saved instance state.
   */
  @Override
  public void onActivityCreated(final Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    ((Injectable) getActivity()).inject(this);
  }
}