package View;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import Database.MovieDAO;
import Database.MovieDatabase;
import Database.MovieInfo;
import Database.Database;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.databinding.ActivityMovieLibBinding;
import algonquin.cst2335.finalproject.databinding.MovieLibRecylerviewBinding;
import data.MovieViewModel;

/**
 * @author Ece Selin Yorgancilar
 *  This class is for the movie library that is created by the user as they add movies in there.
 *  It provides functions that are required in that page where all the information is displayed.
 *  (To see the added movies, delete a movie, see all the information about a movie)
 */
public class MovieLib extends AppCompatActivity {
    ActivityMovieLibBinding binding;
    ArrayList<MovieInfo> informationLib;

    MovieDatabase sharedDatabase;
    MovieDAO mdao;
    MovieViewModel v_model;
    private RecyclerView.Adapter myAdapter;
    int Position;
    View viewItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMovieLibBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        sharedDatabase= Database.getSharedDatabase(getApplicationContext());
        mdao=sharedDatabase.mDAO();


        v_model=new ViewModelProvider(this).get(MovieViewModel.class);

        //the database arraylist = List (arraylist) from the ViewModel
        informationLib=v_model.List.getValue();

        //if empty create new arraylist
        if(informationLib==null){
            informationLib=new ArrayList<>();

            //set the given value-override
            v_model.List.postValue(informationLib);

            //execute runnable (add all information to the database arraylist)
            Executor thread= Executors.newSingleThreadExecutor();
            thread.execute(()->{
                informationLib.addAll(mdao.getAllInformation());
            });
        }

        //start the fragment process to use when a movie is selected.
        v_model.selected.observe(MovieLib.this,(newMovie)->{
            FragmentManager f=getSupportFragmentManager();
            FragmentTransaction tx= f.beginTransaction();
            MovieFragment movieFragment=new MovieFragment(newMovie);
            tx.add(R.id.detailsF,movieFragment);
            tx.addToBackStack("");
            tx.commit();
        });

        //set adapter to the recyclerview that is used to create the movie library.
        binding.movieLibRecyView.setAdapter(myAdapter=new RecyclerView.Adapter<MyRowHolder>(){

            //create a layout for a row, set the textviews.
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                MovieLibRecylerviewBinding binding=MovieLibRecylerviewBinding.inflate(getLayoutInflater());
                return new MyRowHolder(binding.getRoot());
            }

            //initialize a View holder to go to the specified position's row.
            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                MovieInfo movie=informationLib.get(position);
                String imdbID=movie.getImdbID();
                String pathName=getFilesDir()+"/"+imdbID+".jpg";

                //get the movie poster from the file path.
                File file=new File(pathName);
                if(file.exists()){
                    Bitmap image= BitmapFactory.decodeFile(pathName);
                    holder.mPoster.setImageBitmap(image);
                }

                //To update the ViewHolder to represent the contents of the item at the specified
                // position in the data set.
                holder.mTitle.setText(movie.getMovieTitle());
                holder.mActors.setText("Cast Actors: "+movie.getMainActor());
                holder.mYear.setText("Date: "+movie.getYear());
                holder.mRating.setText(movie.getRating());

            }

