package hasmik.example.instagramclone.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import hasmik.example.instagramclone.LoginActivity;
import hasmik.example.instagramclone.MainActivity;
import hasmik.example.instagramclone.Post;
import hasmik.example.instagramclone.R;

public class UserFragment extends HomeFragment {

    View view;
    TextView ivUsername;
    ImageView ivProfilePic;
    Button btnLogOut;
    Button btnChangeProfilePic;
    ParseFile profilePic;
    ParseUser currentUser;

    private static final int GALLERY_REQUEST_CODE = 123;
    private static final String TAG = "UserFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        currentUser = ParseUser.getCurrentUser();
        ivProfilePic = view.findViewById(R.id.ivProfilePic);
        if (profilePic == null) {
            Glide.with(getContext()).load(R.drawable.blank).into(ivProfilePic);
        } else {
            Glide.with(getContext()).load(profilePic).into(ivProfilePic);
        }

        ivUsername = view.findViewById(R.id.ivUsername);
        ivUsername.setText(currentUser.getUsername());

        // Setup logout and change profile pic button
        btnLogOut = view.findViewById(R.id.btnLogOut);
        btnChangeProfilePic = view.findViewById(R.id.btnChangeProfilePic);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    logOut();
                }
            }
        });

        btnChangeProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select a picture"), GALLERY_REQUEST_CODE);
                }
            }
        });
        return view;
    }

    private void logOut() {
        ParseUser.logOut();
        Intent i = new Intent(view.getContext(), LoginActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageData = data.getData();
            ivProfilePic.setImageURI(imageData);
            File photoFile = new File(imageData.getPath());

            //currentUser.put("profilePic", new ParseFile(photoFile));
            currentUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Log.i(TAG, "Successful saving!");
                    } else {
                        Log.e(TAG, "Issue with saving posts", e);
                    }
                }
            });

            Post post = new Post();

            ParseQuery <Post> query = ParseQuery.getQuery(Post.class);
            query.include(Post.KEY_USER);
            query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
            query.findInBackground(new FindCallback<Post>() {
                @Override
                public void done(List<Post> posts, ParseException e) {
                    if (e != null) {
                        Log.e(TAG, "Issue with getting posts", e);
                        return;
                    }
                    post.setProfilePic(new ParseFile(photoFile));
                    adapter.clear();
                    adapter.addAll(posts);
                    swipeContainer.setRefreshing(false);
                }
            });
        }
    }

    protected void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for (Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", User: " + post.getUser().getUsername());
                }
                adapter.clear();
                adapter.addAll(posts);
                swipeContainer.setRefreshing(false);
            }
        });
    }
}
