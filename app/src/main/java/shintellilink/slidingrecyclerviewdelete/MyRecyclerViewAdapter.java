package shintellilink.slidingrecyclerviewdelete;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Intellilink-Kiven on 2017/1/13.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.itemHolder> {

    List<Bean> stringList;
    ItemClickListener delClickListener;
    MyLinearLayout.OnScrollListener mScrollListener;

    public MyRecyclerViewAdapter(List<Bean> stringList) {
        this.stringList = stringList;

    }

    public void setOnItemClickListener(ItemClickListener delClickListener) {
        this.delClickListener = delClickListener;
    }

    public void setScrollClickListener(MyLinearLayout.OnScrollListener mScrollListener) {
        this.mScrollListener = mScrollListener;
    }

    @Override
    public itemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemHolder holder = new itemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final itemHolder holder, int position) {
        holder.tv_content.setText(stringList.get(position).getItemContent());

        if (delClickListener != null) {
            holder.tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    delClickListener.itemDeleteClick(holder.item_root, pos);
                }
            });
        }
        holder.item_root.scrollTo(0, 0);
        holder.item_root.setOnScrollListener(mScrollListener);
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public void removeItem(int position) {
        stringList.remove(position);
        //notifyDataSetChanged();
        notifyItemRemoved(position);
    }

    class itemHolder extends RecyclerView.ViewHolder {
        TextView tv_content;
        MyLinearLayout item_root;
        TextView tv_delete;

        public itemHolder(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            item_root = (MyLinearLayout) itemView.findViewById(R.id.item_root);
            tv_delete = (TextView) itemView.findViewById(R.id.tv_delete);
        }
    }

}
