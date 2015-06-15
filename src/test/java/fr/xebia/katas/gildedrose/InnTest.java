package fr.xebia.katas.gildedrose;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class InnTest {

    private Inn inn;

    @Before
    public void init() {
        inn = new Inn();
    }
    
    @Test
    public void should_list_items() throws Exception {
        List<Item> items = inn.getItems();
        assertThat(items).onProperty("name").containsExactly("+5 Dexterity Vest", "Aged Brie", "Elixir of the Mongoose", "Sulfuras, Hand of Ragnaros", "Backstage passes to a TAFKAL80ETC concert", "Conjured Mana Cake");

        assertThat(items).onProperty("quality").containsExactly(20, 0, 7, 80, 20, 6);
        assertThat(items).onProperty("sellIn").containsExactly(10, 2, 5, 0, 15, 3);
    }

    /**
     *
     */
    @Test
    public void should_update_items() {
        inn.updateQuality();

        assertThat(inn.getItems()).onProperty("quality").containsExactly(19, 1, 6, 80, 21, 5);
        assertThat(inn.getItems()).onProperty("sellIn").containsExactly(9, 1, 4, 0, 14, 2);
    }
    
    @Test
    public void should_update_items_twice() {
        inn.updateQuality();
        inn.updateQuality();

        assertThat(inn.getItems()).onProperty("quality").containsExactly(18, 2, 5, 80, 22, 4);
        assertThat(inn.getItems()).onProperty("sellIn").containsExactly(8, 0, 3, 0, 13, 1);
    }
    
    @Test
    public void should_update_items_a_lot() {
        for (int i = 0; i < 50; i++) {
            inn.updateQuality();
        }

        assertThat(inn.getItems()).onProperty("quality").containsExactly(0, 50, 0, 80, 0, 0);
        assertThat(inn.getItems()).onProperty("sellIn").containsExactly(-40, -48, -45, 0, -35, -47);
    }
    
    /**
     *
     */
    @Test
    public void should_test_against_legacy_code() {
        LegacyInn legacyInn = new LegacyInn();
        for (int day = 0; day < 1000; day++) {
            List<Item> legacyItems = legacyInn.getItems();
            List<Item> innItems = inn.getItems();
            
            for (int j = 0; j < legacyItems.size(); j++) {
                Item item = innItems.get(j);
                Item legacyItem = legacyItems.get(j);
                
                assertThat(item.getName()).isEqualTo(legacyItem.getName());
                assertThat(item.getQuality()).isEqualTo(legacyItem.getQuality());
                assertThat(item.getSellIn()).isEqualTo(legacyItem.getSellIn());
            }
            
            inn.updateQuality();
            legacyInn.updateQuality();
        }
    }
}
