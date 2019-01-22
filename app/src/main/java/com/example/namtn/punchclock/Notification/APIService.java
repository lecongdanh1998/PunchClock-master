package com.example.namtn.punchclock.Notification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAe-1KDyA:APA91bHksoXjtAnZmryY_rEY91ou1gF6r4Grhu5Pqu9xwtjDBD6rgCI_NPlyO9i3Fo-ALUver8SEgJvFhc9ZNYoLgbCUGVW0ugw_zw7rooSWteitMnSgB8zuwMaXk2Xlv2laCy_2Gt3N"
            }
    )

    @POST("fcm/send")
    Call<MyReponse> sendNotification(@Body Sender body);
}
