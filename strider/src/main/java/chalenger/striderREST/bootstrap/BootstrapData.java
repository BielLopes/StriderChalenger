package chalenger.striderREST.bootstrap;

import chalenger.striderREST.domain.Task;
import chalenger.striderREST.repositories.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {

    private final TaskRepository taskRepository;

    public BootstrapData(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Loading Tasks Data");

        Task t1 = new Task();
        t1.setDescription("Fazer um EndPoint para upload de arquivos");
        taskRepository.save(t1);

        Task t2 = new Task();
        t2.setDescription("Conectar o angular.js com a API");

        taskRepository.save(t2);

        Task t3 = new Task();
        t3.setDescription("Criar a finalização de tarefas no android");
        taskRepository.save(t3);

        System.out.println("Qunatidade de Tasks: " + taskRepository.count());
    }
}
