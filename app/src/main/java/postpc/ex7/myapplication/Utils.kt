package postpc.ex7.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.firebase.firestore.ListenerRegistration

class Utils {
    companion object{
        /**
         * this function navigates the user to one of the application's activities depending on what the given status is
         */
        fun switchActivity(context: Context, status: String? = null) {
            val nextActivityIntent = Intent(context, when(status) {
                null, WAITING -> AddAndUpdateOrderActivity::class.java
                IN_PROGRESS -> OrderInProgressActivity::class.java
                else -> OrderDoneActivity::class.java
            })
            context.startActivity(nextActivityIntent)
            (context as Activity).finish()
        }

        /**
         * this function sets a listener for the current order,
         * assigned to the activity represented by the given context
         */
        fun setDocListener(context: Context, currentStatus: String?): ListenerRegistration? {
            val db = MainApp.getInstance().getDataBase()
            val orderID = db.getCurrentOrderID()
            if(orderID != null) {
                return db.fireStore.collection("orders").document(orderID)
                    .addSnapshotListener { data, error ->
                        when {
                            error != null -> Log.d("err_msg", error.message.toString())
                            data == null -> Log.d("err_msg", "data is null")
                            else -> {
                                val currOrder = data.toObject(SandwichOrder::class.java)
                                val status = currOrder?.getStatus()
                                if (((currentStatus == WAITING || currentStatus == null)
                                            && (status == IN_PROGRESS || status == READY))
                                    || (currentStatus == IN_PROGRESS && status == READY))
                                    switchActivity(context, status)
                            }
                        }
                    }
            }
            else {
                return null
            }
        }
    }
}