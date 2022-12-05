package algonquin.cst2335.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import algonquin.cst2335.finalproject.databinding.PexelsActivityBinding;

public class PexelsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    PexelsActivityBinding binding;
    ArrayList<PexelsModel> pexelsResults;
    PexelsAdapter adapter;
    PexelsViewModel pexelsModel;

    String searchQuery;
    RequestQueue queue;


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

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new PexelsAdapter(getApplicationContext(), pexelsResults);
        recyclerView.setAdapter(adapter);

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
                                    pexelsResults.add(result);

                                    adapter.notifyItemInserted(pexelsResults.size()-1);

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
                    })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", "563492ad6f9170000100000191ad29580c3f41adb3f5cf994b22bb92");
                    return params;
                }
            };
            queue.add(request);
        });
    }
}