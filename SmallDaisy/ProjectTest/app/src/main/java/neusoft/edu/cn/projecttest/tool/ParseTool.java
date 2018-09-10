package neusoft.edu.cn.projecttest.tool;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseTool {
    public static List<Map> parseGoods(String json) throws JSONException {
        List<Map> list2=new ArrayList<Map>();
        JSONArray array = new JSONArray(String.valueOf(json));


        for(int i = 0 ; i <array.length(); i ++){
            JSONObject jsonObject2 = (JSONObject) array.opt(i);


            String goodsName = jsonObject2.getString("goodsName");

            String goodsPrice = jsonObject2.getString("goodsPrice");
            String pic = jsonObject2.getString("goodsPic");
            String goodsDiscount = jsonObject2.getString("goodsDiscount");
            Map map=new HashMap();
            map.put("goodsname",goodsName);
            map.put("goodsprice",goodsPrice);
            map.put("pic",pic);
            map.put("goodsdiscount",goodsDiscount);
            list2.add(map);
            Log.d("info","map     "+ map.toString());
        }
        Log.d("info","1-2 ParseTool  list值是======："+list2.size());
        return list2;

    }
}
