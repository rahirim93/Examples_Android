package com.example.testmigration.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

//// Версия 1
//@Entity
//data class TestEntity(@PrimaryKey var id: UUID = UUID.randomUUID(),
//                        var date: Calendar = Calendar.getInstance(),
//                        var salary: Int = 0)

@Entity
data class TestEntity(@PrimaryKey var id: UUID = UUID.randomUUID(),
                      var date: Calendar = Calendar.getInstance(),
                      var salary: Int = 0,
                      var second: Int? = 0)