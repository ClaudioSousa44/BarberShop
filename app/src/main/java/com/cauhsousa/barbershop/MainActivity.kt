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
import com.cauhsousa.barbershop.repository.UserRepository
import com.cauhsousa.barbershop.ui.theme.BarberShopTheme
import com.cauhsousa.barbershop.ui.theme.Purple200
import com.cauhsousa.barbershop.ui.theme.Purple500
import com.cauhsousa.barbershop.ui.theme.Purple700

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BarberShopTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BarberShopScreen()
                }
            }
        }
    }
}

@Composable
fun BarberShopScreen(){
    val context = LocalContext.current
    var nameStatus by remember {
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
        modifier = Modifier.background(Color(18, 18, 24, 255)),
        horizontalAlignment = Alignment.CenterHorizontally
            ) {
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color =  Color(255, 182, 49, 150),
            modifier = Modifier.padding(top = 50.dp)
        )

        Spacer(modifier = Modifier.height(50.dp))
        
        Image(
            painter = painterResource(id = R.drawable.cabelo_de_homem),
            contentDescription = stringResource(R.string.logo)
        )

        Spacer(modifier = Modifier.height(50.dp))



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
                      authenticate(emailStatus,passwordStatus,context)
                      },
            colors = ButtonDefaults.buttonColors(Purple700)
        ) {
            Text(text = stringResource(R.string.login))
        }
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.nao_possui_conta),
                color = Color.White
            )
            TextButton(
                onClick = {
                    var openSingUp = Intent(context, SingUpActivity::class.java)
                    context.startActivity(openSingUp)
                }) {
                Text(
                    text = stringResource(R.string.registrarse),
                    color = Color(255, 182, 49, 255)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    BarberShopTheme {
        BarberShopScreen()
    }
}

fun authenticate(email: String, password: String, context: Context){
    val userRepository = UserRepository(context)
    val user = userRepository.authenticate(
        email,
        password
    )

    if(user != null){
        var openHome = Intent(context, HomeActivity::class.java)
        context.startActivity(openHome)
    }else{
        Toast.makeText(
            context,
            "Usuário não existente",
            Toast.LENGTH_LONG
        ).show()
    }
}
