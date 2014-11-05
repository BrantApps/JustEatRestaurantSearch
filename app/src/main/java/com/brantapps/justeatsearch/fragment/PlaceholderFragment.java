package com.brantapps.justeatsearch.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
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
public class PlaceholderFragment extends Fragment {
  public static final String TAG = "PlaceholderFragment";
  @InjectView(R.id.author_holder) TextView authorHolder;
  @InjectView(R.id.libraries_holder) TextView librariesHolder;


  /**
   * Creates a new instance of the fragment.
   *
   * @return a new fragment.
   */
  public static PlaceholderFragment newInstance(){
    return new PlaceholderFragment();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    final View placeholderInfo = inflater.inflate(R.layout.fragment_placeholder, null);
    ButterKnife.inject(this, placeholderInfo);
    return placeholderInfo;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public void onViewCreated(final View view, final Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    authorHolder.setText(Html.fromHtml(getString(R.string.author_label)));
    librariesHolder.setText(Html.fromHtml(getString(R.string.libraries_used_label)));
    librariesHolder.setMovementMethod(LinkMovementMethod.getInstance());
  }
}
