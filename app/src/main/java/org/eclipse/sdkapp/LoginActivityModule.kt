package org.eclipse.sdkapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import org.eclipse.ecsp.appauth.AuthInterface
import org.eclipse.ecsp.appauth.AuthManager
import org.eclipse.ecsp.environment.Environment
import org.eclipse.ecsp.environment.EnvironmentManager
import org.eclipse.ecsp.helper.AppManager
import org.eclipse.ecsp.userservice.service.UserServiceInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivityModule : ComponentActivity(), AuthInterface {
    private val userServiceInterface: UserServiceInterface by lazy {
        UserServiceInterface.authService(this)
    }
    private lateinit var launcher: ActivityResultLauncher<Intent>

    companion object {
        private const val SIGN_IN_REQUEST_CODE = 101
        private const val SIGN_UP_REQUEST_CODE = 102
    }

    private var isSuccessToken = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.initialize(this.application)

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result: ActivityResult -> result.data?.let { AppManager.authResponseFromIntent(it) } }
    }

    fun initializeSDK(isSuccessToken: Boolean) {
        this.isSuccessToken = isSuccessToken
        AppManager.initialize(this.application)
    }

    fun configureEnvironment() {
        val envData = Environment(
            "ENV", "dummy", "dummy", BASE_URL,
            BASE_URL, BASE_URL, BASE_URL, "auth://authorization",
            arrayListOf("scope1", "scope2", "scope3", "scope4", "scope5", "scope5")
        )
        EnvironmentManager.configure(envData)
    }

    fun launchSignIn() {
        userServiceInterface.signInWithAppAuth(SIGN_IN_REQUEST_CODE, launcher)
    }

    fun launchSignUp() {
        userServiceInterface.signUpWithAppAuth(SIGN_UP_REQUEST_CODE, launcher)
    }

    fun launchSignOut() {
        userServiceInterface.signOutWithAppAuth {

        }
    }

    fun fetchUserData() {
        AuthManager.sharedInterface(this)
        CoroutineScope(Dispatchers.IO).launch {
            userServiceInterface.fetchUserProfile {

            }
        }
    }

    override var accessToken: String = "token"
    override var refreshToken: String = "test_refresh_token"
    override var tokenType: String = "Bearer"
    override var scope: String = "test_scope"
    override var accessTokenExpirationDate: Long = 0L
    override var additionalParameters: HashMap<Any, Any> = hashMapOf()
}