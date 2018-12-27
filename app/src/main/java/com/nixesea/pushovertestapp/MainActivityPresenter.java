package com.nixesea.pushovertestapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.nixesea.pushovertestapp.database.App;
import com.nixesea.pushovertestapp.database.AppDatabase;
import com.nixesea.pushovertestapp.database.HistoryDao;
import com.nixesea.pushovertestapp.database.HistoryUnit;
import com.nixesea.pushovertestapp.network.APIService;
import com.nixesea.pushovertestapp.network.APIUtils;
import com.nixesea.pushovertestapp.network.Post;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter {

    Context context;

    private View view;
    AppDatabase db;
    HistoryDao hd;
    private APIService mAPIService;

    public MainActivityPresenter (View view, Context context){
        this.view = view;
        this.context = context;

        mAPIService = APIUtils.getAPIService();

        db = Room.databaseBuilder(context, AppDatabase.class,"database")
                .allowMainThreadQueries()
                .build();
        hd = db.historyDao();
    }

    private void addInDatabase(String userToken,String message){
        SimpleDateFormat formatDate = new SimpleDateFormat("HH:mm \t dd.MM.yyyy");
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        String date = formatDate.format(gregorianCalendar.getTime());

        HistoryUnit historyUnit = new HistoryUnit("Получатель:\n" + userToken, message, "Отправлено: " + date);
        hd.insert(historyUnit);
    }

    public void sendPost(String appToken, final String userToken, String title, final String message) {
        mAPIService.savePost(appToken, userToken, title, message).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {

                if (response.isSuccessful()) {
                    view.showToast("Сообщение отправлено");
                    addInDatabase(userToken,message);

                    assert response.body() != null;
                    Log.i("TAG", "Сообщение отправлено" + response.body().toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {
                view.showToast("Ошибка при отправке");
                Log.e("TAG", "Unable to submit post to API.");
            }
        });
    }


    public interface View {
        void showToast(String msg);
    }
}
