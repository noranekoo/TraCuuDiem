package com.anhthi.tracuudiem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    EditText edit;
    TextView info;
    RecyclerView rcv;
    DataAdapter mAdapter;
    ProgressBar progressBar;
    ArrayList<ChiTietDiem> Mon;
    boolean doublePress = false;
    private final int DELAY_TIME = 3500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit = findViewById(R.id.editMSSV);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        Mon = new ArrayList<>();
        info = findViewById(R.id.txtInfo);
        rcv = findViewById(R.id.recyclerView);
        mAdapter = new DataAdapter(Mon);
        rcv.setAdapter(mAdapter);
        rcv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onBackPressed() {
        if(doublePress){
            super.onBackPressed();
        }
        else{
            doublePress = true;
            Toast.makeText(this, "Nhấn lần nữa để thoát", Toast.LENGTH_SHORT).show();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doublePress = false;
            }
        },DELAY_TIME);

    }

    void loadInfo(Bundle args) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = null;
        if (connectivityManager != null) {
            info = connectivityManager.getActiveNetworkInfo();
        }
        if (info != null && info.isConnected()) {
            if (getSupportLoaderManager().getLoader(0) != null) {
                getSupportLoaderManager().restartLoader(0,args.getBundle("bun") , this);
            }
            getSupportLoaderManager().initLoader(0, args.getBundle("bun"), this);
        } else{
            taoThongBao("Không thể kết nối đến server").show();
            this.progressBar.setVisibility(View.INVISIBLE);
        }

    }
    private AlertDialog taoThongBao(String m) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        return builder.setMessage(m)
                .setTitle("Lỗi")
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //moveTaskToBack(true);
//                android.os.Process.killProcess(android.os.Process.myPid());
//                System.exit(1);
                    }
                }).create();
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        if(args != null)
            return new DataLoader(this,args.getString("data"));
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        if(data.contains("Mã HSSV nhập vào không hợp lệ")){
            taoThongBao("Thông tin sinh viên không hợp lệ").show();
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }
//        if(data == null)
//        {
//            taoThongBao("Có lỗi khi kết nối kết server").show();
//            progressBar.setVisibility(View.INVISIBLE);
//            return;
//        }

        int index = data.indexOf("Mã HSSV: ");
        data = data.substring(index);
        SinhVien sv = getInfo(data);
        ArrayList<ChiTietDiem> a = getRows(data);
        String c = "MSSV: "+sv.getId()+"\nHọ tên: "+sv.getName()+"\nQuê quán: "+sv.getHomeTown()+
                "\n";
        for(ChiTietDiem b:a){
            this.Mon.add(new ChiTietDiem(b.getHocki(),b.getStt(),b.getTenMon(),b.getDonViHP(),
                    b.getDiem()));
        }
        this.mAdapter.notifyDataSetChanged();
        info.setText(c);
        progressBar.setVisibility(View.INVISIBLE);
        rcv.setVisibility(View.VISIBLE);
    }
    private ArrayList<ChiTietDiem> getRows(String data){
        ArrayList<ChiTietDiem> result = new ArrayList<>();
        ArrayList<String> m = filterInfo(data);
        int i = 0;
        int j = 0;
        int stt = 0;
        String tenMon = null;
        String dvhp = null;
        String diem = null;
        for(String s:m){
            if(s.contains("HỌC KÌ")){
                i =-1;
                j++;
                result.add(new ChiTietDiem(j,0,null,null,null));
            }
            if(i == 0)
                stt = Integer.parseInt(s);
            if(i == 1)
                tenMon = s;
            if(i == 2)
                dvhp = s;
            if(i == 3)
                diem = s;
            i++;
            if(i == 4){
                result.add(new ChiTietDiem(0,stt,tenMon,dvhp,diem));
                i = 0;
            }
            }
        return result;
    }
    private SinhVien getInfo(String data){
        Pattern pattern = Pattern.compile("<b>(.*?)</b>");
        Matcher m = pattern.matcher(data);
        ArrayList<String> info = new ArrayList<>();
        while(m.find()){
            info.add(m.group(1));
        }
        return new SinhVien(info.get(0),info.get(1),info.get(2),info.get(3),info.get(4),
                info.get(5));
    }
    private ArrayList<String> filterInfo(String data){
        Pattern pattern = Pattern.compile("<TD(.*?)>(.*?)</TD>");
        Matcher m = pattern.matcher(data);
        ArrayList<String> s = new ArrayList<>();
        int i =0;
        while (m.find()){
            String value = m.group(2);
            if(value.equals("STT"))
            {i++;s.add("HỌC KÌ "+i);}
            if(!value.equals("Ghi Chú") && !value.equals("Tổng Kết") && !value.equals("ĐVHP") && !value.equals("Tên Môn Học") && !value.equals("STT")){ {
                if(!value.equals("&nbsp;")){
                    s.add(value);
                }
                }
            }
        }
        return s;
    }
    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    public void Tracuu(View view) {
        rcv.setVisibility(View.INVISIBLE);
        Mon.clear();
        info.setText("");
        if(edit.getText().length() == 0)
        {
            taoThongBao("Vui lòng điền đầy đủ thông tin").show();
        }
        else
        {
            progressBar.setVisibility(View.VISIBLE);
            Bundle data = new Bundle();
            data.putString("data",
                    URLEncoder.encode("txtMaHSSV")+"="+URLEncoder.encode(this.edit.getText().toString()));
            data.putBundle("bun",data);
            loadInfo(data);
        }
    }
}
