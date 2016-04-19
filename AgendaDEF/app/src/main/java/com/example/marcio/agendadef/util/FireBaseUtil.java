package com.example.marcio.agendadef.util;

import com.firebase.client.Firebase;

/**
 * Created by marcio on 19/04/2016.
 */
public class FireBaseUtil {
    private static Firebase firebase;
    private static final String URL ="https://myphonebook.firebaseio.com/";

    public static Firebase getFirebase(){
        if( firebase == null ){
            firebase = new Firebase(URL);
        }
        return( firebase );
    }
}
