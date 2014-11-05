package com.brantapps.justeatsearch.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.brantapps.justeatsearch.R;
import com.brantapps.justeatsearch.request.model.CuisineType;
import com.brantapps.justeatsearch.request.model.Restaurant;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Restaurants adapter.
 *
 * Created by davidb on 04/11/2014.
 */
public class RestaurantsAdapter extends ArrayAdapter<Restaurant> {
  private final List<Restaurant> restaurants;
  private final LayoutInflater inflater;
  private String dualDeliveryAvailable;
  private String collectOnly;
  private String deliveryOnly;


  /**
   * Constructs the adapter responsible for displaying the restaurants
   * found under the search area.
   *
   * @param context The adapter context.
   * @param restaurants A list of restaurants to display.
   */
  public RestaurantsAdapter(final Context context, final List<Restaurant> restaurants) {
    super(context, R.layout.element_restaurant, restaurants);
    this.restaurants = restaurants;
    this.inflater = LayoutInflater.from(context);
    this.dualDeliveryAvailable = context.getString(R.string.dual_delivery_method_label);
    this.collectOnly = context.getString(R.string.collect_only_method_label);
    this.deliveryOnly = context.getString(R.string.delivery_only_method_label);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public View getView(final int position, View convertView, final ViewGroup viewGroup) {
    ViewHolder holder;
    if (convertView == null) {
      convertView = inflater.inflate(R.layout.element_restaurant, null);
      holder = new ViewHolder(convertView);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }

    final Restaurant listRestaurant = restaurants.get(position);

    holder.name.setText(listRestaurant.getName());
    holder.rating.setText(String.format("%s *", Float.toString(listRestaurant.getRating())));

    // Show or hide the cuisine listings based on the existence of a classification.
    if (listRestaurant.getCuisineTypes().isEmpty()) {
      holder.cuisine.setVisibility(View.GONE);
    } else {
      holder.cuisine.setVisibility(View.VISIBLE);
      holder.cuisine.setText(getTypes(listRestaurant.getCuisineTypes()));
    }

    // Delivery options.
    if (listRestaurant.isOpenForDelivery() && listRestaurant.isOpenForColletion()) {
      holder.deliveryOptions.setVisibility(View.VISIBLE);
      holder.deliveryOptions.setText(dualDeliveryAvailable);
    } else if (listRestaurant.isOpenForColletion()) {
      holder.deliveryOptions.setVisibility(View.VISIBLE);
      holder.deliveryOptions.setText(collectOnly);
    } else if (listRestaurant.isOpenForDelivery()) {
      holder.deliveryOptions.setVisibility(View.VISIBLE);
      holder.deliveryOptions.setText(deliveryOnly);
    } else {
      holder.deliveryOptions.setVisibility(View.GONE);
    }

    return convertView;
  }


  private String getTypes(final Set<CuisineType> types) {
    final StringBuilder builder = new StringBuilder();
    final Iterator<CuisineType> typeIterator = types.iterator();
    while (typeIterator.hasNext()) {
      final CuisineType type = typeIterator.next();
      builder.append(type.getType());
      if (typeIterator.hasNext()) {
        builder.append(", ");
      }
    }

    return builder.toString();
  }


  /** ViewHolder tuple **/
  static class ViewHolder {
    @InjectView(R.id.restaurant_name) TextView name;
    @InjectView(R.id.rating) TextView rating;
    @InjectView(R.id.cuisine_types) TextView cuisine;
    @InjectView(R.id.delivery_options) TextView deliveryOptions;

    ViewHolder(View view) {
      ButterKnife.inject(this, view);
    }
  }
}
