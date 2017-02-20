package br.mv.redditreader.info;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Marcos Vasconcelos on 20/02/2017.
 */
public class NetworkInfo {
    private Application ctx;

    public NetworkInfo(Application ctx){
        this.ctx = ctx;
    }

    public boolean isConnected(){
        ConnectivityManager system = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(system.getActiveNetworkInfo() == null)
            return false;

        return system.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public  boolean isDisconnected(){
        return !isConnected();
    }

}