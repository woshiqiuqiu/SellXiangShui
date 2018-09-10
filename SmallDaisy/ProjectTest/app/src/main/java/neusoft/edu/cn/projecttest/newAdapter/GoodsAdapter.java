package neusoft.edu.cn.projecttest.newAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import neusoft.edu.cn.projecttest.R;
import neusoft.edu.cn.projecttest.asyncTask.DownImageTask;
import neusoft.edu.cn.projecttest.tool.ImageUtil;


public class GoodsAdapter extends BaseAdapter {
    private List<Map> list = null;

    private Context context = null;
    //private ViewHolder holder =null;

    public GoodsAdapter(Context context, List<Map> list) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        int count = 0;
        if (list != null) {
            count = list.size();
        }
        return count;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.goodslistview, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.pic = (ImageView) convertView.findViewById(R.id.iv_image);
            holder.goodsName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.goodsPrice = (TextView) convertView.findViewById(R.id.tv_price);
            holder.goodsDiscount = (TextView) convertView.findViewById(R.id.tv_info);
            convertView.setTag(holder);

        }
        final ViewHolder holder = (ViewHolder) convertView.getTag();
        String goodsname = (String) list.get(position).get("goodsname");
        String goodsprice = (String) list.get(position).get("goodsprice");
        String pic = (String) list.get(position).get("pic");
        String goodsdiscount = (String) list.get(position).get("goodsdiscount");
        String imageUrl = "http://10.0.2.2:8080/small daisy/" + pic;

        holder.goodsName.setText(goodsname);
        holder.goodsPrice.setText(goodsprice);
        holder.goodsDiscount.setText(goodsdiscount);
        holder.pic.setImageResource(R.drawable.ic_launcher);

        Bitmap bitmap = ImageUtil.readImage(imageUrl);
        if (bitmap!= null) {
            holder.pic.setImageBitmap(bitmap);
            Log.d("info","bitmap 不为空 ");
        } else {
            Log.d("info","bitmap  为空 ");
            //异步任务下载
            new DownImageTask(new DownImageTask.DownLoadBack() {
                @Override
                public void response(Bitmap bitmap) {
                    holder.pic.setImageBitmap(bitmap);
                }
            }).execute(imageUrl);
        }
        return convertView;
    }

    class ViewHolder {
        TextView goodsName, goodsDiscount, goodsPrice;
        ImageView pic;
    }

}

