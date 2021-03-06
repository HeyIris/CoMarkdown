package com.androidproject.comarkdown.filesystem;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidproject.comarkdown.MainActivity;
import com.androidproject.comarkdown.R;
import com.androidproject.comarkdown.data.event.OpenFileEvent;
import com.androidproject.comarkdown.filesystem.adapter.FileAdapter;
import com.androidproject.comarkdown.filesystem.async.QueryAsyncTask;
import com.androidproject.comarkdown.filesystem.utils.FileSortFactory;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class FileFragment extends Fragment implements FileAdapter.OnCopyFileListener {
    private TextView showTextView;
    private ListView listView;
    //菜单
    private Menu actionMenu;
    private ArrayList<File> data = new ArrayList<>();
    private File[] files ;
    private FileAdapter fileAdapter;

    private String rootPath;
    private Stack<String> nowPathStack;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        rootPath = Environment.getExternalStorageDirectory().toString();
        nowPathStack = new Stack<>();
        listView = (ListView) getView().findViewById(R.id.lv);
        showTextView = (TextView) getView().findViewById(R.id.showtv);
        //获得本地文件信息列表，绑定到data
        files = Environment.getExternalStorageDirectory().listFiles();
        //将根路径推入路径栈
        nowPathStack.push(rootPath);
        if(files!=null) {
            for (File f : files) {
                data.add(f);
            }
        }
        showTextView.setText(getPathString());
        fileAdapter = new FileAdapter(getContext(), data);
        fileAdapter.setonCopyListner(this);
        listView.setAdapter(fileAdapter);

        listView.setOnItemClickListener(new FileItemClickListener());
    }

    @Override
    public void doCopy(File file) {

        waitingCopyFile = file;
        Toast.makeText(getContext(),file.getName() + "被添加到粘贴板", Toast.LENGTH_SHORT).show();
    }

    static File waitingCopyFile;

    class FileItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(
                AdapterView<?> parent,
                View view,
                int position,
                long id) {

            File file = files[position];
            if (file.isFile()) {
                Uri data = Uri.fromFile(file);
                EventBus.getDefault().post(new OpenFileEvent(data.toString()));
                ((MainActivity)getActivity()).showMainFragment(MainActivity.FragmentType.EDIT);
            } else {
                //如果是文件夹
                // 清除列表数据
                // 获得目录中的内容，计入列表中
                // 适配器通知数据集改变
                nowPathStack.push("/" + file.getName());
                showChange(getPathString());
            }
        }
    }

    //显示改变data之后的文件数据列表
    private void showChange(String path) {
        showTextView.setText(path);
        files = new File(path).listFiles();
        data.clear();
        for (File f : files) {
            data.add(f);
        }
        files = fileAdapter.setfiledata(data);
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

    MenuItem searchItem;
    SearchView searchView;
    MenuItem sortItem;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_folder:
                doCreateNewFolder();
                break;
            case R.id.action_new_file:
                doCreateNewFile();
                break;
            case R.id.action_copy_paste:
                doPaste();
                break;
            case R.id.action_sort_date:
                fileAdapter.setSortWay(FileSortFactory.SORT_BY_FOLDER_AND_TIME);
                fileAdapter.notifyDataSetChanged();
                break;
            case R.id.action_sort_size:
                fileAdapter.setSortWay(FileSortFactory.SORT_BY_FOLDER_AND_SIZE);
                fileAdapter.notifyDataSetChanged();
                break;
            case R.id.action_sort_name:
                fileAdapter.setSortWay(FileSortFactory.SORT_BY_FOLDER_AND_NAME);
                fileAdapter.notifyDataSetChanged();
                break;
            default:
                files = fileAdapter.setfiledata();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    //判断当前是复制还是粘贴的标识符
    private boolean iscopy = true;

    /**
     * 复制或粘贴
     */
    private void doPaste() {
        File newFile = new File(getPathString()+"/"+waitingCopyFile.getName());
        if (waitingCopyFile.equals(null)) {
            Snackbar.make(getView().findViewById(R.id.main_view), "当前粘贴板为空，不能粘贴", Snackbar.LENGTH_SHORT).show();
        } else {
            if (waitingCopyFile.isFile()&&waitingCopyFile.exists()){
                try {
                    FileInputStream fis = new FileInputStream(waitingCopyFile);
                    FileOutputStream fos = new FileOutputStream(newFile);
                    int len = -1;
                    long contentSize = waitingCopyFile.length();
                    long readed = 0;
                    byte[] buff = new byte[8192];
                    while ((len=fis.read(buff))!=-1){
                        //写文件
                        fos.write(buff,0,len);
                        readed+=len;
                        //发布进度
                    }
                    fos.flush();
                    fis.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                }
            }
            if (newFile.exists()) {
                Toast.makeText(getContext(),"复制" + newFile.getName() + "成功",Toast.LENGTH_SHORT).show();
                fileAdapter.notifyDataSetChanged();
            }
        }

    }


    AlertDialog Floderialog;
    AlertDialog fileDialog;
    EditText newFloderName;
    EditText newFileName;
    private void doCreateNewFile() {
        fileDialog = new AlertDialog.Builder(getContext()).create();
        fileDialog.show();
        fileDialog.getWindow().setContentView(R.layout.dialog_new_file);
        fileDialog.setView(new EditText(getContext()));
        //加入下面两句以后即可弹出输入法
        fileDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        fileDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        newFileName = (EditText) fileDialog.getWindow().findViewById(R.id.newfile_name);

        fileDialog.getWindow()
                .findViewById(R.id.newfile_cancle)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fileDialog.dismiss();
                    }
                });
        fileDialog.getWindow()
                .findViewById(R.id.newfile_create)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name =  newFileName.getText().toString();
                        if (name != null) {
                            File file = new File(getPathString() +"/"+ name+".md");
                            if (file.exists()) {
                                file.delete();
                            }
                            try {
                                //创建文件
                                file.createNewFile();
                                //给一个吐司提示，显示创建成功
                                Toast.makeText(getContext(), "文件"+name + "创建成功", Toast.LENGTH_SHORT).show();
                                showChange(getPathString());
                                fileDialog.dismiss();
                            } catch (IOException e) {
                                Toast.makeText(getContext(), "文件已存在", Toast.LENGTH_SHORT).show();
                            }

                        }

                        else{
                            Toast.makeText(getContext(),"文件名不能为空",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


    /**
     * 创建新文件夹
     */
    private void doCreateNewFolder() {
        Floderialog = new AlertDialog.Builder(getContext()).create();
        Floderialog.show();
        Floderialog.getWindow().setContentView(R.layout.dialog_new_floder);
        Floderialog.setView(new EditText(getContext()));
        //加入下面两句以后即可弹出输入法
        Floderialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        Floderialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        newFloderName = (EditText) Floderialog.getWindow().findViewById(R.id.newfloder_name);

        Floderialog.getWindow()
                .findViewById(R.id.newfloder_cancle)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Floderialog.dismiss();
                    }
                });
        Floderialog.getWindow()
                .findViewById(R.id.newfloder_create)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name =  newFloderName.getText().toString();
                        if (name != null) {
                            File folder = new File(getPathString() +"/"+ name);
                            folder.mkdirs();
                            if (folder.exists()) {
                                Toast.makeText(getContext(),"文件："+name + " 创建成功",Toast.LENGTH_SHORT).show();
                                showChange(getPathString());
                                Floderialog.dismiss();
                            }
                            else{
                                Toast.makeText(getContext(),"文件夹不存在",Toast.LENGTH_SHORT).show();
                            }
                        }

                        else{
                            Toast.makeText(getContext(),"文件夹名不能为空",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    boolean ifSearching = false;
    AlertDialog searchDialog;
    TextView querytv;
    /**
     * 搜索
     * 搜索当前路径下文件夹内文件
     * 使用递归实现
     */
    private void doSearch(String query) {
        ifSearching = true;
        searchDialog = new AlertDialog.Builder(getContext()).create();
        searchDialog.show();
        searchDialog.getWindow().setContentView(R.layout.dialog_query);
        querytv = (TextView) searchDialog.getWindow().findViewById(R.id.query_tv);
        new QueryAsyncTask(querytv,getPathString(),query,fileAdapter,searchDialog).execute();
    }
}
