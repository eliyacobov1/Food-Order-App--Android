package postpc.ex7.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class OrderDoneActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_done)
        MainApp.getInstance().getDataBase().removeOrder()
    }
}