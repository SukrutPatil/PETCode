package actions;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import mainline.UserInterface.TabNames;

/*******
* <p> Title: StepSpecific Class</p>
* 
* <p> Description: A JavaFX application: This controller class established the user interface for
* Steps Tab objects by inheriting the ListItem and building upon it.</p>
* 
* <p> Copyright: Lynn Robert Carter © 2019-07-06 </p>
* 
* @author Lynn Robert Carter
* 
* @version 1.00	2019-07-06 Baseline
* 
*/
public class StepSpecific extends ListItem {
	
	/**********************************************************************************************

	Class Attributes
	
	**********************************************************************************************/
	
	//---------------------------------------------------------------------------------------------
	// These attributes enable us to hide the details of the tab height and the height and width of
	// of the window decorations for the code that implements this user interface
	private int xOffset = 0;
	private int yOffset = 0;
	
	//---------------------------------------------------------------------------------------------
	// The following are the GUI objects that make up the Step specific elements of the
	// user interface	
	
	// This collection of GUI elements are for establishing the set of pre-conditions

	private Label thePreConditionListTitle = new Label("The pre-conditions for this Life Cycle Step");
	private TableView <StringItem> thePreConditionList = new TableView <StringItem>();
	private TableColumn <StringItem, String> preNameColumn = new TableColumn <>("Pre-condition Name");
	private Label thePreSelectTitle = new Label("Select a pre-condition to be added to the list:");
	private ComboBox <String> thePreSelectComboBox = new ComboBox <String>(FXCollections.observableArrayList());
	private Button btnPreInsert = new Button("Insert this condition into the list to the left");
	private Button btnPreMoveUp = new Button("Move Up");
	private Button btnPreMoveDn = new Button("Move Dn");
	private Button btnPreDelete = new Button("Delete");
	
	
	// This collection is for establishing the sequence of tasks to perform once the pre-conditions
	// have been satisfied

	private Label theTaskListTitle = new Label("The Tasks for this Life Cycle Step");
	private TableView <StringItem> theTaskList = new TableView <StringItem>();
	private TableColumn <StringItem, String> taskNameColumn = new TableColumn <>("Task Name");
	private Label theTaskSelectTitle = new Label("Select a Task to be added to the list:");
	private ComboBox <String> theTaskComboBox = new ComboBox <String>(FXCollections.observableArrayList());
	private Button btnTaskInsert = new Button("Insert this task into the list to the left");
	private Button btnTaskMoveUp = new Button("Move Up");
	private Button btnTaskMoveDn = new Button("Move Dn");
	private Button btnTaskDelete = new Button("Delete");
	
	
	// This collection is to establish the post-conditions that signal that work on this Step has
	// been completed
	
	private Label thePostConditionListTitle = new Label("The post-conditions for this Life Cycle Step");
	private TableView <StringItem> thePostConditionList = new TableView <StringItem>();
	private TableColumn <StringItem, String> postNameColumn = new TableColumn <>("Post-condition Name");
	private Label thePostSelectTitle = new Label("Select a post-condition to be added to the list:");
	private ComboBox <String> thePostSelectComboBox = new ComboBox <String>(FXCollections.observableArrayList());
	private Button btnPostInsert = new Button("Insert this condition into the list to the left");
	private Button btnPostMoveUp = new Button("Move Up");
	private Button btnPostMoveDn = new Button("Move Dn");
	private Button btnPostDelete = new Button("Delete");
	
	
	//---------------------------------------------------------------------------------------------
	// The following are the list objects that make it possible to display the GUI elements needed
	// for the user to define the Step specific elements
	
	private boolean updatingTheList = false;	// This flag keeps use from entering an updqate loop
	
	// These lists define the total list of all possible conditions at this point in time as well
	// as the list of available and used for the pre-condition list and the post-condition list
	
	private ObservableList <String> conditionsTotalList = FXCollections.observableArrayList();
	private ObservableList <String> preConditionsSelectedList = FXCollections.observableArrayList();
	private ObservableList <String> preConditionsAvailableList = FXCollections.observableArrayList();
	private ObservableList <String> postConditionsSelectedList = FXCollections.observableArrayList();
	private ObservableList <String> postConditionsAvailableList = FXCollections.observableArrayList();

	
	
	// These lists define the total list of all possible tasks at this point in time as well
	// as the list of available and used for this Task list
	
