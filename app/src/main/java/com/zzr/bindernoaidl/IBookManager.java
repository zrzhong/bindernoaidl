package com.zzr.bindernoaidl;

import android.os.IInterface;
import android.os.RemoteException;

import java.util.List;

public interface IBookManager extends IInterface {

    String DESCRIPTOR = "com.zzr.bindernoaidl.IBookManager";
    int TRANSACTION_getBookList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    int TRANSACTION_addBook = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);

    List<Book> getBookList() throws RemoteException;

    void addBook(Book book) throws RemoteException;
}
