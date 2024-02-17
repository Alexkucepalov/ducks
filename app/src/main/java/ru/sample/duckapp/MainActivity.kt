package ru.sample.duckapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.awaitResponse
import ru.sample.duckapp.infra.Api


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_click_me = findViewById(R.id.button) as Button
        btn_click_me.setOnClickListener {
            val scope = CoroutineScope(Job() + Dispatchers.Main)
            scope.launch (Dispatchers.Main)
            {
                drawImage()
            }
        }

        val get_by_id = findViewById(R.id.getById) as Button
        get_by_id.setOnClickListener {
            var myImageView : ImageView = findViewById(R.id.imageView)
            var inputNum : EditText = findViewById(R.id.inputNum)
            Picasso.get().load("https://random-d.uk/api/" + inputNum.text + ".jpg").into(myImageView)
        }
    }
    suspend fun drawImage() {
        var response = Api.ducksApi.getRandomDuck().awaitResponse()

        var myImageView : ImageView = findViewById(R.id.imageView)
        Picasso.get().load(response.body()?.url).into(myImageView)
    }
}

