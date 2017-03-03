package cn.a10086.www.zengsoundrecord;

/**
 * @author
 * @time 2017/3/2  15:43
 * @desc ${TODD}
 */
public interface OnDatabaseChangedListener {

    //    增加新的记录和 文件的重新命名
    void onNewDatabaseEntryAdded();

    void onDatabaseEntryRenamed();

}
