package chalenger.striderREST.services;

import chalenger.striderREST.domain.Task;
import chalenger.striderREST.repositories.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final Path beseDir;

    public TaskServiceImpl(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
        this.beseDir = Paths.get("/home/gabriellm/MyGitHub/SPRING_REST/deposito/").toAbsolutePath().normalize();
    }

    @Override
    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {

            System.out.println(this.beseDir.toString());
            Path targetLocation = this.beseDir.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            System.out.println("Deu ruin!");
        }

        return "Deu ruin!";
    }

    @Override
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task findOneById(Long id) {
        return taskRepository.findById(id).get();
    }

    @Override
    public List<Task> findPendentsTasks() {
        List<Task> todas = taskRepository.findAll();
        List<Task> pendents = new ArrayList<Task>();

        for(int i = 0; i < todas.size(); i++){
            if (!todas.get(i).getStatus()){
                pendents.add(todas.get(i));
            }
        }

        return pendents;
    }

    @Override
    public List<Task> findCompleteTasks() {

        List<Task> todas = taskRepository.findAll();
        List<Task> concluidas = new ArrayList<Task>();

        for (int i = 0; i < todas.size(); i++) {
            if (todas.get(i).getStatus()) {
                concluidas.add(todas.get(i));
            }
        }

        return concluidas;
    }

    @Override
    public void update(Task task) {
        taskRepository.save(task);
    }

    @Override
    public Task saveTask(Task task) {
        return taskRepository.save(task);

    }
}
