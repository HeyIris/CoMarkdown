package com.androidproject.comarkdown.filesystem;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidproject.comarkdown.MainActivity;
import com.androidproject.comarkdown.R;
import com.androidproject.comarkdown.data.AccountInfo;
import com.androidproject.comarkdown.data.OnlineFileInfo;
import com.androidproject.comarkdown.data.PartakeFileInfo;
import com.androidproject.comarkdown.data.PartakeFileItem;
import com.androidproject.comarkdown.data.event.LoadFileEvent;
import com.androidproject.comarkdown.filesystem.adapter.DownloadFileAdapter;
import com.androidproject.comarkdown.network.ApiClient;
import com.androidproject.comarkdown.network.ApiErrorModel;
import com.androidproject.comarkdown.network.ApiResponse;
import com.androidproject.comarkdown.network.NetworkScheduler;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Stack;

import okhttp3.ResponseBody;

/**
 * Created by wuxinying on 2018/1/9.
 */

public class FileDownloadFragment extends Fragment{
    private File[] files ;
    private String rootPath;
    private TextView showTextView;
    private ListView listView;
    private ArrayList<PartakeFileItem> fileList = new ArrayList<>();
    private DownloadFileAdapter fileAdapter;
    private Stack<String> nowPathStack;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        /**
         * 写路径
         */
        rootPath = Environment.getExternalStorageDirectory().toString();
        listView = (ListView) getView().findViewById(R.id.downlv);
        showTextView = (TextView) getView().findViewById(R.id.showtv);
        //获得本地文件信息列表，绑定到data
        //将根路径推入路径栈
        //nowPathStack.push(rootPath);
        ApiClient.Companion.getInstance().service.onlineFileList(AccountInfo.INSTANCE.getUsername(), AccountInfo.INSTANCE.getToken())
                .compose(NetworkScheduler.INSTANCE.<OnlineFileInfo>compose())
                .subscribe(new ApiResponse<OnlineFileInfo>(getContext()) {
                    @Override
                    public void success(OnlineFileInfo data) {
                        if (data.getList() != null) {
                            ArrayList<PartakeFileItem> temp = new ArrayList<>();
                            for (int i = 0; i < data.getList().size(); i++) {
                                temp.add(new PartakeFileItem(AccountInfo.INSTANCE.getUsername(), data.getList().get(i).getName(), data.getList().get(i).getId()));
                            }
                            fileList.addAll(temp);
                            setFileAdapter();
                        }
                    }

                    @Override
                    public void fail(int statusCode, @NotNull ApiErrorModel apiErrorModel) {

                    }
                });
        ApiClient.Companion.getInstance().service.partakeFileList(AccountInfo.INSTANCE.getUsername(), AccountInfo.INSTANCE.getToken())
                .compose(NetworkScheduler.INSTANCE.<PartakeFileInfo>compose())
                .subscribe(new ApiResponse<PartakeFileInfo>(getContext()) {
                    @Override
                    public void success(PartakeFileInfo data) {
                        if (data.getPartake_files() == null){
                            return;
                        }
                        fileList.addAll(data.getPartake_files());
                        setFileAdapter();
                    }

                    @Override
                    public void fail(int statusCode, @NotNull ApiErrorModel apiErrorModel) {

                    }
                });
        if (files != null) {

        }

        //showTextView.setText(getPathString());
        fileAdapter = new DownloadFileAdapter(getContext(), fileList);
        //fileAdapter.setonCopyListner(this);
        listView.setAdapter(fileAdapter);
        listView.setOnItemClickListener(new DownloadFileItemClickListener());
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

    private void setFileAdapter(){
        //showTextView.setText(getPathString());
        fileAdapter = new DownloadFileAdapter(getContext(), fileList);
        //fileAdapter.setonCopyListner(this);
        listView.setAdapter(fileAdapter);

    }

    class DownloadFileItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(
                AdapterView<?> parent,
                View view,
                int position,
                long id) {
            final PartakeFileItem pfi = fileList.get(position);
            ApiClient.Companion.getInstance().service.downloadFile(AccountInfo.INSTANCE.getUsername(),AccountInfo.INSTANCE.getToken(),pfi.getMaster(),pfi.getName())
                    .compose(NetworkScheduler.INSTANCE.<ResponseBody>compose())
                    .subscribe(new ApiResponse<ResponseBody>(getContext()) {
                        @Override
                        public void success(ResponseBody data) {
                            try{
                                InputStream is = data.byteStream();
                                String path = "/storage/emulated/0/Download";
                                File file = new File(path,pfi.getName());
                                if (file.exists()) {
                                    file.delete();
                                }
                                file.createNewFile();
                                FileOutputStream fos = new FileOutputStream(file);
                                BufferedInputStream bis = new BufferedInputStream(is);
                                byte[] buffer = new byte[1024];
                                int len;
                                while((len = bis.read(buffer)) != -1){
                                    fos.write(buffer,0,len);
                                }
                                fos.flush();
                                fos.close();
                                bis.close();
                                is.close();
                                Uri uriPath = Uri.fromFile(file);
                                EventBus.getDefault().post(new LoadFileEvent(uriPath.toString(),pfi.getMaster(),pfi.getName()));
                                ((MainActivity)getActivity()).showMainFragment(MainActivity.FragmentType.EDIT);
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void fail(int statusCode, @NotNull ApiErrorModel apiErrorModel) {
                        }
                    });
        }
    }
}
