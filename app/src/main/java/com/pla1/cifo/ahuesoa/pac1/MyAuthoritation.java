package com.pla1.cifo.ahuesoa.pac1;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Clase que se ocupa de realizar la autorizacion a Firebase
 * Es subclase de Thread porque al ser un hilo diferente
 * a la conexión a la base de datos se puede retrasar esta última
 * y dar tiempo a que se compruebe la autorización
 */
public class MyAuthoritation extends Thread {
    /*Correo con el que se autorizará*/
    private String email;
    /*Clave con la que se autorizará*/
    private String password;
    /*Instancia de FirebaseAuth con la que se autorizará*/
    private FirebaseAuth mAuth;
    /*Actividad en la que se está intentando la autorización*/
    private Activity mActivity;


    /**
     * Constructor de MyAuthorization
     * @param email
     * @param password
     * @param mActivity
     */
    public MyAuthoritation(String email, String password, Activity mActivity){

        this.email=email;
        this.password=password;
        this.mAuth=FirebaseAuth.getInstance();
        this.mActivity=mActivity;

    }


    /**
     * Método para realizar la autorización en Firebase con el email y la clave indicadas en la construcción
    *
     */

    public void run(){

        //Para evitar mantener una autorización válida previa en caso de que no se consiga
        mAuth.signOut();

        //Registro con correo y clave
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                //Autorización exitosa
                if (task.isSuccessful())
                {

                    Log.i("LoginSuccess", "signInWithEmail:success :"+mAuth.getCurrentUser().getUid());

                }

                //Autorización no exitosa
                else
                {
                    Log.e("failureLogin", "signInWithEmail:failure", task.getException());
                    Log.e("noSuccessfullogin","no success");
                    //Desconectamos para que no se mantenga una conexión exitosa previa con un usuario o clave actuales erróneos
                    mAuth.signOut();

                }


            }
        });



    }



}