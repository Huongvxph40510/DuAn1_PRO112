package com.example.app_food_staff.DataBase;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbSqlServer {

    Connection connection;
    final String TAG = "zzzzzz";

    public Connection openConnect(){
        String ip = "192.168.137.46", port = "1433", user = "sa", pass = "mot23456", db = "QLGoiMon";
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            String connectUrl = "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + db +";user=" + user +";password=" + pass +";";
            this.connection = DriverManager.getConnection(connectUrl);
            Log.d(TAG, "openConnect: OK");
        } catch (Exception e) {
            Log.e(TAG, "getCollection: Loi ket noi CSDL" );
            Log.e(TAG, e.toString());
            e.printStackTrace();
        }
        return connection;
    }
}

