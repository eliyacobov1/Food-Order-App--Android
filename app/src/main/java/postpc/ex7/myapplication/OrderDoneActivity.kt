package postpc.ex7.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class OrderDoneActivity : AppCompatActivity() {
    var db = MainApp.getInstance().getDataBase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_done)
        val buttonDoneOrder: Button = findViewById(R.id.orderDoneButton)
        buttonDoneOrder.setOnClickListener{
            db.removeOrder()
            db.fireStore.collection("orders").document(db.getCurrentOrderID()!!).
            delete().addOnSuccessListener {
                db.resetCurrentOrderID()
                Utils.switchActivity(this@OrderDoneActivity, DONE)
            }
        }
    }
}