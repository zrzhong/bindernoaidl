package com.zzr.bindernoaidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class RemoteService extends Service {
    private BookManagerImpl myBinder;

    @Override
    public IBinder onBind(Intent intent) {
        if (myBinder == null) {
            myBinder = new BookManagerImpl();
        }
        return myBinder;
    }
}
