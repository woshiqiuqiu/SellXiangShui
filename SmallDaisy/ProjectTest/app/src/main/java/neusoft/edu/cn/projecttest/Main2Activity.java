package neusoft.edu.cn.projecttest;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import static android.R.attr.id;
import static android.R.attr.thumbPosition;

public class Main2Activity extends AppCompatActivity {
    private Spinner spinner;
    private Spinner spinner2;
    private Spinner spinner3;
    private List<String> list;
    private List<String>list2;
    private List<String>list3;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter2;
    private ArrayAdapter<String> adapter3;
    Button bt2;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        spinner =(Spinner)this.findViewById(R.id.spinner1);
        spinner2 =(Spinner)this.findViewById(R.id.spinner2);
        spinner3 =(Spinner)this.findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.age,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(this,R.array.environment,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter3=ArrayAdapter.createFromResource(this,R.array.style,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter2);
        spinner3.setAdapter(adapter3);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s="position"+thumbPosition+"id"+id;
                s=s+"内容是： "+spinner.getSelectedItem().toString();
                Toast.makeText(Main2Activity.this,s,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                Toast.makeText(Main2Activity.this,"no selected",Toast.LENGTH_SHORT).show();
            }
        });


        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s="position"+thumbPosition+"id"+id;
                s=s+"内容是： "+spinner2.getSelectedItem().toString();
                Toast.makeText(Main2Activity.this,s,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                Toast.makeText(Main2Activity.this,"no selected",Toast.LENGTH_SHORT).show();
            }
        });

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s="position"+thumbPosition+"id"+id;
                s=s+"内容是： "+spinner3.getSelectedItem().toString();
                Toast.makeText(Main2Activity.this,s,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                Toast.makeText(Main2Activity.this,"no selected",Toast.LENGTH_SHORT).show();
            }
        });


        bt2=(Button)findViewById(R.id.button2);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent();
                intent1.setClass(Main2Activity.this,MainActivity.class);
                startActivity(intent1);
            }
        });

    }


}

