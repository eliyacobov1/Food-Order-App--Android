package postpc.ex7.myapplication

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class OrderDB(context: Context) {
    val fireStore = FirebaseFirestore.getInstance()
    private var currOrder: SandwichOrder? = null
    private var sp: SharedPreferences = context.getSharedPreferences(
        "order_info", Context.MODE_PRIVATE
    )
    private var currID: String? = sp.getString("id", null)
    private var name: String? = sp.getString("name", null)

    fun getCurrentOrder(): SandwichOrder? {
        return currOrder
    }

    fun getCurrentOrderID(): String? {
        return currID
    }

    fun getName(): String? {
        return name
    }

    fun resetCurrentOrderID(){
        currID = null
    }

    fun setOrderID(id: String?){
        currID = id
    }

    /**
     * set the current order to be the given order
     */
    fun setOrder(order: SandwichOrder?) {
        currOrder = order
    }

    /**
     * this function writes the given name into the shared preferences file
     */
    fun addName(name: String){
        sp.edit().putString("name", name).apply()
    }

    /**
     * add the given order to the database and set it as the current order
     */
    fun addOrder(order: SandwichOrder) {
        sp.edit().putString("id", order.getID()).apply()
    }

    /**
     * update the given order's fields in the database and set it to update the current order
     */
    fun editOrder(order: SandwichOrder) {
        fireStore.collection("orders").document(currID!!).set(order)
            .addOnSuccessListener {
                currOrder = order
            }
    }

    /**
     * remove the current order
     */
    fun removeOrder() {
        sp.edit().clear().apply()
        currOrder = null
    }
}