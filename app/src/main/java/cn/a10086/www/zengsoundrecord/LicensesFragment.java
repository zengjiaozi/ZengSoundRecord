package cn.a10086.www.zengsoundrecord;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

/**
 * @author
 * @time 2017/3/2  10:48
 * @desc ${TODD}
 */
public class LicensesFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        创建一个dialog样式的dialog
        View openLiscense = LayoutInflater.from(getContext()).inflate(R.layout.fragment_licenses, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(openLiscense).setTitle("开源许可").setNeutralButton("确认", null);

//        返回创建dailog
        return builder.create();
    }
}
