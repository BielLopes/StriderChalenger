package chalenger.striderREST.repositories;

import chalenger.striderREST.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
