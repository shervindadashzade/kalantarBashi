package com.example.kalantarbashi.Core;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kalantarbashi.FindingActivity;
import com.example.kalantarbashi.ListeningActivity;
import com.example.kalantarbashi.R;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends AsyncTask<Void,String,Account> {
    Context context;
    ServerSocket server;
    LinearLayout layout;
    ImageView userPic;
    TextView username;

    public ServerThread(Context context,LinearLayout layout, ImageView userPic, TextView username) {
        this.context = context;
        this.layout = layout;
        this.userPic = userPic;
        this.username = username;
        try{
            server = new ServerSocket(6969);
            Toast.makeText(context,"server created",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected Account doInBackground(Void... voids) {
        try {
            publishProgress("listining...");
            Socket connection = server.accept();
            publishProgress("accepted");
            DataInputStream dis = new DataInputStream(connection.getInputStream());
            String response = (String) dis.readUTF();
            publishProgress(response);
            String[] informations = response.split("@");
            Account account = new Account(informations[0],informations[1],connection);
            return account;
        }catch (Exception e){
            publishProgress(e.getMessage());
            return null;
        }

    }

    @Override
    protected void onPostExecute(Account account) {
        if(account == null){
            Toast.makeText(context,"no account info",Toast.LENGTH_SHORT).show();
        }else {
            layout.setVisibility(View.VISIBLE);
            username.setText(account.getName());
            if (account.getUserPic().equals("1"))
                userPic.setImageResource(R.drawable.face1);
            else
                userPic.setImageResource(R.drawable.face2);
            ListeningActivity.account = account;
            super.onPostExecute(account);
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
            Toast.makeText(context,values[0],Toast.LENGTH_SHORT).show();
        super.onProgressUpdate(values);
    }
}
