package neusoft.edu.cn.projecttest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class WelcomeActivity extends AppCompatActivity {
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha_anim);
        img = (ImageView) findViewById(R.id.img);
        img.startAnimation(animation);

        img=(ImageView) findViewById(R.id.img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent();
                intent1.setClass(WelcomeActivity.this,MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });
    }
}
