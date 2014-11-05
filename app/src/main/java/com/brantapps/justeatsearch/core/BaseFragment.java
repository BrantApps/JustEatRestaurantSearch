package com.brantapps.justeatsearch.core;

import android.app.Fragment;
import android.os.Bundle;

import com.brantapps.justeatsearch.api.Injectable;

/**
 * Non-support library requiring implementation of the fragment.
 */
public abstract class BaseFragment extends Fragment {

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