	private ObservableList <String> tasksTotalList = FXCollections.observableArrayList();
	private ObservableList <String> tasksSelectedList = FXCollections.observableArrayList();
	private ObservableList <String> tasksAvailableList = FXCollections.observableArrayList();

	
	// This is the Step specific Detail list
	private ObservableList <StepDetail> theStepDetailList = FXCollections.observableArrayList();
	private ListItem conditionTabList = null;
	private ListItem taskTabList = null;


	
	/**********
	 * This constructor establishes the base ListItem and then initializes the Step specific
	 * attributes for the application.
	 * 
	 * @param g		The Group link is used to establish the list of GUI elements for this tab
	 * @param x		The x offset for the GUI elements to fit into the decorative borders
	 * @param y		The y offset
	 * @param t		The enumeration that helps select the right strings for labels, etc.
	 */
	public StepSpecific(Group g, int x, int y, TabNames t) {
		super(g, x, y, t);
		xOffset = x;
		yOffset = y;
		
		// This positions the pre-condition TableView and the ComboBox with their respect titles
		// onto the stage at a precise location
		setupLabelUI(thePreConditionListTitle, "Arial", 14, 100, Pos.BASELINE_LEFT, 360 + xOffset, 260 + yOffset);
		setupTableViewUI(thePreConditionList, 275, 80, 365 + xOffset, 280 + yOffset, false);

		// Establish the columns in the Table View
		preNameColumn.setCellValueFactory(new PropertyValueFactory<>("item"));
		thePreConditionList.getColumns().add(preNameColumn);
		preNameColumn.setMaxWidth(245);
		preNameColumn.setMinWidth(245);

		// Setup the ComboBox+
		setupLabelUI(thePreSelectTitle, "Arial", 14, 100, Pos.BASELINE_LEFT, 650 + xOffset, 260 + yOffset);
		setupComboBoxUI(thePreSelectComboBox, 245, 655 + xOffset, 280 + yOffset);
		thePreSelectComboBox.setTooltip(new Tooltip("Click here to selected an appropriate pre-condition"));

		// This button adds the currently selected pre-condition to the list of pre-conditions used
		setupButtonUI(btnPreInsert, "Arial", 14, 245, Pos.BASELINE_CENTER, 655 + xOffset, 312 + yOffset);
		btnPreInsert.setOnAction((event) -> { insertPreCondition(); });
		btnPreInsert.setTooltip(new Tooltip("Click here to insert the above pre-condition above the selected item in the list to the left"));


		// Set up the Move Up button. It moves the current selected TableView pre-condition up one
		// position. Link the button to the action method and define the tool tip to help the user
		// understand the user interface.
		setupButtonUI(btnPreMoveUp, "Arial", 14, 78, Pos.BASELINE_CENTER, 655 + xOffset, 342 + yOffset);
		btnPreMoveUp.setOnAction((event) -> { moveUpThePreCondition(); });
		btnPreMoveUp.setTooltip(new Tooltip("Click here to move the selected entry to the left up one position"));

		// Set up the Move Dn button. It moves the current selected TabkeView pre-condition down one
		// position. Link the button to the action method and define the tool tip to help the user
		// understand the user interface.
		setupButtonUI(btnPreMoveDn, "Arial", 14, 78, Pos.BASELINE_CENTER, 750 + xOffset, 342 + yOffset);
		btnPreMoveDn.setOnAction((event) -> { moveDnThePreCondition(); });
		btnPreMoveDn.setTooltip(new Tooltip("Click here to move the selected entry to the left down one position"));

		// Set up the Delete button. It removes the current selected TabkeView pre-condition. Link the 
		// button to the action method and define the tool tip to help the user understand the
		// user interface.
		setupButtonUI(btnPreDelete, "Arial", 14, 78, Pos.BASELINE_CENTER, 850 + xOffset, 342 + yOffset);
		btnPreDelete.setOnAction((event) -> { deleteThePreCondition(); });
		btnPreDelete.setTooltip(new Tooltip("Click here to delete the selected entry to the left"));

		
		
		// This positions the Task TableView and the ComboBox with their respect titles
		// onto the stage at a precise location
		setupLabelUI(theTaskListTitle, "Arial", 14, 100, Pos.BASELINE_LEFT, 360 + xOffset, 372 + yOffset);
		setupTableViewUI(theTaskList, 275, 110, 365 + xOffset, 392 + yOffset, false);

		// Establish the columns in the Table View
		taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("item"));
		theTaskList.getColumns().add(taskNameColumn);
		taskNameColumn.setMaxWidth(245);
		taskNameColumn.setMinWidth(245);

		// Setup the ComboBox+
		setupLabelUI(theTaskSelectTitle, "Arial", 14, 100, Pos.BASELINE_LEFT, 650 + xOffset, 372 + yOffset);
		setupComboBoxUI(theTaskComboBox, 245, 655 + xOffset, 392 + yOffset);
		theTaskComboBox.setTooltip(new Tooltip("Click here to selected an appropriate Task"));

		// This button adds the currently selected Task to the list of Tasks used
		setupButtonUI(btnTaskInsert, "Arial", 14, 245, Pos.BASELINE_CENTER, 655 + xOffset, 424 + yOffset);
		btnTaskInsert.setOnAction((event) -> { insertTask(); });
		btnTaskInsert.setTooltip(new Tooltip("Click here to insert the above Task above the selected item in the list to the left"));

		// Set up the Move Up button. It moves the current selected Task TabkeView entry up one
		// position. Link the button to the action method and define the tool tip to help the user
		// understand the user interface.
		setupButtonUI(btnTaskMoveUp, "Arial", 14, 78, Pos.BASELINE_CENTER, 655 + xOffset, 454 + yOffset);
		btnTaskMoveUp.setOnAction((event) -> { moveUpTheTask(); });
		btnTaskMoveUp.setTooltip(new Tooltip("Click here to move the selected entry to the left up one position"));
		
