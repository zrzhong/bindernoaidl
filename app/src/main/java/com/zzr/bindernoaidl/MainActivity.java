package com.zzr.bindernoaidl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private IBookManager bookManager;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected: 连接成功");
            bookManager = BookManagerImpl.asInterface(service);
            try {
                List<Book> bookList = bookManager.getBookList();
                Log.i(TAG, "book size: " + bookList.size());
                Book book = new Book(4, "python");
                bookManager.addBook(book);
                Log.i(TAG, "book size2: " + bookManager.getBookList().size());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected: 连接失败");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //绑定服务
        Intent intent = new Intent(this, RemoteService.class);
//        startService(intent);
        //隐式启动服务不加这句话android5.0 以上会报：Service Intent must be explitict 即服务意图必须是明确的
//        intent.setPackage(getPackageName());
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
