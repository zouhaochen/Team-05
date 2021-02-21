package command;

import java.util.stream.Stream;

/**
 * The enum CommandType.
 */
public enum CommandType {

	/**
	 * Edit continent command type.
	 */
	EDIT_CONTINENT("editcontinent"),
	/**
	 * Edit country command type.
	 */
	EDIT_COUNTRY("editcountry"),
	/**
	 * Edit neighbor command type.
	 */
	EDIT_NEIGHBOR("editneighbor"),
	/**
	 * Show map command type.
	 */
	SHOW_MAP("showmap"),
	/**
	 * Save map command type.
	 */
	SAVE_MAP("savemap"),
	/**
	 * Edit map command type.
	 */
	EDIT_MAP("editmap"),
	/**
	 * Validate map command type.
	 */
	VALIDATE_MAP("validatemap"),
	/**
	 * Load map command type.
	 */
	LOAD_MAP("loadmap"),
	/**
	 * Add player command type.
	 */
	ADD_PLAYER("gameplayer"),
	/**
	 * Assign countries command type.
	 */
	ASSIGN_COUNTRIES("assigncountries"),
	/**
	 * Deploy command type.
	 */
	DEPLOY("deploy");

	/**
	 * The label of the command.
	 */
	private final String d_label;


	/**
	 * Private constructor of the Enum type.
	 *
	 * @param p_label the label of the command
	 */
	CommandType(String p_label) {
		d_label = p_label;
	}

	/**
	 * Gets Enum of CommandType based on the label.
	 *
	 * @param p_Label the label of the command
	 * @return a object of type CommandType
	 */
	public static CommandType getCommandFromLabel(String p_Label) {
		return Stream.of(CommandType.values()).filter(c->c.d_label.toLowerCase().equals(p_Label)).findFirst().orElse(null);
	}
}
