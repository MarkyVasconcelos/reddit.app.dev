package br.mv.redditreader;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import br.mv.redditreader.info.NetworkInfo;
import br.mv.redditreader.model.RedditPost;
import br.mv.redditreader.parser.RedditJSONParser;
import br.mv.redditreader.view.RedditPostView;

public class MainActivity extends Activity {
    private ListView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_view);
        content = (ListView) findViewById(R.id.lv_content);

        refreshContent();
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
                    content.setAdapter(new RedditPostAdapter(allPosts));
                } catch (JSONException e) {
                    e.printStackTrace();
                    content.setAdapter(new ErrorAdapter(e.getMessage()));
                }
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

    private class RedditPostAdapter extends BaseAdapter {
        private final List<RedditPost> data = new ArrayList<>();

        public RedditPostAdapter(List<RedditPost> allPosts) {
            data.addAll(allPosts);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            RedditPostView result;
            if(view != null)
                result = (RedditPostView) view;
            else
                result = new RedditPostView(viewGroup.getContext());

            result.displayItem(getItem(i));

            return result;
        }

        @Override public int getCount() { return data.size(); }
        @Override public RedditPost getItem(int i) { return data.get(i); }
        @Override public long getItemId(int i) { return i; }
    }
}
