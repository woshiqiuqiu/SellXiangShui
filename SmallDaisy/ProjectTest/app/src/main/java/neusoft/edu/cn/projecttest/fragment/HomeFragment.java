package neusoft.edu.cn.projecttest.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import neusoft.edu.cn.projecttest.R;


public class HomeFragment extends Fragment {


    TextView t1,t2,t3;
    ViewPager mPage;
    List<View>listViews;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.fragment_home, container, false);



        // Inflate the layout for this fragment

        return view;
    }




    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            Toast.makeText( HomeFragment.this.getActivity(), "播放完成了", Toast.LENGTH_SHORT).show();
        }
    }

    public class MyPagerAdapter extends PagerAdapter {
        public List<View> mListViews;

        public MyPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(mListViews.get(arg1));
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(mListViews.get(arg1), 0);
            return mListViews.get(arg1);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (arg1);
        }
    }


    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mPage.setCurrentItem(index);
        }
    }


    public void onStart() {
        super.onStart();

        mPage = (ViewPager) getView().findViewById(R.id.viewpager2);
        listViews = new ArrayList<View>();
        LayoutInflater mflater = this.getActivity().getLayoutInflater();
        View mView = mflater.inflate(R.layout.tab3, null);
        listViews.add(mflater.inflate(R.layout.tab1, null));
        listViews.add(mflater.inflate(R.layout.tab2, null));
        listViews.add(mView);
        mPage.setAdapter(new MyPagerAdapter(listViews));
        t1 = (TextView) getView().findViewById(R.id.textView1);
        t2 = (TextView) getView().findViewById(R.id.textView2);
        t3 = (TextView) getView().findViewById(R.id.textView3);
        t1.setOnClickListener(new MyOnClickListener(0));
        t2.setOnClickListener(new MyOnClickListener(1));
        t3.setOnClickListener(new MyOnClickListener(2));

        JZVideoPlayerStandard jzVideoPlayerStandard = (JZVideoPlayerStandard) mView.findViewById(R.id.videoplayer);
        System.out.println(jzVideoPlayerStandard);
        jzVideoPlayerStandard.setUp("http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4", JZVideoPlayer.SCREEN_WINDOW_NORMAL, "生活从遇见开始");

    }
}
