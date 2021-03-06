package com.example.jonyz.jonydownload.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jonyz.jonydownload.Bean.FileBean;
import com.example.jonyz.jonydownload.Presenter.DownloadPresenter;
import com.example.jonyz.jonydownload.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JonyZ on 2017/8/23.
 */

public class FileAdapter extends BaseAdapter {
    private static final String TAG = FileAdapter.class.getSimpleName();
    Context context=null;
    List<FileBean> list = new ArrayList<>();
    LayoutInflater inflater;
    private MyViewHolder viewHolder;
    private DownloadPresenter presenter;

    public FileAdapter(Context context, List<FileBean> list) {
        this.context = context;
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    /**
     * 返回总条数
     *
     * @return
     */
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    /**
     * 返回当前点击条目的id
     *
     * @param i
     * @return
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * 进行数据和View的适配
     *
     * @param i
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final FileBean fileBean=list.get(i);
        if (view==null){
            view=inflater.inflate(R.layout.item,null);
            viewHolder = new MyViewHolder();
            viewHolder.mTvfileName= (TextView) view.findViewById(R.id.Tv_fileName);
            viewHolder.mBarDown=(ProgressBar)view.findViewById(R.id.Pb_down);
            viewHolder.mBtnstart=(Button)view.findViewById(R.id.Btn_start);
            viewHolder.mBtnstop=(Button) view.findViewById(R.id.Btn_stop);
            viewHolder.mTvfileName.setText(list.get(i).getFileName());
            viewHolder.mBarDown.setProgress(fileBean.getDownSize());
            viewHolder.mBtnstart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //// TODO: 2017/8/23 开始下载
                    presenter = new DownloadPresenter();
                    presenter.startDownload(fileBean,context);
                    Log.d(TAG, "onClick:开始");
                    Toast.makeText(context, "点击了开始", Toast.LENGTH_SHORT).show();

                }
            });
            viewHolder.mBtnstop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //// TODO: 2017/8/23 暂停下载
                    presenter.stopDownload(fileBean,context);
                    Log.d(TAG, "onClick:暂停");
                    Toast.makeText(context, "点击了暂停", Toast.LENGTH_SHORT).show();
                }
            });
            view.setTag(viewHolder);
        }else {
            viewHolder= (MyViewHolder) view.getTag();
        }

        viewHolder.mBarDown.setProgress(fileBean.getDownSize());
        return view;

    }

    /**
     * 静态的View ,避免重复加载
     */
    static class MyViewHolder {
        TextView mTvfileName;
        ProgressBar mBarDown;
        Button mBtnstart;
        Button mBtnstop;
    }
    public void updataProgress(int id, int progress) {
        FileBean info = list.get(id);
        info.setDownSize(progress);
       // viewHolder.mBarDown.setProgress(progress);
        notifyDataSetChanged();
    }

}
