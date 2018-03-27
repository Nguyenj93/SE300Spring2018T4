import javafx.collections.ObservableList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonalPlanTest {

    PersonalPlan plan;
    Semester sem1;
    Semester sem2;
    Semester sem3;
    BaseCourse course1;
    BaseCourse course2;
    BaseCourse course3;

    @BeforeEach
    void setUp() {
        plan = new PersonalPlan();
        sem1 = new Semester();
        sem2 = new Semester();
        sem3 = new Semester();
        course1 = new BaseCourse("Test 101", "Test Course", 3);
        course2 = new BaseCourse("Test 201", "Test Course", 3);
        course3 = new BaseCourse("Test 301", "Test Course", 3);
        sem1.addCourse(course1);
        sem2.addCourse(course2);
        sem2.addCourse(course3);
    }

    @Test
    void addSemesterTest() {
        plan.addSemester(sem1);
        assertTrue(plan.getSemesters().contains(sem1), "Error adding semester");
        plan.addSemester(sem2);
        plan.addSemester(sem3);
        assertTrue(plan.getSemesters().contains(sem2), "Error adding second semester");
        assertTrue(plan.getSemesters().contains(sem3), "Error adding third semester");
    }

    @Test
    void addSemesterFailTest() {
        assertThrows(NullPointerException.class, () -> {
            plan.addSemester(null);
        });
    }

    @Test
    void removeSemesterTest() {
        plan.addSemester(sem1);
        plan.addSemester(sem2);
        plan.addSemester(sem3);
        assertEquals(3, plan.getSemesters().size(), "Failed to add semesters");
        plan.removeSemester(sem2);
        assertFalse(plan.getSemesters().contains(sem2), "Failed to remove semester");
        assertEquals(2, plan.getSemesters().size(), "Failed to remove semesters");
    }

    @Test
    void getSemestersTest() {
        assertThrows(UnsupportedOperationException.class, () -> {
            plan.getSemesters().add(sem1);
        });
    }

}