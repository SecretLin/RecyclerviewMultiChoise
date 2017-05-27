package com.example.secret.recyclerviewmultichoise;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class MainActivity extends Activity implements MyAdapter.CheckBoxChangedListener{

    private RecyclerView rv;
    private MyAdapter myAdapter;
    private List<Database> list;
    private boolean isLongClick = false;
    public TextView tvCount,tvCancel,tvSelectAll,tvCancelAll;

    private Button btnDelete;

    private RelativeLayout layout_select;
    private LinearLayout layout_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
    }
    public void initData(){
        rv = (RecyclerView) findViewById(R.id.rv);
        tvCount = (TextView) findViewById(R.id.tvCount);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        tvCancel = (TextView) findViewById(R.id.tvCancel);
        tvSelectAll = (TextView) findViewById(R.id.tvSelectAll);
        tvCancelAll = (TextView) findViewById(R.id.tvCancelAll);

        layout_delete = (LinearLayout) findViewById(R.id.layout_delete);
        layout_select = (RelativeLayout) findViewById(R.id.layout_select);
        myAdapter = new MyAdapter(this);
        rv.setAdapter(myAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        setData();
        myAdapter.setListener(this);

        rv.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.HORIZONTAL));
        rv.addOnItemTouchListener(new RecyclerItemClickListener(this, rv, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                 if (isLongClick){
                     if (list.get(position).isSelected()){
                         list.get(position).setSelected(false);
                     }else {
                         list.get(position).setSelected(true);
                     }

                     myAdapter.notifyItemChanged(position);

                 }
                 else {
                     System.out.println("one~click~hhhhhhhhhhh");
                 }
                System.out.println("one~click~hhhh");
                System.out.println("islongclick:"+isLongClick);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                isLongClick = true;
                myAdapter.isShow = true;
                myAdapter.notifyDataSetChanged();

                layout_delete.setVisibility(View.VISIBLE);
                layout_select.setVisibility(View.VISIBLE);

                if (!myAdapter.isCheckBoxPress){

                    list.get(position).setSelected(true);
                    myAdapter.notifyItemChanged(position);

                    System.out.println("click~click~hhhh");
                }


            }
        }));

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                list.removeAll(myAdapter.selectedItems);
                myAdapter.selectedItems.clear();
                myAdapter.notifyDataSetChanged();
                layout_delete.setVisibility(View.GONE);
                layout_select.setVisibility(View.GONE);
                myAdapter.isShow = false;

            }
        });

        tvSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(list.size());
                myAdapter.selectedItems.clear();

                for (Database d:list){
                    d.setSelected(true);
                }
                myAdapter.notifyDataSetChanged();
                tvCancelAll.setVisibility(View.VISIBLE);
                tvSelectAll.setVisibility(View.GONE);
            }
        });
        tvCancelAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Database d:myAdapter.selectedItems){
                    d.setSelected(false);
                }

                myAdapter.selectedItems.clear();
                myAdapter.notifyDataSetChanged();
                tvSelectAll.setVisibility(View.VISIBLE);
                tvCancelAll.setVisibility(View.GONE);
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Database d:myAdapter.selectedItems){
                    d.setSelected(false);
                }
                myAdapter.isShow = false;
                layout_delete.setVisibility(View.GONE);
                layout_select.setVisibility(View.GONE);
                myAdapter.selectedItems.clear();
                myAdapter.notifyDataSetChanged();

            }
        });

    }

    private void setData() {

        list = new ArrayList<>();

        Database d = new Database();
        d.setS("Apple");
        d.setSelected(false);
        list.add(d);

        Database d1 = new Database();
        d1.setS("Boy");
        d1.setSelected(false);
        list.add(d1);

        Database d2 = new Database();
        d2.setS("Cat");
        d2.setSelected(false);
        list.add(d2);

        Database d3 = new Database();
        d3.setS("Dog");
        d3.setSelected(false);
        list.add(d3);


        myAdapter.bindData(list);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void setCheckChanged() {
        System.out.println("checked:"+myAdapter.selectedItems.size());
        tvCount.setText("已选择"+String.valueOf(myAdapter.selectedItems.size())+"个");

    }

}
