package cn.ut.library.asyncwriter;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import cn.ut.library.asyncwriter.filegetter.FileGetter;
import cn.ut.library.utils.FileUtils;
import cn.ut.library.utils.StringUtils;

/**
 * Created by chenhang on 2016/6/3.
 */
public abstract class AsyncFileWriterImpl<T> implements AsyncFileWriter<T>, Handler.Callback {

    private static final String TAG = "AsyncFileWriterImpl";

    private static final int MSG_WRITE = 1;

    private Context mContext;
    private volatile FileWriter mFileWriter;
    private Handler mWorkHandler;
    private HandlerThread mHandlerThread;
    private FileGetter mFileGetter;

    protected abstract void doWrite(FileWriter writer, T content) throws IOException;

    public AsyncFileWriterImpl(Context context, FileGetter fileGetter) {
        mContext = context;
        mFileGetter = fileGetter;
        init();
    }

    private void init() {
        createFileWriter(mFileGetter.getFileDirectory(mContext), mFileGetter.getFilename(mContext));
        mHandlerThread = new HandlerThread(TAG);
        mHandlerThread.start();
        mWorkHandler = new Handler(mHandlerThread.getLooper(), AsyncFileWriterImpl.this);
    }

    private void createFileWriter(String dir, String filename) {
        if (StringUtils.isEmpty(dir) || StringUtils.isEmpty(filename)) {
            throw new IllegalArgumentException("dir and filename can't be null");
        }

        String filePath = dir + (dir.endsWith(File.separator) ? "" : File.separator) + filename;
        FileUtils.makeDirs(filePath);

        try {
            if (mFileWriter != null) {
                mFileWriter.flush();
                mFileWriter.close();
            }
            mFileWriter = new java.io.FileWriter(new File(filePath), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(T content) {
        if (mWorkHandler == null) {
            return;
        }
        Message message = mWorkHandler.obtainMessage();
        message.what = MSG_WRITE;
        message.obj = content;
        mWorkHandler.sendMessage(message);
    }


    @Override
    public void close() throws IOException {
        if (mFileWriter != null) {
            try {
                mFileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mFileWriter = null;
        }

        if (mWorkHandler != null) {
            mWorkHandler.removeCallbacksAndMessages(null);
            mWorkHandler = null;
        }
        if (mHandlerThread != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                mHandlerThread.quitSafely();
            } else {
                mHandlerThread.quit();
            }
            mHandlerThread = null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean handleMessage(Message msg) {

        if (mFileWriter == null) {
            return true;
        }

        switch (msg.what) {
            case MSG_WRITE:
                try {
                    T content = (T) msg.obj;
                    doWrite(mFileWriter, content);
                    mFileWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        return true;
    }
}
