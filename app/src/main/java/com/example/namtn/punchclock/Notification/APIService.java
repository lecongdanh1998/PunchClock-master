package com.example.namtn.punchclock.Notification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAnUWM3Q0:APA91bFyMeUOP1NFJlQThBni6ucNcqtyUzH9AOeWnrBOCWR_8lsCO2ZGE0-_K25ujMS2t2XqwGKqWWbZrALchrSwd7CXY-yNlWSy1en-REfiV-lV7VLHAJ9BBHrng6v32USOUFPuNhfU"
            }
    )

    @POST("fcm/send")
    Call<MyReponse> sendNotification(@Body Sender body);
}
