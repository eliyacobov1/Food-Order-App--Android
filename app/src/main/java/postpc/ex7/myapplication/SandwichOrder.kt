package postpc.ex7.myapplication

import java.util.*

/**
 * possible status values for a Sandwich-Order
 */
const val WAITING: String = "waiting"
const val IN_PROGRESS: String = "in-progress"
const val READY: String = "ready"
const val DONE: String = "ready"

class SandwichOrder(id: String?, name: String, numPickles: Int, comment:String?, hummus: Boolean, tahini: Boolean) {
    val id: String = id?:UUID.randomUUID().toString()  // generate new id if id argument is null
    var costumerName: String = name
    var pickles: Int = numPickles
    var hummus: Boolean = hummus
    var tahini: Boolean = tahini
    var comment: String? = comment
    var status: String = WAITING
}