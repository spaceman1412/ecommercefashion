package com.example.ecommercefashion

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {


    companion object{
        val TAG = "Login"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_login, container, false)

        val register_btn = view.findViewById<Button>(R.id.register_button_login)


        register_btn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment)
        }

        val login_btn : Button = view.findViewById(R.id.login_button_login)
        val email : EditText = view.findViewById(R.id.email_editText_login)
        val passwd : EditText = view.findViewById(R.id.passwd_editText_login)
        login_btn.setOnClickListener {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                email.text.toString(),passwd.text.toString()
            ).addOnCompleteListener {
                if(!it.isSuccessful) return@addOnCompleteListener
                val currentUser = FirebaseAuth.getInstance().currentUser?.uid
                Log.d(TAG,"Login successfully ${currentUser.toString()}")
            }
                .addOnFailureListener {
                    Log.d(TAG,it.message.toString())
                }
        }
        return view
    }


}