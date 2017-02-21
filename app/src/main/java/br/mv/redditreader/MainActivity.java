package br.mv.redditreader;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.SupportMenuInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import br.mv.redditreader.info.NetworkInfo;
import br.mv.redditreader.model.RedditPost;
import br.mv.redditreader.parser.RedditJSONParser;
import br.mv.redditreader.view.RedditPostView;

public class MainActivity extends AppCompatActivity {
    private ListView content;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().show();

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_view);
        content = (ListView) findViewById(R.id.lv_content);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });

        refreshContent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new SupportMenuInflater(this).inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_refresh){
            refreshLayout.setRefreshing(true);
            refreshContent();
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshContent() {
        if(!(new NetworkInfo(getApplication()).isConnected())) {
            content.setAdapter(new ErrorAdapter("Sem conexão com a internet"));
            return;
        }

        WebRequestService.request(this, "https://www.reddit.com/r/Android/new/.json?sr_detail=true", new ResultReceiver(new Handler()){
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                super.onReceiveResult(resultCode, resultData);
                if(resultCode == WebRequestService.RESULT_ERROR){
                    String errorMsg = resultData.getString(WebRequestService.RESULT_KEY);
                    content.setAdapter(new ErrorAdapter(errorMsg));
                    return;
                }

                String requestContent = resultData.getString(WebRequestService.RESULT_KEY);
                try {
                    List<RedditPost> allPosts = RedditJSONParser.parse(requestContent);
                    content.setAdapter(new RedditPostAdapter(allPosts, new EndListener(){
                        @Override
                        public void onEndReached(RedditPost at) {
                            Toast.makeText(MainActivity.this, "Carregando mais items", Toast.LENGTH_SHORT).show();
                            loadMoreItems(at, (RedditPostAdapter) content.getAdapter());
                        }
                    }));
                } catch (JSONException e) {
                    e.printStackTrace();
                    content.setAdapter(new ErrorAdapter(e.getMessage()));
                }

                refreshLayout.setRefreshing(false);
            }

            private void loadMoreItems(RedditPost from, final RedditPostAdapter adapter) {
                WebRequestService.request(MainActivity.this, "https://www.reddit.com/r/Android/new/.json?sr_detail=true&after="+from.getName(), new ResultReceiver(new Handler()){
                    @Override
                    protected void onReceiveResult(int resultCode, Bundle resultData) {
                        super.onReceiveResult(resultCode, resultData);
                        if(resultCode == WebRequestService.RESULT_ERROR){
                            String errorMsg = resultData.getString(WebRequestService.RESULT_KEY);
                            Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                            return;
                        }

                        String requestContent = resultData.getString(WebRequestService.RESULT_KEY);
                        try {
                            List<RedditPost> allPosts = RedditJSONParser.parse(requestContent);
                            adapter.addAll(allPosts);
                            Toast.makeText(MainActivity.this, "\\/", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    private static class ErrorAdapter extends BaseAdapter {
        private final boolean OBJECT = true;
        private final String msg;

        ErrorAdapter(String msg){
            this.msg = msg;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView errorMsg = new TextView(viewGroup.getContext());
            errorMsg.setText(msg);
            return errorMsg;
        }

        @Override public int getCount() { return 1; }
        @Override public Object getItem(int i) { return OBJECT; }
        @Override public long getItemId(int i) { return i; }
    }

    interface EndListener {
        void onEndReached(RedditPost at);
    }
    private class RedditPostAdapter extends BaseAdapter {
        private final List<RedditPost> data = new ArrayList<>();
        private final EndListener scrollListener;

        public RedditPostAdapter(List<RedditPost> allPosts, EndListener scrollListener) {
            this.scrollListener = scrollListener;
            data.addAll(allPosts);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            RedditPostView result;
            if(view != null)
                result = (RedditPostView) view;
            else
                result = new RedditPostView(viewGroup.getContext());

            RedditPost item = getItem(i);
            result.displayItem(item);
            if(i == getCount() - 1)
                scrollListener.onEndReached(item);

            return result;
        }

        public void addAll(List<RedditPost> allPosts) {
            data.addAll(allPosts);
            notifyDataSetChanged();
        }

        @Override public int getCount() { return data.size(); }
        @Override public RedditPost getItem(int i) { return data.get(i); }
        @Override public long getItemId(int i) { return i; }
    }
}
