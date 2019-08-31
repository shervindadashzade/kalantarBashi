package com.example.kalantarbashi.Core;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.kalantarbashi.ConnectActivity;
import com.example.kalantarbashi.ListeningActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ConnectThread extends AsyncTask<Void,String,Void> {
    String ip;
    String data;
    Context context;

    public ConnectThread(String ip, String data,Context context) {
        this.ip = ip;
        this.data = data;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Socket socket = new Socket(ip, 6969);
            publishProgress("connected");
            ListeningActivity.account.setSocket(socket);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(data);
            publishProgress("write = "+data);
            dos.flush();
            dos.close();
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            String response = (String) dis.readUTF();
            if(response.equals("connect")){
                publishProgress("connect");
            }
        }catch (Exception e){
            publishProgress(e.toString());
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        if(values[0].equals("connect")){
            context.startActivity(new Intent(context,ListeningActivity.class));
        }else {
            Toast.makeText(context, values[0], Toast.LENGTH_LONG).show();
        }
        super.onProgressUpdate(values);
    }
}