		// Set up the Move Dn button. It moves the current selected Task TabkeView entry down one
		// position. Link the button to the action method and define the tool tip to help the user
		// understand the user interface.
		setupButtonUI(btnTaskMoveDn, "Arial", 14, 78, Pos.BASELINE_CENTER, 750 + xOffset, 454 + yOffset);
		btnTaskMoveDn.setOnAction((event) -> { moveDnTheTask(); });
		btnTaskMoveDn.setTooltip(new Tooltip("Click here to move the selected entry to the left down one position"));

		// Set up the Delete button. It removes the current selected Task TabkeView entry. Link the 
		// button to the action method and define the tool tip to help the user understand the
		// user interface.
		setupButtonUI(btnTaskDelete, "Arial", 14, 78, Pos.BASELINE_CENTER, 850 + xOffset, 454 + yOffset);
		btnTaskDelete.setOnAction((event) -> { deleteTheTask(); });
		btnTaskDelete.setTooltip(new Tooltip("Click here to delete the selected entry to the left"));

		
		
		// This positions the post-condition TableView and the ComboBox with their respect titles
		// onto the stage at a precise location
		setupLabelUI(thePostConditionListTitle, "Arial", 14, 100, Pos.BASELINE_LEFT, 360 + xOffset, 513 + yOffset);
		setupTableViewUI(thePostConditionList, 275, 80, 365 + xOffset, 533 + yOffset, false);

		// Establish the columns in the Table View
		postNameColumn.setCellValueFactory(new PropertyValueFactory<>("item"));
		thePostConditionList.getColumns().add(postNameColumn);
		postNameColumn.setMaxWidth(245);
		postNameColumn.setMinWidth(245);

		setupLabelUI(thePostSelectTitle, "Arial", 14, 100, Pos.BASELINE_LEFT, 650 + xOffset, 503 + yOffset);
		setupComboBoxUI(thePostSelectComboBox, 245, 655 + xOffset, 523 + yOffset);
		thePostSelectComboBox.setTooltip(new Tooltip("Click here to selected an appropriate post-condition"));

		// This button adds the currently selected Artifact to the list of Artifacts used
		setupButtonUI(btnPostInsert, "Arial", 14, 245, Pos.BASELINE_CENTER, 655 + xOffset, 555 + yOffset);
		btnPostInsert.setOnAction((event) -> { insertPostCondition(); });
		btnPostInsert.setTooltip(new Tooltip("Click here to insert the above post-condition above the selected item in the list to the left"));

		// Set up the Move Up button. It moves the current selected TabkeView entry up one
		// position. Link the button to the action method and define the tool tip to help the user
		// understand the user interface.
		setupButtonUI(btnPostMoveUp, "Arial", 14, 78, Pos.BASELINE_CENTER, 655 + xOffset, 585 + yOffset);
		btnPostMoveUp.setOnAction((event) -> { moveUpThePostCondition(); });
		btnPostMoveUp.setTooltip(new Tooltip("Click here to move the selected entry to the left up one position"));
		
		// Set up the Move Dn button. It moves the current selected TabkeView entry down one
		// position. Link the button to the action method and define the tool tip to help the user
		// understand the user interface.
		setupButtonUI(btnPostMoveDn, "Arial", 14, 78, Pos.BASELINE_CENTER, 750 + xOffset, 585 + yOffset);
		btnPostMoveDn.setOnAction((event) -> { moveDnThePostCondition(); });
		btnPostMoveDn.setTooltip(new Tooltip("Click here to move the selected entry to the left down one position"));

		// Set up the Delete button. It removes the current selected TabkeView entry. Link the 
		// button to the action method and define the tool tip to help the user understand the
		// user interface.
		setupButtonUI(btnPostDelete, "Arial", 14, 78, Pos.BASELINE_CENTER, 850 + xOffset, 585 + yOffset);
		btnPostDelete.setOnAction((event) -> { deleteThePostCondition(); });
		btnPostDelete.setTooltip(new Tooltip("Click here to delete the selected entry to the left"));


