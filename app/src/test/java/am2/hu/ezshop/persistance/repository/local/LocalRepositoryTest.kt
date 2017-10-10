package am2.hu.ezshop.persistance.repository.local

import am2.hu.ezshop.InstantAppExecutors
import am2.hu.ezshop.argumentCaptor
import am2.hu.ezshop.capture
import am2.hu.ezshop.persistance.dao.HistoryDao
import am2.hu.ezshop.persistance.dao.ItemDao
import am2.hu.ezshop.persistance.dao.ShopDao
import am2.hu.ezshop.persistance.entity.History
import am2.hu.ezshop.persistance.entity.Item
import am2.hu.ezshop.persistance.entity.Shop
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(JUnit4::class)
class LocalRepositoryTest {
    val shopDao = mock(ShopDao::class.java)
    val itemDao = mock(ItemDao::class.java)
    val historyDao = mock(HistoryDao::class.java)
    lateinit var localRepository: LocalRepository

    @Before
    fun init() {
        localRepository = LocalRepository(shopDao, itemDao, historyDao, InstantAppExecutors())
    }

    @Test
    fun getAllShops() {
        localRepository.getAllShops()

        verify(shopDao).getAllShops()
    }

    @Test
    fun addShopTest() {
        val shop = Shop("test")

        localRepository.addShop(shop)

        val captor = argumentCaptor<Shop>()

        verify(shopDao).addShop(capture(captor))

        assertThat(captor.value.shopName, `is`("test"))
    }

    @Test
    fun deleteShop() {
        val shop = Shop("test")

        localRepository.deleteShop(shop)

        val captor = argumentCaptor<Shop>()

        verify(shopDao).deleteShop(capture(captor))

        assertThat(captor.value.shopName, `is`("test"))
    }

    @Test
    fun getItemsForShopTest() {
        localRepository.getItemsForShop("test")

        verify(itemDao).getItemsForShop("test")
    }

    @Test
    fun addItemTest() {
        val item = Item("test", false, "shop")

        localRepository.addItem(item)

        val captor = argumentCaptor<Item>()

        verify(itemDao).addItem(capture(captor))

        assertThat(captor.value.itemName, `is`("test"))

        assertThat(captor.value.shop, `is`("shop"))
    }

    @Test
    fun deleteItemTest() {
        val item = Item("test", false, "shop")

        localRepository.deleteItem(item)

        val captor = argumentCaptor<Item>()

        verify(itemDao).deleteItem(capture(captor))

        assertThat(captor.value.itemName, `is`("test"))

        assertThat(captor.value.shop, `is`("shop"))
    }

    @Test
    fun queryHistoryTest() {
        localRepository.queryHistory("test")

        verify(historyDao).queryHistory("test")
    }

    @Test
    fun getFullHistory() {
        localRepository.getFullHistory()

        verify(historyDao).getFullHistory()
    }

    @Test
    fun addItemToHistoryTest() {
        val item = History("test")

        localRepository.addItemToHistory(item)

        val captor = argumentCaptor<History>()

        verify(historyDao).addHistoryItem(capture(captor))

        assertThat(captor.value.itemName, `is`("test"))
    }

    @Test
    fun deleteItemFromHistoryTest() {
        val item = History("test")

        localRepository.deleteItemFromHistory(item)

        val captor = argumentCaptor<History>()

        verify(historyDao).deleteHistoryItem(capture(captor))

        assertThat(captor.value.itemName, `is`("test"))
    }
}