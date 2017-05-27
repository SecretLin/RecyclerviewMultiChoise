package com.example.secret.recyclerviewmultichoise;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Secret on 2017/5/19.
 */

public class MyAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Database> list = new ArrayList<>();
    public boolean isShow = false;

    public List<Database> selectedItems = new ArrayList<>();
    private MainActivity activity;

    public boolean isCheckBoxPress = false;

    CheckBoxChangedListener listener;

    public MyAdapter(Context context){
        this.context = context;
        activity = (MainActivity) context;
    }
    public void bindData(List<Database> list){
        this.list.clear();
        if (!list.isEmpty()){
            this.list = list;
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyHolder myHolder = new MyHolder(LayoutInflater.from(context).inflate(R.layout.item,parent,false));
        return myHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyHolder myHolder = (MyHolder) holder;
        myHolder.tv.setText(list.get(position).getS());

        System.out.println("isSelected:"+list.get(position).isSelected());


//        if (activity.selectPosiotion!=null) {
////            System.out.println("----------------------->"+activity.selectPosiotion);
//            if (activity.selectPosiotion == position) {
//                System.out.println(myHolder.checkBox.isChecked());
//                if (!myHolder.checkBox.isChecked()){
//                    myHolder.checkBox.setChecked(true);
//                    System.out.println("after:"+myHolder.checkBox.isChecked());
//                }
//                else {
//                    myHolder.checkBox.setChecked(false);
//                    System.out.println("after:"+myHolder.checkBox.isChecked());
//                }
//
//
//            }
//        }
        if (list.get(position).isSelected()){
            myHolder.checkBox.setChecked(true);
            selectedItems.add(list.get(position));
        }
        else {
            myHolder.checkBox.setChecked(false);
//            Integer i = position;
            selectedItems.remove(list.get(position));
        }
        listener.setCheckChanged();


        if (isShow){
            myHolder.checkBox.setVisibility(View.VISIBLE);
            myHolder.checkBox.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    myHolder.checkBox.getParent().requestDisallowInterceptTouchEvent(true);
                    if (myHolder.checkBox.isChecked()){
                        if (event.getAction() == MotionEvent.ACTION_UP ){
                            System.out.println("I'm CheckBox~");
                            list.get(position).setSelected(false);
                            isCheckBoxPress = true;

                        }
                    }
                    else {
                        if (event.getAction() == MotionEvent.ACTION_UP ){
                            System.out.println("I'm CheckBox~");
                            list.get(position).setSelected(true);
                            isCheckBoxPress = true;

                        }
                    }
//                    if (event.getAction() == MotionEvent.ACTION_UP ){
//                        System.out.println("I'm CheckBox~");
//
//                        isCheckBoxPress = true;
//                    }

                    return false;
                }
            });
//            myHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
////                    activity.selectPosiotion = null;
//                    if (isChecked){
//
//
//                    }
//                    else {
//
//                    }
//
//
//
//                }
//            });

        }
        else {
            myHolder.checkBox.setVisibility(View.GONE);
        }


    }

    public void setListener(CheckBoxChangedListener listener){
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public int getSelectedItemCount(){
        return selectedItems.size();
    }



    class MyHolder extends RecyclerView.ViewHolder {
        private TextView tv;
        private CheckBox checkBox;
        public MyHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
            checkBox = (CheckBox) itemView.findViewById(R.id.check);
        }
    }

    interface CheckBoxChangedListener{
        void setCheckChanged();
    }
}
