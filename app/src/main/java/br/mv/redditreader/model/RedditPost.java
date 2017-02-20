package br.mv.redditreader.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Marcos Vasconcelos on 20/02/2017.
 */
public class RedditPost {
    private String selfText;
    private String thumbnailUrl;
    private String title;
    private String permalink;
    private int numComments;
    private int numUps;
    private String author;
    private int createdAt;
    private List<String> imagesPreviewUrls = new ArrayList<>();
    private String fromUrl;

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

    public void setCreatedAt(int createdAt) {
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

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm");

    public String firstPreviewImage() {
        if(imagesPreviewUrls.size() == 0)
            return null;

        return imagesPreviewUrls.get(0);
    }
}
