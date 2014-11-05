package com.brantapps.justeatsearch.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brantapps.justeatsearch.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Placeholder contains details regarding the
 * author and libraries used in this o-so-attractive
 * application.
 *
 * Created by davidb on 04/11/2014.
 */
public class ErrorMessageFragment extends Fragment {
  public static final String TAG = "ErrorMessageFragment";
  private static final String ERROR_MSG = "errorMsg";
  @InjectView(R.id.error_title) TextView errorTitle;
  @InjectView(R.id.error_description) TextView errorDescription;


  /**
   * Creates a new instance of the fragment.
   *
   * @return a new fragment.
   */
  public static ErrorMessageFragment newInstance(final String errorMessage) {
    final ErrorMessageFragment fragment = new ErrorMessageFragment();
    final Bundle bundle = new Bundle();
    bundle.putSerializable(ERROR_MSG, errorMessage);
    fragment.setArguments(bundle);
    return fragment;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    final View placeholderInfo = inflater.inflate(R.layout.fragment_error, null);
    ButterKnife.inject(this, placeholderInfo);
    return placeholderInfo;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void onViewCreated(final View view, final Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    errorTitle.setText(getString(R.string.error_title_label));
    errorDescription.setText(getArguments().getString(ERROR_MSG));
  }
}
