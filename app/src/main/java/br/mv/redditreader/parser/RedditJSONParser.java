package br.mv.redditreader.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.mv.redditreader.model.RedditPost;

/**
 * Created by Marcos Vasconcelos on 20/02/2017.
 */
public class RedditJSONParser {
    public static List<RedditPost> parse(String content) throws JSONException {
        List<RedditPost> result = new ArrayList<>();

        JSONObject obj = new JSONObject(content);
        JSONArray data = obj.getJSONObject("data").getJSONArray("children");
        for(int i = 0; i < data.length(); i++){
            JSONObject postData = data.getJSONObject(i).getJSONObject("data");

            RedditPost resultObj = new RedditPost();
            resultObj.setSelfText(postData.getString("selftext"));
            resultObj.setThumbnailUrl(postData.getString("thumbnail"));
            resultObj.setTitle(postData.getString("title"));
            resultObj.setPermalink(postData.getString("permalink"));
            resultObj.setNumComments(postData.getInt("num_comments"));
            resultObj.setNumUps(postData.getInt("ups"));
            resultObj.setAuthor(postData.getString("author"));
            resultObj.setCreatedAt(postData.getInt("created"));
            resultObj.setFromUrl(postData.getString("url"));
            resultObj.setName(postData.getString("name"));

            JSONObject preview = postData.optJSONObject("preview");
            if(preview != null){
                JSONArray imagePreviews = preview.getJSONArray("images");
                for(int j = 0; j < imagePreviews.length(); j++){
                    JSONObject source = data.getJSONObject(j).optJSONObject("source");
                    if(source != null){
                        String url = source.optString("url", "");
                        if(!url.isEmpty())
                            resultObj.addPreviewImage(url);
                    }
                }
            }

            result.add(resultObj);
        }

        Collections.reverse(result);
        return result;
    }
}
