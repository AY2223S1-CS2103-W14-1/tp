package seedu.condonery.logic.parser.property;

import static seedu.condonery.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.condonery.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.condonery.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.condonery.logic.commands.CommandTestUtil.CLIENT_VALID_ADDRESS_BOB;
import static seedu.condonery.logic.commands.CommandTestUtil.CLIENT_VALID_NAME_BOB;
import static seedu.condonery.logic.commands.CommandTestUtil.CLIENT_VALID_TAG_FRIEND;
import static seedu.condonery.logic.commands.CommandTestUtil.CLIENT_VALID_TAG_HUSBAND;
import static seedu.condonery.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.condonery.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.condonery.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.condonery.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.condonery.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.condonery.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.condonery.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.condonery.logic.commands.CommandTestUtil.PROPERTY_ADDRESS_DESC_SCOTTS;
import static seedu.condonery.logic.commands.CommandTestUtil.PROPERTY_ADDRESS_DESC_WHISTLER;
import static seedu.condonery.logic.commands.CommandTestUtil.PROPERTY_NAME_DESC_SCOTTS;
import static seedu.condonery.logic.commands.CommandTestUtil.PROPERTY_NAME_DESC_WHISTLER;
import static seedu.condonery.logic.commands.CommandTestUtil.PROPERTY_PRICE_DESC_SCOTTS;
import static seedu.condonery.logic.commands.CommandTestUtil.PROPERTY_PRICE_DESC_WHISTLER;
import static seedu.condonery.logic.commands.CommandTestUtil.PROPERTY_STATUS_DESC_SCOTTS;
import static seedu.condonery.logic.commands.CommandTestUtil.PROPERTY_TAGS_DESC_SCOTTS;
import static seedu.condonery.logic.commands.CommandTestUtil.PROPERTY_TAGS_DESC_WHISTLER;
import static seedu.condonery.logic.commands.CommandTestUtil.PROPERTY_VALID_ADDRESS_SCOTTS;
import static seedu.condonery.logic.commands.CommandTestUtil.PROPERTY_VALID_ADDRESS_WHISTLER;
import static seedu.condonery.logic.commands.CommandTestUtil.PROPERTY_VALID_NAME_SCOTTS;
import static seedu.condonery.logic.commands.CommandTestUtil.PROPERTY_VALID_NAME_WHISTLER;
import static seedu.condonery.logic.commands.CommandTestUtil.PROPERTY_VALID_TAG;
import static seedu.condonery.logic.commands.CommandTestUtil.PROPERTY_VALID_TAG_SCOTTS;
import static seedu.condonery.logic.commands.CommandTestUtil.PRICE_DESC_AMY;
import static seedu.condonery.logic.commands.CommandTestUtil.PRICE_DESC_BOB;
import static seedu.condonery.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.condonery.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.condonery.logic.commands.CommandTestUtil.VALID_PRICE_BOB;
import static seedu.condonery.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.condonery.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.condonery.testutil.TypicalProperties.SCOTTS;
import static seedu.condonery.testutil.TypicalProperties.WHISTLER;

import org.junit.jupiter.api.Test;
import seedu.condonery.logic.commands.property.AddPropertyCommand;
import seedu.condonery.model.fields.Address;
import seedu.condonery.model.fields.Name;
import seedu.condonery.model.property.Property;
import seedu.condonery.model.tag.Tag;
import seedu.condonery.testutil.PropertyBuilder;

public class AddPropertyCommandParserTest {
    private final AddPropertyCommandParser parser = new AddPropertyCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Property expectedProperty = new PropertyBuilder(WHISTLER).withTags(PROPERTY_VALID_TAG).build();

        // whitespace only preamble
        assertParseSuccess(parser,  PREAMBLE_WHITESPACE + PROPERTY_NAME_DESC_WHISTLER
            + PROPERTY_ADDRESS_DESC_WHISTLER + PROPERTY_TAGS_DESC_WHISTLER + PROPERTY_PRICE_DESC_WHISTLER
                , new AddPropertyCommand(expectedProperty));

        // multiple names - last name accepted
        assertParseSuccess(parser, PROPERTY_NAME_DESC_SCOTTS + PROPERTY_NAME_DESC_WHISTLER
            + PROPERTY_ADDRESS_DESC_WHISTLER + PROPERTY_TAGS_DESC_WHISTLER+ PROPERTY_PRICE_DESC_WHISTLER, new AddPropertyCommand(expectedProperty));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, PROPERTY_NAME_DESC_WHISTLER + PROPERTY_ADDRESS_DESC_SCOTTS
            + PROPERTY_ADDRESS_DESC_WHISTLER + PROPERTY_TAGS_DESC_WHISTLER+ PROPERTY_PRICE_DESC_WHISTLER, new AddPropertyCommand(expectedProperty));

        // multiple tags - all accepted
        Property expectedPersonMultipleTags =
                new PropertyBuilder(SCOTTS).withTags(PROPERTY_VALID_TAG_SCOTTS, PROPERTY_VALID_TAG).build();
        assertParseSuccess(parser, PROPERTY_NAME_DESC_SCOTTS + PROPERTY_ADDRESS_DESC_SCOTTS
                + PROPERTY_TAGS_DESC_SCOTTS + PROPERTY_TAGS_DESC_WHISTLER + PROPERTY_PRICE_DESC_SCOTTS, new AddPropertyCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Property expectedProperty = new PropertyBuilder(SCOTTS).withTags().build();
        assertParseSuccess(parser, PROPERTY_NAME_DESC_SCOTTS + PROPERTY_ADDRESS_DESC_SCOTTS
                + PROPERTY_PRICE_DESC_SCOTTS,
        new AddPropertyCommand(expectedProperty));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPropertyCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, CLIENT_VALID_NAME_BOB + ADDRESS_DESC_BOB + PRICE_DESC_BOB,
            expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + CLIENT_VALID_ADDRESS_BOB + PRICE_DESC_BOB,
            expectedMessage);

        // missing price prefix
        assertParseFailure(parser, NAME_DESC_BOB + ADDRESS_DESC_BOB + VALID_PRICE_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, CLIENT_VALID_NAME_BOB + CLIENT_VALID_ADDRESS_BOB + VALID_PRICE_BOB,
            expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + ADDRESS_DESC_BOB + PRICE_DESC_BOB
            + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_ADDRESS_DESC + PRICE_DESC_BOB
            + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + ADDRESS_DESC_BOB + PRICE_DESC_BOB
            + INVALID_TAG_DESC + CLIENT_VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_ADDRESS_DESC + PRICE_DESC_BOB,
            Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB
                + ADDRESS_DESC_BOB + PRICE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPropertyCommand.MESSAGE_USAGE));
    }
}
