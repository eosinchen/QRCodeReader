package com.example.eosinchen.qrcodereader;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // 進行 btnReadQRCode 之 Event Handler
        Button btnReadQRCode = (Button) findViewById(R.id.btnReadQRCode);
        btnReadQRCode.setOnClickListener(new onbtnReadQRCodeClicks());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // 處理 btnReadQRCode 按下的事件
    private class onbtnReadQRCodeClicks implements View.OnClickListener {

        public void onClick(View v) {

            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            if(getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size()==0)
            {
                // 未安裝
                Toast.makeText(MainActivity.this, "請至 Play 商店安裝 ZXing 條碼掃描器", Toast.LENGTH_LONG).show();
            }
            else
            {
                // SCAN_MODE, 可判別所有支援的條碼
                // QR_CODE_MODE, 只判別 QRCode
                // PRODUCT_MODE, UPC and EAN 碼
                // ONE_D_MODE, 1 維條碼
                intent.putExtra("SCAN_MODE", "SCAN_MODE");

                // 呼叫ZXing Scanner，完成動作後回傳 1 給 onActivityResult 的 requestCode 參數
                startActivityForResult(intent, 1);
            }

        }
    }

    // 接收 ZXing 掃描後回傳來的結果
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if(requestCode==1)
        {
            if(resultCode==RESULT_OK)
            {
                // ZXing回傳的內容
                String contents = intent.getStringExtra("SCAN_RESULT");
                TextView txvQRCode = (TextView) findViewById(R.id.txvQRCode);
                txvQRCode.setText(contents.toString());
            }
            else
            if(resultCode==RESULT_CANCELED)
            {
                Toast.makeText(this, "取消掃描", Toast.LENGTH_LONG).show();
            }
        }
    }

}
