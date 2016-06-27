package com.example.user.tmalldemo;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener ,ContentFragment.OnFragmentInteractionListener{

    @Bind(R.id.bar_listview)
    ListView mBarListview;
    @Bind(R.id.container)
    FrameLayout container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //设置左边listview适配器
        ListViewAdapter adapter = new ListViewAdapter(this);
        mBarListview.setAdapter(adapter);
        adapter.setData(getData());

        //设置左边listview item点击事件
        mBarListview.setOnItemClickListener(this);

        //默认加载第0项fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container,ContentFragment.newInstance("第0类",null))//这里只是为了方便，应该data或者适配器获取第一项数据
                .commit();
    }

    /**
     * 获取数据源
     * @return
     */
    private ArrayList<ListBarBean> getData(){
        ArrayList<ListBarBean> data = new ArrayList<>();
        for (int i = 0;i<20;i++){
            ListBarBean bean = new ListBarBean();
            bean.setTitle("第"+i+"类");
            bean.setUrl("");
            data.add(bean);
        }
        return data;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //设置适配器选中项，并且通知适配器刷新界面，以便设置选中item背景图片
        ListViewAdapter adapter = ((ListViewAdapter)(parent.getAdapter()));
        adapter.setSelectedPosition(position);
        adapter.notifyDataSetChanged();

        ListBarBean bean = (ListBarBean) adapter.getItem(position);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,ContentFragment.newInstance(bean.getTitle(),bean.getUrl()))
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    static class ListViewAdapter extends BaseAdapter{

        private ArrayList<ListBarBean> data = new ArrayList<>();
        private LayoutInflater inflater;
        private Context context;

        private int selectedPosition = 0;

        public ListViewAdapter(Context context){
            inflater = LayoutInflater.from(context);
            this.context = context;
        }

        public void setSelectedPosition(int selectedPosition){
            this.selectedPosition = selectedPosition;
        }


        public void setData(ArrayList<ListBarBean> data){
            this.data = data;
            notifyDataSetChanged();
        }
        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = inflater.inflate(R.layout.item_listview_bar_layout,null);
            }
            //判断item点击项，若该项被点击选中，那么该项背景色就设置为red,若果是设置图片资源，也在这里设置，不要去设置selector，没有鸟用
            if(position == selectedPosition){
                convertView.setBackgroundColor(Color.RED);
            }else {
                convertView.setBackgroundColor(Color.WHITE);
            }

            TextView tv = (TextView) convertView;
            tv.setText(data.get(position).getTitle());
            return convertView;
    }
}

}
