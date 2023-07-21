package com.cauhsousa.barbershop

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cauhsousa.barbershop.model.User
import com.cauhsousa.barbershop.repository.UserRepository
import com.cauhsousa.barbershop.ui.theme.BarberShopTheme
import com.cauhsousa.barbershop.ui.theme.Purple700

class SingUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BarberShopTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SingUpScreen()
                }
            }
        }
    }
}

@Composable
fun SingUpScreen() {
    val context = LocalContext.current
    var nameStatus by remember {
        mutableStateOf("")
    }
    var phoneStatus by remember {
        mutableStateOf("")
    }
    var emailStatus by remember {
        mutableStateOf("")
    }
    var passwordStatus by remember {
        mutableStateOf("")
    }
    var passwordVisibilityState by remember {
        mutableStateOf(false)
    }

    Column (
        modifier = Modifier
            .background(Color(18, 18, 24, 255))
            .verticalScroll(rememberScrollState())
            ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Spacer(modifier = Modifier.height(50.dp))

        Image(
            painter = painterResource(id = R.drawable.cabelo_de_homem),
            contentDescription = stringResource(R.string.logo)
//            ,
//            modifier = Modifier.height(60.dp)
        )

        Spacer(modifier = Modifier.height(50.dp))
        
        Text(
            text = "Registrar",
            color = Color(255, 182, 49, 255),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value =  nameStatus,
            onValueChange = {nameStatus = it},
            placeholder = {
                Text(
                    text = stringResource(R.string.nome_perfil)
                )
            },
            modifier = Modifier.background(Color.White)
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value =  phoneStatus,
            onValueChange = {phoneStatus = it},
            placeholder = {
                Text(
                    text = "(**) *****-****"
                )
            },
            modifier = Modifier.background(Color.White)
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value =  emailStatus,
            onValueChange = {emailStatus = it},
            placeholder = {
                Text(
                    text = stringResource(R.string.email)
                )
            },
            modifier = Modifier.background(Color.White)
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value =  passwordStatus,
            onValueChange = {passwordStatus = it},
            placeholder = {
                Text(
                    text = stringResource(R.string.senha)
                )
            },
            modifier = Modifier.background(Color.White),
            trailingIcon = {
                IconButton(
                    onClick = {
                        passwordVisibilityState = !passwordVisibilityState
                    }
                ) {
                    Icon(
                        imageVector = if(passwordVisibilityState)
                            Icons.Default.VisibilityOff
                        else
                            Icons.Default.Visibility,
                        contentDescription = stringResource(R.string.icone_olho)

                    )
                }
            },
            visualTransformation = if(!passwordVisibilityState)
                PasswordVisualTransformation()
            else
                VisualTransformation.None
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                      userSave(context,emailStatus, nameStatus, phoneStatus, passwordStatus)
                      },
            colors = ButtonDefaults.buttonColors(Purple700)
        ) {
            Text(text = stringResource(R.string.registrar))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.ja_possui_conta),
                color = Color.White
            )
            TextButton(
                onClick = {
                    var openMain = Intent(context, MainActivity::class.java)
                    context.startActivity(openMain)
                }) {
                Text(
                    text = stringResource(id = R.string.fazer_login) ,
                    color = Color(255, 182, 49, 255),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview2() {
    BarberShopTheme {
        SingUpScreen()
    }
}



fun userSave(context: Context, email: String, userName: String, phone: String, password: String) {
    val userRepository = UserRepository(context)

    //Recuperando o usuario que tenha o email informado
    var user = userRepository.findUserByEmail(email)

    //Se user for  null, gravamos o novo usuario, senão, avisamos que o usuário ja existe
    if(user == null){
        val newUser = User(
            userName = userName,
            phone = phone,
            email = email,
            password = password

        )
        val id = userRepository.save(newUser)
        Toast.makeText(
            context,
            "User created #$id",
            Toast.LENGTH_LONG
        ).show()
    }else{
        Toast.makeText(
            context,
            "User already exists!!",
            Toast.LENGTH_LONG
        ).show()
    }
}