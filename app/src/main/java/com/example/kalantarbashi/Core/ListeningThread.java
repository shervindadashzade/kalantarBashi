package com.example.kalantarbashi.Core;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.kalantarbashi.LandPageActivity;
import com.example.kalantarbashi.ListeningActivity;
import com.example.kalantarbashi.ResultActivity;

import java.io.DataInputStream;
import java.net.Socket;

public class ListeningThread extends AsyncTask<Void,String,Void> {
    Socket connection;
    Context context;
    public ListeningThread(Context context,Socket connection) {
        this.context = context;
        this.connection = connection;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        if(values[0].equals("go")){
            ResultActivity.rival = ListeningActivity.account;
            ResultActivity.me = LandPageActivity.myAccount;
         context.startActivity(new Intent(context, ResultActivity.class));
        }else {
            Toast.makeText(context, values[0], Toast.LENGTH_SHORT).show();
        }
        super.onProgressUpdate(values);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try{
            DataInputStream dis = new DataInputStream(connection.getInputStream());
            String res = (String) dis.readUTF();
            ListeningActivity.isRecieved = true;
            ListeningActivity.account = new Account(res);
            if(ListeningActivity.isShooted) {
                publishProgress("go");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
