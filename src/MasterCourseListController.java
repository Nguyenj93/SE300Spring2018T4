import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import static javafx.event.ActionEvent.ACTION;


public class MasterCourseListController {

    private static final MasterCourseList masterCourseList = MasterCourseList.get();

    public static class CreateCourseWindow implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            CreateMasterCourseWindow.init();
            CreateMasterCourseWindow.show();
        }
    }

    public static class AddCourse implements EventHandler<ActionEvent> {

        private TextField IDField;
        private TextField nameField;
        private ChoiceBox<Integer> numCreditsField;
        private TextField prereqsField;
        private TextField coreqsField;

        public AddCourse(TextField IDField, TextField nameField, ChoiceBox<Integer> numCreditsField,
                         TextField prereqsField, TextField coreqsField) {
            this.IDField = IDField;
            this.nameField = nameField;
            this.numCreditsField = numCreditsField;
            this.prereqsField = prereqsField;
            this.coreqsField = coreqsField;
        }

        @Override
        public void handle(ActionEvent e) {
            BaseCourse course = new BaseCourse(IDField.getText(), nameField.getText(), numCreditsField.getValue());
            if (verifyID() && verifyName()) {
                masterCourseList.addCourse(course);
                IDField.clear();
                nameField.clear();
                numCreditsField.setValue(3);
                // TODO do something with prereqs and coreqs
            }
            // TODO do something when false
        }

        private boolean verifyID() {
            String currentText = IDField.getText();
            IDField.setText(currentText.toUpperCase());
            currentText = IDField.getText();

            return currentText.matches("[A-Z]{2,5} [0-9]{3}");
        }

        private boolean verifyName() {
            return true;
        }
    }

    public static class DeleteCourse implements EventHandler<ActionEvent> {

        private TableView.TableViewSelectionModel<BaseCourse> selectionModel;

        public DeleteCourse(TableView.TableViewSelectionModel<BaseCourse> selectionModel) {
            this.selectionModel = selectionModel;
        }

        @Override
        public void handle(ActionEvent e) {
            BaseCourse course = selectionModel.getSelectedItem();
            // popup to confirm
            masterCourseList.removeCourse(course);
        }
    }
}
