package algonquin.cst2335.finalproject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PexelsModel {

    @PrimaryKey()
    @ColumnInfo(name = "id")
    protected int id;

    @ColumnInfo(name = "width")
    protected int width;

    @ColumnInfo(name = "height")
    protected int height;

    @ColumnInfo(name = "url")
    protected String url;

    @ColumnInfo(name = "photographer")
    protected String photographer;

    @ColumnInfo(name = "imgThumbnail")
    protected String imgThumbnail;

    @ColumnInfo(name = "originalImg")
    protected String originalImg;

    public PexelsModel() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhotographer() {
        return photographer;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public String getImgThumbnail() {
        return imgThumbnail;
    }

    public void setImgThumbnail(String imgThumbnail) {
        this.imgThumbnail = imgThumbnail;
    }

    public String getOriginalImg() {
        return originalImg;
    }

    public void setOriginalImg(String originalImg) {
        this.originalImg = originalImg;
    }

}
