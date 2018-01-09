package com.androidproject.comarkdown.filesystem.adapter;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidproject.comarkdown.R;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wuxinying on 2018/1/9.
 */

public class DownloadFileAdapter extends BaseAdapter{

    ArrayList<File> filedata;
    Context context;

    public DownloadFileAdapter(Context context, ArrayList<File> data) {
        this.context = context;
        this.filedata = data;
        downloadFileItemListener = new DownloadFileListItemListender();
    }


    @Override
    public int getCount() {
        return filedata.size();
    }

    @Override
    public Object getItem(int position) {
        return filedata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    DownloadFileListItemListender downloadFileItemListener;


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        File file = filedata.get(position);
        downloadFileItemListener = new DownloadFileListItemListender();
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_download_file, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //将position与ibMore绑定
        viewHolder.filemore.setTag(position);
         DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         viewHolder.fileName.setText(file.getName()+".md");
        viewHolder.filemore.setOnClickListener(downloadFileItemListener);
        return convertView;
    }

    public static class ViewHolder {
        ImageView fileImage;
        TextView fileName;
        ImageButton filemore;
        public ViewHolder(View v) {
            fileImage = (ImageView) v.findViewById(R.id.download_file_image);
            fileName = (TextView) v.findViewById(R.id.download_file_name);
            filemore = (ImageButton) v.findViewById(R.id.download_file_more);
        }
    }
    public class DownloadFileListItemListender implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        Integer position;


        @Override
        public void onClick(final View v) {
            //获取view中绑定的tag
            position = (Integer) v.getTag();
            //创建菜单
            PopupMenu popupMenu = new PopupMenu(context, v);
            //加载布局
            popupMenu.inflate(R.menu.file_download_menu);
            //设置消失时的监听器
            popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
                @Override
                public void onDismiss(PopupMenu menu) {
                    //旋转动画(消失的时候会显示旋转动画)
                    RotateAnimation rotateAnimation = new RotateAnimation(180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    //设置动画时间300ms
                    rotateAnimation.setDuration(200);
                    //设置动画保留状态
                    rotateAnimation.setFillAfter(true);
                    v.startAnimation(rotateAnimation);
                }
            });
            popupMenu.setOnMenuItemClickListener(this);
            RotateAnimation rotateAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(200);
            rotateAnimation.setFillAfter(true);
            v.startAnimation(rotateAnimation);
            popupMenu.show();


        }

        /**
         * 菜单项点击
         *
         * @param item
         * @return true 事件处理完毕  false
         */
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.more_download:
                    doDownload();
                    break;
                default:
                    break;
            }
            return true;
        }

        /**
         * 复制
         */
        private void doDownload() {

        }


    }
}