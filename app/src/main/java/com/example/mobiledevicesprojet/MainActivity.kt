package com.example.mobiledevicesprojet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobiledevicesprojet.ui.theme.MobileDevicesProjetTheme

data class Players(val name: String, val favourites: String, val description: String, val position: String, val image: Int, val age: Int)

object CartList {
    var cartItems: MutableList<Players> = mutableListOf()
}

object playerDetails {
    var playerDetails1: MutableList<Players> = mutableListOf()
}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Achivements : Screen("Achivements")
    object Favourite : Screen("Favourite")
    object PlayerDetails : Screen("PlayerDetails")
}

data class NavItem(
    var label: String,
    var icon: ImageVector,
    val screen: Screen
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileDevicesProjetTheme {
                MainFunction()
            }
        }
    }
}

@Composable
fun MainFunction() {
    val navController = rememberNavController()
    val navItemList = listOf(
        NavItem(label = "Home", icon = Icons.Default.Home, screen = Screen.Home),
        NavItem(label = "Favourites", icon = Icons.Default.Settings, screen = Screen.Achivements),
        NavItem(label = "Achivements", icon = Icons.Default.Notifications, screen = Screen.Favourite)
    )

    var selectedIndex by rememberSaveable { mutableStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomAppBar {
                NavigationBar {
                    navItemList.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedIndex == index,
                            onClick = {
                                selectedIndex = index
                                navController.navigate(item.screen.route)
                            },
                            icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                            label = { Text(text = item.label) }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navController = navController)
            }
            composable(Screen.Achivements.route) {
                FavouritesScreen()
            }
            composable(Screen.Favourite.route) {
                AchievementsScreen()
            }
            composable(Screen.PlayerDetails.route) {
                PlayerDetailScreen(navController = navController)
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController) {
    val products = listOf(
        Players("Erling Haaland", "-", "Born in Leeds, England. Joined the team in 2022.", "Forward",R.drawable.haaland,24),
        Players("Kevin De Bruyne", "-", "Born in Drongen, Belgium. Joined the team in 2015.", "Midfielder", R.drawable.kevin,33),
        Players("Phil Foden", "-", "Born in Stockport, England. Joined the team in 2017.", "Midfielder", R.drawable.phil,24),
        Players("Savihno", "-", "Born in Belo Horizonte, Brazil. Joined the team in 2023.", "Forward", R.drawable.savihno,20),
        Players("Rodri", "-", "Born in Madrid, Spain. Joined the team in 2019.", "Midfielder", R.drawable.rodri,28),
        Players("Ilkay Gundogan", "-", "Born in Gelsenkirchen, Germany. Joined the team in 2016.", "Midfielder", R.drawable.illkay,34),
        Players("Jack Grealish", "-", "Born in Birmingham, England. Joined the team in 2021.", "Midfielder", R.drawable.jack,29),
        Players("Jeremy Doku", "-", "Born in Borgerhout, Belgium. Joined the team in 2023.", "Forward", R.drawable.jeremy,22),
        Players("Kyle Walker", "-", "Born in Sheffield, England. Joined the team in 2017.", "Defender", R.drawable.kyle,34),
        Players("Ederson", "-", "Born in Osasco, Brazil. Joined the team in 2017.", "Goalkeeper", R.drawable.endersosn,31)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.mancity),
            contentDescription = "Manchester City Logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    text = "Manchester City Players",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0288D1),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
            items(products) { player ->
                ProductItem(player = player, navController = navController)
            }
        }
    }
}


@Composable
fun ProductItem(player: Players, navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = player.image),
                contentDescription = player.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = player.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(text = player.position, fontSize = 16.sp)
                Text(text = "Age: ${player.age}", fontSize = 16.sp, color = Color.Black)

            }

            TextButton(onClick = {
                CartList.cartItems.add(player)


            }) {
                Text(text = "❤\uFE0F")
            }
            TextButton(onClick = {
                playerDetails.playerDetails1.clear()
                playerDetails.playerDetails1.add(player)
                navController.navigate(Screen.PlayerDetails.route)

            }) {

                Text(text = "Details")
            }
        }
    }
}
@Composable
fun FavouritesScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
    ) {

        Image(
            painter = painterResource(id = R.drawable.mancity),
            contentDescription = "Manchester City Logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )


        if (CartList.cartItems.isEmpty()) {
            Text(
                text = "NO FAVOURITES",
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(CartList.cartItems) { player ->
                    CartItem(player = player, onRemove = {
                        CartList.cartItems.remove(player)
                    })
                }
            }
        }
    }
}
@Composable
fun CartItem(player: Players, onRemove: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = player.image),
                contentDescription = player.name,
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 16.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(text = player.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(text = player.position, fontSize = 16.sp)
            }

            TextButton(onClick = onRemove) {
                Text(text = "Remove")
            }
        }
    }
}

@Composable
fun AchievementsScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.mancity),
            contentDescription = "Manchester City Logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()

                .alpha(0.8f)
                .padding(16.dp)
        ) {
            Text(
                text = "Manchester City Achievements",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0288D1),
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Text(text = "1937: First League Title - Won the First Division Title for the first time.\n", color = Color.White)
            Text(text = "1969: FA Cup Victory - Won the FA Cup for the fourth time.\n", color = Color.White)
            Text(text = "2012: Premier League Title - Dramatic last-minute goal to win first Premier League title.\n", color = Color.White)

            Spacer(modifier = Modifier.height(230.dp))

            Text(text = "2019: Domestic Treble - Premier League, FA Cup, and League Cup in one season.\n", color = Color.White)
            Text(text = "2023: UEFA Champions League - First-ever Champions League title.\n", color = Color.White)
            Text(text = "2023: Historic Treble - Premier League, FA Cup, and Champions League in the same season.\n", color = Color.White)
        }
    }
}

@Composable
fun PlayerDetailScreen(navController: NavController) {
    val player = playerDetails.playerDetails1.firstOrNull()

    Box(
    ) {
        Image(
            painter = painterResource(id = R.drawable.mancity),
            contentDescription = "Manchester City Logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            player?.let {
                Card(
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Player Image
                        Image(
                            painter = painterResource(id = player.image),
                            contentDescription = player.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(150.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = player.name,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0288D1),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Position: ${player.position}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()

                        )
                        Text(
                            text = "Description: ${player.description}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        TextButton(
                            onClick = {
                                playerDetails.playerDetails1.clear()
                                navController.popBackStack()
                            },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = Color(0xFF0288D1)
                            )
                        ) {
                            Text(text = "Back")
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMainFunction() {
    MobileDevicesProjetTheme {
        MainFunction()
    }
}
