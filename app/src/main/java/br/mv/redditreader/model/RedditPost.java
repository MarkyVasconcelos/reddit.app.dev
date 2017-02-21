package br.mv.redditreader.model;

import android.os.Parcel;
import android.os.Parcelable;

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
    private List<String> imagesPreviewUrls = new ArrayList<>();
    private String fromUrl;
    private String name;

    public RedditPost(){ }

    protected RedditPost(Parcel in) {
        selfText = in.readString();
        thumbnailUrl = in.readString();
        title = in.readString();
        permalink = in.readString();
        numComments = in.readInt();
        numUps = in.readInt();
        author = in.readString();
        createdAt = in.readLong();
        imagesPreviewUrls = in.createStringArrayList();
        fromUrl = in.readString();
        name = in.readString();
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

    public void addPreviewImage(String imageUrl) {
        imagesPreviewUrls.add(imageUrl);
    }

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

    public String firstPreviewImage() {
        if(imagesPreviewUrls.size() == 0)
            return null;

        return imagesPreviewUrls.get(0);
    }

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
        dest.writeString(selfText);
        dest.writeString(thumbnailUrl);
        dest.writeString(title);
        dest.writeString(permalink);
        dest.writeInt(numComments);
        dest.writeInt(numUps);
        dest.writeString(author);
        dest.writeLong(createdAt);
        dest.writeStringList(imagesPreviewUrls);
        dest.writeString(fromUrl);
        dest.writeString(name);
    }
}
