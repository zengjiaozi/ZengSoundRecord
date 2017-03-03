package cn.a10086.www.zengsoundrecord;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * @author
 * @time 2017/3/2  15:32
 * @desc ${TODD}
 */
public class DBhelper extends SQLiteOpenHelper {

    //  public static final String DATABASE_NAME = "saved_recordings.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "saved_recordings.db";

    //    回调的监听
    private static OnDatabaseChangedListener mOnDatabaseChangedListener;
    private Context mContext;

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    public static abstract class DBHelperItem implements BaseColumns {
        public static final String TABLE_NAME = "saved_recordings";

        public static final String COLUMN_NAME_RECORDING_NAME = "recording_name";
        public static final String COLUMN_NAME_RECORDING_FILE_PATH = "file_path";
        public static final String COLUMN_NAME_RECORDING_LENGTH = "length";
        public static final String COLUMN_NAME_TIME_ADDED = "time_added";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + DBHelperItem.TABLE_NAME + " (" +
            DBHelperItem._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
            DBHelperItem.COLUMN_NAME_RECORDING_NAME + TEXT_TYPE + COMMA_SEP +
            DBHelperItem.COLUMN_NAME_RECORDING_FILE_PATH + TEXT_TYPE + COMMA_SEP +
            DBHelperItem.COLUMN_NAME_RECORDING_LENGTH + " INTEGER " + COMMA_SEP +
            DBHelperItem.COLUMN_NAME_TIME_ADDED + " INTEGER " + ")";


    //创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    //    更新数据库
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    //    创建一个回调接口
    public static void setOnDatabaseChangedListener(OnDatabaseChangedListener listener) {
        mOnDatabaseChangedListener = listener;
    }


    //    开始记录  增
    public long addRecording(String recordingName, String filePath, long length) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelperItem.COLUMN_NAME_RECORDING_NAME, recordingName);
        cv.put(DBHelperItem.COLUMN_NAME_RECORDING_FILE_PATH, filePath);
        cv.put(DBHelperItem.COLUMN_NAME_RECORDING_LENGTH, length);
//        插入一条数据  自动生成一条id数据
        long insertId = db.insert(DBHelperItem.TABLE_NAME, null, cv);
        if (mOnDatabaseChangedListener != null) {

            mOnDatabaseChangedListener.onNewDatabaseEntryAdded();
        }

        return insertId;

    }


    //   录音记录重新命名  改
    public void renameItem(RecordingItem item, String recordingName, String filePath) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelperItem.COLUMN_NAME_RECORDING_NAME, recordingName);
        cv.put(DBHelperItem.COLUMN_NAME_RECORDING_FILE_PATH, filePath);
        db.update(DBHelperItem.TABLE_NAME, cv, DBHelperItem._ID + "=" + item.getmId(), null);

        if (mOnDatabaseChangedListener != null) {

            mOnDatabaseChangedListener.onDatabaseEntryRenamed();
        }


    }


    public Context getContext() {
        return mContext;
    }

//    获取录音的数量  查

    public int getCount() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {DBHelperItem._ID};
        Cursor query = db.query(DBHelperItem.TABLE_NAME, projection, null, null, null, null, null, null);
        int count = query.getCount();
        query.close();
        return count;
    }

//    删除一条记录

    public void deleteItemById(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String[] whereArgs = {String.valueOf(id)};

        db.delete(DBHelperItem.TABLE_NAME, "_ID=?", whereArgs);
    }


    public RecordingItem getItemAt(int position) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {DBHelperItem._ID, DBHelperItem.COLUMN_NAME_RECORDING_NAME, DBHelperItem.COLUMN_NAME_RECORDING_FILE_PATH, DBHelperItem.COLUMN_NAME_RECORDING_LENGTH, DBHelperItem.COLUMN_NAME_TIME_ADDED};
        Cursor c = db.query(DBHelperItem.TABLE_NAME, projection, null, null, null, null, null);
        if (c.moveToPosition(position)) {
            RecordingItem item = new RecordingItem();
            item.setmId(c.getInt(c.getColumnIndex(DBHelperItem._ID)));
            item.setmName(c.getString(c.getColumnIndex(DBHelperItem.COLUMN_NAME_RECORDING_NAME)));
            item.setmFilePath(c.getString(c.getColumnIndex(DBHelperItem.COLUMN_NAME_RECORDING_FILE_PATH)));
            item.setmLength(c.getInt(c.getColumnIndex(DBHelperItem.COLUMN_NAME_RECORDING_LENGTH)));
            item.setmTime(c.getLong(c.getColumnIndex(DBHelperItem.COLUMN_NAME_TIME_ADDED)));
            c.close();
            return item;
        }
        return null;
    }
}
