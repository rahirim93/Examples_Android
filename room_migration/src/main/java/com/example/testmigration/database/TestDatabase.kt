package com.example.testmigration.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

//// Версия 1
//@Database(version = 1,entities = [ TestEntity::class ])
//@TypeConverters(TestTypeConverters::class)
//abstract class TestDatabase: RoomDatabase() {
//    abstract fun testDao(): TestDao
//}

// Версия 2
@Database(
    version = 2,
    entities = [ TestEntity::class ],
    autoMigrations = [
        AutoMigration(
            from = 1,
            to = 2
        )
    ]
)
@TypeConverters(TestTypeConverters::class)
abstract class TestDatabase: RoomDatabase() {
    abstract fun testDao(): TestDao
}