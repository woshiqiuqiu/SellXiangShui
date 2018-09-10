package neusoft.edu.cn.projecttest.asyncTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import neusoft.edu.cn.projecttest.R;
import neusoft.edu.cn.projecttest.tool.HttpUtil;
import neusoft.edu.cn.projecttest.tool.ImageUtil;
import neusoft.edu.cn.projecttest.tool.ParseTool;

/**
 * Created by qiuji on 2018/7/11.
 */
public class SaleGoodsTask extends AsyncTask<String, Void, List<Map>> {
    private Context context;
    private GridView gridView;

    public SaleGoodsTask(Context context, GridView gridView) {
        super();
        this.context = context;
        this.gridView=gridView;
    }
    @Override
    protected List<Map> doInBackground(String... params) {
        // TODO Auto-generated method stub
        String url = params[0];
        List<Map> list = null;
        if(url!=null)
        {
            try {
                Log.d("info"," SaleGoodsTask doInBackground--1 url地址是："+url);
                byte[] data = HttpUtil.getJsonString(url);
                String jsonString = new String(data,"utf-8");
                list = ParseTool.parseGoods(jsonString);
                Log.d("info","SaleGoodsTask doInBackground--2 返回的list的长度 ："+list.size());

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return list;
    }
    @Override
    protected void onPostExecute(List<Map> result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);

        SaleGoodsAdapter adapter = new SaleGoodsAdapter(context, result);
        gridView.setAdapter(adapter);
        Log.d("info","onPostExecute--1 设置适配器 ");
    }
}

class SaleGoodsAdapter extends BaseAdapter {
    private List<Map> list = null;

    private Context context = null;
    //private ViewHolder holder =null;

    public SaleGoodsAdapter(Context context, List<Map> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.salegoodslist, parent, false);
            SaleGoodsAdapter.ViewHolder holder = new SaleGoodsAdapter.ViewHolder();
            holder.pic = (ImageView) convertView.findViewById(R.id.iv_salepic);
            holder.goodsName = (TextView) convertView.findViewById(R.id.tv_salename);
            holder.goodsPrice = (TextView) convertView.findViewById(R.id.tv_saleprice);

            convertView.setTag(holder);

        }
        final SaleGoodsAdapter.ViewHolder holder = (SaleGoodsAdapter.ViewHolder) convertView.getTag();
        String goodsname = (String) list.get(position).get("goodsname");
        String goodsprice = (String) list.get(position).get("goodsprice");
        String pic = (String) list.get(position).get("pic");

        String imageUrl = "http://10.0.2.2:8080/eshop/" + pic;

        holder.goodsName.setText(goodsname);
        holder.goodsPrice.setText(goodsprice);

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
        TextView goodsName,  goodsPrice;
        ImageView pic;
    }

}
