package com.aduilio.mytasks.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.aduilio.mytasks.R
import com.aduilio.mytasks.databinding.ActivityLoginBinding
import com.aduilio.mytasks.extension.value
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initGoogleLogin()
        initComponents()
    }

    private fun initGoogleLogin() {
        val googleSignOpt = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignOpt)
    }

    private fun initComponents() {
        binding.btLogin.setOnClickListener {
            login()
        }

        binding.btLoginGoogle.setOnClickListener{
            googleLogin()
        }

        binding.btCreateAccount.setOnClickListener {
            createAccount()
        }
    }

    private val googleSignInLauncher = registerForActivityResult(
    ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            firebaseAuthWithGoogle(account)
        } catch (e: ApiException) {
            Log.w("LoginActivity", "Login com o Google falhou", e)
        }
    }

    private fun googleLogin() {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        Firebase.auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    Log.e("LoginActivity", "LoginComCredenciais:FUNCIONOU", task.exception)
                    finish()
                } else {
                    Log.e("LoginActivity", "LoginComCredenciais:falhou", task.exception)
                    Toast.makeText(baseContext,"Falha na Autenticação.",Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun login() {
        Firebase.auth.signInWithEmailAndPassword(binding.etEmail.value(), binding.etPassword.value())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        finish()
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        Log.e("auth", "createUserWithEmail:failure", task.exception)

                        task.exception?.message?.let { errorMessage ->
                            binding.tilEmail.error = errorMessage
                        }

                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
    }

    private fun createAccount() {
        Firebase.auth.createUserWithEmailAndPassword(binding.etEmail.value(), binding.etPassword.value())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        login()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e("auth", "createUserWithEmail:failure", task.exception)

                        task.exception?.message?.let { errorMessage ->
                            binding.tilEmail.error = errorMessage
                        }

                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
    }
}