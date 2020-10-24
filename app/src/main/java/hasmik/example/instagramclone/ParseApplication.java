package hasmik.example.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {

    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("oz5DmiT3SBDE6wZOU7dCk1y0uEp2nVzDEOGrfWrX")
                .clientKey("BjaKs7k5TTsNwQJtVAtgeBVbWKRPxywWHbR6VyIj")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
