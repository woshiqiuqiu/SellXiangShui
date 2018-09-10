package neusoft.edu.cn.projecttest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import java.util.ArrayList;

import neusoft.edu.cn.projecttest.fragment.HomeFragment;
import neusoft.edu.cn.projecttest.fragment.LikeFragment;
import neusoft.edu.cn.projecttest.fragment.MeFragment;
import neusoft.edu.cn.projecttest.fragment.MessageFragment;


public class MainActivity extends FragmentActivity
        implements OnCheckedChangeListener {

    //ViewPager
    private ViewPager main_viewPager;
    //RadioGroup
    private RadioGroup main_tab_RadioGroup;
    //RadioButton
    private RadioButton radio_home, radio_like,
            radio_message, radio_me;
    //
    private ArrayList<Fragment> fragmentList;
    private TextView MAP;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        // 界面初始函数，用来获取定义的各控件对应的ID
        InitView();
        // ViewPager初始化函数
        InitViewPager();

        MAP = (TextView) findViewById(R.id.tv_message_home);
        MAP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,MapActivity.class);
                startActivity(intent);
            }
        });


    }

    public void InitView() {
        main_tab_RadioGroup = (RadioGroup) findViewById(R.id.main_tab_RadioGroup);
        radio_home = (RadioButton) findViewById(R.id.radio_home);
        radio_like = (RadioButton) findViewById(R.id.radio_like);
        radio_message = (RadioButton) findViewById(R.id.radio_message);
        radio_me = (RadioButton) findViewById(R.id.radio_me);
        main_tab_RadioGroup.setOnCheckedChangeListener(this);
    }

    public void InitViewPager() {
        main_viewPager = (ViewPager) findViewById(R.id.main_ViewPager);
        fragmentList = new ArrayList<Fragment>();
        Fragment homeFragment = new HomeFragment();
        Fragment LikeFragment = new LikeFragment();
        Fragment MessageFragment = new MessageFragment();
        Fragment meFragment = new MeFragment();
        fragmentList.add(homeFragment);
        fragmentList.add(LikeFragment);
        fragmentList.add(MessageFragment);
        fragmentList.add(meFragment);
        main_viewPager.setAdapter(new MyAdapter(getSupportFragmentManager(), fragmentList));
        main_viewPager.setCurrentItem(0);
        main_viewPager.addOnPageChangeListener(new MyListner());

    }
    //内部类
    public class MyAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> list;
        public MyAdapter(FragmentManager fm, ArrayList<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int arg0) {
            return list.get(arg0);
        }
        @Override
        public int getCount() {
            return list.size();
        }
    }

    public class MyListner implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int arg0) {
            int current = main_viewPager.getCurrentItem();
            switch (current) {
                case 0:
                    main_tab_RadioGroup.check(R.id.radio_home);
                    break;
                case 1:
                    main_tab_RadioGroup.check(R.id.radio_like);
                    break;
                case 2:
                    main_tab_RadioGroup.check(R.id.radio_message);
                    break;
                case 3:
                    main_tab_RadioGroup.check(R.id.radio_me);
                    break;
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
        int current = 0;
        switch (checkId) {
            case R.id.radio_home:
                current = 0;
                break;
            case R.id.radio_like:
                current = 1;
                break;
            case R.id.radio_message:
                current = 2;
                break;
            case R.id.radio_me:
                current = 3;
                break;
        }
        if (main_viewPager.getCurrentItem() != current) {
            main_viewPager.setCurrentItem(current);
        }
    }

}
