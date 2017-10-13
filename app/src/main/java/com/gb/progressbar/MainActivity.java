package com.gb.progressbar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;

/**
 * MainActivity,AppCompatActivity
 */

public class MainActivity extends Activity {
  private ProgressBar progressBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    progressBar = (ProgressBar) findViewById(R.id.mypro);

    //测试进度条
    new Thread(){

      @Override public void run() {
        progressBar.setMax(100);
        int current = progressBar.getProgress();
        for(int i = 0; i< 100 ;i++){
          current = current + 1;
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          progressBar.setProgress(current);
        }
      }
    }.start();

  }
}
