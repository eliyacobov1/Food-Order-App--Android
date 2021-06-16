package postpc.ex7.myapplication

import android.app.Application
import com.google.firebase.FirebaseApp

class MainApp: Application() {
    private lateinit var db: OrderDB

    override fun onCreate() {
        super.onCreate()
        instance = this
        FirebaseApp.initializeApp(this)
        db = OrderDB(this)
    }

    fun getDataBase(): OrderDB {
        return db
    }

    companion object { // singleton class
        private lateinit var instance: MainApp
        fun getInstance(): MainApp{
            return instance
        }
    }
}