package com.zzr.bindernoaidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.List;

public class BookManagerImpl extends Binder implements IBookManager {

//    private static final java.lang.String DESCRIPTOR = "com.zzr.aidldemo.IBookManager";

    /**
     * Construct the stub at attach it to the interface.
     */
    public BookManagerImpl() {
        this.attachInterface(this, DESCRIPTOR);
        Book book = new Book(0, "android");
        Book book2 = new Book(1, "java");
        Book book3 = new Book(2, "c++");
        Book book4 = new Book(3, "ios");

        bookList.add(book);
        bookList.add(book2);
        bookList.add(book3);
        bookList.add(book4);
    }

    /**
     * Cast an IBinder object into an com.zzr.aidldemo.IBookManager interface,
     * generating a proxy if needed.
     */
    public static IBookManager asInterface(IBinder obj) {
        if ((obj == null)) {
            return null;
        }
        android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
        if (((iin != null) && (iin instanceof IBookManager))) {
            return ((IBookManager) iin);
        }
        return new BookManagerImpl.Proxy(obj);
    }

    @Override
    public android.os.IBinder asBinder() {
        return this;
    }

    @Override
    public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
        java.lang.String descriptor = DESCRIPTOR;
        switch (code) {
            case INTERFACE_TRANSACTION: {
                reply.writeString(descriptor);
                return true;
            }
            case TRANSACTION_getBookList: {
                data.enforceInterface(descriptor);
                java.util.List<Book> _result = this.getBookList();
                reply.writeNoException();
                reply.writeTypedList(_result);
                return true;
            }
            case TRANSACTION_addBook: {
                data.enforceInterface(descriptor);
                Book _arg0;
                if ((0 != data.readInt())) {
                    _arg0 = Book.CREATOR.createFromParcel(data);
                } else {
                    _arg0 = null;
                }
                this.addBook(_arg0);
                reply.writeNoException();
                return true;
            }
            default: {
                return super.onTransact(code, data, reply, flags);
            }
        }
    }

    private List<Book> bookList = new ArrayList<>();

    //服务端方法的具体实现
    @Override
    public List<Book> getBookList() throws RemoteException {
        synchronized (bookList) {
            return bookList;
        }
    }

    @Override
    public void addBook(Book book) throws RemoteException {
        synchronized (bookList) {
            if (book != null && !bookList.contains(book)) {
                bookList.add(book);
            }
        }
    }

    private static class Proxy implements IBookManager {
        private android.os.IBinder mRemote;

        Proxy(android.os.IBinder remote) {
            mRemote = remote;
        }

        @Override
        public android.os.IBinder asBinder() {
            return mRemote;
        }

        public java.lang.String getInterfaceDescriptor() {
            return DESCRIPTOR;
        }

        @Override
        public java.util.List<Book> getBookList() throws android.os.RemoteException {
            android.os.Parcel _data = android.os.Parcel.obtain();
            android.os.Parcel _reply = android.os.Parcel.obtain();
            java.util.List<Book> _result;
            try {
                _data.writeInterfaceToken(DESCRIPTOR);
                //客户端调用远程方法，其实是调用Proxy里面的方法
                //调用transact方法传输数据给服务端，然后服务端回调onTransact方法并返回数据给客户端
                boolean _status = mRemote.transact(TRANSACTION_getBookList, _data, _reply, 0);
//                if (!_status && getDefaultImpl() != null) {
//                    return getDefaultImpl().getBookList();
//                }
                _reply.readException();
                _result = _reply.createTypedArrayList(Book.CREATOR);
            } finally {
                _reply.recycle();
                _data.recycle();
            }
            return _result;
        }

        @Override
        public void addBook(Book book) throws android.os.RemoteException {
            android.os.Parcel _data = android.os.Parcel.obtain();
            android.os.Parcel _reply = android.os.Parcel.obtain();
            try {
                _data.writeInterfaceToken(DESCRIPTOR);
                if ((book != null)) {
                    _data.writeInt(1);
                    book.writeToParcel(_data, 0);
                } else {
                    _data.writeInt(0);
                }
                boolean _status = mRemote.transact(TRANSACTION_addBook, _data, _reply, 0);
//                if (!_status && getDefaultImpl() != null) {
//                    getDefaultImpl().addBook(book);
//                    return;
//                }
                _reply.readException();
            } finally {
                _reply.recycle();
                _data.recycle();
            }
        }
    }
}

