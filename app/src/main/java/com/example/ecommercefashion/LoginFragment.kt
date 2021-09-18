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
import com.example.ecommercefashion.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {



    companion object{
        val TAG = "Login"
    }

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        val view= inflater.inflate(R.layout.fragment_login, container, false)

        binding = FragmentLoginBinding.inflate(inflater,container,false)

        val register_btn = binding.registerButtonLogin


        register_btn.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_loginFragment_to_registerFragment)
        }

        val login_btn : Button = binding.loginButtonLogin
        val email : EditText = binding.emailEditTextLogin
        val passwd : EditText = binding.passwdEditTextLogin
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
        return binding.root
    }


}