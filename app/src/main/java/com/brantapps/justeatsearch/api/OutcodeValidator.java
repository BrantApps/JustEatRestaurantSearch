package com.brantapps.justeatsearch.api;

import android.util.Pair;

/**
 * Contract for validating outcodes.
 *
 * Created by davidb on 04/11/2014.
 */
public interface OutcodeValidator {

  /**
   * Validate the entered outcode according to
   * the localisation rules under scrutiny.
   *
   * @param outcode The outcode to validate.
   * @return an {@link android.util.Pair} containing validation results key'ed by a boolean
   *         where the outcode is <code>true</code> if the code is valid.
   */
  Pair<Boolean, String> isValid(final String outcode);
}
