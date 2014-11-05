package com.brantapps.justeatsearch.api;

import android.app.Fragment;

/**
 * Interface for class wishing to exert the power to switch fragments in
 * a view.
 *
 * Created by ray.britton on 11/07/2014.
 */
public interface FragmentChanger {

    /**
     * Change to the specific fragment.
     *
     * @param fragment An instance of the fragment to change to.
     * @param tag The fragment tag.
     * @param addToBackStack <code>true</code> if the fragment is to be added to the back stack.
     */
    public void changeFragment(final Fragment fragment, final String tag, final boolean addToBackStack);


    /**
     * Pop the back-stack up to the provided transaction name.
     *
     * @param transactionTag The transaction name.
     * @see android.app.FragmentTransaction#addToBackStack(String)
     */
    public void popBackStackUpTo(final String transactionTag);
}
