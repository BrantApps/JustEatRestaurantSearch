package com.brantapps.justeatsearch.model;

import android.os.Build;
import android.util.Pair;

import com.brantapps.justeatsearch.RobolectricGradleTestRunner;
import com.brantapps.justeatsearch.api.OutcodeValidator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test to ensure the correct operation of the {@link com.brantapps.justeatsearch.model.GbOutcodeValidator}.
 *
 * Created by davidb on 04/11/2014.
 */
@Config(emulateSdk = Build.VERSION_CODES.JELLY_BEAN_MR2)
@RunWith(RobolectricGradleTestRunner.class)
public class GbOutcodeValidatorTest {


  /**
   * Test to ensure that too short an outcode is correctly id'ed
   * and reported.
   */
  @Test
  @Config(qualifiers="en")
  public void checkTooShortAnOutcodeError() {
    // Given.
    final OutcodeValidator validator = new GbOutcodeValidator(Robolectric.application);

    // When.
    final Pair<Boolean, String> validationResult = validator.isValid("B");

    // Then.
    assertThat(validationResult.first, is(equalTo(false)));
    assertThat(validationResult.second, is(equalTo("Outcodes are required to be at least 2 characters long")));
  }
}
