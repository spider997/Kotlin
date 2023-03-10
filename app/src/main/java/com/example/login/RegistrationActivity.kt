package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.example.login.data.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_registration_layout.*
private val users = ArrayList<User>()
class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_layout)
        supportActionBar?.hide()

        //regisztráció indítása
        btnRegistrationSave.setOnClickListener {

            val etUserNameSave = etUsernameSave.text.toString()
            val etPassWordSave = etPasswordSave.text.toString()
            val etPassWordAgainSave = etPasswordAgainSave.text.toString()

            //felhasználó név mező ürességének visgálata
            if (TextUtils.isEmpty(etUserNameSave)) {

                etUsernameSave.setError("Please enter username")

                //jelszó mező ürességének visgálata
            } else if (TextUtils.isEmpty(etPassWordSave)) {

                etPasswordSave.setError("Please enter password")

                //jelszó megerősítő mező ürességének visgálata
            } else if (TextUtils.isEmpty(etPassWordAgainSave)) {

                etPasswordAgainSave.setError("Please enter confirmatory password")

                //megadott jelszavak egyezésének vizsgálata
            } else if (!etPassWordSave.equals(etPassWordAgainSave)) {

                etPasswordAgainSave.setError("Passwords do not match")

            } /*else if (etUserNameSave.isNotEmpty()){

                for (i in users.indices)
                {

                    if (users[i].username.equals(etUsername)){

                        etUsernameSave.setError("This username already exists")

                    }

                }
            //ha minden mező ki lett töltve továbbítódik az adat a MainActivity-be, és vissza is lép oda
            }*/else {

                val intent = Intent(this, MainActivity::class.java)

                intent.putExtra("usernameSave", etUserNameSave) //megadott adatok továbbítása a main-be
                intent.putExtra("passwordSave", etPassWordSave)

                startActivity(intent)
                finish()

            }
        }
        //visszalépés a bejelentkezés oldalra
        btnBack.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
            finish()

        }
    }
}