package am2.hu.ezshop.persistence.dao

import am2.hu.ezshop.getValueTest
import am2.hu.ezshop.persistance.db.EzDatabase
import am2.hu.ezshop.persistance.entity.Item
import am2.hu.ezshop.persistance.entity.Shop
import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.database.sqlite.SQLiteConstraintException
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ItemDaoTest {
    @Rule
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
    fun getItemFromEmptyDb() {
        val result = database.itemDao().getItemsForShop("test").getValueTest()

        assertThat(result.size, `is`(0))
    }

    @Test(expected = SQLiteConstraintException::class)
    fun addItemWithoutShopPresentInDbTest() {
        val item = Item("tej", false, "Aldi")

        database.itemDao().addItem(item)

        val result = database.itemDao().getItemsForShop("Aldi").getValueTest()

        assertThat(result.size, `is`(0))
    }

    @Test
    fun addItemTest() {
        val shop = Shop("Aldi")

        database.shopDao().addShop(shop)

        val item = Item("tej", false, "Aldi")

        database.itemDao().addItem(item)

        val result = database.itemDao().getItemsForShop("Aldi").getValueTest()

        assertThat(result.size, `is`(1))

        assertThat(result[0].itemName, `is`("tej"))

        assertThat(result[0].shop, `is`("Aldi"))

        assertThat(result[0].completed, `is`(false))
    }

    @Test
    fun getItemsForShopTest() {
        val s1 = Shop("Aldi")

        database.shopDao().addShop(s1)

        val s2 = Shop("Tesco")

        database.shopDao().addShop(s2)

        for (i in 1..4) {
            database.itemDao().addItem(Item("test$i", false, "Aldi"))
            database.itemDao().addItem(Item("test$i", false, "Tesco"))

        }
        val r1 = database.itemDao().getItemsForShop("Tesco").getValueTest()

        assertThat(r1.size, `is`(4))

        assertThat(r1.size, `is`(4))

        assertThat(r1[0].itemName, `is`("test1"))

        assertThat(r1[0].shop, `is`("Tesco"))

        assertThat(r1[1].itemName, `is`("test2"))

        assertThat(r1[1].shop, `is`("Tesco"))

        assertThat(r1[2].itemName, `is`("test3"))

        assertThat(r1[2].shop, `is`("Tesco"))

        val result = database.itemDao().getItemsForShop("Aldi").getValueTest()

        assertThat(result.size, `is`(4))

        assertThat(result[0].itemName, `is`("test1"))

        assertThat(result[0].shop, `is`("Aldi"))

        assertThat(result[1].itemName, `is`("test2"))

        assertThat(result[1].shop, `is`("Aldi"))

        assertThat(result[2].itemName, `is`("test3"))

        assertThat(result[2].shop, `is`("Aldi"))
    }

    @Test
    fun deleteItemTest() {
        val s1 = Shop("Aldi")

        database.shopDao().addShop(s1)

        val item = Item("tej", false, "Aldi")

        database.itemDao().addItem(item)

        val result = database.itemDao().getItemsForShop("Aldi").getValueTest()

        assertThat(result.size, `is`(1))

        assertThat(result[0].itemName, `is`("tej"))

        val item2 = result[0]

        database.itemDao().deleteItem(item2)

        val resultTest = database.itemDao().getItemsForShop("Aldi").getValueTest()

        assertThat(resultTest.size, `is`(0))
    }
}