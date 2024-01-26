package com.example.compose.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.compose.navigation.Screen

data class NavigationItem(
    val title:String,
    val description:String,
   val route:String,
    val icon: ImageVector
)

data class DrawerItem(
    val icon: ImageVector,
    val text: String,
    val onClick:  () -> Unit
)

val navigationItemsList =listOf<NavigationItem>(
    NavigationItem(
        title = "Home",
        icon= Icons.Default.Home,
        description = "Home Screen",
        route=Screen.HomeScreen.route
    ),
    NavigationItem(
        title = "Profile",
        icon= Icons.Default.AccountBox,
        description = "Profile Screen",
        route = Screen.ProfileScreen.route
    ),
    NavigationItem(
        title = "About",
        icon= Icons.Default.Help,
        description = "About Screen",
        route=Screen.AboutScreen.route
    ),
    NavigationItem(
        title = "Privacy Policy",
        icon= Icons.Filled.Lock,
        description = "Privacy Screen",
        route=Screen.TermsAndConditionsScreen.route
    )



)








@Composable
fun ShareButton() {
    val context = LocalContext.current
    val shareContent = "Check out this amazing app: https://yourappurl.com"

    // Register the activity result launcher for the share intent
    val shareLauncher = (context as ComponentActivity).activityResultRegistry.register(
        "key",
        ActivityResultContracts.StartActivityForResult()
    ) { _ -> /* Handle result if needed */ }

    Button(
        onClick = {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shareContent)
                type = "text/plain"
            }
            shareLauncher.launch(sendIntent)
        },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp)
    ) {
        Text("click me")
    }
}

@Composable
fun ContactUsButton() {
    val context = LocalContext.current
    val outlookEmail = "organization@example.com"
    val gmailEmail = "organization@gmail.com"

    Button(
        onClick = {
            // Show dialog to choose between Outlook and Gmail
            showEmailChooserDialog(context, outlookEmail, gmailEmail)
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Contact Us")
    }
}

fun showEmailChooserDialog(
    context: Context,
    outlookEmail: String,
    gmailEmail: String
) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf(outlookEmail, gmailEmail))
    }

    val emailChooser = Intent.createChooser(intent, "Choose Email Client")
    context.startActivity(emailChooser)
}