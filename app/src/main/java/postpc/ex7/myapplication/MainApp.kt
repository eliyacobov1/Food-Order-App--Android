package postpc.ex7.myapplication

import android.app.Application

class MainApp: Application() {
    private var db: OrderDB = OrderDB()
    fun getDataBase(): OrderDB {
        return db
    }

    companion object { // singleton implementation
        var instance: MainApp = MainApp()
    }
}