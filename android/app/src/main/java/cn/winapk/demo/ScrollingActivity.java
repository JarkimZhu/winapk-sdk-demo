package cn.winapk.demo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;

import cn.winapk.sdk.WinApk;


public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        WinApk.INSTANCE.init (
                this,
                new WinApk.Options("partnerId"),
                (slotId, event, data) -> Log.d("IAdCallback", "slotId: " + slotId + ", event: " + event + ", data: " + data)
        );

        WinApk.INSTANCE.requestAllSdkPermissions(this, t -> Log.d("Permissions", "request result: " + (t == PackageManager.PERMISSION_GRANTED)));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1,
                Arrays.asList("全屏", "插屏", "横幅")
        );

        ListView demoList = findViewById(R.id.demo_list);
        demoList.setAdapter(adapter);

        demoList.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    WinApk.INSTANCE.showFullScreenVideo(this, "test-001");
                    break;
                case 1:
                    showInterCut(parent);
                    break;
                case 2:
                    startActivity(new Intent(this, BannerActivity.class));
                    break;
            }
        });
    }

    private void showInterCut(AdapterView<?> parent) {
        View videoView = LayoutInflater.from(this).inflate(R.layout.winapk_video_activity, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(videoView);
//                .setTitle("自定义dialog——登录").setIcon(R.mipmap.ic_launcher)
//                .setPositiveButton("登录", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(ScrollingActivity.this, "账号： " + etUsername.getText().toString() + "  密码： " + etPassword.getText().toString()
//                                , Toast.LENGTH_LONG).show();
//                    }
//                });

        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
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
}
