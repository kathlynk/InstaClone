package hasmik.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        this.getSupportActionBar().hide();
    }
}