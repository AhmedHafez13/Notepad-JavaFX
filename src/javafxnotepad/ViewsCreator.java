package javafxnotepad;

import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCombination;

public class ViewsCreator {
	private ActionsHandler actionsHandler;
	private TextArea textArea;

	ViewsCreator(ActionsHandler actionsHandler) {
		this.actionsHandler = actionsHandler;
	}

	TextArea getTextArea() {
		return textArea;
	}

	Menu createFileMenu() {
		MenuItem newMenuItem = new MenuItem("New");
		newMenuItem.setAccelerator(KeyCombination.keyCombination("ctrl+n"));
		newMenuItem.setOnAction(event -> actionsHandler.handleNew());

		MenuItem openMenuItem = new MenuItem("Open");
		openMenuItem.setAccelerator(KeyCombination.keyCombination("ctrl+o"));
		openMenuItem.setOnAction(event -> actionsHandler.handleOpen());

		MenuItem saveMenuItem = new MenuItem("Save");
		saveMenuItem.setAccelerator(KeyCombination.keyCombination("ctrl+s"));
		saveMenuItem.setOnAction(event -> actionsHandler.handleSave());

		MenuItem exitMenuItem = new MenuItem("Exit");
		exitMenuItem.setAccelerator(KeyCombination.keyCombination("ctrl+e"));
		exitMenuItem.setOnAction(event -> actionsHandler.handleExit());

		Menu fileMenu = new Menu("File");
		fileMenu.getItems().addAll(newMenuItem, openMenuItem, saveMenuItem,
				new SeparatorMenuItem(), exitMenuItem);
		return fileMenu;
	}

	Menu createEditMenu() {
		MenuItem undoItem = new MenuItem("Undo");
		undoItem.setAccelerator(KeyCombination.keyCombination("ctrl+z"));
		undoItem.setOnAction(event -> actionsHandler.handleUndo());

		MenuItem cutItem = new MenuItem("Cut");
		cutItem.setAccelerator(KeyCombination.keyCombination("ctrl+x"));
		cutItem.setOnAction(event -> actionsHandler.handleCut());

		MenuItem copyItem = new MenuItem("Copy");
		copyItem.setAccelerator(KeyCombination.keyCombination("ctrl+c"));
		copyItem.setOnAction(event -> actionsHandler.handleCopy());

		MenuItem pasteItem = new MenuItem("Paste");
		pasteItem.setAccelerator(KeyCombination.keyCombination("ctrl+v"));
		pasteItem.setOnAction(event -> actionsHandler.handlePaste());

		MenuItem deleteItem = new MenuItem("Delete");
		deleteItem.setAccelerator(KeyCombination.keyCombination("ctrl+d"));
		deleteItem.setOnAction(event -> actionsHandler.handleDelete());

		MenuItem selectAllItem = new MenuItem("Select All");
		selectAllItem.setAccelerator(KeyCombination.keyCombination("ctrl+a"));
		selectAllItem.setOnAction(event -> actionsHandler.handleSelectAll());

		Menu editMenu = new Menu("Edit");
		editMenu.getItems().addAll(undoItem, new SeparatorMenuItem(),
				cutItem, copyItem, pasteItem, deleteItem,
				new SeparatorMenuItem(), selectAllItem);
		return editMenu;
	}

	Menu createHelpMenu() {
		MenuItem aboutItem = new MenuItem("About Notepad");
		aboutItem.setOnAction(event -> actionsHandler.handleAbout());

		Menu editMenu = new Menu("File");
		editMenu.getItems().addAll(aboutItem);
		return editMenu;
	}

	TextArea createEditArea() {
		textArea = new TextArea();
		textArea.setPrefColumnCount(50);
		textArea.setPrefRowCount(20);

		textArea.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				actionsHandler.onTextChange();
			}
		});

		return textArea;
	}

	static void showMessageDialog(String title, String content) {
		Dialog<String> dialog = new Dialog<>();
		dialog.setTitle(title);
		dialog.setContentText(content);
		dialog.getDialogPane().getButtonTypes().add(new ButtonType("Ok"));
		dialog.show();
	}

	static Optional<ButtonType> showSaveDialog(String title, String content) {
		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.setTitle(title);
		dialog.setContentText(content);
		dialog.getDialogPane().getButtonTypes()
				.addAll(ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		return dialog.showAndWait();
	} // Yes No Cancel
}
