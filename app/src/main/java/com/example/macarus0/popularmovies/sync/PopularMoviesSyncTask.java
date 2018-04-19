package com.example.macarus0.popularmovies.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.macarus0.popularmovies.R;
import com.example.macarus0.popularmovies.data.MovieContract;
import com.example.macarus0.popularmovies.util.MovieJSONUtilities;
import com.example.macarus0.popularmovies.util.NetworkUtils;


public class PopularMoviesSyncTask {

    synchronized public static void syncMovies(Context context) {
        try {

            String movieRequestUrl = NetworkUtils.getPopularMoviesUrl(context.getString(R.string.tmbd_api_key));
            String jsonPopularMoviesResponse = NetworkUtils.getStringFromUrl(movieRequestUrl);

            ContentValues[] movieValues = MovieJSONUtilities.parsePopularJSON(jsonPopularMoviesResponse);

            if(movieValues != null && movieValues.length != 0) {
                ContentResolver contentResolver = context.getContentResolver();

                int rowsInserted = contentResolver.bulkInsert(
                        MovieContract.MovieEntry.CONTENT_URI,
                        movieValues);

            } else {
                Log.d("SyncMovies", String.format("Got no movies"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
