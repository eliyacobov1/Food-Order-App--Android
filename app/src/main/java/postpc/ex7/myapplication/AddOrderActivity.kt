package postpc.ex7.myapplication
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.Observer

class AddOrderActivity: AppCompatActivity() {
    private var nameText : EditText? = null
    private var comment : EditText? = null
    private var addPickle : Button? = null
    private var subtractPickle : Button? = null
    private var pickleCounter : TextView? = null
    private var hummusSwitch : SwitchCompat? = null
    private var tahiniSwitch : SwitchCompat? = null

    private fun getNumPickles(num: Int): Int{
        return when {
            num >= 10 -> 10
            num <= 0 -> 0
            else -> num
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_order)

        /* find all UI elements */
        val orderButton = findViewById<Button>(R.id.newOrderButton)
        nameText = findViewById(R.id.editName)
        addPickle = findViewById(R.id.buttonAddPickle)
        subtractPickle = findViewById(R.id.buttonSubtractPickle)
        pickleCounter = findViewById(R.id.pickleCounter)
        hummusSwitch = findViewById(R.id.hummusSwitch)
        tahiniSwitch = findViewById(R.id.tahiniSwitch)
//        comment = findViewById<EditText>(R.id.comm)

        /* define on click listeners */
        addPickle!!.setOnClickListener{
            val numPickles = pickleCounter!!.text.toString().toInt()
            pickleCounter!!.text = (getNumPickles(numPickles+1)).toString()
        }

        subtractPickle!!.setOnClickListener{
            val numPickles = pickleCounter!!.text.toString().toInt()
            pickleCounter!!.text = (getNumPickles(numPickles-1)).toString()
        }

        orderButton.setOnClickListener {
            val newOrder = SandwichOrder(
                id=null,
                name=nameText!!.text.toString(),
                numPickles=pickleCounter!!.text.toString().toInt(),
                hummus=hummusSwitch!!.isChecked,
                tahini=tahiniSwitch!!.isChecked,
                comment=comment?.text?.toString()?:""
            )
            orderButton.text = "Update Order"
            MainApp.instance.getDataBase().getOrderStatusLiveData(newOrder.getID()).observe(
                this, {
                    val nextActivityIntent = Intent(this, OrderInProgressActivity::class.java)
                    nextActivityIntent.putExtra("order_id", newOrder.getID())
                    finish()
                    }
            )
        }
    }
}