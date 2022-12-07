package algonquin.cst2335.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import algonquin.cst2335.finalproject.databinding.PexelsActivityBinding;
import algonquin.cst2335.finalproject.databinding.ResultsActivityBinding;

public class PexelsActivity extends AppCompatActivity {

    PexelsActivityBinding binding;

    PexelsViewModel pexelsModel;

    ArrayList<PexelsModel> pexelsResults;

    RecyclerView.Adapter<RecyclerViewHolder> adapter;

    RecyclerView recyclerView;

    String searchQuery;

    RequestQueue queue;

    /**
     * Creates an options meny
     *
     * @param menu the mmenu to create
     * @return boolean true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.pexels_menu, menu);
        return true;
    }

    /**
     * Controls what happens after an option is clicked
     *
     * @param item the selected menu item
     * @return sends the item to the parent constructor
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.savedPexels:
                Intent savedImgPage = new Intent(PexelsActivity.this, PexelsSavedActivity.class);
                savedImgPage.putExtra("savedImgs", pexelsModel.pexelsSaved.getValue());
                startActivity(savedImgPage);
                break;

            case R.id.about:
                Toast.makeText(this, "Ekon Hashi, CST2335 Final Project:Pexels", Toast.LENGTH_SHORT).show();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        queue = Volley.newRequestQueue(this);
        binding = PexelsActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pexelsModel = new ViewModelProvider(this).get(PexelsViewModel.class);
        pexelsResults = pexelsModel.pexelsResults.getValue();

        recyclerView = binding.recycleView;
        if (pexelsResults == null) {
            pexelsModel.pexelsResults.postValue(pexelsResults = new ArrayList<>());
        }

        binding.button.setOnClickListener(click -> {
            pexelsResults.clear();
            adapter.notifyDataSetChanged();
            searchQuery = binding.searchText.getText().toString();
            String url = "https://api.pexels.com/v1/search/?per_page=80&query=" + URLEncoder.encode(searchQuery);
            getSearchResults(url);
        });

        binding.recycleView.setAdapter(adapter = new RecyclerView.Adapter<RecyclerViewHolder>() {
            /**
             * Creates a new receyclerviewholder
             * @param parent owner of the view
             * @param viewType
             * @return a new receyclerviewholder and attached it to the resultsaa
             * ctivity
             */
            @NonNull
            @Override
            public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                ResultsActivityBinding binding = ResultsActivityBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new RecyclerViewHolder(binding.getRoot());
            }

            /**
             * sets the position for the items in the view
             * @param holder the recyeclerViewHolder
             * @param position the position in the recyclerView
             */
            @Override
            public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
                PexelsModel img = pexelsResults.get(position);
                holder.photographer.setText(img.getPhotographer());
                Picasso.get().load(img.getImgThumbnail()).into(holder.thumbnail);
            }

            /**
             * Gets the number of items to add to the view
             * @return size of items list
             */
            @Override
            public int getItemCount() {
                return pexelsResults.size();
            }
        });

        pexelsModel.pexelsSelected.observe(this, (newValue) -> {
            PexelsDetailsFragment pexelsFragment = new PexelsDetailsFragment(newValue);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLocation, pexelsFragment).addToBackStack("").commit();
        });

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

    }

    /**
     * uses volley to connect to the pexels api and
     * search for the query entered in the editText
     *
     * @param url the pexels url
     */
    private void getSearchResults(String url) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, (response) -> {
            try {
                JSONArray photosArray = response.getJSONArray("photos");
                for (int i = 0; i < photosArray.length(); i++) {
                    JSONObject nextObj = photosArray.getJSONObject(i);

                    try {
                        PexelsModel result = new PexelsModel();

                        result.setId(nextObj.getInt("id"));
                        result.setPhotographer(nextObj.getString("photographer"));
                        result.setUrl(nextObj.getString("url"));
                        result.setHeight(nextObj.getInt("height"));
                        result.setWidth(nextObj.getInt("width"));
                        result.setImgThumbnail(nextObj.getJSONObject("src").get("small").toString());
                        result.setOriginalImg(nextObj.getJSONObject("src").get("original").toString());
                        pexelsResults.add(result);

                        adapter.notifyItemInserted(pexelsResults.size() - 1);

                    } catch (Exception e) {
                        Log.d("test", "problem");
                    }
                }
            } catch (Exception ex) {
                Log.e("Error:", ex.getMessage());
                Log.d("test", "ohoh");
            }

        }, (error) -> {
            Log.e("Error", "error");
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "563492ad6f9170000100000191ad29580c3f41adb3f5cf994b22bb92");
                return params;
            }
        };
        queue.add(request);
    }

    /**
     * The recyclerViewHolder
     */
    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView photographer;
        ImageView thumbnail;

        /**
         * @param itemView
         */
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(click -> {
                int position = getAdapterPosition();
                PexelsModel selected = pexelsResults.get(position);
                pexelsModel.pexelsSelected.postValue(selected);
            });
            photographer = itemView.findViewById(R.id.photogName);
            thumbnail = itemView.findViewById(R.id.thumbnail);
        }
    }
}