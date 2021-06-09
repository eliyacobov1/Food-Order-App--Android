package postpc.ex7.myapplication

class OrderDB {
    var orderList: HashMap<String, SandwichOrder> = HashMap()

    fun addOrder(customerName: String, numPickles: Int, comment: String?, hummus: Boolean, tahini: Boolean){
        val order = SandwichOrder(id = null, name=customerName, numPickles=numPickles, hummus=hummus,
            comment=comment, tahini=tahini)
        orderList[order.id] = order
    }

    fun editOrder(id: String, newNumPickles: Int?, newHummusValue: Boolean?, newTahiniValue: Boolean?,
                  newComment: String?){
        val order = orderList[id] ?: return
        orderList.remove(id)
        val editedOrder = SandwichOrder(id=order.id, name=order.costumerName,
            numPickles=newNumPickles?:order.pickles, hummus=newHummusValue?:order.hummus,
            tahini=newTahiniValue?:order.tahini, comment=newComment?:order.comment)
        orderList[order.id] = editedOrder
    }

    fun deleteOrder(id: String){
        orderList.remove(id)
    }
}