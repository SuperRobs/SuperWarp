package commands.category;

import helpers.Utils;
import net.ddns.superrobs.commands.CategoryList;
import net.ddns.superrobs.warpmap.CategoryAvailability;
import net.ddns.superrobs.warpmap.CategoryManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CategoryListTests {

    public static CategoryList categoryList = new CategoryList();

    public CommandSender senderFake = mock(CommandSender.class);
    public Command cmdFake = mock(Command.class);
    public String alias = "something";

    @BeforeEach
    public void setUp() {
        Utils.clearWarpMap();
    }

    @Test
    public void noPermission(){
        when(senderFake.hasPermission(anyString())).thenReturn(false);
        //Act
        categoryList.onCommand(senderFake, cmdFake, alias, new String[]{});
        //Assert
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(senderFake).sendMessage(message.capture());
        assertThat(message.getValue()).containsIgnoringCase("permission");
    }

    @Test
    public void noCategories() {
        //arrange
        when(senderFake.hasPermission(anyString())).thenReturn(true);
        //act
        categoryList.onCommand(senderFake, cmdFake, alias, new String[]{});
        //assert
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(senderFake).sendMessage(message.capture());
        assertThat(message.getValue()).hasLineCount(1);
    }

    @Test
    public void oneCategory(){
        //arrange
        when(senderFake.hasPermission(anyString())).thenReturn(true);
        CategoryManager.addCategory("someCategory", CategoryAvailability.PUBLIC);
        //act
        categoryList.onCommand(senderFake, cmdFake, alias, new String[]{});
        //assert
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(senderFake).sendMessage(message.capture());
        assertThat(message.getValue()).hasLineCount(2);
        assertThat(message.getValue()).contains("someCategory");
    }

    @Test
    public void twoCategories(){
        //arrange
        when(senderFake.hasPermission(anyString())).thenReturn(true);
        CategoryManager.addCategory("someCategory", CategoryAvailability.PUBLIC);
        CategoryManager.addCategory("anotherCategory", CategoryAvailability.PUBLIC);
        //act
        categoryList.onCommand(senderFake, cmdFake, alias, new String[]{});
        //assert
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(senderFake).sendMessage(message.capture());
        assertThat(message.getValue()).hasLineCount(3);
        assertThat(message.getValue()).contains("someCategory");
        assertThat(message.getValue()).contains("anotherCategory");
    }

    @Test
    public void threeCategories(){
        //arrange
        when(senderFake.hasPermission(anyString())).thenReturn(true);
        CategoryManager.addCategory("someCategory", CategoryAvailability.PUBLIC);
        CategoryManager.addCategory("anotherCategory", CategoryAvailability.PUBLIC);
        CategoryManager.addCategory("yetAnotherCategory", CategoryAvailability.PUBLIC);
        //act
        categoryList.onCommand(senderFake, cmdFake, alias, new String[]{});
        //assert
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(senderFake).sendMessage(message.capture());
        assertThat(message.getValue()).hasLineCount(4);
        assertThat(message.getValue()).contains("someCategory");
        assertThat(message.getValue()).contains("anotherCategory");
        assertThat(message.getValue()).contains("yetAnotherCategory");
    }

    //ToDo add tests for inaccessible categories
    @Test
    public void onlyInaccessibleCategories(){
        //arrange
        when(senderFake.hasPermission("superwarp.categories.read.someCategory")).thenReturn(false);
        when(senderFake.hasPermission("superwarp.categories.list")).thenReturn(true);
        CategoryManager.addCategory("someCategory", CategoryAvailability.PERMISSION);
        //act
        categoryList.onCommand(senderFake, cmdFake, alias, new String[]{});
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(senderFake).sendMessage(message.capture());
        assertThat(message.getValue()).hasLineCount(1);
        assertThat(message.getValue()).doesNotContain("someCategory");
    }

    @Test
    public void mixedAccessibleAndInaccessibleCategories(){
        //arrange
        when(senderFake.hasPermission("superwarp.categories.read.someCategory")).thenReturn(false);
        when(senderFake.hasPermission("superwarp.categories.read.someOtherCategory")).thenReturn(false);
        when(senderFake.hasPermission("superwarp.categories.list")).thenReturn(true);
        CategoryManager.addCategory("someCategory", CategoryAvailability.PERMISSION);
        CategoryManager.addCategory("someOtherCategory", CategoryAvailability.PUBLIC);
        //act
        categoryList.onCommand(senderFake, cmdFake, alias, new String[]{});
        ArgumentCaptor<String> message = ArgumentCaptor.forClass(String.class);
        verify(senderFake).sendMessage(message.capture());
        assertThat(message.getValue()).hasLineCount(2);
        assertThat(message.getValue()).doesNotContain("someCategory");
        assertThat(message.getValue()).contains("someOtherCategory");
    }
}
