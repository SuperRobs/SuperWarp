package commands.category;

import net.ddns.superrobs.commands.AddCategory;
import net.ddns.superrobs.warpmap.CategoryAvailability;
import net.ddns.superrobs.warpmap.CategoryManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import helpers.Utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddCategoryTests {

    //since in game too multiple calls to onCategory are called on the same instance that should not be a problem
    private static final AddCategory addCategory = new AddCategory();

    private final CommandSender senderFake = mock(CommandSender.class);
    //this fake is not used internally as far as I know
    private final Command cmdFake = mock(Command.class);
    private final String commandAlias = "something";

    @BeforeEach
    public void setUp() {
        Utils.clearWarpMap();
    }

    @Test
    public void noPermission(){
        //Arrange
        when(senderFake.hasPermission(anyString())).thenReturn(false);
        //Act
        addCategory.onCommand(senderFake,
                cmdFake,
                commandAlias,
                new String[]{"somename", "public"});
        //Assert
        assertThat(CategoryManager.getCategoryNames()).isEmpty();
    }

    @Test
    public void noArguments() {
        //Arrange
        when(senderFake.hasPermission(anyString())).thenReturn(true);
        //Act
        addCategory.onCommand(senderFake,
                cmdFake,
                commandAlias,
                new String[]{});
        //Assert
        assertThat(CategoryManager.getCategoryNames()).isEmpty();
    }

    @Test
    public void OneArgument() {
        //Arrange
        when(senderFake.hasPermission(anyString())).thenReturn(true);
        //Act
        addCategory.onCommand(senderFake,
                cmdFake,
                commandAlias,
                new String[]{"somename"});
        //Assert
        assertThat(CategoryManager.getCategoryNames()).contains("somename");
        assertThat(CategoryManager.getCategoryAvailability("somename").get())
                .isEqualTo(CategoryAvailability.PUBLIC);
    }

    @Test
    public void tooManyArguments() {
        //Arrange
        when(senderFake.hasPermission(anyString())).thenReturn(true);
        //Act
        addCategory.onCommand(senderFake,
                cmdFake,
                commandAlias,
                new String[]{"somename", "public", "something"});
        //Assert
        assertThat(CategoryManager.getCategoryNames()).isEmpty();
    }

    @Test
    public void invalidAvailability() {
        //Arrange
        when(senderFake.hasPermission(anyString())).thenReturn(true);
        //Act
        addCategory.onCommand(senderFake,
                cmdFake,
                commandAlias,
                new String[]{"somename", "someStupidThing"});
        //Assert
        assertThat(CategoryManager.getCategoryNames()).isEmpty();
    }

    //this one just tests that any category is added, the others also test the correct availability
    @Test
    public void validCommand() {
        //Arrange
        when(senderFake.hasPermission(anyString())).thenReturn(true);
        //Act
        addCategory.onCommand(senderFake,
                cmdFake,
                commandAlias,
                new String[]{"somename", "public"});
        //Assert
        assertThat(CategoryManager.getCategoryNames()).containsExactly("somename");
    }

    @Test
    public void autoAppliedAvailability() {
        //Arrange
        when(senderFake.hasPermission(anyString())).thenReturn(true);
        //Act
        addCategory.onCommand(senderFake,
                cmdFake,
                commandAlias,
                new String[]{"somename", "auto"});
        //Assert
        assertThat(CategoryManager.getCategoryNames()).containsExactly("somename");
        assertThat(CategoryManager.getCategoryAvailability("somename").get())
                .isEqualTo(CategoryAvailability.AUTOMATICALLY_APPLIED);
    }

    @Test
    public void publicAvailability() {
        //Arrange
        when(senderFake.hasPermission(anyString())).thenReturn(true);
        //Act
        addCategory.onCommand(senderFake,
                cmdFake,
                commandAlias,
                new String[]{"somename", "public"});
        //Assert
        assertThat(CategoryManager.getCategoryNames()).containsExactly("somename");
        assertThat(CategoryManager.getCategoryAvailability("somename").get())
                .isEqualTo(CategoryAvailability.PUBLIC);
    }

    @Test
    public void privateAvailability() {
        //Arrange
        when(senderFake.hasPermission(anyString())).thenReturn(true);
        //Act
        addCategory.onCommand(senderFake,
                cmdFake,
                commandAlias,
                new String[]{"somename", "private"});
        //Assert
        assertThat(CategoryManager.getCategoryNames()).containsExactly("somename");
        assertThat(CategoryManager.getCategoryAvailability("somename").get())
                .isEqualTo(CategoryAvailability.PERMISSION);
    }

    @Test
    public void addAlreadyExistingWithSameAccessibility() {
        //Arrange
        when(senderFake.hasPermission(anyString())).thenReturn(true);
        //Act
        addCategory.onCommand(senderFake,
                cmdFake,
                commandAlias,
                new String[]{"somename", "public"});
        addCategory.onCommand(senderFake,
                cmdFake,
                commandAlias,
                new String[]{"somename", "public"});
        //Assert
        assertThat(CategoryManager.getCategoryNames()).containsExactly("somename");
        assertThat(CategoryManager.getCategoryAvailability("somename").get())
                .isEqualTo(CategoryAvailability.PUBLIC);
    }

    @Test
    public void addAlreadyExistingWithDifferentAccessibility() {
        //Arrange
        when(senderFake.hasPermission(anyString())).thenReturn(true);
        //Act
        addCategory.onCommand(senderFake,
                cmdFake,
                commandAlias,
                new String[]{"somename", "auto"});
        addCategory.onCommand(senderFake,
                cmdFake,
                commandAlias,
                new String[]{"somename", "public"});
        //Assert
        assertThat(CategoryManager.getCategoryNames()).containsExactly("somename");
        assertThat(CategoryManager.getCategoryAvailability("somename").get())
                .isEqualTo(CategoryAvailability.AUTOMATICALLY_APPLIED);
    }

    @Test
    public void addMultipleDifferent() {
        //Arrange
        when(senderFake.hasPermission(anyString())).thenReturn(true);
        //Act
        addCategory.onCommand(senderFake,
                cmdFake,
                commandAlias,
                new String[]{"somename", "auto"});
        addCategory.onCommand(senderFake,
                cmdFake,
                commandAlias,
                new String[]{"someothername", "public"});
        //Assert
        assertThat(CategoryManager.getCategoryNames()).containsExactlyInAnyOrder("somename", "someothername");
        assertThat(CategoryManager.getCategoryAvailability("somename").get())
                .isEqualTo(CategoryAvailability.AUTOMATICALLY_APPLIED);
        assertThat(CategoryManager.getCategoryAvailability("someothername").get())
                .isEqualTo(CategoryAvailability.PUBLIC);
    }

}
