package com.cauhsousa.barbershop.DAO

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cauhsousa.barbershop.model.User

@Database(entities = [User::class], version = 1)
abstract class BarberShopDB: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object{
        private lateinit var instanceDb: BarberShopDB
        fun getDataBase(context: Context): BarberShopDB{
            if(!::instanceDb.isInitialized){
                instanceDb = Room
                    .databaseBuilder(
                        context,
                        BarberShopDB::class.java,
                        "db_barber_shop"
                    ).allowMainThreadQueries().build()
            }
            return instanceDb
        }
    }
}