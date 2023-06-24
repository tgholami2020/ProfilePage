package com.example.profilepage

import android.app.Instrumentation.ActivityResult
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.profilepage.ui.theme.ProfilePageTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfilePageTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                   ProfileScreen()
                }
            }
        }
    }
}

@Composable
fun ProfileScreen() {
    val notification= rememberSaveable{ mutableStateOf("") }
    if (notification.value.isNotEmpty()){
        Toast.makeText(LocalContext.current,notification.value,Toast.LENGTH_LONG).show()
        notification.value= ""
    }
    var name by rememberSaveable { mutableStateOf("default value") }
    var username by rememberSaveable{ mutableStateOf("default username") }
    var bio by rememberSaveable{ mutableStateOf("default bio") }

    Column(modifier =
    Modifier
        .verticalScroll(rememberScrollState())
        .padding(8.dp)
    ) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
            Text(text = "Cancel", modifier = Modifier.clickable { notification.value ="cancelled"  })
            Text(text = "Save", modifier = Modifier.clickable { notification.value =" Profile updated" })
    }
    ProfileImage()
        //for name
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Name", modifier = Modifier.width(100.dp))
            TextField(
                value = name,
                onValueChange ={name=it},
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black
                )
                 )
        }
        //for user name
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Username", modifier = Modifier.width(100.dp))
            TextField(
                value = username,
                onValueChange ={username=it},
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black
                )
            )
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(text = "Bio", modifier = Modifier
                .width(100.dp)
                .padding(8.dp))
            TextField(value = bio,
                onValueChange = {bio=it},
                colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                textColor = Color.Black
            ),
                singleLine = false,
                modifier = Modifier.height(150.dp)
            )
        }
}
}

@Composable
fun ProfileImage(){
    val imageUrl= rememberSaveable { mutableStateOf("") }
    val painter = rememberImagePainter(
        if (imageUrl.value.isEmpty())
            R.drawable.ic_user
    else
        imageUrl.value
    )
    val laucher=
        rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()){uri: Uri?->
            uri?.let { imageUrl.value = it.toString() }
        }
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card (
            shape= CircleShape,
            modifier = Modifier
                .padding(8.dp)
                .size(100.dp)
                ){
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .wrapContentSize()
                    .clickable { laucher.launch("image/*") },
                contentScale = ContentScale.Crop
                )
        }
        Text(text = "Change profile picture")
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ProfilePageTheme {
       ProfileScreen()
    }
}