package am2.hu.ezshop.persistence.dao

import am2.hu.ezshop.getValueTest
import am2.hu.ezshop.persistance.db.EzDatabase
import am2.hu.ezshop.persistance.entity.Item
import am2.hu.ezshop.persistance.entity.ListName
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
        val shop = ListName("Aldi")

        database.listNameDao().addListName(shop)

        val item = Item("tej", false, "Aldi")

        database.itemDao().addItem(item)

        val result = database.itemDao().getItemsForShop("Aldi").getValueTest()

        assertThat(result.size, `is`(1))

        assertThat(result[0].itemName, `is`("tej"))

        assertThat(result[0].listName, `is`("Aldi"))

        assertThat(result[0].completed, `is`(false))
    }

    @Test
    fun getItemsForShopTest() {
        val s1 = ListName("Aldi")

        database.listNameDao().addListName(s1)

        val s2 = ListName("Tesco")

        database.listNameDao().addListName(s2)

        for (i in 1..4) {
            database.itemDao().addItem(Item("test$i", false, "Aldi"))
            database.itemDao().addItem(Item("test$i", false, "Tesco"))

        }
        val r1 = database.itemDao().getItemsForShop("Tesco").getValueTest()

        assertThat(r1.size, `is`(4))

        assertThat(r1.size, `is`(4))

        assertThat(r1[0].itemName, `is`("test1"))

        assertThat(r1[0].listName, `is`("Tesco"))

        assertThat(r1[1].itemName, `is`("test2"))

        assertThat(r1[1].listName, `is`("Tesco"))

        assertThat(r1[2].itemName, `is`("test3"))

        assertThat(r1[2].listName, `is`("Tesco"))

        val result = database.itemDao().getItemsForShop("Aldi").getValueTest()

        assertThat(result.size, `is`(4))

        assertThat(result[0].itemName, `is`("test1"))

        assertThat(result[0].listName, `is`("Aldi"))

        assertThat(result[1].itemName, `is`("test2"))

        assertThat(result[1].listName, `is`("Aldi"))

        assertThat(result[2].itemName, `is`("test3"))

        assertThat(result[2].listName, `is`("Aldi"))
    }

    @Test
    fun deleteItemTest() {
        val s1 = ListName("Aldi")

        database.listNameDao().addListName(s1)

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

    @Test
    fun updateItemTest() {
        val s1 = ListName("Aldi")

        database.listNameDao().addListName(s1)

        val item = Item("tej", false, "Aldi")

        database.itemDao().addItem(item)

        val result = database.itemDao().getItemsForShop("Aldi").getValueTest()

        assertThat(result.size, `is`(1))

        assertThat(result[0].itemName, `is`("tej"))

        val up: Item = result[0].copy(completed = true)

        up.id = result[0].id

        database.itemDao().updateItem(up)

        val result2 = database.itemDao().getItemsForShop("Aldi").getValueTest()


        assertThat(result2.size, `is`(1))

        assertThat(result2[0].itemName, `is`("tej"))

        assertThat(result2[0].completed, `is`(true))
    }

    @Test
    fun deleteAllCompletedTest() {
        database.listNameDao().addListName(ListName("test"))
        database.listNameDao().addListName(ListName("test2"))
        for (i in 1..10) {
            database.itemDao().addItem(Item("name$i", i % 2 == 0, "test"))
            database.itemDao().addItem(Item("name$i", i % 2 == 0, "test2"))
        }

        val result = database.itemDao().getItemsForShop("test").getValueTest()

        assertThat(result.size, `is`(10))

        val result2 = database.itemDao().getItemsForShop("test2").getValueTest()

        assertThat(result2.size, `is`(10))

        database.itemDao().deleteAllCompleted("test")

        val result3 = database.itemDao().getItemsForShop("test").getValueTest()

        assertThat(result3.size, `is`(5))

        result3.map { assertThat(it.completed, `is`(false)) }

        val result4 = database.itemDao().getItemsForShop("test2").getValueTest()

        assertThat(result4.size, `is`(10))
    }
}