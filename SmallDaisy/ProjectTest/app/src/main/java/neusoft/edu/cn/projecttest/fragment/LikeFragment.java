package neusoft.edu.cn.projecttest.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import neusoft.edu.cn.projecttest.R;
import neusoft.edu.cn.projecttest.asyncTask.SaleGoodsTask;


public class LikeFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ViewPager viewPager;
    private int[] imageResIds;
    private ArrayList<ImageView> imageViewList;
    private LinearLayout ll_point_container;
    private String[] contentDescs;
    private TextView tv_desc;
    private int previousSelectedPosition = 0;
    boolean isRunning = false;
    private GridView gridView;
    private String path = "http://192.168.191.1:8080/Small_daisy/goods/saleGoodsForMobile.action";
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_like, container, false);

        initGridView();

        // Inflate the layout for this fragment
        // 初始化布局 View视图
        initViews();

        // Model数据
        initData();

        // Controller 控制器
        initAdapter();

        // 开启轮询
        new Thread() {
            public void run() {
                isRunning = true;
                while (isRunning) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 往下跳一位
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            System.out.println("设置当前位置: " + viewPager.getCurrentItem());
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        }
                    });
                }
            }

            ;
        }.start();
        return view;
    }




    private void runOnUiThread(Runnable runnable) {
    }

    private void initViews() {
        viewPager = (ViewPager)view.findViewById(R.id.viewpager);
//		viewPager.setOffscreenPageLimit(1);// 左右各保留几个对象
        ll_point_container = (LinearLayout)view.findViewById(R.id.ll_point_container);

        tv_desc = (TextView)view.findViewById(R.id.tv_desc);

    }

    /**
     * 初始化要显示的数据
     */
    private void initData() {
        // 图片资源id数组
        imageResIds = new int[]{R.drawable.ad_1, R.drawable.ad_2, R.drawable.ad_3};

        // 文本描述
        contentDescs = new String[]{
                "教你混搭出独属于你的夏日花香"
        };

        // 初始化要展示的5个ImageView
        imageViewList = new ArrayList<ImageView>();

        ImageView imageView;
        View pointView;
        LinearLayout.LayoutParams layoutParams;
        for (int i = 0; i < imageResIds.length; i++) {
            // 初始化要显示的图片对象
            imageView = new ImageView(this.getActivity());
            imageView.setBackgroundResource(imageResIds[i]);
            imageViewList.add(imageView);

            // 加小白点, 指示器
            pointView = new View(this.getActivity());
            pointView.setBackgroundResource(R.drawable.selector_bg_point);
            layoutParams = new LinearLayout.LayoutParams(5, 5);
            if (i != 0)
                layoutParams.leftMargin = 10;
            // 设置默认所有都不可用
            pointView.setEnabled(false);
            ll_point_container.addView(pointView, layoutParams);
        }
    }

    private void initAdapter() {
        ll_point_container.getChildAt(0).setEnabled(true);
        tv_desc.setText(contentDescs[0]);
        previousSelectedPosition = 0;

        // 设置适配器
        viewPager.setAdapter(new MyAdapter());

        // 默认设置到中间的某个位置
        int pos = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % imageViewList.size());
        // 2147483647 / 2 = 1073741823 - (1073741823 % 5)
        viewPager.setCurrentItem(5000000); // 设置到某个位置
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        // 3. 指定复用的判断逻辑, 固定写法
        @Override
        public boolean isViewFromObject(View view, Object object) {
//			System.out.println("isViewFromObject: "+(view == object));
            // 当划到新的条目, 又返回来, view是否可以被复用.
            // 返回判断规则
            return view == object;
        }

        // 1. 返回要显示的条目内容, 创建条目
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            System.out.println("instantiateItem初始化: " + position);
            // container: 容器: ViewPager
            // position: 当前要显示条目的位置 0 -> 4

//			newPosition = position % 5
            int newPosition = position % imageViewList.size();

            ImageView imageView = imageViewList.get(newPosition);
            // a. 把View对象添加到container中
            container.addView(imageView);
            // b. 把View对象返回给框架, 适配器
            return imageView; // 必须重写, 否则报异常
        }

        // 2. 销毁条目
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // object 要销毁的对象
            System.out.println("destroyItem销毁: " + position);
            container.removeView((View) object);
        }
    }


    public void initGridView(){
        gridView= (GridView)view.findViewById(R.id.gridview1);
        new SaleGoodsTask(this.getActivity(), gridView).execute(path);
    }


}

