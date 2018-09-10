package neusoft.edu.cn.projecttest;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
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

public class RegistActivity extends Activity {
    private EditText etUsername;
    private EditText etPassword;
    private EditText etPasswordAgain;

    private Button btnBack;
    private Button btnOn;

    private TextView tvTitle;

    private String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.CustomTheme);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_register_username);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        etPasswordAgain = (EditText) findViewById(R.id.et_passwordAgain);



        btnBack = (Button) findViewById(R.id.btn_first);

        btnOn = (Button) findViewById(R.id.btn_second);



        tvTitle = (TextView) findViewById(R.id.tv_title);

        btnBack.setBackgroundResource(R.drawable.img_back);

        btnOn.setText("下一步");

        tvTitle.setText("注册");
        //实例化注册1页面的监听器对象
        Register1OnClickListener register1OnClickListener = new Register1OnClickListener();
        Register1OnClickListener register1OnClickListener2 = new Register1OnClickListener();
        //为可能点击的按钮绑定监听器
        btnBack.setOnClickListener(register1OnClickListener);

        btnOn.setOnClickListener(register1OnClickListener);
    }
    class Register1OnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            //返回上一级的按钮被单击
            if(v.getId() == R.id.btn_first) {
                Intent intent = new Intent(RegistActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.back_out, R.anim.back_in);
            }
//下一步按钮被单击
            else if(v.getId() == R.id.btn_second ) {

                String userName = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String passwordAgain = etPasswordAgain.getText().toString();

                if(userName.equals("")) { //如果用户名为空
                    Toast.makeText(getApplicationContext(), "用户名不能为空", Toast.LENGTH_LONG).show();
                }
                else if(password.equals("")) { //如果密码为空
                    Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_LONG).show();
                }
                else if(passwordAgain.equals("")) { //如果确认密码为空
                    Toast.makeText(getApplicationContext(), "确认密码不能为空", Toast.LENGTH_LONG).show();
                }
                else if(!password.equals(passwordAgain)) { //如果两次输入的密码不相同
                    Toast.makeText(getApplicationContext(), "两次输入密码必须相同", Toast.LENGTH_LONG).show();
                }
                else{
                    //用户名密码均已正确填写
                    Toast.makeText(RegistActivity.this, "reg", Toast.LENGTH_SHORT).show();
                    final String username = etUsername.getText().toString().trim();
                    final String passWord = etPassword.getText().toString().trim();

                    //用户名密码均已正确填写
                    try {
                        data = "userName=" +URLEncoder.encode(username, "UTF-8") +"&userPass=" + URLEncoder.encode(passWord, "UTF-8");
                        //获取输入框的输入数据
                        //接口是这样的http://localhost:8080/eshop/login.action?userName=user&userPass=123
                        Log.d("info","用户名和密码 ："+data);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    AsyncHttpConnection ac = new AsyncHttpConnection();
                    try {
                        //这里发送异步请求，防止模拟器的IP和web服务器的IP冲突，所以改为10.0.3.2
                        Toast.makeText(RegistActivity.this, "ac", Toast.LENGTH_SHORT).show();
                        URL url = new URL("http://192.168.191.1:8080/eshop/anreg.action?" + data);
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
        }
    }

}
