package chalenger.striderREST.services;

import chalenger.striderREST.domain.Task;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TaskService {

    String storeFile(MultipartFile file);

    List<Task> findAllTasks();

    Task findOneById(Long id);

    List<Task> findPendentsTasks();

    List<Task> findCompleteTasks();

    void update(Task task);

    Task saveTask(Task task);

}
