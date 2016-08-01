package cn.ut.library.asyncwriter;

import java.io.Closeable;

/**
 * Created by chenhang on 2016/6/16.
 */
public interface AsyncFileWriter<T> extends Closeable {

    void write(T content);
}
