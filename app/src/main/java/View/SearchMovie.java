package View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import Database.Database;
import Database.MovieDAO;
import Database.MovieDatabase;
import Database.MovieInfo;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.databinding.ActivitySearchmovieBinding;
import algonquin.cst2335.finalproject.databinding.ResultsRecylerviewBinding;
import data.MovieViewModel;
import utils.SpacingItemDecorator;

/**
 * This class is for all the search related activities.
 * Also where the Json request takes place.
 * @author Ece Selin Yorgancilar.
 */
public class SearchMovie  extends AppCompatActivity {
    private RecyclerView.Adapter myAdapter;
    ArrayList<MovieInfo> Lists;
    MovieDAO mDao;
    ActivitySearchmovieBinding binding;

    RequestQueue queue;

    MovieViewModel viewModel;
    ResultView result;
    ArrayList<ResultView> viewResults;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchmovieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //for the space in between recyclerView
        RecyclerView recyclerView = binding.resultsView;
        SpacingItemDecorator itemDecorator = new SpacingItemDecorator (30);
        recyclerView.addItemDecoration(itemDecorator);



        //create viewModel
        viewModel=new ViewModelProvider(this).get(MovieViewModel.class);

        //create a database connection
        MovieDatabase db= Database.getSharedDatabase(getApplicationContext());
        mDao=db.mDAO();
        Lists=viewModel.List.getValue();

        /**
         * initialziing the movelist
         */
        if(Lists==null){
            Lists=new ArrayList<>();
            viewModel.List.postValue(Lists);
            Executor thread= Executors.newSingleThreadExecutor();
            thread.execute(()->{
                Lists.addAll(mDao.getAllInformation());
            });
        }

        // arraylist = result view model arraylist
        viewResults=viewModel.result.getValue();

        // if empty create a list again
        if(viewResults==null){
            viewResults=new ArrayList<>();

        }
        queue= Volley.newRequestQueue(this);


        /**
         * use sharedpreferences to store the latest searched movie data.
         */
        SharedPreferences sharedInput=getSharedPreferences("input",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedInput.edit();

        binding.titleInput.setText(sharedInput.getString("input",""));

        /**
         * the list of search results will be cleared and loaded new search results
         */
        binding.searchBtn.setOnClickListener(clk -> {
            String input = binding.titleInput.getText().toString();
            if(viewModel.result.getValue()!=null){
                viewModel.result.getValue().clear();
            }
            viewResults.clear();
            myAdapter.notifyDataSetChanged();
            getResults(input,viewResults);

        });

        binding.aboutImage.setOnClickListener(click -> {
            Toast.makeText(this,"Version 1.0, created by Ece Selin Yorgancilar", Toast.LENGTH_LONG).show();
        });

        viewModel.result.postValue(viewResults);
        binding.resultsView.setLayoutManager(new LinearLayoutManager(this));

        binding.resultsView.setAdapter(myAdapter= new RecyclerView.Adapter<myRowHolder>() {
            @NonNull
            @Override
            /**
             * override the onCreateViewHolder method.
             * Create a viewholder object that represent a single row in a list.
             */
            public myRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                ResultsRecylerviewBinding
                        binding= ResultsRecylerviewBinding.inflate(getLayoutInflater());
                return new myRowHolder(binding.getRoot());

            }

            @Override
            /**
             * Called by RecyclerView to show the data at the position specified.
             */
            public void onBindViewHolder(@NonNull myRowHolder holder, int position) {
                queue=Volley.newRequestQueue(SearchMovie.this);
                ResultView result=viewResults.get(position);
                holder.lookupTitle.setText(result.getMovieTitle());
                holder.lookupYear.setText(result.getMovieYear());
                String resultURL=result.getPosterUrl();
                if(!resultURL.equalsIgnoreCase("N/A")){
                    ImageRequest imgRequest=new ImageRequest(resultURL, new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            holder.lookupPoster.setImageBitmap(response);
                        }
                    },1024,1024,null,error -> {});
                    queue.add(imgRequest);
                }

            }

            @Override
            /**
             * Returns an int for the number items in the data set held by the adapter.
             */
            public int getItemCount() {
                return viewResults.size();
            }

