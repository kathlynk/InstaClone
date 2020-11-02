package hasmik.example.instagramclone;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>  {

    public static final String TAG = "HomeAdapter";

    private Context context;
    private List<Post> posts;

    public HomeAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        try {
            holder.bind(post);
        } catch (ParseException e) {
            Log.e(TAG, "Issue with binding", e);
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUsername;
        private ImageView ivImage;
        private ImageView ivLike;
        private ImageView ivComment;
        private ImageView ivSave;
        private TextView tvLikeCount;
        private TextView tvDescription;
        private TextView tvCreatedAt;
        private ImageView ivProfilePic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivImage);
            ivLike = itemView.findViewById(R.id.ivLike);
            ivComment = itemView.findViewById(R.id.ivComment);
            ivSave = itemView.findViewById(R.id.ivSave);
            tvLikeCount = itemView.findViewById(R.id.tvLikeCount);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
        }

        public void bind(Post post) throws ParseException {
            // Bind the post data to the view elements
            tvUsername.setText(post.getUser().getUsername());
            Glide.with(context).load(post.getImage().getUrl()).into(ivImage);
            tvLikeCount.setText(post.getLikes() + " likes");
            tvDescription.setText(post.getDescription());
            String time = post.getCreatedAt().toString();
            tvCreatedAt.setText(TimeFormatter.getTimeDifference(time));
            if (post.getProfilePic() == null) {
                Glide.with(context).load(R.drawable.blank).into(ivProfilePic);
            } else {
                Glide.with(context).load(post.getProfilePic()).into(ivProfilePic);
            }

            // loading Animation from
            final Animation animation = AnimationUtils.loadAnimation(context, R.anim.bounce);

            // Liking post
            ivLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!post.isLiked()) {     // if post liked = false
                        ivLike.startAnimation(animation);
                        post.updateLikes(true);
                        post.setLiked(true);
                    } else {
                        ivLike.startAnimation(animation);
                        post.updateLikes(false);
                        post.setLiked(false);
                    }
                    notifyDataSetChanged();
                }
            });

            // Commenting on post
            ivComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Opens comment activity to read and post comments
                }
            });

            // Saving post
            ivSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Saves post
                }
            });
        }
    }

}
