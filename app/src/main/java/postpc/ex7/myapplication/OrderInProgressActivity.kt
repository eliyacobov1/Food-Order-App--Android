package postpc.ex7.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.ListenerRegistration

class OrderInProgressActivity : AppCompatActivity() {
    var orderDocListener: ListenerRegistration? = Utils.setDocListener(
        this@OrderInProgressActivity, currentStatus=IN_PROGRESS
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_in_progress)
    }

    override fun onDestroy() {
        super.onDestroy()
        orderDocListener!!.remove()
    }
}