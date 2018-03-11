package com.example.arg_a.moviesapp.DB;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by arg-a on 08/03/2018.
 */

public class MoviesContract {

    public static final String AUTHORITY = "com.example.arg_a.moviesapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_MOVIES          = "movies";
    public static final String PATH_MOVIE_WITH_ID   = "movieID";

    public static final class MoviesTable implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI
                                                .buildUpon()
                                                .appendPath(PATH_MOVIES)
                                                .build();

        public static final Uri MOVIE_ID_URI = BASE_CONTENT_URI
                                                .buildUpon()
                                                .appendPath(PATH_MOVIE_WITH_ID)
                                                .build();

        public static final String TABLE_NAME               = "movies";
        public static final String COLUMN_ORIGINAL_TITLE    = "originalTitle";
        public static final String COLUMN_POSTER_IMAGE      = "posterImage";
        public static final String COLUMN_SYNOPSIS          = "synopsis";
        public static final String COLUMN_RATING            = "rating";
        public static final String COLUMN_RELEASE_DATE      = "releaseDate";

        public static Uri buildMovieIDUri(int id){

            Uri uri = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE_WITH_ID)
                    .appendPath(Integer.toString(id))
                    .build();

            return uri;
        }
    }
}