            /**
             * provide how many items to draw.
             * @return an int specifying how many items to draw (size of the arraylist).
             */
            @Override
            public int getItemCount() {
                return informationLib.size();
            }
        });

        //set recyclerciew's layout so that it goes in a vertical order.
        binding.movieLibRecyView.setLayoutManager(new LinearLayoutManager(this));


        //notify the adapter that the entire Arraylist has changed.
        myAdapter.notifyDataSetChanged();

        //notify the adapter about the fact that items have been added to the Arraylist.
        myAdapter.notifyItemInserted(informationLib.size()-1);

        //Set a Toolbar to replace the ActionBar for this page.
        setSupportActionBar(binding.MovieToolbar);
    }

    //represents everything that goes into the list.
    class MyRowHolder extends RecyclerView.ViewHolder{

        TextView mYear;
        TextView mRating;
        TextView mActors;
        ImageView mPoster;
        TextView mTitle;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            mPoster=itemView.findViewById(R.id.listPoster);
            mTitle= itemView.findViewById(R.id.listTitle);
            mYear=itemView.findViewById(R.id.listYear);
            mRating=itemView.findViewById(R.id.listRating);
            mActors=itemView.findViewById(R.id.listActors);

            //when a movie is clicked, get the position and get the information page to be loaded.
            itemView.setOnClickListener(clk->{
                int p=getAdapterPosition();
                Position=p;
                viewItem=itemView;
                MovieInfo selectedm=informationLib.get(p);

                //set the given value to selected movie.
                v_model.selected.postValue(selectedm);
            });

        }
    }

    @Override
    /**
     *  calling the setSupportActionBar() for the toolbar, called this onCreateOptionsMenu(Menu m).
     *  inflate the menu from the xml layout.
     */
    public boolean onCreateOptionsMenu(Menu m) {
        super.onCreateOptionsMenu(m);
        getMenuInflater().inflate(R.menu.m_menu, m);
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
            case R.id.collectionDelete:
                if (viewItem!=null){
                    TextView movieTitle=viewItem.findViewById(R.id.listTitle);
                    AlertDialog.Builder builder=new AlertDialog.Builder(MovieLib.this);
                    builder.setTitle("Question: ");
                    builder.setMessage("Do you want to delete this movie: "+movieTitle.getText()+" ?");
                    builder.setNegativeButton("No",(diag,clk)->{});
                    builder.setPositiveButton("Yes",(diag,clk)->{
                        MovieInfo s=informationLib.get(Position);
                        String pathName=getFilesDir()+"/"+s.getImdbID()+".jpg";
                        File file=new File(pathName);
                        Executor thread=Executors.newSingleThreadExecutor();
                        thread.execute(()->{
                            mdao.deleteInformation(s);
                        });
                        Snackbar.make(movieTitle,"You deleted the selected movie"+movieTitle.getText(),Snackbar.LENGTH_LONG)
                                .setAction("undo",click->{
                                    if(!file.exists()){
                                        getPosterBack(s);
                                    }

                                    //get the movie information back
                                    Executor t=Executors.newSingleThreadExecutor();
                                    t.execute(()->{
                                        mdao.insertMovieInfo(s);
                                    });

                                    informationLib.add(Position,s);
                                    //notify adapter that an item has been added.
                                    myAdapter.notifyItemInserted(Position);
                                })
                                .show();

                        //remove from the library.
                        informationLib.remove(Position);
                        //notify adapter that an item has been removed.
                        myAdapter.notifyItemRemoved(Position);
                        file.delete();
                    });

                    builder.create().show();
                }else{
                    Toast.makeText(MovieLib.this,"Please choose a movie first.... ",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.menu_help:
                AlertDialog.Builder builder=new AlertDialog.Builder(MovieLib.this);
                builder.setTitle(R.string.lib_help_title);
                builder.setMessage(R.string.lib_help_message);
                builder.create().show();
                break;

        }


        return true;
    }

    /**
     *Take the deleted movie, get the removed poster back.
     * @param t the movie that is removed.
     */
    public void getPosterBack(MovieInfo t){
        RequestQueue queue= Volley.newRequestQueue(this);
        ImageRequest imgRequest=new ImageRequest(t.getPosterUrl(), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                FileOutputStream fout=null;
                try {
                    fout= MovieLib.this.openFileOutput(t.getImdbID()+".jpg",MODE_PRIVATE);
                    response.compress(Bitmap.CompressFormat.JPEG,100,fout);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        },1024,1024,ImageView.ScaleType.CENTER,null,error -> {});
        queue.add(imgRequest);
    }
}