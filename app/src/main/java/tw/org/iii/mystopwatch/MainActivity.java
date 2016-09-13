package tw.org.iii.mystopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
public class MainActivity extends AppCompatActivity {
    private TextView clock ;
    private Button btnLeft,btnRight ;
    private boolean isRunning ;
    private  int counter ; //計數器
    private  Timer timer ;
    private  UIHandler handler ;
    private CountTask countTask ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       btnLeft=(Button)findViewById(R.id.btnLeft);
        btnRight=(Button)findViewById(R.id.btnRight);
        clock =(TextView)findViewById(R.id.clock); //初始化
        timer= new Timer(); //被執行
        handler =new UIHandler() ; //UIhandler
    }

    @Override
    public void finish() {
        timer.purge() ; //清除任務
        timer.cancel(); //停止
        timer=null ; //結束timer
        super.finish();
    }

    //reset/lap
    public  void  doLeft(View v) {
        doRest();
        doLap();

    }
    //start or stop
    public  void  doRight(View v) {
     isRunning =!isRunning ; //當run變成!run
        btnRight.setText(isRunning?"Stop":"Start");
        btnLeft.setText(isRunning?"Lap":"Reset");
        if(isRunning){
            doStart();

        }else{
            doStop();
        }

    }
    private  void doStart() {
        countTask = new CountTask() ;
        timer.schedule(countTask,0,10);

    }
    private  void doStop(){
        if(countTask!=null){
            countTask.cancel() ;
            countTask=null ;
        } counter=0 ;
        handler.sendEmptyMessage(0);


    }
    private  void doLap(){

    }
    private  void doRest(){

    }

    private  class  CountTask extends TimerTask{
        @Override
        public void run() {
            counter++ ;
            handler.sendEmptyMessage(0);
        }
    }
    private  class  UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clock.setText(""+counter );
        }
    }
}

