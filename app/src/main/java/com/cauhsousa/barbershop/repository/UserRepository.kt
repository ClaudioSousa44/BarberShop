package com.cauhsousa.barbershop.repository

import android.content.Context
import com.cauhsousa.barbershop.DAO.BarberShopDB
import com.cauhsousa.barbershop.model.User

class UserRepository(context: Context) {
    private val db = BarberShopDB.getDataBase(context)

    fun save(user: User): Long{
        return  db.userDao().save(user)
    }

    fun findUserByEmail(email: String): User {
        return db.userDao().findUserByEmail(email)
    }

    fun authenticate(email: String, password: String): User{
        return db.userDao().authenticate(email,password)
    }
}