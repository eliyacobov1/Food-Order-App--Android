package postpc.ex7.myapplication

import com.google.firebase.firestore.DocumentId
import java.util.*

/**
 * possible status values for a Sandwich-Order
 */
const val WAITING: String = "waiting"
const val IN_PROGRESS: String = "in-progress"
const val READY: String = "ready"

class SandwichOrder(id: String?=null, name: String="", numPickles: Int=0, var comment: String="",
                    private var hummus: Boolean=false, private var tahini: Boolean=false
) {
    @DocumentId
    private val documentId: String = id?:UUID.randomUUID().toString()  // generate new id if id argument is null
    private var costumerName: String = name
    private var pickles: Int = numPickles
    private var status = WAITING
    /* getters and setters */
    fun getID(): String { return documentId }
    fun getCostumerName(): String { return costumerName }
    fun getPickleCount(): Int { return pickles }
    fun getStatus(): String { return status }
    fun getHummus(): Boolean { return hummus }
    fun getTahini(): Boolean { return tahini }
}