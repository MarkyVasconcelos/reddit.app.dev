package br.mv.redditreader;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import br.mv.redditreader.model.RedditPost;

public class PostDetailsActivity extends AppCompatActivity {
    private static final String OBJECT_KEY = "object_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        final RedditPost item = getIntent().getParcelableExtra(OBJECT_KEY);

        TextView info = (TextView) findViewById(R.id.tv_info);
        TextView text = (TextView) findViewById(R.id.tv_content_text);
        final ImageView previewImage = (ImageView) findViewById(R.id.iv_preview_image);

        getSupportActionBar().setTitle(item.getTitle());
        info.setText(item.buildInfoString());
        text.setText(item.getSelfText());

        findViewById(R.id.btn_from_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(WebUrlActivity.createOpenIntent(PostDetailsActivity.this, "https://www.reddit.com" + item.getPermalink(), item.getTitle()));
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.reddit.com" + item.getPermalink())));
            }
        });

        findViewById(R.id.btn_from_url).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(WebUrlActivity.createOpenIntent(PostDetailsActivity.this, item.getFromUrl(), item.getTitle()));
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(item.getFromUrl())));
            }
        });

        String firstPreview = item.getThumbnailUrl();
        if(firstPreview != null && !"self".equals(firstPreview)) {//Self doesnt contain preview
            previewImage.setVisibility(View.VISIBLE);
            Picasso.with(this).load(firstPreview).into(previewImage);
        }else
            previewImage.setVisibility(View.GONE);
    }

    public static Intent createOpenIntent(Context from, RedditPost item) {
        Intent intent = new Intent(from, PostDetailsActivity.class);
        intent.putExtra(OBJECT_KEY, item);
        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
