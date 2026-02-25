package com.example.bromode.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bromode.ui.navigation.Routes

@Composable
fun HomeScreen(navController: NavController) {

    val bg = Brush.verticalGradient(
        colors = listOf(Color(0xFF0B0B0F), Color(0xFF0F172A))
    )
    val neon = Color(0xFFA3FF00)
    val card = Color.White.copy(alpha = 0.06f)

    // MVP fake values (later from ViewModel)
    val name = "Salman"
    val streakDays = 4
    val nextSession = "Chest + Triceps • 6:30 PM"
    val gymStatus = "Busy: Medium"
    val broMsg = "You showed up. That already puts you ahead of yesterday’s you."

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            BroBottomNavBar(
                currentRoute = Routes.HOME,
                onNavigate = { route ->
                    if (route == Routes.HOME) return@BroBottomNavBar
                    navController.navigate(route)
                },
                neon = neon
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(bg)
                .statusBarsPadding()
                .padding(innerPadding)
                .padding(18.dp)
        ) {

            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Yo, $name",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "BroMode: ON",
                        color = neon,
                        fontSize = 14.sp,
                        letterSpacing = 1.sp
                    )
                }

                // Profile chip
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.White.copy(alpha = 0.08f))
                        .clickable { navController.navigate(Routes.PROFILE) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Profile",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Entry Pass / QR Card (placeholder)
            Card(
                colors = CardDefaults.cardColors(containerColor = card),
                shape = RoundedCornerShape(22.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Entry Pass",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            Text(
                                text = "Tap to show QR",
                                color = Color.White.copy(alpha = 0.75f),
                                fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Membership: Active",
                                color = neon,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(neon.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("QR", color = neon, fontWeight = FontWeight.Black)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Today Snapshot Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // Streak card
                Card(
                    colors = CardDefaults.cardColors(containerColor = card),
                    shape = RoundedCornerShape(22.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Streak", color = Color.White.copy(alpha = 0.75f), fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "$streakDays days",
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Keep it alive.",
                            color = neon,
                            fontSize = 12.sp
                        )
                    }
                }

                // Next session card
                Card(
                    colors = CardDefaults.cardColors(containerColor = card),
                    shape = RoundedCornerShape(22.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Next", color = Color.White.copy(alpha = 0.75f), fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = nextSession,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            lineHeight = 18.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = gymStatus,
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Start Workout (main CTA)
            Button(
                onClick = { navController.navigate(Routes.WORKOUT) },
                colors = ButtonDefaults.buttonColors(containerColor = neon, contentColor = Color.Black),
                shape = RoundedCornerShape(22.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.FitnessCenter,
                    contentDescription = "Workout",
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "START WORKOUT",
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.5.sp,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Bro Message Card
            Card(
                colors = CardDefaults.cardColors(containerColor = card),
                shape = RoundedCornerShape(22.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Bro Message",
                        color = Color.White.copy(alpha = 0.75f),
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = broMsg,
                        color = Color.White,
                        fontSize = 15.sp,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        AssistChip(
                            onClick = { /* later */ },
                            label = { Text("Hype me up") },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = neon.copy(alpha = 0.14f),
                                labelColor = neon
                            ),
                            border = null
                        )
                        AssistChip(
                            onClick = { /* later */ },
                            label = { Text("Savage mode") },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = Color.White.copy(alpha = 0.08f),
                                labelColor = Color.White
                            ),
                            border = null
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
        }
    }
}

@Composable
private fun BroBottomNavBar(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    neon: Color
) {
    val barBg = Color(0xFF0B0B0F)

    val items = listOf(
        NavItem(Routes.HOME, Icons.Filled.Home, "Home"),
        NavItem(Routes.WORKOUT, Icons.Filled.FitnessCenter, "Workout"),
        NavItem(Routes.VIRTUAL, Icons.Filled.PlayArrow, "Virtual"),
        NavItem(Routes.CALENDAR, Icons.Filled.CalendarToday, "Calendar"),
        NavItem(Routes.PROFILE, Icons.Filled.Person, "Profile"),
    )


    NavigationBar(containerColor = barBg) {
        items.forEach { item ->
            val selected = currentRoute == item.route

            NavigationBarItem(
                selected = selected,
                onClick = { onNavigate(item.route) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = neon,
                    unselectedIconColor = Color.White.copy(alpha = 0.6f),
                    indicatorColor = neon.copy(alpha = 0.12f)
                ),
                label = null
            )
        }
    }
}

private data class NavItem(
    val route: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val label: String
)
