package neusoft.edu.cn.projecttest.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import neusoft.edu.cn.projecttest.Main2Activity;
import neusoft.edu.cn.projecttest.R;
import neusoft.edu.cn.projecttest.RegistActivity;


public class MeFragment extends Fragment {

    View view;
    private EditText etUsername;
    private EditText etPassword;
    private TextView regist;

    private Button btnLogin;
    private String data;


    //这个线程获取登录状态，为1的时候Toast登录成功并跳转到MainActivity中
    Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(MeFragment.this.getActivity(), "登录成功", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MeFragment.this.getActivity(), Main2Activity.class);
                    startActivity(intent);
                    break;

                default:
                    break;
            }

        };
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {;
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_me, container, false);

        etUsername = (EditText)view.findViewById(R.id.textPersonName);
        etPassword = (EditText)view.findViewById(R.id.textPassword);
        regist = (TextView)view.findViewById(R.id.regist);

        btnLogin= (Button) view.findViewById(R.id.loginbutton);

        LoginOnClickListener loginOnClickListener = new LoginOnClickListener();
        btnLogin.setOnClickListener(loginOnClickListener); //为【登录按钮】绑定监听器对象
        regist.setOnClickListener(loginOnClickListener); //为【新用户文本域】绑定监听器对象

        return view;
    }
    private class LoginOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.regist) { //用户注册的按钮或新用户的文本域被单击
                Intent intent = new Intent(MeFragment.this.getActivity(), RegistActivity.class);
                startActivity(intent);
            }
            else if(v.getId() == R.id.loginbutton) { //用户登录的按钮被单击
                final String userName = etUsername.getText().toString().trim();
                final String password = etPassword.getText().toString().trim();
                if (userName.equals("") || password.equals("")) { //用户名或者密码未填写
                    Toast.makeText(MeFragment.this.getActivity(),"请将用户名密码填写完全后再登录", Toast.LENGTH_LONG).show();
                } else {
                    //用户名密码均已正确填写
                    try {
                        data = "userName=" + URLEncoder.encode(userName, "UTF-8") +"&userPass=" + URLEncoder.encode(password, "UTF-8");
                        //获取输入框的输入数据
                        //接口是这样的http://localhost:8080/eshop/login.action?userName=user&userPass=123
                        Log.d("info","用户名和密码 ："+data);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    AsyncHttpConnection ac = new AsyncHttpConnection();
                    try {
                        //这里发送异步请求，防止模拟器的IP和web服务器的IP冲突，所以改为10.0.3.2
                        URL url = new URL("http://192.168.191.1:8080/Small_daisy/login.action?" + data);
                        ac.execute(url);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }


                }
            }

        }
    }

    class AsyncHttpConnection extends AsyncTask<URL, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(URL... params) {
            JSONObject result = null;
            HttpURLConnection conn = null;
            try {
                Log.d("info","doInBackground  发起连接请求");
                conn = (HttpURLConnection) params[0].openConnection();
                conn.setConnectTimeout(3000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                OutputStream out = conn.getOutputStream();
                out.write(data.getBytes());
                out.flush();
                out.close();
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream in = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String str = br.readLine();
                    result = new JSONObject(str);
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
            return result;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            if (jsonObject != null) {
                if (jsonObject.optBoolean("login") == true) {
                    Log.d("info","onPostExecute  返回数据 login ： "+jsonObject.optBoolean("login")+"    username :" +jsonObject.optString("username"));
                    //模拟登录过程
                    final ProgressDialog pd = new ProgressDialog(MeFragment.this.getActivity());
                    pd.setMessage("正在登录...");
                    pd.show();
                    new Thread() {
                        public void run() {
                            try {
                                Thread.sleep(2000);
                                // handler.sendEmptyMessage(1);
                                Log.d("info","登陆成功");
                                Intent intent = new Intent(MeFragment.this.getActivity(), Main2Activity.class);
                                startActivity(intent);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            pd.dismiss();
                        };
                    }.start();
                } else {
                    Toast.makeText(MeFragment.this.getActivity(), jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}