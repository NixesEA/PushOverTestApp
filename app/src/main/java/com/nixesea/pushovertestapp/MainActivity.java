package com.nixesea.pushovertestapp;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    String TOKEN = "ae8sx39y3n8bzjoqobgfregg65g5mg";//app token

    EditText userTokenED;
    EditText titleED;
    EditText messageED;
    Button btnSend;
    Button btnHistory;

    private APIService mAPIService;
    HistoryDao hd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAPIService = APIUtils.getAPIService();

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
    protected void onResume() {
        super.onResume();

        AppDatabase db = Room.databaseBuilder(this, AppDatabase.class,"database")
                .allowMainThreadQueries()
                .build();
        hd = db.historyDao();
    }

    public void sendPost(String appToken, final String userToken, String title, final String message) {
        mAPIService.savePost(appToken, userToken, title, message).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (response.isSuccessful()) {
                    showToast("Сообщение отправлено");
                    writeInRoom(userToken,message);

                    Log.i("TAG", "Сообщение отправлено" + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                showToast("Ошибка при отправке");
                Log.e("TAG", "Unable to submit post to API.");
            }
        });
    }

    private void writeInRoom(String userToken,String message) {
        SimpleDateFormat formatDate = new SimpleDateFormat("HH:mm \t dd.MM.yyyy");
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        String date = formatDate.format(gregorianCalendar.getTime());

        HistoryUnit historyUnit = new HistoryUnit("Получатель:\n" + userToken, message, "Отправлено: " + date);
        hd.insert(historyUnit);
    }


    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnSend.getId()){
            String userToken = userTokenED.getText().toString();
            String userMessage = messageED.getText().toString();
            String userTitle = titleED.getText().toString();

            if (!userToken.equals("") && !userMessage.equals("")) {
                sendPost(TOKEN, userToken, userTitle, userMessage);
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
            String qRtext = intent.getStringExtra("QRtext");
            messageED.setText(qRtext);
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
