package com.androidproject.comarkdown.filesystem;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidproject.comarkdown.R;
import com.androidproject.comarkdown.filesystem.adapter.DownloadFileAdapter;
import com.androidproject.comarkdown.filesystem.adapter.FileAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by wuxinying on 2018/1/9.
 */

public class ActivityFileDownload extends AppCompatActivity{
    private File[] files ;
    private String rootPath;
    private TextView showTextView;
    private ListView listView;
    private ArrayList<File> data = new ArrayList<>();
    private DownloadFileAdapter fileAdapter;
    private Stack<String> nowPathStack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        initView();
    }


    private void initView() {
        /**
         * 写路径
         */
        rootPath = Environment.getExternalStorageDirectory().toString();
        listView = (ListView) findViewById(R.id.downlv);
        showTextView = (TextView) findViewById(R.id.showtv);
        //获得本地文件信息列表，绑定到data
        files = Environment.getExternalStorageDirectory().listFiles();
        //将根路径推入路径栈
        //nowPathStack.push(rootPath);
        if(files!=null) {
            for (File f : files) {
                data.add(f);
            }
        }

        //showTextView.setText(getPathString());
        fileAdapter = new DownloadFileAdapter(this, data);
        //fileAdapter.setonCopyListner(this);
        listView.setAdapter(fileAdapter);


    }

    //得到当前栈路径的String
    private String getPathString() {
        Stack<String> temp = new Stack<>();
        temp.addAll(nowPathStack);
        String result = "";
        while (temp.size() != 0) {
            result = temp.pop() + result;
        }
        return result;
    }
}
