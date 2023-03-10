package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.example.login.data.User
import kotlinx.android.synthetic.main.activity_main.*

private val users = ArrayList<User>()

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()    //fejléc elrejtése

        //beégetett felhasználó be egyszeri bevitele a listába
        if (users.size == 0) {

            users.add(User(1, "admin", "admina"))

        }

        var login = false

        //bejelentkezés indítása

        btnLogin.setOnClickListener {

            val etUserName = etUsername.text.toString()
            val etPassWord = etPassword.text.toString()

            //felhasználó név mező ürességének visgálata
            if (TextUtils.isEmpty(etUserName))
            {

                etUsername.setError("Please enter username")

                //jelszó mező ürességének visgálata
            } else if (TextUtils.isEmpty(etPassWord))
            {

                etPassword.setError("Please enter password")

                //lista vizsgálata, létezik e a megadott felhasználó név - jelszó páros
            } else if (!TextUtils.isEmpty(etPassWord) && !TextUtils.isEmpty(etUserName))
            {

                for (i in users.indices){

                    if (etUserName.equals(users[i].username) && etPassWord.equals(users[i].password)){

                       login = true

                        break

                    }

                }

            }
            //ha vizsgálat után kiderül, hogy létezik a felhasználónk, akkor tovább léphet az új felületre
            if (login)
            {
                login = false

                val intent = Intent(this, EnteredActivity::class.java)   //új Activity deklarálása / meghívása
                intent.putExtra("username", etUserName)     //a másik Activitire továbbítom a felhasználó nevét, hogy üdvözölhessem

                startActivity(intent)
                finish()

                //rossz felhasználó név -és jelszó párosítással nem engedi tovább a felhasználót
            }   else
            {

                etPassword.setError("Wrong username or password")

            }
        }

        //amennyiben a felhasználó regisztrálni szeretne, a gomb megnyomása után tujda megtenni
        btnRegistration.setOnClickListener {

            val intent = Intent(this, RegistrationActivity::class.java) //regisztrációs Activity meghívása

            startActivity(intent)   //regisztrációs Activity indítása
            finish()                //jelenlegi Activity leállítása

        }


        //a regisztrációból származó adatok visszahívása
        val usernameSave = intent.getStringExtra("usernameSave").toString()
        val passwordSave = intent.getStringExtra("passwordSave").toString()

        //ha nem null értékű a kapott felhasználói adatok, akkor hozzáadom a listához
        if (usernameSave != null){

            users.add(User(users.size, usernameSave, passwordSave))

        }

    }
}