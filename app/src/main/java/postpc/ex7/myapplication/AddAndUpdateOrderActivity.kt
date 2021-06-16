package postpc.ex7.myapplication
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat

class AddAndUpdateOrderActivity: AppCompatActivity() {
    private lateinit var nameText : EditText
    private var comment : EditText? = null
    private lateinit var addPickle : Button
    private lateinit var subtractPickle : Button
    private lateinit var pickleCounter : TextView
    private lateinit var hummusSwitch : SwitchCompat
    private lateinit var tahiniSwitch : SwitchCompat
    private lateinit var orderButton: Button
    private var db = MainApp.getInstance().getDataBase()

    private fun getNumPickles(num: Int): Int{
        return when {
            num >= 10 -> 10
            num <= 0 -> 0
            else -> num
        }
    }

    /**
     * set order field according to the last order made by the user
     */
    private fun setFieldByCurrentOrder(){
        val currentOrder = db.getCurrOrder()
        Log.d("msg", "$currentOrder")
        if(currentOrder != null) {
            nameText.setText(currentOrder.getCostumerName())
            pickleCounter.setText(currentOrder.getPickleCount())
            hummusSwitch.isChecked = currentOrder.getHummus()
            tahiniSwitch.isChecked = currentOrder.getTahini()
        }
    }

    /**
     * set the add order button to function as update order button
     */
    private fun setUpdateOrderHandler() {
        orderButton.setOnClickListener{
            val updatedOrder = SandwichOrder(
                id= db.getCurrOrder()?.getID(),
                name= nameText.text.toString(),
                numPickles= pickleCounter.text.toString().toInt(),
                hummus= hummusSwitch.isChecked,
                tahini= tahiniSwitch.isChecked,
                comment=comment?.text?.toString()?:""
            )
            db.editOrder(updatedOrder)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_order)

        /* find all UI elements */
        orderButton = findViewById<Button>(R.id.newOrderButton)
        nameText = findViewById(R.id.editName)
        addPickle = findViewById(R.id.buttonAddPickle)
        subtractPickle = findViewById(R.id.buttonSubtractPickle)
        pickleCounter = findViewById(R.id.pickleCounter)
        hummusSwitch = findViewById(R.id.hummusSwitch)
        tahiniSwitch = findViewById(R.id.tahiniSwitch)
//        comment = findViewById<EditText>(R.id.comm)

        /* define on click listeners */
        addPickle.setOnClickListener{
            val numPickles = pickleCounter.text.toString().toInt()
            pickleCounter.text = (getNumPickles(numPickles+1)).toString()
        }

        subtractPickle.setOnClickListener{
            val numPickles = pickleCounter.text.toString().toInt()
            pickleCounter.text = (getNumPickles(numPickles-1)).toString()
        }

        /* create a new order */
        orderButton.setOnClickListener {
            val newOrder = SandwichOrder(
                id=null,
                name= nameText.text.toString(),
                numPickles= pickleCounter.text.toString().toInt(),
                hummus= hummusSwitch.isChecked,
                tahini= tahiniSwitch.isChecked,
                comment=comment?.text?.toString()?:""
            )
            orderButton.text = "Update Order"
            db.addOrder(newOrder)
            setUpdateOrderHandler()
        }
        setFieldByCurrentOrder()
    }
}