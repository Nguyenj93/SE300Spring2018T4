import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

import static javafx.event.ActionEvent.ACTION;

/**
 * @author Christopher McFall
 *
 * Controller for the master course list. Any actions for the master course list from the gui must go through this class
 */
public class MasterCourseListController {

    private static final MasterCourseList masterCourseList = MasterCourseList.get();

    public static void openNewCourseWindow() {
        MasterCourseWindowDialog.init();
        MasterCourseWindowDialog.show();
    }

    public static void deleteCourse(TableView.TableViewSelectionModel<BaseCourse> selection) {
        BaseCourse course = selection.getSelectedItem();
        masterCourseList.removeCourse(course);
    }

    public static void addCourse(TextField IDField, TextField nameField, ChoiceBox<Integer> numCreditsField,
                                 ComboBox<BaseCourse> pre1, ComboBox<BaseCourse> pre2,
                                 ComboBox<BaseCourse> co1, ComboBox<BaseCourse> co2) {

        String currentText = IDField.getText();
        IDField.setText(currentText.toUpperCase());
        currentText = IDField.getText();

        boolean validID = currentText.matches("[A-Z]{2,3} [0-9]{3}L?");

        if (validID) {
            ArrayList<BaseCourse> prereqs = new ArrayList<>(2);
            ArrayList<BaseCourse> coreqs = new ArrayList<>(2);

            BaseCourse pre1Selection = pre1.getSelectionModel().getSelectedItem();
            BaseCourse pre2Selection = pre2.getSelectionModel().getSelectedItem();
            BaseCourse co1Selection = co1.getSelectionModel().getSelectedItem();
            BaseCourse co2Selection = co2.getSelectionModel().getSelectedItem();

            String courseIdString[] = IDField.getText().split(" ");
            DepartmentID id = DepartmentID.valueOf(courseIdString[0]);
            int courseNum = Integer.parseInt(courseIdString[1]);
            BaseCourse course = new BaseCourse(id, courseNum, nameField.getText(), numCreditsField.getValue());
            if (pre1Selection != null) {
                course.addPrereq(pre1Selection);
            }
            if (pre2Selection != null) {
                course.addPrereq(pre2Selection);
            }
            if (co1Selection != null) {
                course.addCoreq(co1Selection);
            }
            if (co2Selection != null) {
                course.addCoreq((co2Selection));
            }

            masterCourseList.addCourse(course);
            IDField.clear();
            nameField.clear();

            pre1.getSelectionModel().clearSelection();
            pre2.getSelectionModel().clearSelection();
            co1.getSelectionModel().clearSelection();
            co2.getSelectionModel().clearSelection();
            numCreditsField.setValue(3);
        }
    }
}
