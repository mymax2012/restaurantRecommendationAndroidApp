package com.example.zhenya.laiofferproject;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantListFragment extends Fragment {

    private Fragment mfragment;
    private ListView listView;
    private DataService dataService;

    OnItemSelectListener mCallback;

    public RestaurantListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_list,container,false);

        listView = (ListView) view.findViewById(R.id.restaurant_list);
        mfragment = this;

        // Set a listener to ListView.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurant r = (Restaurant) listView.getItemAtPosition(position);

                // Prepare all the data we need to start map activity.
                Bundle bundle = new Bundle();
                bundle.putParcelable(
                        RestaurantMapActivity.EXTRA_LATLNG,
                        new LatLng(r.getLat(), r.getLng()));
                Intent intent = new Intent(view.getContext(), RestaurantMapActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        dataService = new DataService(getActivity());
        //refreshRestaurantList(dataService);

        updateRestaurant(Config.user_name);
        return view;
    }

    //Update restaurants
    public void updateRestaurant(String user_id) {
        String url2 = "http://laiproject.mooo.com/chihuo/recommendation?user_id=" + user_id;
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                new GetRestaurantsNearbyAsyncTask(mfragment, response, dataService).execute();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Cookie",  Config.cookies);
                return headers;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest2);
    }


    // Make a async call to get restaurant data.
 /*   private void refreshRestaurantList(DataService dataService) {
        new GetRestaurantsNearbyAsyncTask(this, dataService).execute();
    }*/


    //create AsyncTask background thread task
    private class GetRestaurantsNearbyAsyncTask extends AsyncTask<Void, Void, List<Restaurant>> {
        private Fragment fragment;
        private DataService dataService;
        private Clock clock;
        private String response;
        private List<Restaurant> restaurantList;


        public GetRestaurantsNearbyAsyncTask(Fragment fragment, String response,DataService dataService) {
            this.fragment = fragment;
            this.dataService = dataService;

            this.response = response;
            restaurantList = new ArrayList<Restaurant>();

            this.clock = new Clock();
            //this.clock.reset();
        }


        @Override
        protected List<Restaurant> doInBackground(Void... params) {
            try {
                JSONArray reader = new JSONArray(response);
                for(int i = 0; i < reader.length(); i++){
                    JSONObject item = reader.getJSONObject(i);
                    Restaurant restaurant = new Restaurant();
                    restaurant.setName(item.getString("name"));
                    restaurant.setAddress(item.getString("full_address"));
                    restaurant.setLat(item.getDouble("latitude"));
                    restaurant.setLng(item.getDouble("longitude"));
                    restaurant.setStars(item.getDouble("stars"));
                    restaurant.setThumbnail(dataService.getBitmapFromURL(item.getString("image_url")));


                    JSONArray category = item.getJSONArray("categories");
                    List<String> cat = new ArrayList<String>();
                    for(int j = 0; j < category.length(); j++){
                        cat.add(category.get(j).toString());
                    }
                    restaurant.setCategories(cat);
                    restaurantList.add(restaurant);
                }
            }catch (JSONException ex){
                ex.printStackTrace();
            }
            return restaurantList;
        }


        @Override
        protected void onPostExecute(List<Restaurant> restaurants) {

            // Measure the latency of the API call.
            clock.stop();
            Log.e("Latency", Long.toString(clock.getCurrentInterval()));

            if (restaurants != null) {
                super.onPostExecute(restaurants);
                RestaurantAdapter adapter = new  RestaurantAdapter(fragment.getActivity(), restaurants);
                listView.setAdapter(adapter);
            } else {
                Toast.makeText(fragment.getActivity(), "Data service error.", Toast.LENGTH_LONG);
            }
        }
    }

    private String[] getRestaurantNames() {
        String[] names= {
                "Restaurant1", "Restaurant2", "Restaurant3",
                "Restaurant4", "Restaurant5", "Restaurant6",
                "Restaurant7", "Restaurant8", "Restaurant9",
                "Restaurant10"};
        return names;
    }


    public interface OnItemSelectListener {
        public void onItemSelected(int position);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            mCallback = (OnItemSelectListener) context;
        }catch (ClassCastException e){

        }
    }
}
