package com.example.android.newsappstage2_rebuild;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    // The Guardian URL for chemistry

    private static final String GUARDIAN_REQUEST_URL =
            "http://content.guardianapis.com/search?";


    // Constant for the API search Key
    private static final String API_KEY = "api-key";

    // Constant value for the API Key
    private static final String KEY = "f1dfc1ea-9071-49cc-b586-005ed71ac92c";

    // Constant value for ordering by date
    private static final String ORDER = "order-by";
    private static final String DATE = "newest";

    // Constant value for adding article author
    private static final String TAGS = "show-tags";
    private static final String AUTHOR = "contributor";

    // Constant value for No's of articles
    private static final String PAGE = "page-size";
    private static final String PAGES = "15";

    // Constant value for section
    private static final String SECTION = "section";

    // Constant value for adding article thumbnail
    private static final String SHOW_FIELDS = "show-fields";
    private static final String THUMBNAIL = "thumbnail";

    // Constant value for the news loader ID. We can choose any integer.
    private static final int NEWS_LOADER_ID = 1;
    SwipeRefreshLayout swipeRefreshLayout;


    private LinearLayoutManager linearLayoutManager;

    private RecyclerView mNewsRecyclerView;
    private RecyclerViewAdapter mAdapter;
    public static List<News> mNewsList = new ArrayList<>();

    //warning message
    private String messageForUser;


    // Empty text view
    private TextView mEmptyStateTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rv_activity_main);


        //set swipe layout for refreshing news
        swipeRefreshLayout = findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeRefreshLayout.setRefreshing(false);
                newsLoader(GUARDIAN_REQUEST_URL);

            }
        });

        newsLoader(GUARDIAN_REQUEST_URL);

    }

    /**
     * Call the loader after a search term has been entered in the search bar.
     * @param searchString
     */

    private void newsLoader(String searchString){

        mNewsRecyclerView = findViewById(R.id.recycler_view);
        mAdapter = new RecyclerViewAdapter(mNewsList, this);

        mNewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mNewsRecyclerView.setAdapter(mAdapter);

        // Restart the loader to clear it if a search has already been carried out
        getLoaderManager().restartLoader(NEWS_LOADER_ID, null, this);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connectivityMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        assert connectivityMgr != null;
        NetworkInfo networkInfo = connectivityMgr.getActiveNetworkInfo();

        // Get the loading indicator view and assign it to loadingIndicator.
        View loadingIndicator = findViewById(R.id.loading_info);

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);

            // Make the loadingIndicator view visible.
            loadingIndicator.setVisibility(View.VISIBLE);

        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible

            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            messageForUser = (String) getText(R.string.no_connection);
            warningMessage(messageForUser);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        // Create a new loader for the given URL

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String searchCategory = sharedPreferences.getString(
                getString(R.string.pick_category1),
                getString(R.string.all));

        //extract news category name from list menu
        String[] categorySep = searchCategory.split(" ");
        String categoryCap = categorySep[0];
        String category = categoryCap.toLowerCase();


        // Create an URI and an URI Builder
        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Append the search parameters to the request URL

        //for all news category tag is not aplly
        if (!category.equals("all")) {
            uriBuilder.appendQueryParameter(SECTION, category);
        }

        // for US new change tag
        if (category.equals(getString(R.string.tagUS))) {
            category = category + getString(R.string.tagForUsUk);
            uriBuilder.appendQueryParameter(SECTION, category);
        }
        // for UK new change tag
        if (category.equals(getString(R.string.tagUK))) {
            category = category + getString(R.string.tagForUsUk);
            uriBuilder.appendQueryParameter(SECTION, category);
        }

        uriBuilder.appendQueryParameter(ORDER, DATE);
        uriBuilder.appendQueryParameter(TAGS, AUTHOR);
        uriBuilder.appendQueryParameter(PAGE, PAGES);
        uriBuilder.appendQueryParameter(SHOW_FIELDS, THUMBNAIL);
        uriBuilder.appendQueryParameter(API_KEY, KEY);

        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> newsLoader, List<News> news) {

        //Hide loading indicator because data were loaded
        View loadingIndicator = findViewById(R.id.loading_info);
        loadingIndicator.setVisibility(View.GONE);

        // Clear the adapter of previous earthquake data
        this.mNewsList.clear();
        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (news != null && !news.isEmpty()) {
            this.mNewsList.addAll(news);

            if (news.isEmpty()) {
                // Set empty state text view to display
                messageForUser = (String) getText(R.string.sorry_there_are_no_news_to_display);

                warningMessage(messageForUser);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // Loader reset, so we can clear out our existing data.
        this.mNewsList.clear();
    }

    /**
     * hides the loading indicator and displays a message with explanation
     */
    private void warningMessage(String messageForUser) {
        // Hide progress indicator

        View loadingIndicator = findViewById(R.id.loading_info);
        loadingIndicator.setVisibility(View.GONE);
        // set text
        mEmptyStateTextView.setVisibility(View.VISIBLE);
        mEmptyStateTextView.setText(messageForUser);
    }

    @Override
    // This method initialize the contents of the Activity's options menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    // This method is called whenever an item in the options menu is selected.
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.setting) {
            Intent settingsIntent = new Intent(this, NewsSetting.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        // Restart the loader.
        loaderManager.restartLoader(NEWS_LOADER_ID, null, this);
    }

    //method for refreshing news
    public void restartLoader() {
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.restartLoader(1, null, this);
    }
}