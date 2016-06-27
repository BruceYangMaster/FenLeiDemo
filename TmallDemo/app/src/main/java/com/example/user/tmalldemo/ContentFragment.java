package com.example.user.tmalldemo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.fragment_content_listview)
    ListView mListview;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ContentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContentFragment newInstance(String param1, String param2) {
        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        ButterKnife.bind(this, view);

        //为ListView添加headview,并设置点击事件
        View headView =  inflater.inflate(R.layout.fragment_listview_head_layout,null);
        TextView tv = (TextView) headView.findViewById(R.id.item_listview_head_button);
        tv.setText("进入"+mParam1+"频道");
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to do
                Toast.makeText(getActivity(),"你进入了"+mParam1+"频道",Toast.LENGTH_SHORT).show();
            }
        });
        mListview.addHeaderView(headView);

        //listview添加适配器
        ListViewAdapter adapter = new ListViewAdapter(getContext());
        mListview.setAdapter(adapter);
        adapter.setData(getData());

        return view;
    }

    private String img = "http://d.hiphotos.baidu.com/image/h%3D300/sign=c6ae3b26b5fb4316051f7c7a10a64642/ac345982b2b7d0a2f7375f70ccef76094a369a65.jpg";
    private ArrayList<ListBean> getData(){
        ArrayList<ListBean> data = new ArrayList<>();
        for (int i = 0;i<100;i++){
            ListBean listBean = new ListBean();
            listBean.setText("第"+i+"项");
            ArrayList<GridBean> beans = new ArrayList<>();
            for (int j = 0;j<9;j++){
                GridBean gridBean = new GridBean();
                gridBean.setImg(img);
                gridBean.setName("第"+j+"项");
                beans.add(gridBean);
            }
            listBean.setData(beans);
            data.add(listBean);
        }
        return data;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /**
     * ListView的适配器
     */
    static class ListViewAdapter extends BaseAdapter {
        private ArrayList<ListBean> data = new ArrayList<>();
        private LayoutInflater inflater;
        private Context context;

        public ListViewAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        public void setData(ArrayList<ListBean> data) {
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
            ViewHolder viewHolder;
            GridViewAdapter adapter;
            ListBean bean = data.get(position);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_fragment_listview_layout, null);
                viewHolder = new ViewHolder(convertView);
                adapter = new GridViewAdapter(context);
                viewHolder.gridView.setAdapter(adapter);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
                adapter = (GridViewAdapter) viewHolder.gridView.getAdapter();
            }
            //以下才是将数据和item绑定
            viewHolder.textView.setText(bean.getText());

            ArrayList<GridBean> beans = bean.getData();
            viewHolder.gridView.setAdapter(adapter);
            adapter.setData(beans);

            return convertView;
        }

        static class ViewHolder {
            @Bind(R.id.text_view)
            TextView textView;
            @Bind(R.id.grid_view)
            MyGridView gridView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }


    /**
     * ListView item中GridView的适配器
     */
    static class GridViewAdapter extends BaseAdapter {

        private ArrayList<GridBean> data = new ArrayList<>();
        private LayoutInflater inflater;
        private Context context;

        public GridViewAdapter(Context context) {
            inflater = LayoutInflater.from(context);
            this.context = context;
        }

        public void setData(ArrayList<GridBean> data) {
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

            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_gridview_layout, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            GridBean bean = data.get(position);
            viewHolder.textView.setText(bean.getName());
            Picasso.with(context).load(bean.getImg()).into(viewHolder.image);
            return convertView;
        }

        static class ViewHolder {
            @Bind(R.id.image)
            ImageView image;
            @Bind(R.id.text_view)
            TextView textView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
