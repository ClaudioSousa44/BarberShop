package com.cauhsousa.barbershop.DAO

import androidx.room.*
import com.cauhsousa.barbershop.model.User

@Dao
interface UserDao {
    @Insert
    fun save(user: User) :Long

    @Update
    fun update(user: User) :Int

    @Delete
    fun delete(user: User) : Int

    @Query("SELECT * FROM tbl_user WHERE email= :email")
    fun findUserByEmail(email: String): User

    @Query("SELECT * FROM tbl_user WHERE email = :email AND password = :password")
    fun authenticate(email: String, password: String): User
}