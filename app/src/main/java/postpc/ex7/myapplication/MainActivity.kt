package postpc.ex7.myapplication
import android.os.Bundle
import android.util.Log
import kotlin.concurrent.schedule
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    var db = MainApp.getInstance().getDataBase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val currOrderID = db.getCurrentOrderID()

        Timer("SettingUp", false).schedule(2500) {
            if (currOrderID != null) { /* if an order was already pending */
                db.fireStore.collection("orders").document(currOrderID).get()
                    .addOnSuccessListener {
                        if (it != null) db.setOrder(it.toObject(SandwichOrder::class.java))
                        Utils.switchActivity(this@MainActivity, db.getCurrentOrder()?.getStatus())
                    }
                    .addOnFailureListener {
                        db.resetCurrentOrderID()
                        db.removeOrder()
                        Log.d(
                            "firebase_err",
                            it.message ?: "failed to retrieve previously created order"
                        )
                        Utils.switchActivity(this@MainActivity)
                    }
            } else {
                Utils.switchActivity(this@MainActivity)
            }
        }
    }
}