package hasmik.example.instagramclone;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String LIKE_COUNT = "likeCount";
    public static final String LIKED = "liked";
    public static final String PROFILE_PIC = "profilePic";

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile) {
        put(KEY_IMAGE, parseFile);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public int getLikes() {
        return getInt(LIKE_COUNT);
    }

    public void updateLikes(boolean like) {
        if (like) {
            put(LIKE_COUNT, getLikes() + 1);
        } else {
            put(LIKE_COUNT, getLikes() - 1);
        }
    }

    public boolean isLiked() {
        return getBoolean(LIKED);
    }

    public void setLiked(boolean liked) {
        put(LIKED, liked);
    }

    public ParseFile getProfilePic() {
        return getParseFile(PROFILE_PIC);
    }

    public void setProfilePic(ParseFile parseFile) {
        put(PROFILE_PIC, parseFile);
    }
}
