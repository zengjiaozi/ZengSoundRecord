package cn.a10086.www.zengsoundrecord;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;

public class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    private PagerSlidingTabStrip tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(pager);

    }


    //    创建菜单的方法
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_mian, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        控制option点击以后的切换 选择切换的界面

        switch (item.getItemId()) {
            case R.id.action_licenses:
//                打开证书的介绍
                openLicenses();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //    打开证书的操作
    private void openLicenses() {
//   显示这个fragmentDialog
        LicensesFragment licensesFragment = new LicensesFragment();
        licensesFragment.show(getSupportFragmentManager().beginTransaction(), "dialog_licenses");
    }

    public class MyAdapter extends FragmentPagerAdapter {
        private String[] titles = {getString(R.string.tab_title_record), getString(R.string.tab_title_saved_recordings)};

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: {
//                    // TODO: 创建一个记录的fragment
                    RecordFragment fragment = RecordFragment.newInstance(position);
                    return fragment;
                }
                case 1: {
//                    // TODO: 创建一个记录的fragment
                    RecordFragment fragment = RecordFragment.newInstance(position);
                    return fragment;
                }


            }
            return null;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
