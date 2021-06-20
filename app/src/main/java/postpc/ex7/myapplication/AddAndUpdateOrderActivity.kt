package postpc.ex7.myapplication
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.google.firebase.firestore.ListenerRegistration

class AddAndUpdateOrderActivity: AppCompatActivity() {
    private lateinit var nameText : EditText
    private lateinit var comment : EditText
    private lateinit var addPickle : Button
    private lateinit var subtractPickle : Button
    private lateinit var pickleCounter : TextView
    private lateinit var hummusSwitch : SwitchCompat
    private lateinit var tahiniSwitch : SwitchCompat
    private lateinit var orderButton: Button
    var orderDocListener: ListenerRegistration? = null
    private var db = MainApp.getInstance().getDataBase()

    private fun getNumPickles(num: Int): Int{
        return when {
            num >= 10 -> 10
            num <= 0 -> 0
            else -> num
        }
    }

    /**
     * this method hides the on-screen keyboard
     */
    private fun hideSoftKeyboard(view: View) {
        val imm =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * set order field according to the last order made by the user
     */
    private fun setFieldByCurrentOrder(){
        val currentOrder = db.getCurrentOrder()
        if(currentOrder != null) {
            nameText.setText(currentOrder.getCostumerName())
            pickleCounter.text = currentOrder.getPickleCount().toString()
            hummusSwitch.isChecked = currentOrder.getHummus()
            tahiniSwitch.isChecked = currentOrder.getTahini()
            comment.setText(currentOrder.getComment())
        }
    }

    /**
     * set the add order button to function as update order button
     */
    private fun setUpdateOrderHandler() {
        orderButton.setOnClickListener{
            nameText.clearFocus()
            comment.clearFocus()
            hideSoftKeyboard(nameText)
            val updatedOrder = SandwichOrder(
                id= db.getCurrentOrderID(),
                name= nameText.text.toString(),
                numPickles= pickleCounter.text.toString().toInt(),
                hummus= hummusSwitch.isChecked,
                tahini= tahiniSwitch.isChecked,
                comment=comment.text.toString()
            )
            db.editOrder(updatedOrder)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_order)

        /* find all UI elements */
        orderButton = findViewById(R.id.newOrderButton)
        nameText = findViewById(R.id.editName)
        addPickle = findViewById(R.id.buttonAddPickle)
        subtractPickle = findViewById(R.id.buttonSubtractPickle)
        pickleCounter = findViewById(R.id.pickleCounter)
        hummusSwitch = findViewById(R.id.hummusSwitch)
        tahiniSwitch = findViewById(R.id.tahiniSwitch)
        comment = findViewById(R.id.editComment)

        /* restore fields of last application run */
        if(db.getCurrentOrderID() != null) setFieldByCurrentOrder()

        /* define on click listeners */
        addPickle.setOnClickListener{
            val numPickles = pickleCounter.text.toString().toInt()
            pickleCounter.text = (getNumPickles(numPickles+1)).toString()
        }

        if(db.getName() != null) nameText.setText(db.getName())

        subtractPickle.setOnClickListener{
            val numPickles = pickleCounter.text.toString().toInt()
            pickleCounter.text = (getNumPickles(numPickles-1)).toString()
        }

        if(db.getCurrentOrder() != null){ // if previous order was loaded
            orderButton.text = "Update Order"
            if(orderDocListener == null) orderDocListener = Utils.setDocListener(
                this@AddAndUpdateOrderActivity, currentStatus=WAITING
            )
            setUpdateOrderHandler()
        }
        else{
            /* create a new order */
            orderButton.setOnClickListener {
                if(nameText.text.toString() == ""){
                    Toast.makeText(this, "please enter a costumer name",
                        Toast.LENGTH_LONG).show()
                }

                if(db.getName() == null) db.addName(nameText.text.toString())
                nameText.clearFocus()
                comment.clearFocus()
                hideSoftKeyboard(nameText)
                val newOrder = SandwichOrder(
                    name= nameText.text.toString(),
                    numPickles= pickleCounter.text.toString().toInt(),
                    hummus= hummusSwitch.isChecked,
                    tahini= tahiniSwitch.isChecked,
                    comment=comment.text.toString()
                )
                orderButton.text = "Update Order"
                db.fireStore.collection("orders").document(newOrder.getID()).set(newOrder)
                    .addOnSuccessListener {
                        db.setOrder(newOrder)
                        db.setOrderID(newOrder.getID())
                        db.addOrder(newOrder)
                        nameText.isEnabled = false
                        if(orderDocListener == null) orderDocListener = Utils.setDocListener(
                            this@AddAndUpdateOrderActivity, currentStatus=WAITING
                        )
                        setUpdateOrderHandler()
                    }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        orderDocListener?.remove()
    }
}