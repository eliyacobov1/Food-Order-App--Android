package postpc.ex7.myapplication

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class OrderDB(private var context: Context) {
    private val fireStore = FirebaseFirestore.getInstance()
    private var currOrder: SandwichOrder? = null
    private var sp: SharedPreferences = context.getSharedPreferences(
        "order_info", Context.MODE_PRIVATE
    )

    init {
        initializeFromSp()
    }

    private fun initializeFromSp(){
        val orderID: String = sp.getString("id", null)?:return
        fireStore.collection("orders").document(orderID).get()
            .addOnSuccessListener {
                currOrder = it.toObject(SandwichOrder:: class.java)
                if(currOrder == null) sp.edit().remove("id").apply()
            }.addOnCanceledListener {
            }.addOnFailureListener{
            }
    }

    fun getCurrOrder(): SandwichOrder?{
        return currOrder
    }

    fun addOrder(order: SandwichOrder){
        sp.edit().putString("id", order.getID()).apply()
        fireStore.collection("orders").add(order)
            .addOnSuccessListener {
                currOrder = order
            }
    }

    fun editOrder(order: SandwichOrder){
        fireStore.collection("orders").document(order.getID()).set(order)
            .addOnSuccessListener {
                currOrder = order
            }
    }

    /**
     * remove the current order
     */
    fun removeOrder() {
        sp.edit().remove("id").apply()
    }

//    fun editOrder(id: String, newNumPickles: Int?, newHummusValue: Boolean?, newTahiniValue: Boolean?,
//                  newComment: String?){
//        val order = orderList[id] ?: return
//        orderList.remove(id)
//        val editedOrder = SandwichOrder(id=order.getID(), name=order.getCostumerName(),
//            numPickles=newNumPickles?:order.getPickleCount(), hummus=newHummusValue?:order.hummus,
//            tahini=newTahiniValue?:order.tahini, comment=newComment?:order.comment)
//        orderList[order.getID()] = editedOrder
//    }

//    fun deleteOrder(id: String){
//        orderList.remove(id)
//    }
}