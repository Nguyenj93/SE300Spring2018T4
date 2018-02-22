import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RequiredCourse {

    private BaseCourse course;

    private Grade requiredGrade;

    private ArrayList<BaseCourse> prereqsOveride;
    private ArrayList<BaseCourse> coreqsOveride;

    // disable default constructor
    private RequiredCourse() { }

    /**
     * Creates a RequiredCourse object with pre/co requisites overridden.
     *
     * @param baseCourse Course from master course list
     * @param requiredGrade Grade required for the degree program
     * @param prereqsOveride List of new prereqs
     * @param coreqsOveride List of old prereqs
     * @throws NullPointerException if baseCourse is null
     * @throws IllegalArgumentException if requiredGrade is below a D or is for a grade associated with incompletion.
     */
    public RequiredCourse(BaseCourse baseCourse, Grade requiredGrade,
                          List<BaseCourse> prereqsOveride, List<BaseCourse> coreqsOveride) {
        if (baseCourse == null) {
            throw new NullPointerException();
        }
        if (requiredGrade.getGradeValue() < 1) {
            throw new IllegalArgumentException();
        }
        course = baseCourse;
        this.requiredGrade = requiredGrade;

        if (prereqsOveride == null) {
            this.prereqsOveride = null;
        } else {
            this.prereqsOveride = new ArrayList<>(prereqsOveride);
        }

        if (coreqsOveride == null) {
            this.coreqsOveride = null;
        } else {
            this.coreqsOveride = new ArrayList<>(coreqsOveride);
        }
    }

    /**
     * @param baseCourse Course from master course list
     * @param requiredGrade Grade required for the degree program
     * @throws NullPointerException if baseCourse is null
     * @throws IllegalArgumentException if requiredGrade is below a D or is for a grade associated with incompletion.
     */
    public RequiredCourse(BaseCourse baseCourse, Grade requiredGrade) {
        this(baseCourse, requiredGrade, null, null);
    }

    /**
     * Creates a RequiredCourse object with a required grade of D.
     * @param baseCourse Course from master course list
     * @throws NullPointerException if baseCourse is null
     */
    public RequiredCourse(BaseCourse baseCourse) {
        this(baseCourse, Grade.D);
    }

    public Grade getRequiredGrade() {
        return requiredGrade;
    }

    public List<BaseCourse> getPrereqs() {
        if (prereqsOveride == null) {
            return course.getPrereqs();
        } else {
            return Collections.unmodifiableList(prereqsOveride);
        }
    }

    public List<BaseCourse> getCoreqs() {
        if (coreqsOveride == null) {
            return course.getCoreqs();
        } else {
            return Collections.unmodifiableList(coreqsOveride);
        }
    }

}