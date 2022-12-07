package utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Use this class to add space in between the recyclerview items
 * @author Ece Selin Yorgancilar
 */
public class SpacingItemDecorator extends RecyclerView.ItemDecoration {

    private final int spaceHeight;
    public SpacingItemDecorator(int spaceHeight){
        this.spaceHeight = spaceHeight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect r, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        r.bottom = spaceHeight;
    }
}
