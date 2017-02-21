package br.mv.redditreader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class WebUrlActivity extends AppCompatActivity {
    private static final String URL_KEY = "url_key";
    private static final String TITLE_KEY = "title_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView content = new WebView(this);
        setContentView(content);

        content.setWebChromeClient(new WebChromeClient());
        content.getSettings().setJavaScriptEnabled(true);
        String loadUrl = getIntent().getStringExtra(URL_KEY);
        content.loadUrl(loadUrl);

        getSupportActionBar().setTitle(getIntent().getStringExtra(TITLE_KEY));
    }

    public static Intent createOpenIntent(Context ctx, String fromUrl, String title) {
        Intent open = new Intent(ctx, WebUrlActivity.class);
        open.putExtra(URL_KEY, fromUrl);
        open.putExtra(TITLE_KEY, title);
        return open;
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
