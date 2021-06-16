package postpc.ex7.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer

class OrderInProgressActivity : AppCompatActivity() {
    private lateinit var order: SandwichOrder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_in_progress)

        val orderID = intent.getStringExtra("order_id")
//        if(orderID != null){
//            var orderStatusLiveData = MainApp.instance.getDataBase().getOrderStatusLiveData(orderID)
//            orderStatusLiveData.observe(this, {
//                // TODO
//            })
//        }

    }
}