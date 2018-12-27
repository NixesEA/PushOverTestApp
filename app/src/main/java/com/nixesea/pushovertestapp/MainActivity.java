package com.nixesea.pushovertestapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nixesea.pushovertestapp.database.HistoryActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener, MainActivityPresenter.View {

    String TOKEN = "ae8sx39y3n8bzjoqobgfregg65g5mg";//app token

    EditText userTokenED;
    EditText titleED;
    EditText messageED;
    Button btnSend;
    Button btnHistory;

    MainActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new MainActivityPresenter(this,getApplicationContext());

        userTokenED = findViewById(R.id.userToken);
        titleED = findViewById(R.id.message_title);

        messageED = findViewById(R.id.message);
        messageED.setOnTouchListener(this);

        btnHistory = findViewById(R.id.btnOpenHistory);
        btnHistory.setOnClickListener(this);
        btnSend = findViewById(R.id.btnSendMessage);
        btnSend.setOnClickListener(this);
    }




    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnSend.getId()){
            String userToken = userTokenED.getText().toString();
            String userMessage = messageED.getText().toString();
            String userTitle = titleED.getText().toString();

            if (!userToken.equals("") && !userMessage.equals("")) {
                mPresenter.sendPost(TOKEN, userToken, userTitle, userMessage);
            } else {
                showToast("Ошибка при отправке");
            }
        }else{
            Intent intent = new Intent(this,HistoryActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (intent != null) {
            String QRtext = intent.getStringExtra("QRtext");
            messageED.setText(QRtext);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        final int DRAWABLE_RIGHT = 2;

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getRawX() >= (messageED.getRight() - messageED.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                Intent intent = new Intent(this, QRActivity.class);
                startActivityForResult(intent, 1);
                return true;
            }
        }
        return false;
    }
}
