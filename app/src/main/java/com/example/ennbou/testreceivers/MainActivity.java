package com.example.ennbou.testreceivers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "com.ennbou.channel";

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        ActivityCompat.requestPermissions ( this,
                new String[] { Manifest.permission.RECEIVE_SMS },
                10 );

        ( findViewById ( R.id.clear ) ).setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ) {
                    // Create the NotificationChannel, but only on API 26+ because
                    // the NotificationChannel class is new and not in the support library
                    CharSequence name = getString ( R.string.channel_name );
                    String description = getString ( R.string.channel_description );
                    final int importance = NotificationManagerCompat.IMPORTANCE_DEFAULT;
                    @SuppressLint("WrongConstant") NotificationChannel channel = new NotificationChannel ( CHANNEL_ID, name, importance );
                    channel.setDescription ( description );
                    // Register the channel with the system
                    NotificationManager notificationManager = (NotificationManager) getSystemService(
                            NOTIFICATION_SERVICE);
                    notificationManager.createNotificationChannel(channel);
                }
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder ( getApplicationContext ( ), CHANNEL_ID )
                        .setSmallIcon ( R.drawable.notification_icon )
                        .setContentTitle ( "My notification" )
                        .setContentText ( "Much longer text that cannot fit one line..." )
                        .setStyle ( new NotificationCompat.BigTextStyle ( )
                                .bigText ( "Much longer text that cannot fit one line..." ) )
                        .setPriority ( NotificationCompat.PRIORITY_DEFAULT );

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from ( getApplicationContext ( ) );

                // notificationId is a unique int for each notification that you must define
                int notificationId = 0;
                notificationManager.notify ( notificationId, mBuilder.build ( ) );
            }
        } );

    }

    @Override
    public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults ) {
        super.onRequestPermissionsResult ( requestCode, permissions, grantResults );
        if ( requestCode == 10 ) {
            // YES!!
            Log.i ( "TAG", "MY_PERMISSIONS_REQUEST_SMS_RECEIVE --> YES" );
        }
    }
}
