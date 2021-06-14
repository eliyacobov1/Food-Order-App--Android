package postpc.ex7.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class OrderDB {
    private var orderList: HashMap<String, SandwichOrder> = HashMap()

    fun getOrderStatusLiveData(id: String): LiveData<SandwichOrder?> {
        return MutableLiveData(orderList[id]) // TODO
    }

    fun addOrder(customerName: String, numPickles: Int, comment: String, hummus: Boolean, tahini: Boolean){
        val order = SandwichOrder(id = null, name=customerName, numPickles=numPickles, hummus=hummus,
            comment=comment, tahini=tahini)
        orderList[order.getID()] = order
    }

    fun editOrder(id: String, newNumPickles: Int?, newHummusValue: Boolean?, newTahiniValue: Boolean?,
                  newComment: String?){
        val order = orderList[id] ?: return
        orderList.remove(id)
        val editedOrder = SandwichOrder(id=order.getID(), name=order.getCostumerName(),
            numPickles=newNumPickles?:order.getPickleCount(), hummus=newHummusValue?:order.hummus,
            tahini=newTahiniValue?:order.tahini, comment=newComment?:order.comment)
        orderList[order.getID()] = editedOrder
    }

    fun deleteOrder(id: String){
        orderList.remove(id)
    }
}