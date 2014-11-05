package com.brantapps.justeatsearch.model;

import android.content.Context;
import android.util.Pair;

import com.brantapps.justeatsearch.api.OutcodeValidator;

import org.apache.commons.lang3.StringUtils;

/**
 * GB validator of outcodes.
 *
 * Created by davidb on 04/11/2014.
 */
public class GbOutcodeValidator implements OutcodeValidator {
  private final Context context;


  /**
   * Construct the GB outcode validator.
   *
   * @param context Resource accessing context.
   */
  public GbOutcodeValidator(final Context context) {
    this.context = context;
  }


  /**
   * Validate an outcode according to UK rules.
   *
   * @param outcode The outcode to validate.
   *
   * @return <code>true</code> if the code passes UK rules.
   * @see "http://www.bph-postcodes.co.uk/guidetopc.cgi"
   */
  @Override
  public Pair<Boolean, String> isValid(final String outcode) {
    boolean valid = true;
    GbOutcodeErrorType errorType = null;
    if (StringUtils.isNumeric(outcode)) {
      valid = false;
      errorType = GbOutcodeErrorType.MUST_CONTAIN_LETTERS;
    } else if (outcode.length() > 4) {
      valid = false;
      errorType = GbOutcodeErrorType.LENGTH_TOO_LONG;
    } else if (outcode.length() < 2) {
      valid = false;
      errorType = GbOutcodeErrorType.LENGTH_TOO_SHORT;
    } else if (StringUtils.isNumeric(String.valueOf(outcode.charAt(0)))) {
      valid = false;
      errorType = GbOutcodeErrorType.MUST_START_WITH_LETTER;
    }

    return new Pair<>(valid, GbOutcodeErrorType.getLocalisedErrorMessage(context, errorType));
  }
}
