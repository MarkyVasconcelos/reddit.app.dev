package br.mv.redditreader.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Marcos Vasconcelos on 20/02/2017.
 */
public class RedditPost implements Parcelable {
    private String selfText;
    private String thumbnailUrl;
    private String title;
    private String permalink;
    private int numComments;
    private int numUps;
    private String author;
    private long createdAt;
//    private List<String> imagesPreviewUrls = new ArrayList<>();
    private String fromUrl;
    private String name;

    public RedditPost(){ }

    protected RedditPost(Parcel in) {
        selfText = in.readString();
        if(selfText.equals("null")) selfText = null;
        thumbnailUrl = in.readString();
        if(thumbnailUrl.equals("null")) thumbnailUrl = null;
        title = in.readString();
        if(title.equals("null")) title = null;
        permalink = in.readString();
        if(permalink.equals("null")) permalink = null;
        numComments = in.readInt();
        numUps = in.readInt();
        author = in.readString();
        if(author.equals("null")) author = null;
        createdAt = in.readLong();
        //imagesPreviewUrls = in.createStringArrayList();
        fromUrl = in.readString();
        if(fromUrl.equals("null")) fromUrl = null;
        name = in.readString();
        if(name.equals("null")) name = null;
    }

    public static final Creator<RedditPost> CREATOR = new Creator<RedditPost>() {
        @Override
        public RedditPost createFromParcel(Parcel in) {
            return new RedditPost(in);
        }

        @Override
        public RedditPost[] newArray(int size) {
            return new RedditPost[size];
        }
    };

    public void setSelfText(String selfText) {
        this.selfText = selfText;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        if("null".equals(thumbnailUrl))
            this.thumbnailUrl = null;
        else
            this.thumbnailUrl = thumbnailUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public void setNumComments(int numComments) {
        this.numComments = numComments;
    }

    public void setNumUps(int numUps) {
        this.numUps = numUps;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

//    public void addPreviewImage(String imageUrl) {
//        imagesPreviewUrls.add(imageUrl);
//    }

    public void setFromUrl(String fromUrl) {
        this.fromUrl = fromUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getFromUrl() {
        return fromUrl;
    }

    public String buildInfoString() {
        StringBuilder result = new StringBuilder();
        result.append(numUps).append("ups, ");
        result.append(numComments).append(" comentarios.");
        result.append("Criado em: ").append(formatter.format(new Date(createdAt)));
        result.append(" por ").append(author).append(".");

        return result.toString();
    }

    final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm");

//    public String firstPreviewImage() {
//        if(imagesPreviewUrls.size() == 0)
//            return null;
//
//        return imagesPreviewUrls.get(0);
//    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(selfText == null ? "null" : selfText);
        dest.writeString(thumbnailUrl == null ? "null" : thumbnailUrl);
        dest.writeString(title == null ? "null" : title);
        dest.writeString(permalink == null ? "null" : permalink);
        dest.writeInt(numComments);
        dest.writeInt(numUps);
        dest.writeString(author == null ? "null" : author);
        dest.writeLong(createdAt);
        //dest.writeStringList(imagesPreviewUrls);
        dest.writeString(fromUrl == null ? "null" : fromUrl);
        dest.writeString(name == null ? "null" : name);
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getSelfText() {
        return selfText;
    }

    public String getPermalink() {
        return permalink;
    }
}
