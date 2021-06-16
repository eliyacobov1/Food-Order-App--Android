package postpc.ex7.myapplication
import android.content.Intent
import android.os.Bundle
import kotlin.concurrent.schedule
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    var db = MainApp.getInstance().getDataBase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val currOrder = db.getCurrOrder()
        Timer("SettingUp", false).schedule(2500) {
            val nextActivityIntent = Intent(this@MainActivity, when{
                currOrder == null || currOrder.getStatus() == WAITING -> AddAndUpdateOrderActivity::class.java
                currOrder.getStatus() == IN_PROGRESS -> OrderInProgressActivity::class.java
                else -> OrderDoneActivity::class.java
            })
            startActivity(nextActivityIntent)
            finish()
        }
    }
}