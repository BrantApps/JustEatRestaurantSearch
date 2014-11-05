package com.brantapps.justeatsearch.model;

import android.content.Context;

import com.brantapps.justeatsearch.R;

/**
 * Categorisation of outcode error types.
 *
 * Created by davidb on 04/11/2014.
 */
public enum GbOutcodeErrorType {

  /**
   * GB outcode are required to be at least length 2.
   */
  LENGTH_TOO_SHORT,


  /**
   * GB outcodes cannot be longer than length 4.
   */
  LENGTH_TOO_LONG,

  /**
   * GB outcodes must start with a letter.
   */
  MUST_START_WITH_LETTER,


  /**
   * GB outcodes must contain at least one letter.
   */
  MUST_CONTAIN_LETTERS;


  /**
   * Retrieve the human readable error message.
   *
   * @param context The context to use to decode the string resource ref.
   * @param type The localised error code type
   * @return the human readable error message.
   */
  public static String getLocalisedErrorMessage(final Context context, final GbOutcodeErrorType type) {
    String errorMessage = null;
    if (type != null) {
      switch (type) {
        case LENGTH_TOO_SHORT:
          errorMessage = context.getString(R.string.too_short_error_message);
          break;
        case LENGTH_TOO_LONG:
          errorMessage = context.getString(R.string.too_long_error_message);
          break;
        case MUST_CONTAIN_LETTERS:
          errorMessage = context.getString(R.string.must_contain_a_letter_message);
          break;
        case MUST_START_WITH_LETTER:
          errorMessage = context.getString(R.string.must_start_with_letter_message);
          break;
        default:
          throw new IllegalArgumentException(String.format("Unrecognised error type [%s]", type.name()));
      }
    }

    return errorMessage;
  }
}
