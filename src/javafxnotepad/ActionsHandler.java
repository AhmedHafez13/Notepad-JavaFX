package javafxnotepad;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

class ActionsHandler {
	private Stage stage;
	private ViewsCreator views;
	private Notepad notepad;

	ActionsHandler(Stage stage, Notepad notepad) {
		this.stage = stage;
		this.notepad = notepad;
	}

	void setViewsCreator(ViewsCreator viewsCreator) {
		this.views = viewsCreator;
	}

	/*
	 * ----- * ----- * ----- * ----- *
	 * File menu items
	 * ----- * ----- * ----- * ----- *
	 */

	void handleNew() {
		// TODO: Check if the current opened file need to be saved
		// TODO: Clear the text area
		
		// TODO: set opened file to null if new file is created
		boolean canceled = showSaveDialog();
		if (!canceled) {
			notepad.setOpenedFile(null);
			views.getTextArea().clear();
			notepad.setFileSaved(true);
		}

		System.out.println("handleNew, menu item clicked!");
	}

	void handleOpen() {
		// TODO: Check if the current opened file need to be saved
		boolean canceled = showSaveDialog();
		if (!canceled) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().add(new ExtensionFilter("Text files", "*.txt"));
			File file = fileChooser.showOpenDialog(stage);
			if (file != null) {
				views.getTextArea().setText(StorageManager.readFile(file));
				notepad.setOpenedFile(file);
				notepad.setFileSaved(true);
			}
		}
		System.out.println("handleOpen, menu item clicked!");
	}

	/**
	 * @return true if the process successfully done, false otherwise
	*/
	boolean handleSave() {
		// TODO: Save the file in the choosed file
		String data = views.getTextArea().getText();
		if (data.length() == 0)
			return true;

		File file = notepad.getOpenedFile();
		if (file == null) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().add(new ExtensionFilter("Text files", "*.txt"));
			file = fileChooser.showSaveDialog(stage);
		}
		if (file != null) {
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					ViewsCreator.showMessageDialog("Save file", "Error can't save the file!");
					e.printStackTrace();
					return false;
				}
			}
			notepad.setOpenedFile(file);
			notepad.setFileSaved(true);
			StorageManager.writeFile(file, data);
			return true;
		}
		System.out.println("handleSave, menu item clicked!");
		return false;
	}

	void handleExit() {
		// TODO: Check if the file need to be saved
		boolean canceled = showSaveDialog();
		if (!canceled) {
			stage.close();
		}
		System.out.println("handleExit, menu item clicked!");
	}

	void onTextChange() {
		notepad.setFileSaved(false);
	}

	/*
	 * ----- * ----- * ----- * ----- *
	 * Edit menu items
	 * ----- * ----- * ----- * ----- *
	 */

	void handleUndo() {
		// TODO: ??
		views.getTextArea().undo();
		System.out.println("handleUndo, menu item clicked!");
	}

	void handleCut() {
		// TODO: Check if there is selected text
		// TODO: cut selected text if found
		views.getTextArea().cut();
		System.out.println("handleCut, menu item clicked!");
	}

	void handleCopy() {
		// TODO: Check if there is selected text
		// TODO: copy selected text if found
		views.getTextArea().copy();
		System.out.println("handleCopy, menu item clicked!");
	}

	void handlePaste() {
		// TODO: Paste text from the clipboard
		views.getTextArea().paste();
		System.out.println("handlePaste, menu item clicked!");
	}

	void handleDelete() {
		// TODO: Check if there is selected text
		// TODO: delete selected text if found
		views.getTextArea().replaceSelection("");
		System.out.println("handleDelete, menu item clicked!");
	}

	void handleSelectAll() {
		views.getTextArea().selectAll();
		System.out.println("handleSelectAll, menu item clicked!");
	}

	/*
	 * ----- * ----- * ----- * ----- *
	 * Help menu items
	 * ----- * ----- * ----- * ----- *
	 */

	void handleAbout() {
		ViewsCreator.showMessageDialog("About", "By Ahmed Hafez from ITI");
		System.out.println("handleAbout, menu item clicked!");
	}
	
	/*
	 * ----- * ----- * ----- * ----- *
	 * Confirmatin Dialog
	 * ----- * ----- * ----- * ----- *
	 */

	/**
	 * @return true if the user chooses 'Cancel', false otherwise
	*/
	private boolean showSaveDialog() {
		if (notepad.isFileSaved())
			return false;

		Optional<ButtonType> result = ViewsCreator.showSaveDialog("Save", "Do you want to save the file?");
		if (result.isPresent() && result.get() == ButtonType.YES) {
			System.out.println("ButtonType.YES");
			return !handleSave();

		} else if (result.isPresent() && result.get() == ButtonType.NO) {
			System.out.println("ButtonType.NO");
			
		} else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
			System.out.println("ButtonType.CANCEL");
			return true;
		}

		return false;
	}
}
