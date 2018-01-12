package am2.hu.ezshop.persistence.dao

import am2.hu.ezshop.getValueTest
import am2.hu.ezshop.persistance.db.EzDatabase
import am2.hu.ezshop.persistance.entity.History
import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.hasSize
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class HistoryDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    lateinit var database: EzDatabase

    @Before
    fun init() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), EzDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun getAllHistoryFromEmptyDbTest() {
        val list = database.historyDao().getFullHistory().getValueTest()
        assertThat(list, hasSize(0))
    }

    @Test
    fun addHistoryTest() {
        val history = History("test")

        database.historyDao().addHistoryItem(history)

        val result = database.historyDao().queryHistory("test")

        assertThat(result, hasSize(1))
        assertThat(result[0].itemName, `is`("test"))
    }

    @Test
    fun deleteHistoryTest() {
        val history = History("test")

        database.historyDao().addHistoryItem(history)

        val result = database.historyDao().queryHistory("test")

        assertThat(result, hasSize(1))
        assertThat(result[0].itemName, `is`("test"))


        database.historyDao().deleteHistoryItem(history)

        val testResult = database.historyDao().queryHistory("test")

        assertThat(testResult.size, `is`(0))
    }

    @Test
    fun historyQueryTest() {
        val h1 = History("te")

        database.historyDao().addHistoryItem(h1)

        val h2 = History("test")

        database.historyDao().addHistoryItem(h2)

        val h3 = History("something")

        database.historyDao().addHistoryItem(h3)

        val h4 = History("nothing")

        database.historyDao().addHistoryItem(h4)

        val result = database.historyDao().queryHistory("te")

        assertThat(result.size, `is`(2))

        assertThat(result[0].itemName, `is`("te"))

        assertThat(result[1].itemName, `is`("test"))

        val result2 = database.historyDao().queryHistory("some")

        assertThat(result2.size, `is`(1))

        assertThat(result2[0].itemName, `is`("something"))
    }
}