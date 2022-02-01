package javafxnotepad;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author Ahmed Hafez
 */
public class Notepad extends Application {
	ViewsCreator viewsCreator;

	private File openedFile = null;
	private boolean isFileSaved = true;

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		BorderPane borderPane = new BorderPane();

		initializeComponents(borderPane, primaryStage);

		Scene scene = new Scene(borderPane/* , 300, 250 */);

		primaryStage.setTitle("Notepad");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void initializeComponents(BorderPane pane, Stage primaryStage) {
		ActionsHandler actionsHandler = new ActionsHandler(primaryStage, this);
		viewsCreator = new ViewsCreator(actionsHandler);
		actionsHandler.setViewsCreator(viewsCreator);

		pane.setTop(new MenuBar(
				viewsCreator.createFileMenu(),
				viewsCreator.createEditMenu(),
				viewsCreator.createHelpMenu()));

		pane.setCenter(viewsCreator.createEditArea());
	}
	
	void setOpenedFile(File file) {
		this.openedFile = file;
	}

	File getOpenedFile() {
		return openedFile;
	}

	void setFileSaved(boolean isSaved) {
		isFileSaved = isSaved;
	}

	boolean isFileSaved() {
		return isFileSaved;
	}
}
