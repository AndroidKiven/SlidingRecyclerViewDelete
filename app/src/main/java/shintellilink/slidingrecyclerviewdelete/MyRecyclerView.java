package shintellilink.slidingrecyclerviewdelete;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Intellilink-Kiven on 2017/1/13.
 */

public class MyRecyclerView extends RecyclerView {
    private Rect mTouchFrame;
    private int pos;
    private MyLinearLayout itemRoot;

    public MyRecyclerView(Context context) {
        this(context, null);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                //通过点击的坐标计算当前的position
                int mFirstPosition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();

                Rect frame = mTouchFrame;
                if (frame == null) {
                    mTouchFrame = new Rect();
                    frame = mTouchFrame;
                }
                int count = getChildCount();
                for (int i = count - 1; i >= 0; i--) {
                    final View child = getChildAt(i);
                    if (child.getVisibility() == View.VISIBLE) {
                        child.getHitRect(frame);
                        if (frame.contains(x, y)) {
                            pos = mFirstPosition + i;
                        }
                    }
                }
                //通过position得到item的viewHolder
                View view = getChildAt(pos - mFirstPosition);
                Toast.makeText(getContext(), "pos:" + (pos - mFirstPosition), Toast.LENGTH_SHORT).show();
                MyRecyclerViewAdapter.itemHolder viewHolder = (MyRecyclerViewAdapter.itemHolder) getChildViewHolder(view);
                itemRoot = viewHolder.item_root;
            }
            break;
            default:
                break;
        }
        if (itemRoot != null) {
            itemRoot.disPatchTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }
}


