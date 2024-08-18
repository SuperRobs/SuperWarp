package commands.category;

import helpers.Utils;
import net.ddns.superrobs.commands.EditCategoryType;
import net.ddns.superrobs.warpmap.CategoryAvailability;
import net.ddns.superrobs.warpmap.CategoryManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EditCategoryTypeTests {

    public static EditCategoryType editCategoryType = new EditCategoryType();

    public CommandSender senderFake = mock(CommandSender.class);
    public Command cmdFake = mock(Command.class);
    public String alias = "something";

    @BeforeEach
    public void setUp() {
        Utils.clearWarpMap();
    }

    @Test
    public void categoryDoesNotExist() {
        //Arrange
        when(senderFake.hasPermission(anyString())).thenReturn(true);
        CategoryManager.addCategory("someName", CategoryAvailability.PERMISSION);
        //Act
        editCategoryType.onCommand(senderFake, cmdFake, alias, new String[]{"someOtherName", "public"});
        //Assert
        assertThat(CategoryManager.getCategoryNames()).containsExactly("someName");
        assertThat(CategoryManager.getCategoryAvailability("someName").get())
                .isEqualTo(CategoryAvailability.PERMISSION);
    }

    @Test
    public void noPermission(){
        //Arrange
        when(senderFake.hasPermission(anyString())).thenReturn(false);
        CategoryManager.addCategory("someName", CategoryAvailability.PERMISSION);
        //Act
        editCategoryType.onCommand(senderFake, cmdFake, alias, new String[]{"someName", "public"});
        //Assert
        assertThat(CategoryManager.getCategoryNames()).containsExactly("someName");
        assertThat(CategoryManager.getCategoryAvailability("someName").get())
                .isEqualTo(CategoryAvailability.PERMISSION);
    }

    @Test
    public void notManageable(){
        //Arrange
        when(senderFake.hasPermission("superwarp.categories.edittype")).thenReturn(true);
        when(senderFake.hasPermission("superwarp.categories.manage.someName")).thenReturn(false);
        CategoryManager.addCategory("someName", CategoryAvailability.PERMISSION);
        //Act
        editCategoryType.onCommand(senderFake, cmdFake, alias, new String[]{"someName", "public"});
        //Assert
        assertThat(CategoryManager.getCategoryNames()).containsExactly("someName");
        assertThat(CategoryManager.getCategoryAvailability("someName").get())
                .isEqualTo(CategoryAvailability.PERMISSION);
    }

    @Test
    public void NoArguments(){
        //Arrange
        when(senderFake.hasPermission(anyString())).thenReturn(true);
        CategoryManager.addCategory("someName", CategoryAvailability.PERMISSION);
        //Act
        editCategoryType.onCommand(senderFake, cmdFake, alias, new String[]{});
        //Assert
        assertThat(CategoryManager.getCategoryNames()).containsExactly("someName");
        assertThat(CategoryManager.getCategoryAvailability("someName").get())
                .isEqualTo(CategoryAvailability.PERMISSION);
    }

    @Test
    public void TooFewArguments(){
        //Arrange
        when(senderFake.hasPermission(anyString())).thenReturn(true);
        CategoryManager.addCategory("someName", CategoryAvailability.PERMISSION);
        //Act
        editCategoryType.onCommand(senderFake, cmdFake, alias, new String[]{"someName"});
        //Assert
        assertThat(CategoryManager.getCategoryNames()).containsExactly("someName");
        assertThat(CategoryManager.getCategoryAvailability("someName").get())
                .isEqualTo(CategoryAvailability.PERMISSION);
    }

    @Test
    public void TooManyArguments(){
        //Arrange
        when(senderFake.hasPermission(anyString())).thenReturn(true);
        CategoryManager.addCategory("someName", CategoryAvailability.PERMISSION);
        //Act
        editCategoryType.onCommand(senderFake, cmdFake, alias, new String[]{"someName", "public", "Nonsense"});
        //Assert
        assertThat(CategoryManager.getCategoryNames()).containsExactly("someName");
        assertThat(CategoryManager.getCategoryAvailability("someName").get())
                .isEqualTo(CategoryAvailability.PERMISSION);
    }

    @Test
    public void NoChange(){
        //Arrange
        when(senderFake.hasPermission(anyString())).thenReturn(true);
        CategoryManager.addCategory("someName", CategoryAvailability.PUBLIC);
        //Act
        editCategoryType.onCommand(senderFake, cmdFake, alias, new String[]{"someName", "public"});
        //Assert
        assertThat(CategoryManager.getCategoryNames()).containsExactly("someName");
        assertThat(CategoryManager.getCategoryAvailability("someName").get())
                .isEqualTo(CategoryAvailability.PUBLIC);
    }

    @Test
    public void PublicToPrivate(){
        //Arrange
        when(senderFake.hasPermission(anyString())).thenReturn(true);
        CategoryManager.addCategory("someName", CategoryAvailability.PUBLIC);
        //Act
        editCategoryType.onCommand(senderFake, cmdFake, alias, new String[]{"someName", "permission"});
        //Assert
        assertThat(CategoryManager.getCategoryNames()).containsExactly("someName");
        assertThat(CategoryManager.getCategoryAvailability("someName").get())
                .isEqualTo(CategoryAvailability.PERMISSION);
    }

    @Test
    public void PrivateToPublic(){
        //Arrange
        when(senderFake.hasPermission(anyString())).thenReturn(true);
        CategoryManager.addCategory("someName", CategoryAvailability.PERMISSION);
        //Act
        editCategoryType.onCommand(senderFake, cmdFake, alias, new String[]{"someName", "public"});
        //Assert
        assertThat(CategoryManager.getCategoryNames()).containsExactly("someName");
        assertThat(CategoryManager.getCategoryAvailability("someName").get())
                .isEqualTo(CategoryAvailability.PUBLIC);
    }

    @Test
    public void AutoToPublic(){
        //Arrange
        when(senderFake.hasPermission(anyString())).thenReturn(true);
        CategoryManager.addCategory("someName", CategoryAvailability.AUTOMATICALLY_APPLIED);
        //Act
        editCategoryType.onCommand(senderFake, cmdFake, alias, new String[]{"someName", "public"});
        //Assert
        assertThat(CategoryManager.getCategoryNames()).containsExactly("someName");
        assertThat(CategoryManager.getCategoryAvailability("someName").get())
                .isEqualTo(CategoryAvailability.PUBLIC);
    }

    //I will not add tests for every possible change, the last three variations will have to suffice
}
