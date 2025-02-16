package com.example.mindspark.profile.ui.sections

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindspark.R
import com.example.mindspark.auth.components.AuthTopBar
import com.example.mindspark.profile.model.Friend
import com.example.mindspark.ui.theme.LightBlueBackground
import com.example.mindspark.ui.theme.customTypography


@Composable
fun InviteFriendsScreen(navController: NavController) {
    // Light background color similar to your screenshot
    val screenBackgroundColor = Color(0xFFF8FBFE)

    // Custom color for the "Invite" button
    val inviteBlue = Color(0xFF007AFF)

    // Sample list of friends
    val friendsData = listOf(
        Friend("Rani Thomas", "(+91) 702-897-7965", isInvited = true),
        Friend("Anastasia", "(+91) 702-897-7965", isInvited = true),
        Friend("Vaibhav", "(+91) 727-688-4052", isInvited = false),
        Friend("Rahul Juman", "(+91) 601-897-1714", isInvited = true),
        Friend("Abby", "(+91) 802-312-3230", isInvited = false)
    )

    // Use a mutable state list so we can toggle each friend’s "invited" status if desired
    val friendsList = remember { mutableStateListOf<Friend>().apply { addAll(friendsData) } }

    Scaffold(
        modifier = Modifier.background(LightBlueBackground),
        containerColor = LightBlueBackground,
        topBar = {
            AuthTopBar(
                title = "Invite Frineds",
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBlueBackground)
                .padding(padding)
                .padding(16.dp)
        ) {


            // Main screen layout
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(screenBackgroundColor)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Rounded card with friend list
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF4FD)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            friendsList.forEachIndexed { index, friend ->
                                FriendRow(
                                    friend = friend,
                                    inviteBlue = inviteBlue,
                                    onInviteToggle = {
                                        // Toggle the invited state
                                        friendsList[index] =
                                            friend.copy(isInvited = !friend.isInvited)
                                    }
                                )
                                // Divider between rows, except after the last one
                                if (index < friendsList.lastIndex) {
                                    Divider(
                                        modifier = Modifier
                                            .padding(vertical = 12.dp)
                                            .fillMaxWidth(),
                                        thickness = 0.5.dp,
                                        color = Color.LightGray
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // "Share Invite Via" label
                    Text(
                        text = "Share Invite Via",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Row of social icons
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Replace painterResource(...) with your actual icon resources
                        Image(
                            painter = painterResource(id = R.drawable.ic_instagram),
                            contentDescription = "Instagram",
                            modifier = Modifier.size(35.dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_facebook),
                            contentDescription = "Instagram",
                            modifier = Modifier.size(30.dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_x),
                            contentDescription = "Instagram",
                            modifier = Modifier.size(30.dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_whatsapp),
                            contentDescription = "Instagram",
                            modifier = Modifier.size(30.dp)
                        )

                    }
                }
            }
        }
    }
}

// Individual friend row
@Composable
fun FriendRow(
    friend: Friend,
    inviteBlue: Color,
    onInviteToggle: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min), // Adjust to content
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Black circular avatar
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.Black)
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Name and phone
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = friend.name,
                style = MaterialTheme.customTypography.jost.semiBold,
                fontSize = 16.sp
            )
            Text(
                text = friend.phoneNumber,
                style = MaterialTheme.customTypography.mulish.bold,
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

//        // Invite button (filled vs outlined)
//        val buttonText = if (friend.isInvited) "Invited" else "Invite"
//
//        Button(
//            onClick = {
//                friend.isInvited = !friend.isInvited
//                onInviteToggle()
//            },
//            colors = ButtonDefaults.buttonColors(
//                containerColor = if (friend.isInvited) inviteBlue else Color.White
//            ),
//            shape = RoundedCornerShape(28.dp),
//            modifier = Modifier
//                .width(80.dp)
//                .height(28.dp)
//        ) {
//            Text(
//                text = buttonText,
//                style = MaterialTheme.customTypography.mulish.bold,
//                fontSize = 13.sp
//            )
//        }

        InviteButton()

    }
}

@Composable
fun InviteButton(modifier: Modifier = Modifier) {
    val isInvite = remember { mutableStateOf(false) }

    Button(
        onClick = { isInvite.value = !isInvite.value },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFE8F1FF)
        ),
        modifier = modifier.height(31.dp),
        shape = RoundedCornerShape(40.dp)
    ) {
        Text(
            text = if (isInvite.value) "Invited" else "Invite",
            style = MaterialTheme.customTypography.jost.semiBold,
            fontSize = 13.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InviteFriendsPreview() {
    MaterialTheme {
        InviteFriendsScreen(navController = NavController(LocalContext.current))
    }
}
