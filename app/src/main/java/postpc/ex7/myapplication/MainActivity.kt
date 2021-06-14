package postpc.ex7.myapplication
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var db = MainApp.instance.db
    var order: SandwichOrder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Thread.sleep(5_000)
        if(order == null) {
            val nextActivityIntent = Intent(this, AddOrderActivity::class.java)
            startActivity(nextActivityIntent)
            finish()
        }
    }
}