package koi.com.crasher;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.PurchaseEvent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {


    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    private Toolbar mToolbar;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new MoviesAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareMovieData();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_search:
                handleMenuSearch();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    protected void handleMenuSearch() {
        ActionBar action = getSupportActionBar(); //get the actionbar

        if (isSearchOpened) { //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_open_search));


            mAdapter.getFilter().filter("");

            isSearchOpened = false;
        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            edtSeach = (EditText) action.getCustomView().findViewById(R.id.edtSearch); //the text editor


            edtSeach.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    filter(s.toString());
                }
            });


            edtSeach.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);


            //add the close icon
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_close_search));

            isSearchOpened = true;


        }
    }

    private void filter(String text) {

        mAdapter.getFilter().filter(text);
    }

    @Override
    public void onBackPressed() {
        if (isSearchOpened) {
            handleMenuSearch();
            return;
        }
        super.onBackPressed();
    }


    private void prepareMovieData() {
        Movie movie = new Movie("Mad Max: Fury Road", "Action & Adventure", "2015");
        movieList.add(movie);

        movie = new Movie("Inside Out", "Animation, Kids & Family", "2015");
        movieList.add(movie);

        movie = new Movie("Star Wars: Episode VII - The Force Awakens", "Action", "2015");
        movieList.add(movie);

        movie = new Movie("Shaun the Sheep", "Animation", "2015");
        movieList.add(movie);

        movie = new Movie("The Martian", "Science Fiction & Fantasy", "2015");
        movieList.add(movie);

        movie = new Movie("Mission: Impossible Rogue Nation", "Action", "2015");
        movieList.add(movie);

        movie = new Movie("Up", "Animation", "2009");
        movieList.add(movie);

        movie = new Movie("Star Trek", "Science Fiction", "2009");
        movieList.add(movie);

        movie = new Movie("The LEGO Movie", "Animation", "2014");
        movieList.add(movie);

        movie = new Movie("Iron Man", "Action & Adventure", "2008");
        movieList.add(movie);

        movie = new Movie("Aliens", "Science Fiction", "1986");
        movieList.add(movie);

        movie = new Movie("Chicken Run", "Animation", "2000");
        movieList.add(movie);

        movie = new Movie("Back to the Future", "Science Fiction", "1985");
        movieList.add(movie);

        movie = new Movie("Raiders of the Lost Ark", "Action & Adventure", "1981");
        movieList.add(movie);

        movie = new Movie("Goldfinger", "Action & Adventure", "1965");
        movieList.add(movie);

        movie = new Movie("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014");
        movieList.add(movie);

        mAdapter.notifyDataSetChanged();
    }
}



        /*

         String[] countries = {
            "United States of America", " Afghanistan", " Albania", " Algeria", " Andorra", " Angola", " Antigua & Deps",
            " Argentina", " Armenia", " Australia", " Austria", " Azerbaijan", " Bahamas", " Bahrain", " Bangladesh", " Barbados"};
            ,
            " Belarus", " Belgium", " Belize", " Benin", " Bhutan", " Bolivia", " Bosnia Herzegovina", " Botswana", " Brazil", " Brunei",
            " Bulgaria", " Burkina", " Burma", " Burundi", " Cambodia", " Cameroon", " Canada", " Cape Verde", " Central African Rep",
            " Chad", " Chile", " People's Republic of China", " Republic of China", " Colombia", " Comoros", " Democratic Republic of the Congo",
            " Republic of the Congo", " Costa Rica", "", " Croatia", " Cuba", " Cyprus", " Czech Republic", " Danzig", " Denmark", " Djibouti",
            " Dominica", " Dominican Republic", " East Timor", " Ecuador", " Egypt", " El Salvador", " Equatorial Guinea", " Eritrea", " Estonia",
            " Ethiopia", " Fiji", " Finland", " France", " Gabon", " Gaza Strip", " The Gambia", " Georgia", " Germany", " Ghana", " Greece",
            " Grenada", " Guatemala", " Guinea", " Guinea-Bissau", " Guyana", " Haiti", " Holy Roman Empire", " Honduras", " Hungary",
            " Iceland", " India", " Indonesia", " Iran", " Iraq", " Republic of Ireland", " Israel", " Italy", " Ivory Coast", " Jamaica",
            " Japan", " Jonathanland", " Jordan", " Kazakhstan", " Kenya", " Kiribati", " North Korea", " South Korea", " Kosovo",
            " Kuwait", " Kyrgyzstan", " Laos", " Latvia", " Lebanon", " Lesotho", " Liberia", " Libya", " Liechtenstein", " Lithuania",
            " Luxembourg", " Macedonia", " Madagascar", " Malawi", " Malaysia", " Maldives", " Mali", " Malta", " Marshall Islands",
            " Mauritania", " Mauritius", " Mexico", " Micronesia", " Moldova", " Monaco", " Mongolia", " Montenegro", " Morocco", " Mount Athos",
            " Mozambique", " Namibia", " Nauru", " Nepal", " Newfoundland", " Netherlands", " New Zealand", " Nicaragua", " Niger", " Nigeria",
            " Norway", " Oman", " Ottoman Empire", " Pakistan", " Palau", " Panama", " Papua New Guinea", " Paraguay", " Peru", " Philippines",
            " Poland", " Portugal", " Prussia", " Qatar", " Romania", " Rome", " Russian Federation", " Rwanda", " St Kitts & Nevis", " St Lucia",
            " Saint Vincent & the", " Grenadines", " Samoa", " San Marino", " Sao Tome & Principe", " Saudi Arabia", " Senegal", " Serbia",
            " Seychelles", " Sierra Leone", " Singapore", " Slovakia", " Slovenia", " Solomon Islands", " Somalia", " South Africa", " Spain",
            " Sri Lanka", " Sudan", " Suriname", " Swaziland", " Sweden", " Switzerland", " Syria", " Tajikistan", " Tanzania", " Thailand",
            " Togo", " Tonga", " Trinidad & Tobago", " Tunisia", " Turkey", " Turkmenistan", " Tuvalu", " Uganda", " Ukraine",
            " United Arab Emirates", " United Kingdom", " Uruguay", " Uzbekistan", " Vanuatu", " Vatican City", " Venezuela", " Vietnam",
            " Yemen", " Zambia", " Zimbabwe"};*/

