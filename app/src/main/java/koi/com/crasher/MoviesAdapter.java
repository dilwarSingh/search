package koi.com.crasher;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

/**
 * Created by Vibrant1 on 20-Sep-17.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> implements Filterable {

    private List<Movie> moviesList, allMovieList;

    dataFilter mContactsFilter;

    public Filter getFilter() {
        if (mContactsFilter == null)
            mContactsFilter = new dataFilter();

        return mContactsFilter;
    }

    class dataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();

            // If the constraint (search string/pattern) is null
            // or its length is 0, i.e., its empty then
            // we just set the `values` property to the
            // original contacts list which contains all of them
            if (constraint == null || constraint.length() == 0 || constraint.toString() == "") {
                results.values = allMovieList;
                results.count = allMovieList.size();
            } else {

                List<Movie> temp = new ArrayList<>();
                for (Movie d : allMovieList) {
                    //or use .equal(text) with you want equal match
                    //use .toLowerCase() for better matches
                    if (d.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        temp.add(d);
                    }
                }
                results.values = temp;
                results.count = temp.size();
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            moviesList = (ArrayList<Movie>) results.values;
            notifyDataSetChanged();
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
        }
    }


    public MoviesAdapter(List<Movie> moviesList) {
        this.moviesList = moviesList;
        this.allMovieList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        holder.genre.setText(movie.getGenre());
        holder.year.setText(movie.getYear());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }


}