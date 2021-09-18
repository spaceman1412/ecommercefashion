package com.example.ecommercefashion

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.Navigation
import com.firebase.ui.auth.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase


class RegisterFragment : Fragment() {
    // TODO: Rename and change types of parameters

    companion object{
        val TAG = "Register"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_register, container, false)

        val email = view.findViewById<EditText>(R.id.email_editText_register)
        val passwd = view.findViewById<EditText>(R.id.passwd_editText_register)

        val register_btn = view.findViewById<Button>(R.id.signup_button_register)



        register_btn.setOnClickListener {
            Log.d(TAG,email.text.toString())
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.text.toString(),passwd.text.toString())
                .addOnCompleteListener {
                    if(!it.isSuccessful) return@addOnCompleteListener
                    Log.d(TAG,"Register complete ${it.result?.user?.uid}")
                    val currentUser = FirebaseAuth.getInstance().currentUser?.uid
                    Log.d(TAG,"Current user:  ${currentUser.toString()}")
                }
                .addOnFailureListener {
                    Log.d(TAG,"Failed to create ${it.message}")
                }
        }

        val back_txt  : TextView = view.findViewById(R.id.back_login_text_register)
        back_txt.setOnClickListener {
//            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment)
        }

        return view
    }


}