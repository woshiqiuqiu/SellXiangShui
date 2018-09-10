package neusoft.edu.cn.projecttest.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import neusoft.edu.cn.projecttest.R;


public class MessageFragment extends Fragment {

    private ListView mListView;
    private ArrayList<HashMap<String,Object>> maplist;
    private HashMap<String,Object> map;
    private RatingBar ratingBar;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view=inflater.inflate(R.layout.fragment_message, container, false);

        mListView = (ListView)view.findViewById(R.id.listview1);
        getAdapter();


        return view;
    }
    public void input(int image,String name,String info,int price,float bar){
        map = new HashMap<String,Object>();
        map.put("image",image );
        map.put("name",name );
        map.put("info",info );
        map.put("price",price );
        map.put("bar", bar);
        maplist.add(map);
    }

    public ArrayList<HashMap<String,Object>> getSortList(ArrayList<HashMap<String,Object>> maplist){
        for (int j= 0; j< maplist.size()-1; j++) {
            for (int i = 0; i< maplist.size()-1-j; i++) {
                float bar = (Float) maplist.get(i).get("bar");
                float bar1 = (Float) maplist.get(i+1).get("bar");
                if (bar >= bar1) {

                } else {
                    HashMap<String, Object> theList=maplist.get(i+1);
                    maplist.remove(i+1);
                    maplist.add(i, theList);
                }
            }
        }
        return maplist;
    }
    public void getAdapter(){
        // 构建数据
        maplist = new ArrayList<HashMap<String,Object>>();
        input(R.drawable.a01, "黑石榴香水","前调：石榴，中调：香水百合，后调：愈创木", 23,1f);
        input(R.drawable.z18,"青柠罗勒与柑橘香水","前调：柑橘，中调：罗勒，后调：琥珀木，檀香木，雪松木",45,2f);
        input(R.drawable.a09,"法国菩提花香水","前调：菩提花，中调：龙蒿草，后调：康乃馨",65,3f);
        input(R.drawable.z11, "晚香玉与天使草","前调：天使草，柑橘，粉胡椒，中调：晚香玉，茉莉，栀子，后调：琥珀木，檀香木，雪松木", 34,4f);
        input(R.drawable.a08, "末药与冬加豆","前调 : 薰衣草,中调 :末药,后调 :冬加豆", 38,5f);
        BaseAdapter adapter = new NewAdapter(this.getContext(),getSortList(maplist));
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                Toast.makeText(
                        MessageFragment.this.getActivity(),
                        "您选择的是 " + maplist.get(position).get("name")+"？",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
    private class NewAdapter extends BaseAdapter{
        Context context;
        ArrayList<HashMap<String,Object>> maplist2;
        public NewAdapter(Context context2, ArrayList<HashMap<String,Object>> maplist){
            context = context2;
            maplist2 = maplist;
        }
        public int getCount() {
            // TODO Auto-generated method stub
            return maplist2.size();
        }
        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return maplist2.get(position);
        }
        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh;
            if(convertView==null){
                final LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.layout4, null);
                vh = new ViewHolder();
                vh.imageView = (ImageView) convertView.findViewById(R.id.iv_image);
                vh.name = (TextView) convertView.findViewById(R.id.tv_name);
                vh.info = (TextView) convertView.findViewById(R.id.tv_info);
                vh.price = (TextView) convertView.findViewById(R.id.tv_price);
                vh.ratbar = (RatingBar) convertView.findViewById(R.id.ratbar);
                convertView.setTag(vh);
            }else{
                vh = (ViewHolder) convertView.getTag();
            }
            vh.imageView.setImageResource((Integer) maplist2.get(position).get("image"));
            vh.name.setText(maplist2.get(position).get("name")+"");
            vh.info.setText(maplist2.get(position).get("info")+"");
            vh.price.setText(maplist2.get(position).get("price")+"");
            vh.ratbar.setRating((Float) maplist2.get(position).get("bar"));
            return convertView;
        }
        class ViewHolder{
            ImageView imageView;
            TextView name;
            TextView info;
            TextView price;
            RatingBar ratbar;
        }
    }
}
