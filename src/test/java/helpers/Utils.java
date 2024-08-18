package helpers;

import net.ddns.superrobs.warpmap.CategoryAvailability;
import net.ddns.superrobs.warpmap.CategoryManager;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Utils {

    public static void clearWarpMap(){
        for(String categoryName : CategoryManager.getCategoryNames()){
            CategoryManager.removeCategory(categoryName);
        }
    }

    //these tests assume that CategoryManager.getCategoryNames() works properly, this is kind of circular, because
    //clearWarpMap is also used in the tests for that method
    //I find that is fine for now, since most errors will still be caught
    @Test
    public void clearWarpMapTest(){
        //Arrange
        //just add some more, there might already be some but that's not very important now
        CategoryManager.addCategory("Test", CategoryAvailability.PLAYER);
        CategoryManager.addCategory("Test2", CategoryAvailability.PERMISSION);
        CategoryManager.addCategory("Test3", CategoryAvailability.PUBLIC);
        CategoryManager.addCategory("Test4", CategoryAvailability.AUTOMATICALLY_APPLIED);
        CategoryManager.addCategory("Test5", CategoryAvailability.AUTOMATICALLY_APPLIED);
        //Act
        clearWarpMap();
        //Assert
        assertThat(CategoryManager.getCategoryNames()).isEmpty();
    }

    @Test
    public void applyTwice(){
        //Arrange
        //just add some more, there might already be some but that's not very important now
        CategoryManager.addCategory("Test", CategoryAvailability.PLAYER);
        CategoryManager.addCategory("Test2", CategoryAvailability.PERMISSION);
        CategoryManager.addCategory("Test3", CategoryAvailability.PUBLIC);
        CategoryManager.addCategory("Test4", CategoryAvailability.AUTOMATICALLY_APPLIED);
        CategoryManager.addCategory("Test5", CategoryAvailability.AUTOMATICALLY_APPLIED);
        //Act
        clearWarpMap();
        clearWarpMap();
        //Assert
        assertThat(CategoryManager.getCategoryNames()).isEmpty();
    }

}