            @Override
            /**
             * Return the view type of the item at position for
             * the purposes of recycler view.
             */
            public int getItemViewType(int position) {
                return 0;
            }
        });

        //if the saved movies button is clicked it will go to another page.
        editor.putString("input",binding.titleInput.getText().toString());
        editor.apply();

        //Set a Toolbar to replace the ActionBar for this page.
        setSupportActionBar(binding.MovieToolbar);

    }
    @Override
    /**
     *  calling the setSupportActionBar() for the toolbar, called this method onCreateOptionsMenu(Menu m).
     *  inflate the menu from the xml layout.
     */
    public boolean onCreateOptionsMenu(Menu m) {
        super.onCreateOptionsMenu(m);
        getMenuInflater().inflate(R.menu.other_menu, m);
        return true;
    }

    @Override
    /**
     * Responding to the menu item click. When clicked the latest clicked movie
     * will be asked to delete or not.
     */
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){


            case R.id.menu_help:
                AlertDialog.Builder builder=new AlertDialog.Builder(SearchMovie.this);
                builder.setTitle(R.string.help_title);
                builder.setMessage(R.string.howto_message);
                builder.create().show();
                break;
            case R.id.addedList:

                Intent listIntent = new Intent(SearchMovie.this, MovieLib.class);
                startActivity(listIntent);
                break;
            case R.id.about:
                Toast.makeText(this,"Version 1.0, created by Ece Selin Yorgancilar", Toast.LENGTH_LONG).show();
                break;
        }


        return true;
    }

    //represent what goes on a row in a list.
    class myRowHolder extends RecyclerView.ViewHolder {
        TextView lookupTitle;
        TextView lookupYear;
        ImageView lookupPoster;

        /**
         * represent everything that goes on a list
         * @param itemView a myRowHolder object
         */
        public myRowHolder(@NonNull ConstraintLayout itemView) {
            super(itemView);
            lookupTitle = itemView.findViewById(R.id.resultsTitle);
            lookupYear = itemView.findViewById(R.id.resultYear);
            lookupPoster=itemView.findViewById(R.id.result_poster);

            //when the item movie is clicked ask if they want to save the movie or not.
            itemView.setOnClickListener(clk->{
                AlertDialog.Builder builder=new AlertDialog.Builder(SearchMovie.this);
                builder.setTitle("Question:");
                builder.setMessage("Save this movie to the Library?");
                builder.setNegativeButton("No",(dialogue,click)->{});
                builder.setPositiveButton("Yes",(dialogue,click)->{
                    int position=getAdapterPosition();
                    String movieName=viewResults.get(position).getMovieTitle();
                    String imdbId=viewResults.get(position).getImdbID();

                    if(sameMovie(imdbId)){
                        Toast.makeText(SearchMovie.this,"Already exists...",Toast.LENGTH_SHORT).show();
                    }else{
                        getMovieInfo(movieName);

                    }

                });

                builder.create().show();

            });

        }

    }

    /**
     * Use this to get the movie results according to what the user entered as a movie name.
     * @param t movie entered by the user.
     * @param results search results according to the input.
     */
    public void getResults(String t,ArrayList<ResultView> results){
        String url="http://www.omdbapi.com/?apikey=6c9862c2%20&s=";
        this.viewResults=results;
        try {
            url+= URLEncoder.encode(t,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JsonObjectRequest objectRequest=new JsonObjectRequest(

                Request.Method.GET,url,null,(response) -> {
            try {

                JSONArray sArray=response.getJSONArray("Search");
                for(int i=0;i<sArray.length();i++){

                    JSONObject positionI=sArray.getJSONObject(i);
                    String  mTitle=positionI.getString("Title");
                    String mYear=positionI.getString("Year");
                    String imdbID=positionI.getString("imdbID");
                    String pUrl=positionI.getString("Poster");
                    result=new ResultView(mTitle,mYear,imdbID,pUrl);
                    viewResults.add(result);
                    myAdapter.notifyItemInserted(viewResults.size()-1);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        },error -> {} );
        queue.add(objectRequest);
    }


    /**
     * method to test if the saved movie is being tried to saved again.
     * @param imdbID id of the movie item
     * @return the result
     */
    public boolean sameMovie(String imdbID){
        boolean sameMovie=false;

        for(int i=0;i< Lists.size();i++){
            if(Lists.get(i).getImdbID().equalsIgnoreCase(imdbID)){
                sameMovie=true;
            }
        }
        return sameMovie;
    }

    /**
     * Use Volley to get get information from the url.
     * Get the name from the search edit text to have access to every information including the plot.
     * save the information to a database.
     * @param s_Movie searched movie
     */
    public void getMovieInfo(String s_Movie){
        queue=Volley.newRequestQueue(SearchMovie.this);
        String url="http://www.omdbapi.com/?apikey=6c9862c2%20&t=";
        try {
            url+=URLEncoder.encode(s_Movie,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET,url,null,
                (response -> {
                    try {
                        String title =response.getString("Title");

                        String imdbID=response.getString("imdbID");
                        String year=response.getString("Released");
                        String rating="imdb: "+response.getString("imdbRating")+"/10";
                        String runtime=response.getString("Runtime");
                        String mainActor=response.getString("Actors");
                        String plot=response.getString("Plot");
                        String posterUrl=response.getString("Poster");
                        MovieInfo oneMovie=new MovieInfo(title,imdbID,year,rating,runtime,mainActor,plot,posterUrl);
                        Lists.add(oneMovie);
                        Executor thread=Executors.newSingleThreadExecutor();
                        thread.execute(()->{
                            mDao.insertMovieInfo(oneMovie);
                        });

                        String pathName=getFilesDir()+"/"+imdbID+".jpg";
                        File file=new File(pathName);
                        if(!file.exists()){
                            getPoster( imdbID,posterUrl);
                        }

                        Toast.makeText(SearchMovie.this,"Successfully saved to your list",Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }),error -> {});
        queue.add(objectRequest);


    }

    /**
     * this method takes an imdbID of a movie and an url link to download  the poster of the selected
     * movie and store it as a local file with imdbID as the file name
     * @param imdbID from search results
     * @param posterUrl from search results
     */
    public void getPoster(String imdbID,String posterUrl){
        ImageRequest imageRequest=new ImageRequest(posterUrl,new Response.Listener<Bitmap>(){

            @Override
            public void onResponse(Bitmap response) {
                FileOutputStream fout=null;
                try {
                    fout= SearchMovie.this.openFileOutput(imdbID+".jpg",MODE_PRIVATE);
                    response.compress(Bitmap.CompressFormat.JPEG,100,fout);
                    fout.flush();
                    fout.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        },1024,1024, ImageView.ScaleType.CENTER,null, error->{ });
        queue.add(imageRequest);
    }


}