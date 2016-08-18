package cn.j1angvei.cnbetareader.data.local.mapper;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by Wayne on 2016/8/14.
 * convert data to contentValues or from cursor to data
 */

public interface Mapper<T> {

    ContentValues store(T item);

    T retrieve(Cursor cursor);
}
