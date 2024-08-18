package commands.category;

import helpers.Utils;
import net.ddns.superrobs.commands.CategoryList;
import net.ddns.superrobs.commands.DeleteCategory;
import net.ddns.superrobs.warpmap.CategoryAvailability;
import net.ddns.superrobs.warpmap.CategoryManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeleteCategoryTests {

    public static DeleteCategory deleteCategory = new DeleteCategory();

    public CommandSender senderFake = mock(CommandSender.class);
    public Command cmdFake = mock(Command.class);
    public String alias = "something";

    @BeforeEach
    public void setUp() {
        Utils.clearWarpMap();
    }

    @Test
    public void NoCategory(){
        //Arrange
        when(senderFake.hasPermission(anyString())).thenReturn(true);
        //Act
        deleteCategory.onCommand(senderFake, cmdFake, alias, new String[]{"someCategory"});
        //Assert
        assertThat(CategoryManager.getCategoryNames()).isEmpty();
    }

    @Test
    public void NoPermission(){
        //Arrange
        when(senderFake.hasPermission(anyString())).thenReturn(false);
        //Act
        deleteCategory.onCommand(senderFake, cmdFake, alias, new String[]{"someCategory"});
        //Assert
        assertThat(CategoryManager.getCategoryNames()).isEmpty();
    }

    @Test
    public void NoCategoryWithName(){
        //Arrange
        when(senderFake.hasPermission(anyString())).thenReturn(true);
        CategoryManager.addCategory("someCategory", CategoryAvailability.PUBLIC);
        //Act
        deleteCategory.onCommand(senderFake, cmdFake, alias, new String[]{"someOtherCategory"});
        //Assert
        assertThat(CategoryManager.getCategoryNames()).containsExactlyInAnyOrder("someCategory");
    }

    @Test
    public void CategoryWithName(){
        //Arrange
        when(senderFake.hasPermission(anyString())).thenReturn(true);
        CategoryManager.addCategory("someCategory", CategoryAvailability.PUBLIC);
        //Act
        deleteCategory.onCommand(senderFake, cmdFake, alias, new String[]{"someCategory"});
        //Assert
        assertThat(CategoryManager.getCategoryNames()).isEmpty();
    }

    @Test
    public void MultipleCategoriesOneWithName(){
        //Arrange
        when(senderFake.hasPermission(anyString())).thenReturn(true);
        CategoryManager.addCategory("someCategory", CategoryAvailability.PUBLIC);
        CategoryManager.addCategory("someOtherCategory", CategoryAvailability.PUBLIC);
        //Act
        deleteCategory.onCommand(senderFake, cmdFake, alias, new String[]{"someOtherCategory"});
        //Assert
        assertThat(CategoryManager.getCategoryNames()).containsExactlyInAnyOrder("someCategory");
    }

    @Test
    public void CategoryIsNotManageable(){
        //Arrange
        when(senderFake.hasPermission("superwarp.categories.delete")).thenReturn(true);
        when(senderFake.hasPermission("superwarp.categories.manage.someCategory")).thenReturn(false);
        CategoryManager.addCategory("someCategory", CategoryAvailability.PUBLIC);
        //Act
        deleteCategory.onCommand(senderFake, cmdFake, alias, new String[]{"someCategory"});
        //Assert
        assertThat(CategoryManager.getCategoryNames()).containsExactlyInAnyOrder("someCategory");
    }
}
