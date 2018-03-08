import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Christopher McFall
 *
 * Data class that stores information about an ERAU course. BaseCourse objects are meant to be kept in a master list
 * to be referenced by courses required by a major, minor, or elective, or by courses planned in an academic plan.
 */
public class BaseCourse implements Serializable, Comparable<BaseCourse> {

    // This keeps the serialization backwards compatable if we add or change methods.
    static final long serialVersionUID = -3027139528909119438L;

    private String ID;
    private String name;
    private int numCredits;

    private ArrayList<BaseCourse> prereqs;
    private ArrayList<BaseCourse> coreqs;

    // disable default constructor
    private BaseCourse() { };

    /**
     * @throws NullPointerException if ID or name are null
     * @throws IllegalArgumentException if numCredits is not between 0 and 6
     */
    public BaseCourse(String ID, String name, int numCredits, List<BaseCourse> prereqs, List<BaseCourse> coreqs) {
        setID(ID);
        setName(name);
        setNumCredits(numCredits);

        if (prereqs == null) {
            this.prereqs = new ArrayList<>(0);
        } else {
            this.prereqs = new ArrayList<>(prereqs);
        }

        if (coreqs == null) {
            this.coreqs = new ArrayList<>(0);
        } else {
            this.coreqs = new ArrayList<>(coreqs);
        }
    }

    /**
     * @throws NullPointerException if ID or name are null
     * @throws IllegalArgumentException if numCredits is not between 0 and 6
     */
    public BaseCourse(String ID, String name, int numCredits) {
        this(ID, name, numCredits, null, null);
    }

    public String getID() {
        return ID;
    }

    /**
     * @throws NullPointerException if ID is null
     */
    public void setID(String ID) {
        if (ID == null) {
            throw new NullPointerException();
        }
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    /**
     * @throws NullPointerException if name is null
     */
    public void setName(String name) {
        if (name == null) {
            throw new NullPointerException();
        }
        this.name = name;
    }

    public int getNumCredits() {
        return numCredits;
    }

    /**
     * @throws IllegalArgumentException if numCredits is not between 0 and 6
     */
    public void setNumCredits(int numCredits) {
        if (numCredits > 6 || numCredits < 0) {
            throw new IllegalArgumentException("numCredits must be between 0 and 6");
        }
        this.numCredits = numCredits;
    }

    /**
     * @throws NullPointerException if course is null
     */
    public void addPrereq(BaseCourse course) {
        if (course == null) {
            throw new NullPointerException();
        }
        prereqs.add(course);
    }

    /**
     * @throws NullPointerException if course is null
     */
    public void addCoreq(BaseCourse course) {
        if (course == null) {
            throw new NullPointerException();
        }
        coreqs.add(course);
    }

    /**
     * @throws NullPointerException if course is null
     */
    public boolean removePrereq(BaseCourse course) {
        if (course == null) {
            throw new NullPointerException();
        }
        return prereqs.remove(course);
    }

    /**
     * @throws NullPointerException if course is null
     */
    public boolean removeCoreq(BaseCourse course) {
        if (course == null) {
            throw new NullPointerException();
        }
        return coreqs.remove(course);
    }

    public List<BaseCourse> getPrereqs() {
        return Collections.unmodifiableList(prereqs);
    }

    public List<BaseCourse> getCoreqs() {
        return Collections.unmodifiableList(coreqs);
    }

    @Override
    public String toString() {
        return this.getID();
    }

    @Override
    public int compareTo(BaseCourse o) {
        return this.ID.compareTo(o.ID);
    }
}
