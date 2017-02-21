package br.mv.redditreader.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import br.mv.redditreader.PostDetailsActivity;
import br.mv.redditreader.R;
import br.mv.redditreader.model.RedditPost;

/**
 * Created by Marcos Vasconcelos on 20/02/2017.
 */
public class RedditPostView extends LinearLayout {
    private final TextView title, info, fromUrl;
    private final ImageView previewImage;

    public RedditPostView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.view_reddit_post, this, true);

        title = (TextView) findViewById(R.id.tv_title);
        info = (TextView) findViewById(R.id.tv_info);
        fromUrl = (TextView) findViewById(R.id.tv_from_url);
        previewImage = (ImageView) findViewById(R.id.iv_preview_image);
    }

    public void displayItem(RedditPost item) {
        previewImage.setImageDrawable(null); // Clear old image if recycled view

        title.setText(item.getTitle());
        info.setText(item.buildInfoString());
        fromUrl.setText(item.getFromUrl());

        String firstPreview = item.getThumbnailUrl();
        if(firstPreview != null && !"self".equals(firstPreview)){
            previewImage.setVisibility(View.VISIBLE);
            Picasso.with(getContext()).load(firstPreview).into(previewImage);
        }else
            previewImage.setVisibility(View.GONE);
    }
}
