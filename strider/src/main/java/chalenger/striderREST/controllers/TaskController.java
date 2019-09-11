package chalenger.striderREST.controllers;

import chalenger.striderREST.domain.Task;
import chalenger.striderREST.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(allowCredentials = "false")
@RestController
@RequestMapping(TaskController.BASE_URL)
public class TaskController {

    public static final String BASE_URL = "/api/tasks";
    private TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping
    List<Task> getAllTasks(){
        return taskService.findAllTasks();
    }

    @GetMapping("/complete")
    List<Task> getCompleteTasks() {
        return taskService.findCompleteTasks();
    }

    @GetMapping("/pendents")
    List<Task> getPendentsTasks(){
        return taskService.findPendentsTasks();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task saveTask(@RequestBody Task task){
        return taskService.saveTask(task);
    }

    @GetMapping("/finish/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Task finishTask(@PathVariable Long id){
        Task tarefa = taskService.findOneById(id);
        tarefa.setStatus();

        taskService.update(tarefa);

        return tarefa;
    }

    @GetMapping("/{id}")
    public Task oneTask(@PathVariable Long id){
        Task tarefa = taskService.findOneById(id);
        return tarefa;
    }

    @PostMapping("/uploadFile")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = taskService.storeFile(file);

        if (fileName == "Deu ruin!"){
            System.out.println("Deu ruin!");
        }

        return "{\"message\" :\"Tudo Certo\"}";
    }
}
