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
import com.example.ecommercefashion.databinding.ActivityMainBinding
import com.example.ecommercefashion.databinding.FragmentRegisterBinding
import com.firebase.ui.auth.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class RegisterFragment : Fragment() {
    // TODO: Rename and change types of parameters

    companion object{
        val TAG = "Register"
    }

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        val view =  inflater.inflate(R.layout.fragment_register, container, false)

        binding = FragmentRegisterBinding.inflate(inflater,container,false)

        val email = binding.emailEditTextRegister
        val passwd = binding.passwdEditTextRegister

        val register_btn = binding.signupButtonRegister



        register_btn.setOnClickListener {
           performRegister()
        }

        val back_txt  : TextView = binding.backLoginTextRegister
        back_txt.setOnClickListener {
//            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            Navigation.findNavController(binding.root).navigate(R.id.action_registerFragment_to_loginFragment)
        }

        return binding.root
    }
    private fun performRegister(){
        val email = binding.emailEditTextRegister
        val passwd = binding.passwdEditTextRegister
        Log.d(TAG,email.text.toString())
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.text.toString(),passwd.text.toString())
            .addOnCompleteListener {
                if(!it.isSuccessful) return@addOnCompleteListener
                Log.d(TAG,"Register complete ${it.result?.user?.uid}")
                val currentUser = FirebaseAuth.getInstance().currentUser?.uid
                Log.d(TAG,"Current user:  ${currentUser.toString()}")
                saveUserToDatabase()
            }
            .addOnFailureListener {
                Log.d(TAG,"Failed to create ${it.message}")
            }
    }

    private fun saveUserToDatabase(){
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val email = binding.emailEditTextRegister
        val passwd = binding.passwdEditTextRegister
        val user = User(uid.toString(),email.text.toString(),passwd.text.toString())
        ref.setValue(user)
            .addOnCompleteListener {
                Log.d(TAG,"Saved user to database")
            }
            .addOnFailureListener {
                Log.d(TAG,it.message.toString())
            }
    }
    class User ( val uid: String ,val username : String, password: String )
}
