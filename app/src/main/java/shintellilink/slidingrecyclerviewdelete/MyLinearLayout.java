package shintellilink.slidingrecyclerviewdelete;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by Intellilink-Kiven on 2017/1/13.
 */

public class MyLinearLayout extends LinearLayout {
    private int mlastX = 0;
    private final int MAX_WIDTH = 90;
    private Context mContext;
    private Scroller mScroller;
    private OnScrollListener mScrollListener;
    float downX = 0;
    float downY = 0;


    public static interface OnScrollListener {
        public void OnScroll(MyLinearLayout view);
    }


    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mScroller = new Scroller(context, new LinearInterpolator(context, null));
    }

    public void disPatchTouchEvent(MotionEvent event) {
        int maxLength = dipToPx(mContext, MAX_WIDTH);

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                downX = x;
                downY = y;
                if (mScrollListener != null) {
                    mScrollListener.OnScroll(this);
                }
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                float deltaX = x - downX;
                float deltaY = y - downY;
              if (Math.abs(deltaX) > Math.abs(deltaY) && Math.abs(deltaX) > 5) {

                    int scrollX = this.getScrollX();
                    int newScrollX = scrollX + mlastX - x;
                    if (newScrollX < 0) {
                        newScrollX = 0;
                    } else if (newScrollX > maxLength) {
                        newScrollX = maxLength;
                    }
                    this.scrollTo(newScrollX, 0);

                    if (mScrollListener != null) {
                        mScrollListener.OnScroll(this);
                    }
                }
           }
            break;
            case MotionEvent.ACTION_UP: {
                int scrollX = this.getScrollX();
                int newScrollX = scrollX + mlastX - x;
                if (scrollX > maxLength / 15) {
                    newScrollX = maxLength;
                    //当完全展开时,通知出去
                    if (mScrollListener != null) {
                        mScrollListener.OnScroll(this);
                    }
                } else {
                    newScrollX = 0;
                }
                mScroller.startScroll(scrollX, 0, newScrollX - scrollX, 0);
                invalidate();
            }
            break;
        }

  /*      }*/
        mlastX = x;
    }

    public void setOnScrollListener(OnScrollListener scrollListener) {
        mScrollListener = scrollListener;
    }

    public void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        int delta = destX - scrollX;
        mScroller.startScroll(scrollX, 0, delta, 0);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            this.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
        }
        invalidate();
    }

    private int dipToPx(Context context, int dip) {
        return (int) (dip * context.getResources().getDisplayMetrics().density + 0.5f);
    }
}
