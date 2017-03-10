package shintellilink.slidingrecyclerviewdelete;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemClickListener,MyLinearLayout.OnScrollListener {

    private MyLinearLayout mLastScrollView;
    MyRecyclerView rv_delete;
    List<Bean> beanList = new ArrayList<>();
    MyRecyclerViewAdapter myRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv_delete = (MyRecyclerView) findViewById(R.id.rv_delete);
        for (int i = 0; i < 10; i++) {
            Bean bean = new Bean("第" + i + "项");
            beanList.add(bean);
        }
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(beanList);
        rv_delete.setLayoutManager(new LinearLayoutManager(getApplicationContext() ));
        myRecyclerViewAdapter.setOnItemClickListener(this);
        myRecyclerViewAdapter.setScrollClickListener(this);
        rv_delete.setAdapter(myRecyclerViewAdapter);
    }

    @Override
    public void itemDeleteClick(View view, int position) {
        myRecyclerViewAdapter.removeItem(position);
        Toast.makeText(this, "删除" + position + "个", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnScroll(MyLinearLayout view) {
        if (mLastScrollView != null){
            mLastScrollView.smoothScrollTo(0,0);
        }
        mLastScrollView = view;
    }
}
