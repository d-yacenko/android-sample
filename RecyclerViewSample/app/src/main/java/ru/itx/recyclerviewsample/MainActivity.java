package ru.itx.recyclerviewsample;

// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// обратите внимание на то что в build.gradle (app) добавлена строка
// compile 'com.android.support:recyclerview-v7:26.1.+'
// compile 'com.android.support:cardview-v7:26.1.+'
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    String data[]={"Ivan","Petr","Katya","Svetlana","Oleg","Georg","Khan","Olga","Nina","Sam","Andrey"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //listener added to Recycler view at this guide - http://antonioleiva.com/recyclerview-listener/
        MyAdapter mAdapter = new MyAdapter(data, new MyAdapter.OnItemClickListener() {
            @Override public void onItemClick(int itemNum) {
                Toast.makeText(MainActivity.this, "Item Clicked "+itemNum, Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }
}
