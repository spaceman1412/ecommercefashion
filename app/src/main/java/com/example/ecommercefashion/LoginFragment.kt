package com.example.ecommercefashion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.ecommercefashion.databinding.FragmentLoginBinding

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.lang.Exception

class LoginFragment : Fragment() {

    companion object{
        val TAG = "Login"
        private const val RC_SIGN_IN = 100

    }
    private lateinit var binding: FragmentLoginBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth
    //constants
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //val view= inflater.inflate(R.layout.fragment_login, container, false)

        binding = FragmentLoginBinding.inflate(inflater,container,false)

        val register_btn = binding.signUpText

        register_btn.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_loginFragment_to_registerFragment)
        }
        // configure the Google SignIn
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail() // we only need email from google account
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)

        // init firebase auth
        firebaseAuth = FirebaseAuth.getInstance();

        //Google sign in textview, click to begin Google SignIn
        binding.googleLoginBtn.setOnClickListener {
            Log.d(TAG, "onCreate: begin Google SignIn")
            val intent = googleSignInClient.signInIntent
            startActivityForResult(intent, RC_SIGN_IN)
        }
        checkUser()

        val login_btn : Button = binding.loginButtonLogin
        val email : EditText = binding.emailEditTextLogin
        val passwd : EditText = binding.passwdEditTextLogin

        login_btn.setOnClickListener {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                email.text.toString(),passwd.text.toString()
            )
                .addOnCompleteListener {
                    if(!it.isSuccessful) return@addOnCompleteListener
                    val currentUser = FirebaseAuth.getInstance().currentUser?.uid
                    Log.d(TAG,"Login successfully ${currentUser.toString()}")
                    val intent = Intent(activity,MainActivity::class.java)
                    startActivity(intent)
            }
                .addOnFailureListener {
                    Log.d(TAG,it.message.toString())
                }
        }
        return binding.root
    }

    private fun checkUser() {
        //check if user is logged in or not
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null) {
            //user is already logged in
            val intent = Intent(activity,MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // result returned from lunching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Log.d(TAG, "onActivityResult: Google SignIn intent result")
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                //Google sign in successful, now auth with firebase
                val account = accountTask.getResult(ApiException::class.java)
                firebaseAuthWithGoogleAccount(account)
            }
            catch (e: Exception) {
                Log.d(TAG, "onActivityResult: ${e.message}")
            }
        }
    }

    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount?) {
        Log.d(TAG, "firebaseAuthWithGoogleAccount: begin firebase with google account")
        val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->
                Log.d(TAG, "firebaseAuthWithGoogleAccount: LoggedIn")
                //get loggedIn user
                val firebaseUser = firebaseAuth.currentUser
                //get user info
                val uid = firebaseUser!!.uid
                val email = firebaseUser.email
                Log.d(TAG, "firebaseAuthWithGoogleAccount: Uid: $uid")
                Log.d(TAG, "firebaseAuthWithGoogleAccount: Email: $email")
                // check if user is new or existing
                if(authResult.additionalUserInfo!!.isNewUser) {
                    // user is new - Account created
                    Log.d(TAG, "firebaseAuthWithGoogleAccount: Account created...\n$email")
                    Toast.makeText(this.context, "Account created...", Toast.LENGTH_SHORT).show()
                }
                else {
                    Log.d(TAG, "firebaseAuthWithGoogleAccount: Existing user...\n$email")
                }
                // start profile activity
                val intent = Intent(activity,MainActivity::class.java)
                startActivity(intent)

            }
            .addOnFailureListener { e ->
                Log.d(TAG, "firebaseAuthWithGoogleAccount: Loggin Failed due to${e.message}")
            }
    }


}