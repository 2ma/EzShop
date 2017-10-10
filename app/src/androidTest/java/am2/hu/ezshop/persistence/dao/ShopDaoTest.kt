package am2.hu.ezshop.persistence.dao

import am2.hu.ezshop.getValueTest
import am2.hu.ezshop.persistance.db.EzDatabase
import am2.hu.ezshop.persistance.entity.Item
import am2.hu.ezshop.persistance.entity.Shop
import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
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
class ShopDaoTest {
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
    fun getShopsEmptyDbTest() {
        val result = database.shopDao().getAllShops().getValueTest()

        assertThat(result.size, `is`(0))
    }

    @Test
    fun addShopTest() {
        val shop = Shop("test")

        database.shopDao().addShop(shop)

        val result = database.shopDao().getAllShops().getValueTest()

        assertThat(result.size, `is`(1))

        assertThat(result[0].shopName, `is`("test"))
    }

    @Test
    fun getAllShopsTest() {
        for (i in 1..3) {
            database.shopDao().addShop(Shop("shop$i"))
        }

        val result = database.shopDao().getAllShops().getValueTest()

        assertThat(result.size, `is`(3))

        assertThat(result[0].shopName, `is`("shop1"))

        assertThat(result[1].shopName, `is`("shop2"))

        assertThat(result[2].shopName, `is`("shop3"))
    }

    @Test
    fun deleteShopTest() {
        val shop = Shop("test")

        database.shopDao().addShop(shop)

        val result = database.shopDao().getAllShops().getValueTest()

        assertThat(result.size, `is`(1))

        assertThat(result[0].shopName, `is`("test"))

        database.shopDao().deleteShop(shop)

        val resultTest = database.shopDao().getAllShops().getValueTest()

        assertThat(resultTest.size, `is`(0))
    }

    @Test
    fun deleteAllItemsWithShopTest() {
        val shop = Shop("test")

        database.shopDao().addShop(shop)

        for (i in 1..4) {
            database.itemDao().addItem(Item("item$i", false, "test"))
        }

        val shopResult = database.shopDao().getAllShops().getValueTest()

        assertThat(shopResult.size, `is`(1))

        assertThat(shopResult[0].shopName, `is`("test"))

        val itemResult = database.itemDao().getItemsForShop("test").getValueTest()

        assertThat(itemResult.size, `is`(4))

        assertThat(itemResult[0].itemName, `is`("item1"))

        assertThat(itemResult[1].itemName, `is`("item2"))

        database.shopDao().deleteShop(shop)

        val shopResultTest = database.shopDao().getAllShops().getValueTest()

        assertThat(shopResultTest.size, `is`(0))

        val itemResultTest = database.itemDao().getItemsForShop("test").getValueTest()

        assertThat(itemResultTest.size, `is`(0))
    }
}