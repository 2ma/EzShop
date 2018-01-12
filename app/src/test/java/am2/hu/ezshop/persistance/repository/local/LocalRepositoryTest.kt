package am2.hu.ezshop.persistance.repository.local

import am2.hu.ezshop.InstantAppExecutors
import am2.hu.ezshop.argumentCaptor
import am2.hu.ezshop.capture
import am2.hu.ezshop.persistance.dao.HistoryDao
import am2.hu.ezshop.persistance.dao.ItemDao
import am2.hu.ezshop.persistance.dao.ListNameDao
import am2.hu.ezshop.persistance.entity.History
import am2.hu.ezshop.persistance.entity.Item
import am2.hu.ezshop.persistance.entity.ListName
import android.content.SharedPreferences
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
    private val shopDao = mock(ListNameDao::class.java)
    private val itemDao = mock(ItemDao::class.java)
    private val historyDao = mock(HistoryDao::class.java)
    private lateinit var localRepository: LocalRepository
    private val shared = mock(SharedPreferences::class.java)
    private val exe = InstantAppExecutors()

    @Before
    fun init() {
        localRepository = LocalRepository(shopDao, itemDao, historyDao, exe, shared)
    }

    @Test
    fun getAllShops() {
        localRepository.getAllListNames()

        verify(shopDao).getAllListNames()
    }

    @Test
    fun addShopTest() {
        val shop = ListName("test")

        localRepository.addListName(shop)

        val captor = argumentCaptor<ListName>()

        verify(shopDao).addListName(capture(captor))

        assertThat(captor.value.listName, `is`("test"))
    }

    @Test
    fun deleteShop() {
        val shop = ListName("test")

        localRepository.deleteListName(shop)

        val captor = argumentCaptor<ListName>()

        verify(shopDao).deleteListName(capture(captor))

        assertThat(captor.value.listName, `is`("test"))
    }

    @Test
    fun getItemsForShopTest() {
        localRepository.getItemsForList("test")

        verify(itemDao).getItemsForShop("test")
    }

    @Test
    fun addItemTest() {
        val item = Item("test", false, "listName")

        localRepository.addItem(item)

        val captor = argumentCaptor<Item>()

        verify(itemDao).addItem(capture(captor))

        assertThat(captor.value.itemName, `is`("test"))

        assertThat(captor.value.listName, `is`("listName"))
    }

    @Test
    fun deleteItemTest() {
        val item = Item("test", false, "listName")

        localRepository.deleteItem(item)

        val captor = argumentCaptor<Item>()

        verify(itemDao).deleteItem(capture(captor))

        assertThat(captor.value.itemName, `is`("test"))

        assertThat(captor.value.listName, `is`("listName"))
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