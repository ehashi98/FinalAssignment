package algonquin.cst2335.finalproject;

public class ModelClass {

    private int imageView1;
    private String textView;
    private String button;
    private String divider;

    ModelClass (int imageView1,String textView, String button, String divider){
        this.imageView1 = imageView1;
        this.textView = textView;
        this.button = button;
        this.divider = divider;
    }

    public int getImageView1() {
        return imageView1;
    }

    public void setImageView1(int imageView1) {
        this.imageView1 = imageView1;
    }

    public String getTextView() {
        return textView;
    }

    public void setTextView(String textView) {
        this.textView = textView;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }

    public String getDivider() {
        return divider;
    }

    public void setDivider(String divider) {
        this.divider = divider;
    }
}
