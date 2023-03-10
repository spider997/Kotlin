package com.example.login

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.login.data.CityResult
import com.example.login.network.CityAPI
import kotlinx.android.synthetic.main.activity_entered_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class EnteredActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entered_layout)
        supportActionBar?.hide()
        //városok felvitele listába
        val city = arrayListOf("Budapest",
                            "Debrecen",
                            "Miskolc",
                            "Nyíregyháza",
                            "Szeged")
        //létrehozott lista adapter módon való letárolása
        val adapter = ArrayAdapter(this,
                                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                                    city)

        //legördülő menü feltöltése a városok listájával
        sCity.adapter = adapter

        //bejelentkezett felhasználó nevének visszahívása, üdvözlésre
        val username = intent.getStringExtra("username")

        tvWelcome.setText("Welcome, ${username}")

        //retrofitos objektum létrehozása
        val retrofit = Retrofit.Builder()
            .baseUrl("https://wft-geo-db.p.rapidapi.com")   //json fájl eléréséhez szükséges host url link megadása
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val cityAPI = retrofit.create(CityAPI::class.java)  //gyűjtemény létrehozása a lekérdezésekhez
        var check = 0

        //kiválasztott listaelem lekérdezései
        sCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                //belépés után auto selectet tiltom manuális választásig
                if (check < 1)
                {
                    check++
                }  else
                {   //város választás után elérhetővé válik a lekérdezés eredménye
                    cvDetails.visibility = View.VISIBLE
                    tvDetails.visibility = View.VISIBLE
                    btnOpenMap.visibility = View.VISIBLE
                }

                //lekérdezésekhez szükséges query adatok megadása api-s kulccsal
                val cityCall = cityAPI.getCityDetails(5, 0, "${city[p2]}", "b2e9f8d5f8msh59247e52dcadc38p1b4415jsnee1acdfe662c")

                //lekérdezés indítása
                cityCall.enqueue(object : Callback<CityResult> {
                    override fun onResponse(call: Call<CityResult>, response: Response<CityResult>) {
                        val cityResult = response.body()

                        //térkép helymeghatározásához szükséges adatok kimentése label-ekbe
                        tvCityLatitude.setText(cityResult?.data?.get(0)?.latitude!!.toDouble().toString())
                        tvCityLongitude.setText(cityResult?.data?.get(0)?.longitude!!.toDouble().toString())

                        tvCityName.text = "City: ${cityResult?.data?.get(0)?.city}".trimIndent()    //város lekérdezésének indítása / kiíratása

                        tvCityPopulation.text = "Population: ${cityResult?.data?.get(0)?.population}".trimIndent()  //a kiválasztott város népességének lekérdezése

                        tvCityCountry.text = "Country: ${cityResult?.data?.get(0)?.country}/" +
                                            "${cityResult?.data?.get(0)?.countryCode}".trimIndent() //a kiválasztott város országának lekérdezése

                        tvCityRegion.text = "Region: ${cityResult?.data?.get(0)?.region}/" +
                                            "${cityResult?.data?.get(0)?.regionCode}".trimIndent()  //a kiválasztott város megyéjének lekérdezése

                    }

                    //hibaüzenet dobása a felhasználónak, amennyiben a lekérdezést nem tudja elindítani
                    override fun onFailure(call: Call<CityResult>, t: Throwable) {
                        tvDetails.text = t.message
                        cvDetails.visibility = View.GONE
                        tvDetails.visibility = View.VISIBLE
                        btnOpenMap.visibility = View.GONE
                    }

                })
            }

            //ha nincs kiválasztva semmi a listábó, értesítjük a felhasználót
            override fun onNothingSelected(p0: AdapterView<*>?) {
                tvDetails.setText("No selected city")
                cvDetails.visibility = View.GONE
                tvDetails.visibility = View.VISIBLE
                btnOpenMap.visibility = View.GONE
            }

        }

        //gomb megnyomásával térképet nyithatunk az adott városról
        btnOpenMap.setOnClickListener {

            //label-ekből az adatokat deklarájuk
            var chooseCityLatitude = tvCityLatitude.text
            var chooseCityLongitude = tvCityLongitude.text
            val zoom = 13

            val intent = Intent(Intent.ACTION_VIEW)

            intent.data = Uri.parse("geo:$chooseCityLatitude,$chooseCityLongitude?z=$zoom")//térkép lokálásához tartozó adatok feltöltése

            if (intent.resolveActivity(packageManager) != null)//ha nem null értékű adattal tér vissza, megnyitja a térképet
            {
                startActivity(intent)
            }

        }

        //kilépés a felületről
        btnLogOut.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)//vissza a mainActivitibe / bejelentkezés oldalra

            startActivity(intent)
            finish()

        }

    }
}