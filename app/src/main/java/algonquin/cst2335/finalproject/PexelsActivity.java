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
    RecyclerView recyclerView;
    PexelsActivityBinding binding;
    ArrayList<PexelsModel> pexelsResults;
    PexelsModel pexelsSelected;
    RecyclerView.Adapter<RecyclerViewHolder> adapter;
    PexelsViewModel pexelsModel;

    String searchQuery;
    RequestQueue queue;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.pexels_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.savedPexels:
                Intent savedImgPage = new Intent(PexelsActivity.this, PexelsSavedActivity.class);
                startActivity(savedImgPage);
        }

        return super.onOptionsItemSelected(item);
    }

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


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    (response) -> {
                        try {
                            JSONArray photosArray = response.getJSONArray("photos");
                            for (int i = 0; i < photosArray.length(); i++) {
                                JSONObject nextObj = photosArray.getJSONObject(i);

                                try {
                                    PexelsModel result = new PexelsModel();

                                    result.setId(nextObj.getInt("id"));
                                    result.setPhotographer(nextObj.getString("photographer").toString());
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

                    },
                    (error) -> {
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
        });

        binding.recycleView.setAdapter(adapter = new RecyclerView.Adapter<RecyclerViewHolder>() {
            @NonNull
            @Override
            public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                ResultsActivityBinding binding = ResultsActivityBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new RecyclerViewHolder(binding.getRoot());
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
                PexelsModel img = pexelsResults.get(position);
                holder.photographer.setText(img.getPhotographer());
                Picasso.get().load(img.getImgThumbnail()).into(holder.thumbnail);
            }

            @Override
            public int getItemCount() {
                return pexelsResults.size();
            }
        });

        pexelsModel.pexelsSelected.observe(this, (newValue) -> {
            PexelsDetailsFragment pexelsFragment = new PexelsDetailsFragment(newValue);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentLocation, pexelsFragment)
                    .addToBackStack("")
                    .commit();
        });


        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView photographer;
        ImageView thumbnail;


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