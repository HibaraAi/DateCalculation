package cn.snowt.datecalculation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: Snow_T
 * @Date: 2021-07-28
 * @Description:
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        EditText autoDate = findViewById(R.id.date_input1);
        autoDate.setText(simpleDateFormat.format(new Date()));

        findViewById(R.id.btn_calculation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText date1 = findViewById(R.id.date_input1);
                EditText date2 = findViewById(R.id.date_input2);
                String date1Str = date1.getText().toString();
                String date2Str = date2.getText().toString();


                Date newerDate = null;
                Date olderDate = null;
                try {
                    newerDate = simpleDateFormat.parse(date1Str);
                    olderDate = simpleDateFormat.parse(date2Str);
                    if(newerDate==null || olderDate==null){
                        tip("日期输入错误，必须是“2008-08-08”类型");
                        return;
                    }
                } catch (ParseException e) {
                    tip("日期输入错误，必须是“2008-08-08”类型");
                    return;
                }
                if(newerDate.after(olderDate)){
                    Date tempDate = new Date();
                    tempDate = newerDate;
                    newerDate = olderDate;
                    olderDate = tempDate;
                }
                int year = (int) ((olderDate.getTime() - newerDate.getTime()) / (1000 * 60 * 60 * 24)) / 365;
                int day = (int) ((olderDate.getTime() - newerDate.getTime()) / (1000 * 60 * 60 * 24));
                String resultStr = "\t"+simpleDateFormat.format(olderDate)+"跟"+simpleDateFormat.format(newerDate)+"相距:\n" +
                        "\t"+day + "天。\n"+
                        "\t"+year + "年又"+(int) ((olderDate.getTime() - newerDate.getTime()) / (1000 * 60 * 60 * 24)) % 365+"天。\n";

                TextView resultView = findViewById(R.id.result_text);
                resultView.setText(resultStr);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_about:{
                AlertDialog.Builder builder  = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("说明" ) ;
                String s = "本程序实现的功能为计算两个日期之间相距的天数。\n\n" +
                        "注：在计算相差几年时，是用相距天数除以365得到的，所以实际结果会有偏差。" +
                        "例如2007-01-01和2013-01-01应该是相距6年整，但本程序的计算结果为相距6年又2天，" +
                        "原因在于中间出现了两个闰年，多了2008年2月29日和2012年2月29日。但相距天数绝对准确。" +
                        "另外如果输入2021-07-32这种错误日期，程序会自动帮你转成对应的2021-08-01进行计算。\n\n" +
                        "作者:HibaraAi\n" +
                        "版本:1.0\t\t日期:2021-07-28\n" +
                        "源码:https://github.com/HibaraAi/DateCalculation";
                builder.setMessage(s) ;
                builder.setPositiveButton("OK" ,  null );
                builder.show();
                break;
            }
            default:{
                return false;
            }
        }
        return true;
    }

    private void tip(String msg){
        Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
    }
}