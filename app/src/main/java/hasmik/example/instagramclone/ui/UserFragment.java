package hasmik.example.instagramclone.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.parse.ParseUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import hasmik.example.instagramclone.LoginActivity;
import hasmik.example.instagramclone.MainActivity;
import hasmik.example.instagramclone.R;

public class UserFragment extends Fragment {

    View view;
    Button btnLogOut;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // Setup logout button
        btnLogOut = view.findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ParseUser.getCurrentUser() != null) {
                    logOut();
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
}
