package com.example.arg_a.moviesapp.DB;

import android.provider.BaseColumns;

/**
 * Created by arg-a on 08/03/2018.
 */

public class MoviesContract {

    public static final class MoviesTable implements BaseColumns{

        public static final String TABLE_NAME               = "movies";
        public static final String COLUMN_ORIGINAL_TITLE    = "originalTitle";
        public static final String COLUMN_POSTER_IMAGE      = "posterImage";
        public static final String COLUMN_SYNOPSIS          = "synopsis";
        public static final String COLUMN_RATING            = "rating";
        public static final String COLUMN_RELEASE_DATE      = "releaseDate";
    }
}
