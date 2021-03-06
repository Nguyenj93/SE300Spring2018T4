import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

    private Course course;

    @BeforeEach
    void setUp() {
        BaseCourse baseCourse1 = new BaseCourse(DepartmentID.AF, 101, "Test", 3);
        course = new Course(baseCourse1);
    }

    @Test
    void constructorFailTest() {
        assertThrows(NullPointerException.class, () -> {
            course = new Course(null);
        });
    }

    @Test
    void setGrade() {
        assertEquals(Grade.NONE, course.getGrade());
        course.setGrade(Grade.B);
        assertEquals(Grade.B, course.getGrade());
    }
}