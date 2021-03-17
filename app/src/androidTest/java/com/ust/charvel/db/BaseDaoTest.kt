package com.ust.charvel.db

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.core.app.ApplicationProvider
import com.ust.charvel.utils.MockitoTest
import org.junit.After
import org.junit.Before

abstract class BaseDaoTest<Database : RoomDatabase>(
    private val database: Class<Database>
) : MockitoTest() {

    protected lateinit var db: Database

    @Before
    override fun setup() {
        super.setup()
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), database)
            .build()
    }

    @After
    fun teardown() = db.close()
}