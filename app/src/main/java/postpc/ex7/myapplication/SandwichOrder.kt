package postpc.ex7.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.*

/**
 * possible status values for a Sandwich-Order
 */
const val WAITING: String = "waiting"
const val IN_PROGRESS: String = "in-progress"
const val READY: String = "ready"
const val DONE: String = "done"

class SandwichOrder(id: String?, name: String, numPickles: Int, var comment: String,
                    private var hummus: Boolean, private var tahini: Boolean
) {
    private val id: String = id?:UUID.randomUUID().toString()  // generate new id if id argument is null
    private var costumerName: String = name
    private var pickles: Int = numPickles
    private var status = WAITING
    /* getters for all of the class fields */
    fun getID(): String { return id }
    fun getCostumerName(): String { return costumerName }
    fun getPickleCount(): Int { return pickles }
    fun getStatus(): String { return status }
    fun getHummus(): Boolean { return hummus }
    fun getTahini(): Boolean { return tahini }
}