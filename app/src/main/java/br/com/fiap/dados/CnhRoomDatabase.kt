package br.com.fiap.dados

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.fiap.dados.repositorios.CnhDao
import br.com.fiap.entidades.Cnh

@Database(entities = [Cnh::class], version = 1)
public abstract class CnhRoomDatabase : RoomDatabase() {

    abstract fun cnhDao(): CnhDao

    companion object {
        @Volatile
        private var INSTANCE: CnhRoomDatabase? = null

        fun getDatabase(context: Context): CnhRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CnhRoomDatabase::class.java,
                    "Cnh_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}