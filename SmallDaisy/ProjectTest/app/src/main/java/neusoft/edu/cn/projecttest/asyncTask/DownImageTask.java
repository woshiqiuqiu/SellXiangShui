package neusoft.edu.cn.projecttest.asyncTask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.apache.http.client.ClientProtocolException;

import java.io.IOException;

import neusoft.edu.cn.projecttest.tool.HttpUtil;
import neusoft.edu.cn.projecttest.tool.ImageUtil;


public class DownImageTask extends AsyncTask<String, Void, Bitmap> {

    private DownLoadBack downLoadBack;

    public DownImageTask(DownLoadBack downLoadBack)
    {
        this.downLoadBack = downLoadBack;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String url = params[0];
        Bitmap bitmap = null;
        if(url!=null)
        {
            try {
                byte[] data = HttpUtil.getJsonString(url);

                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);


                ImageUtil.saveImage(url, bitmap, ImageUtil.FORMAT_JPEG);
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);

        downLoadBack.response(result);
    }

    public interface DownLoadBack
    {
        public void response(Bitmap bitmap);
    }
}