		g.getChildren().addAll(
				thePreConditionListTitle, thePreConditionList, thePreSelectTitle, thePreSelectComboBox,
				btnPreInsert, btnPreMoveUp, btnPreMoveDn, btnPreDelete, 
				
				theTaskListTitle, theTaskList, theTaskSelectTitle, theTaskComboBox, btnTaskInsert, 
				btnTaskMoveUp, btnTaskMoveDn, btnTaskDelete,
				
				thePostConditionListTitle, thePostConditionList, thePostSelectTitle, thePostSelectComboBox, 
				btnPostInsert, btnPostMoveUp, btnPostMoveDn, btnPostDelete);
	}

	
	/**********
	 * Private local method to initialize the standard fields for a JavaFX Label object
	 * 
	 * @param l		The Label object to be initialized
	 * @param ff	The font face for the label's text
	 * @param f		The font size for the label's text
	 * @param w		The minimum width for the Label
	 * @param p		The alignment for the text within the specified width
	 * @param x		The x-axis location for the Label
	 * @param y		The y-axis location for the Label
	 */
	private void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y){
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setAlignment(p);
		l.setLayoutX(x);
		l.setLayoutY(y);
	}
	
	

	/**********
	 * Private local method to initialize the standard fields for a JavaFX ComboBox object
	 * 
	 * @param c		The ComboBox object
	 * @param w		The width of the GUI element
	 * @param x		The x-axis location for the ComboBox
	 * @param y		The y-axis location for the ComboBox
	 */
	private void setupComboBoxUI(ComboBox <String> c, double w, double x, double y){
		c.setMinWidth(w);
		c.setLayoutX(x);
		c.setLayoutY(y);
	}


	/**********
	 * Private local method to initialize the standard fields for a TableView
	 * 
	 * @param a		The TableView to be initialized
	 * @param w		The minimum width for the TextArea
	 * @param h		The maximum height for the TextArea
	 * @param x		The x-axis location for the TextArea
	 * @param y		The y-axis location for the TextArea
	 * @param e		A flag that specific if the TextArea is editable
	 */
	private void setupTableViewUI(TableView<StringItem> a, double w, double h, double x, double y, boolean e){
 		a.setMinWidth(w);
		a.setMaxWidth(w);
		a.setMinHeight(h);
		a.setMaxHeight(h);
		a.setLayoutX(x);
		a.setLayoutY(y);		
		a.setEditable(e);
	}

	/**********
	 * Private local method to initialize the standard fields for a button
	 * 
	 * @param b		The Button to be initialized
	 * @param ff	The font face for the label's text
	 * @param f		The font size for the label's text
	 * @param w		The minimum width for the TextArea
	 * @param p		The alignment for the text within the specified width
	 * @param x		The x-axis location for the TextField
	 * @param y		The y-axis location for the TextField
	 */
	private void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, double y){
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);		
	}
	
	public String toString() {
		int numElements = this.getTheList().size();
		String result = "ListItem: Size = " + numElements + "; Contents = [";
		for (int ndx = 0; ndx < numElements; ndx++) {
			result += this.getTheList().get(ndx).getListItemName();
			if (ndx < numElements - 1) result += ", ";
		}
		return result + "]";
	}
	

	/**********
	 * This helper method sees if a string is NOT in the list of Conditions
	 * 
	 * @param str	The String we are looking to find
	 * 
	 * @return		true is not found, else false
	 */
	private boolean conditionIsNotAvailable(String str) {
		for (int ndx = 0; ndx < conditionsTotalList.size(); ndx++)
			if (str.contentEquals(conditionsTotalList.get(ndx))) return false;
		return true;
	}

	/**********
	 * This helper method sees if a string is NOT in the list of Tasks
	 * 
	 * @param str	The String we are looking to find
	 * 
	 * @return		true is not found, else false
	 */
	private boolean taskIsNotAvailable(String str) {
		for (int ndx = 0; ndx < tasksTotalList.size(); ndx++)
			if (str.contentEquals(tasksTotalList.get(ndx))) return false;
		return true;
	}

	/**********
	 * This helper method sees if a string is in the list of Conditions
	 * 
	 * @param str	The String we are looking to find
	 * 
	 * @return		true is not found, else false
	 */
	private boolean preConditionFound(String str) {
		for (int ndx = 0; ndx < preConditionsSelectedList.size(); ndx++)
			if (str.contentEquals(preConditionsSelectedList.get(ndx))) return true;
		return false;
	}
	
	/**********
	 * This helper method sees if a string is in the list of Conditions
	 * 
	 * @param str	The String we are looking to find
	 * 
	 * @return		true is not found, else false
	 */
	private boolean postConditionFound(String str) {
		for (int ndx = 0; ndx < postConditionsSelectedList.size(); ndx++)
			if (str.contentEquals(postConditionsSelectedList.get(ndx))) return true;
		return false;
	}

	/**********
	 * This helper method sees if a string is in the list of Tasks
	 * 
	 * @param str	The String we are looking to find
	 * 
	 * @return		true is not found, else false
	 */
	private boolean taskFound(String str) {
		for (int ndx = 0; ndx < tasksSelectedList.size(); ndx++)
			if (str.contentEquals(tasksSelectedList.get(ndx))) return true;
		return false;
	}

	
	/**********
	 * Every time this tab is activated, we must assume things have changed, so we re-populate the 
	 * lists based on the information from the other tabs.  We try to keep all of the Selected
	 * items, and will only remove one if it can't be found in the list of all items (e.g. the 
	 * TotalList)
	 * 
	 * @param cl	This is the list of conditions
	 * @param tl	This is the list of tasks
	 */
	public void repopulate(ListItem cl, ListItem tl) {
		// When we re-populate, we do not what event processing to happen
		updatingTheList = true;

		// If this is the first repopulate method call, the parameters are those passed in from 
		// the UserInterface class and we need to capture them for use when an edit action asks
		// the system to edit a previously defined Step... the conditions and the tasks may have
		// changed from the time with the step was defined and save and the time now that the
		// user wants to edit them.
		if (conditionTabList == null) {
			conditionTabList = cl;
			taskTabList = tl;
		}
		
		// Reset the two master lists of Strings to empty
		conditionsTotalList = FXCollections.observableArrayList();
		tasksTotalList = FXCollections.observableArrayList();
		
		// Copy over the condition names from the input list into the internal list
		for (int ndx = 0; ndx < cl.getTheList().size(); ndx++)
			conditionsTotalList.add(cl.getTheList().get(ndx).getListItemName());

		// Copy over the tasks names from the input list into the internal list
		for (int ndx = 0; ndx < tl.getTheList().size(); ndx++)
			tasksTotalList.add(tl.getTheList().get(ndx).getListItemName());
		
		
	
		// Scan each of the internal lists and remove any selected names in the lists that have
		// that been removed by the user since the last time this tab was accessed
		
		// Rebuild the list of current selected pre-conditions
		for (int index = 0; index < preConditionsSelectedList.size(); index++)
			if (conditionIsNotAvailable(preConditionsSelectedList.get(index))) 
				preConditionsSelectedList.remove(index--);	// Doing this post decrement is critical!
					// If you don't understand why the post decrement is needed, get help... don't
					// just remove it. Please!
		
		// Rebuild the list of current selected post-conditions
		for (int index = 0; index < postConditionsSelectedList.size(); index++)
			if (conditionIsNotAvailable(postConditionsSelectedList.get(index))) 
				postConditionsSelectedList.remove(index--);
		
		// Rebuild the list of current selected tasks
		for (int index = 0; index < tasksSelectedList.size(); index++)
			if (taskIsNotAvailable(tasksSelectedList.get(index))) 
				tasksSelectedList.remove(index--);
		
		
		// Rebuild the available lists using the total list and the selected list
		// Start by copying the total list into the available list
		// Then remove any items from the available list that are in the selected list
		
		// Rebuild the pre-conditions available list
		preConditionsAvailableList = FXCollections.observableArrayList(conditionsTotalList);
		
		for (int index = 0; index < preConditionsAvailableList.size(); index++)
			if (preConditionFound(preConditionsAvailableList.get(index))) 
				preConditionsAvailableList.remove(index--);
		
		
		// Rebuild the post-conditions available list
		postConditionsAvailableList = FXCollections.observableArrayList(conditionsTotalList);

		for (int index = 0; index < postConditionsAvailableList.size(); index++)
			if (postConditionFound(postConditionsAvailableList.get(index))) 
				postConditionsAvailableList.remove(index--);
		
		
		// Rebuild the tasks available list
		tasksAvailableList = FXCollections.observableArrayList(tasksTotalList);
		
		for (int index = 0; index < tasksAvailableList.size(); index++)
			if (taskFound(tasksAvailableList.get(index))) 
				tasksAvailableList.remove(index--);
		
		
		// Populate the ComboBoxes using the available lists
		thePreSelectComboBox.setItems(preConditionsAvailableList);
		thePreSelectComboBox.getSelectionModel().clearAndSelect(0);
		thePostSelectComboBox.setItems(postConditionsAvailableList);
		thePostSelectComboBox.getSelectionModel().clearAndSelect(0);
		theTaskComboBox.setItems(tasksAvailableList);
		theTaskComboBox.getSelectionModel().clearAndSelect(0);
		
		// Re-populate the Table Views
		// Rebuild the <StringItem> pre list Table View
		ObservableList <StringItem> pre = thePreConditionList.getItems();
		pre.clear();
		for (int ndx = 0; ndx < preConditionsSelectedList.size(); ndx++)
			pre.add(new StringItem(preConditionsSelectedList.get(ndx)));
		thePreConditionList.setItems(pre);

		// Rebuild the  <StringItem> task list Table View
		ObservableList <StringItem> task = theTaskList.getItems();
		task.clear();
		for (int ndx = 0; ndx < tasksSelectedList.size(); ndx++)
			task.add(new StringItem(tasksSelectedList.get(ndx)));
		theTaskList.setItems(task);
		
		// Rebuild the <StringItem> post list Table View
		ObservableList <StringItem> post = thePostConditionList.getItems();
		post.clear();
		for (int ndx = 0; ndx < postConditionsSelectedList.size(); ndx++)
			post.add(new StringItem(postConditionsSelectedList.get(ndx)));
		thePostConditionList.setItems(post);
		
		// We are done re-populating, so event processing can now happen again
		updatingTheList = false;
		
		
		// Debugging: Did we create the lists properly?
/*		
		System.out.println("Conditions Total:\n" + conditionsTotalList + "\n");
		System.out.println("Pre-condition Selected:\n" + preConditionsSelectedList + "\n");
		System.out.println("Pre-condition Available:\n" + preConditionsAvailableList + "\n");
		System.out.println("Post-condition Selected:\n" + postConditionsSelectedList + "\n");
		System.out.println("Post-condition Available:\n" + postConditionsAvailableList + "\n");
		System.out.println("Tasks Total:\n" + tasksTotalList + "\n");
		System.out.println("Tasks Selected:\n" + tasksSelectedList + "\n");
		System.out.println("Tasks Available:\n" + tasksAvailableList + "\n");
*/
	}
	
	/**********
	 * Shift the specified String from the Available List to the Selected List and then set up the 
	 * Table View to reflect this action
	 * 
	 * @param str	The string to be moved from the Available to the Selected List
	 * @param src	The Available List
	 * @param dst	The Selected List
	 */
	private void shift(TableView <StringItem> tvl, String str, ObservableList <String> src, ObservableList <String> dst) {
		// Scan through the Available List, find the String, and remove it from the list
		for (int ndx = 0; ndx < src.size(); ndx++)
			if (src.get(ndx).equals(str)) {
				src.remove(ndx);
				break;
			}
		
		// Add the String to the end of the Selected List
		dst.add(str);
		
		// Fetch the list of items from the Table View GUI item and change it to agree with the
		// new Selected List
		ObservableList<StringItem> de = tvl.getItems();	// Fetch the list
		de.clear();												// Clear it
		int lastNdx = 0;										// Keep track of the insert index
		for (int i = 0; i < dst.size(); i++) {
			if (dst.get(i).equals(str))
				lastNdx = i;
			// Create a new item entry based on those values
			StringItem def = new StringItem(dst.get(i));
			// Add the new item entry at the end of the list
			de.add(def);
		}
		
		// Insert the new list into the Table View, Scroll to the point of insertion, and select it
		tvl.setItems(de);
		tvl.scrollTo(lastNdx);
		tvl.getSelectionModel().clearAndSelect(lastNdx);
	}
	
	
	/**********
	 * The method is used to shift an items back from the Selected List to the Available List when
	 * a selected items is deleted.
	 * 
	 * @param str	The name to be shifted back from the Selected List to the Available List
	 * @param mstr	The Master list, so the new item is inserted in the right place
	 * @param avl	The Available List
	 * @param sel	The Selected List
	 */
	private void shiftBack(ComboBox <String> cb, String str, ObservableList <String> mstr, ObservableList <String> avl, ObservableList <String> sel) {
		// We will scan through the Master List which gives the order of items that the user specified
		// The Master List should be the union of the Available List and the Selected List
		int theSize = mstr.size();	// Set up the size of the master list
		int availableIndex = 0;		// Establish the index for the Available List
		int selectIndex = 0;		// Establish the index for the Selected List

		// The Master list elements are either in the Available List or the Selected List.  The
		// order of the items in the Available and Selected List match the order in the Master List.
		// We go through the Master list starting at the beginning and examine each item for the
		// that list.  We know it will be in one or the other
		for (int masterIndex = 0; masterIndex < theSize; masterIndex++)
			// If the item from the master list matched the specified String, we know it must be in
			// in the Selected List and so we can remove it.
			if (str.equals(mstr.get(masterIndex))) {
				// Remove it from the selected list at this point
				sel.remove(selectIndex);
				
				// The item has been found. Add it to the available list at this point.
				avl.add(availableIndex, str);
				
				// Once the shift has been performed, there is no need to continue the loop
				break;
			}
			else {
				// If the Specified String does not match the element from the Master list, it
				// must be in either the Selected List or the Available list.
				// 
				// So, we see if it is in the Available list.
				if (availableIndex < avl.size() && mstr.get(masterIndex).contentEquals(avl.get(availableIndex)))
					// If it is in the Available List, we increment that index
					availableIndex++;
				else
					// Otherwise if must be in the Selected List, so we increment that index
					selectIndex++;
			}
		
		// With the shift back performed, we reset the comboBox and select what we just put back
		cb.setItems(avl);
		cb.getSelectionModel().clearAndSelect(availableIndex);
	}

	
	/**********
	 * This method insert a new row into the specified Table View and updated the associated lists accordingly
	 */
	private void insertAction(TableView <StringItem> tvl, ComboBox <String> cb, ObservableList <String> src, ObservableList <String> dst) {
		if (updatingTheList) return;
		// This avoids needless repetitive calls due to programmatic requests
		updatingTheList = true;
		
		// Check to be sure there is an item in the ComboBox to insert
		if (src.size()<1) {
			Alert alert = new Alert(AlertType.WARNING, "Insert is only valid when there's an item to insert!", ButtonType.OK);
			alert.showAndWait();
			updatingTheList = false;
			return;
		}
		
		// Signal that a change has been made and do the change
		showTheContentChangedMessage();

		String str = cb.getValue();
		shift(tvl, str, src, dst);
		
		// Select the first item in the Available list
		cb.getSelectionModel().clearAndSelect(0);
		
		// We are done updating the lists
		updatingTheList = false;	
	}
	
	/**********
	 * This method moves the currently selected item up in the TableView list.
	 */
	private void moveUpAction(TableView <StringItem> tvl) {
		// Fetch the index of the selected row in the TableView
		int theIndex = tvl.getSelectionModel().getSelectedIndex();
		
		// If no row is selected, give a warning and ignore the request
		if (theIndex == -1) {
			Alert alert = new Alert(AlertType.WARNING, "Move Up is only valid when a row has been selected!", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		// If the selected row is the top most row, it is not possible to move it up, so give an
		// appropriate warning and ignore the request
		if (theIndex == 0) {
			Alert alert = new Alert(AlertType.WARNING, "Move Up is not valid for the first row!", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		// Updating the list is indeed happening
		updatingTheList = true;
		
		// Make the Content Changed Message visible
		showTheContentChangedMessage();
		
		// Given a valid and proper index, produce an observable list from the TableView and swap
		// the selected item with the one above it.
		ObservableList<StringItem> de = tvl.getItems();
		StringItem tempDE = de.get(theIndex-1);
		de.set(theIndex-1, de.get(theIndex));
		de.set(theIndex, tempDE);
		
		// Place the updated list back into the table view
		tvl.setItems(de);
		
		// Select the entry that was moved up and make sure it is visible
		tvl.scrollTo(theIndex-1);
		tvl.getSelectionModel().select(theIndex-1);
		
		// Done updating the list
		updatingTheList = false;
	}

	/**********
	 * This method moves the currently selected item down in the TableView list.
	 */
	private void moveDnAction(TableView <StringItem> tvl) {
		// Fetch the index of the selected row in the TableView
		int theIndex = tvl.getSelectionModel().getSelectedIndex();
		
		// If no row is selected, give a warning and ignore the request
		if (theIndex == -1) {
			Alert alert = new Alert(AlertType.WARNING, "Move Dn is only valid when a row has been selected!", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		// If the selected row is the bottom row, it is not possible to move it down, so give an
		// appropriate warning and ignore the request
		ObservableList<StringItem> de = tvl.getItems();
		if (theIndex == de.size()-1) {
			Alert alert = new Alert(AlertType.WARNING, "Move Up is not valid for the last row!", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		// Updating the list
		updatingTheList = true;
		
		// Make the Content Changed Message visible
		showTheContentChangedMessage();		
		
		// Given a valid and proper index, produce an observable list from the TableView and swap
		// the selected item with the one above it.
		StringItem tempDE = de.get(theIndex+1);
		de.set(theIndex+1, de.get(theIndex));
		de.set(theIndex, tempDE);

		// Place the updated list back into the table view
		tvl.setItems(de);
		
		// Select the entry that was moved down and make sure it is visible
		tvl.scrollTo(theIndex+1);
		tvl.getSelectionModel().select(theIndex+1);
		
		// Done updating the list
		updatingTheList = false;
	}

	/**********
	 * This method deletes the currently selected item in the TableView list and updates all the associated lists.
	 */
	private void deleteAction(TableView <StringItem> tvl, ComboBox <String> cb, ObservableList <String> mstr, ObservableList <String> sel, ObservableList <String> avl) {
		// Fetch the index of the selected row in the TableView
		int theIndex = tvl.getSelectionModel().getSelectedIndex();
		
		// If no row is selected, give a warning and ignore the request
		if (theIndex == -1) {
			Alert alert = new Alert(AlertType.WARNING, "Delete is only valid when a row has been selected!", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		// Updating the list
		updatingTheList = true;
		
		showTheContentChangedMessage();
		
		// If beyond the end of the list just return (This should never happen,
		// so we have not spent effort raising a warning.)
		ObservableList<StringItem> de = tvl.getItems();
		if (theIndex > de.size()-1) return;
		
		// Save the name that is being removed
		String removedName = de.get(theIndex).getItem();
		
		// Remove the item from the TableView list, shift any below up.
		de.remove(theIndex);
		
		// Place the updated list back into the TableView
		tvl.setItems(de);
		
		// De-select the TableView so no entry is selected
		tvl.getSelectionModel().select(-1);

		// Add the deleted item back into the available list and update that comboBox
		shiftBack(cb, removedName, mstr, avl, sel);
		
		// Done updating the list
		updatingTheList = false;
	}

	
	
	private void insertPreCondition() {
		insertAction(thePreConditionList, thePreSelectComboBox, preConditionsAvailableList, preConditionsSelectedList);
	}
	
	private void moveUpThePreCondition() {
		moveUpAction(thePreConditionList);
	}
	
	private void moveDnThePreCondition() {
		moveDnAction(thePreConditionList);
	}
	
	private void deleteThePreCondition() {
		deleteAction(thePreConditionList, thePreSelectComboBox, conditionsTotalList, preConditionsSelectedList, preConditionsAvailableList);
	}

	
	
	private void insertTask() {
		insertAction(theTaskList, theTaskComboBox, tasksAvailableList, tasksSelectedList);
	}
	
	private void moveUpTheTask() {
		moveUpAction(theTaskList);
	}
	
	private void moveDnTheTask() {
		moveDnAction(theTaskList);
	}
	
	private void deleteTheTask() {
		deleteAction(theTaskList, theTaskComboBox, tasksTotalList, tasksSelectedList, tasksAvailableList);
	}

	
	
	private void insertPostCondition() {
		insertAction(thePostConditionList, thePostSelectComboBox, postConditionsAvailableList, postConditionsSelectedList);
	}
	
	private void moveUpThePostCondition() {
		moveUpAction(thePostConditionList);
	}
	
	private void moveDnThePostCondition() {
		moveDnAction(thePostConditionList);
	}
	
	private void deleteThePostCondition() {
		deleteAction(thePostConditionList, thePostSelectComboBox, conditionsTotalList, postConditionsSelectedList, postConditionsAvailableList);
	}
	

	/**********
	 * Handle the editTheEntry Button action by doing the super class function and then the Tab
	 * specific work
	 */	
	protected void editTheEntry() {
		super.editTheEntry();
		if (tabSpecificIndex == -1) return;
		
		// If the above generic code was successful, process the following specific code
		StepDetail stepTemp = theStepDetailList.get(tabSpecificIndex);
		preConditionsSelectedList = stepTemp.selectedPreList;
		tasksSelectedList = stepTemp.selectedTaskList;
		postConditionsSelectedList = stepTemp.selectedPostList;
		
		// Re-populate the lists
		repopulate(conditionTabList, taskTabList);
	}

	/**********
	 * Handle the saveTheEntry Button action by doing the super class function and then the Tab
	 * specific work
	 */	
	protected void saveTheEntry() {
		super.saveTheEntry();
		if (tabSpecificIndex == -1) return;
		hideTheContentChangedMessage();
		StepDetail stepTemp = new StepDetail(preConditionsSelectedList, tasksSelectedList, postConditionsSelectedList);
		theStepDetailList.set(tabSpecificIndex, stepTemp);
	}
	
	/**********
	 * Handle the AddTheEntryToTheBottom Button action by doing the super class function and then the Tab
	 * specific work
	 */	
	protected void AddTheEntryToTheBottom() {
		super.AddTheEntryToTheBottom();
		if (tabSpecificIndex == -1) return;
		StepDetail stepTemp = new StepDetail(preConditionsSelectedList, tasksSelectedList, postConditionsSelectedList);
		theStepDetailList.add(tabSpecificIndex, stepTemp);
	}
	
	/**********
	 * Handle the AddTheEntryAbove Button action by doing the super class function and then the Tab
	 * specific work
	 */	
	protected void AddTheEntryAbove() {
		super.AddTheEntryAbove();
		if (tabSpecificIndex == -1) return;
		StepDetail stepTemp = new StepDetail(preConditionsSelectedList, tasksSelectedList, postConditionsSelectedList);
		theStepDetailList.add(tabSpecificIndex, stepTemp);
	}
	
	/**********
	 * Handle the AddTheEntryBelow Button action by doing the super class function and then the Tab
	 * specific work
	 */	
	protected void AddTheEntryBelow() {
		super.AddTheEntryBelow();
		if (tabSpecificIndex == -1) return;
		StepDetail stepTemp = new StepDetail(preConditionsSelectedList, tasksSelectedList, postConditionsSelectedList);
		theStepDetailList.add(tabSpecificIndex+1, stepTemp);
	}
	
	/**********
	 * Handle the clearTheEntry Button action by doing the super class function and then the Tab
	 * specific work
	 */	
	protected void clearTheEntry() {
		super.clearTheEntry();
		if (tabSpecificIndex == -1) return;
	}

	
	
	/**********
	 * This nested protected Class is used to populate the rightmost TableView GUI.  It is just a 
	 * list of String objects.  The methods for this class are totally obvious, so I'll not 
	 * document them.
	 * 
	 * @author Lynn Robert Carter
	 *
	 */
	protected class StringItem {
		private String item = "";
		
		public StringItem(String s) {
			item = s;
		}
		
		public String toString() {
			return item;
		}
		
		public String getItem() {
			return item;
		}
		
		public void setItem(String s) {
			item = s;
		}
	}
	

	/**********
	 * This nested protected Class is used to define a specific Step with a set of three
	 * lists.  Each of the three is just a list of String objects, but each time this tab is 
	 * entered these three lists must be set up.  The methods for this class are totally obvious,
	 * so I'll not document them.
	 * 
	 * @author Lynn Robert Carter
	 *
	 */
	protected class StepDetail {
		private ObservableList<String> selectedPreList = FXCollections.observableArrayList();
		private ObservableList<String> selectedTaskList = FXCollections.observableArrayList();
		private ObservableList<String> selectedPostList = FXCollections.observableArrayList();
		
		public StepDetail(ObservableList<String> pre, ObservableList<String> task, ObservableList<String> post) {
			// Make shallow copies of each of the four lists 
			selectedPreList = FXCollections.observableArrayList(pre);
			selectedTaskList = FXCollections.observableArrayList(task);
			selectedPostList = FXCollections.observableArrayList(post);
		}
		
		public String toString() {
			return "\nPre:    " + selectedPreList + "\nTask:     " + selectedTaskList + "\nPost:     " + selectedPostList;
		}
		
		public ObservableList<String> getSelectedPreList() {
			return selectedPreList;
		}

		public void setSelectedPreList(ObservableList<String> sPre) {
			selectedPreList = sPre;
		}
		
		public ObservableList<String> getSelectedTaskList() {
			return selectedTaskList;
		}

		public void setSelectedTaskList(ObservableList<String> sTask) {
			selectedPostList = sTask;
		}
		
		public ObservableList<String> getSelectedPostList() {
			return selectedPostList;
		}

		public void setSelectedPostList(ObservableList<String> sPost) {
			selectedPostList = sPost;
		}
	}
}
