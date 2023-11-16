import com.entities.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.Calendar;



@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProjectCRUDTest {

    private EntityManagerFactory emf;
    private EntityManager em;

    @BeforeAll
    public void setUpClass() {
        emf = Persistence.createEntityManagerFactory("test-pu");
    }

    @BeforeEach
    public void setUp() {
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    @AfterEach
    public void tearDown() {
        em.getTransaction().rollback();
        em.close();
    }

    @AfterAll
    public void tearDownClass() {
        emf.close();
    }

    @Test
    public void testSaveProject() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.JANUARY, 1);
        Project project = new Project("Database Manager", "desktop", "description", calendar.getTime());

        em.persist(project);
        em.flush(); //ensure ID is assigned

        // Assertions
        Assertions.assertNotNull(project.getProjectId(), "Project ID should not be null after persisting");

        Project retrievedProject = em.find(Project.class, project.getProjectId());
        Assertions.assertNotNull(retrievedProject, "Retrieved project should not be null");
        Assertions.assertEquals("Database Manager", retrievedProject.getName(), "Project name should match");
    }

    @Test
    public void testEditProject() {
        Project project = em.find(Project.class, 6);
        Assertions.assertNotNull(project, "Project should exist before editing");

        em.getTransaction().begin();
        project.setName("New Project");
        em.merge(project);
        em.getTransaction().commit();

        // Assertions
        Project updatedProject = em.find(Project.class, project.getProjectId());
        Assertions.assertNotNull(updatedProject, "Updated project should not be null");
        Assertions.assertEquals("New Project", updatedProject.getName(), "Project name should be updated");
    }

    @Test
    public void testDeleteProject() {
        Project project = em.find(Project.class, 2);
        Assertions.assertNotNull(project, "Project should exist before deletion");

        // Delete the project
        em.getTransaction().begin();
        em.remove(project);
        em.getTransaction().commit();

        // Assertions
        Project deletedProject = em.find(Project.class, project.getProjectId());
        Assertions.assertNull(deletedProject, "Deleted project should be null");
    }
}
