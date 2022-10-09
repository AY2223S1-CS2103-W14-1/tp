package seedu.condonery.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.condonery.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.condonery.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.condonery.testutil.Assert.assertThrows;
import static seedu.condonery.testutil.TypicalIndexes.INDEX_FIRST_PROPERTY;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.condonery.logic.commands.AddCommand;
import seedu.condonery.logic.commands.ClearCommand;
import seedu.condonery.logic.commands.DeleteCommand;
import seedu.condonery.logic.commands.EditCommand;
import seedu.condonery.logic.commands.EditCommand.EditPropertyDescriptor;
import seedu.condonery.logic.commands.ExitCommand;
import seedu.condonery.logic.commands.FindCommand;
import seedu.condonery.logic.commands.HelpCommand;
import seedu.condonery.logic.commands.ListCommand;
import seedu.condonery.logic.parser.exceptions.ParseException;
import seedu.condonery.model.property.NameContainsKeywordsPredicate;
import seedu.condonery.model.property.Property;
import seedu.condonery.testutil.EditPropertyDescriptorBuilder;
import seedu.condonery.testutil.PropertyBuilder;
import seedu.condonery.testutil.PropertyUtil;

public class CondoneryParserTest {

    private final CondoneryParser parser = new CondoneryParser();

    @Test
    public void parseCommand_add() throws Exception {
        Property property = new PropertyBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PropertyUtil.getAddCommand(property));
        assertEquals(new AddCommand(property), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
            DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PROPERTY.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PROPERTY), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Property property = new PropertyBuilder().build();
        EditPropertyDescriptor descriptor = new EditPropertyDescriptorBuilder(property).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
            + INDEX_FIRST_PROPERTY.getOneBased() + " " + PropertyUtil.getEditPropertyDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PROPERTY, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
            FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
