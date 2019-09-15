import { Component } from '@angular/core'
import { TaskService } from "./tasks.service"
import { Task } from "./shared/task.model"

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  public title: string = 'Interface WEB'
  public todas: Task[] = []
  public feitas: Task[] = []
  public pendentes: Task[] = []

  private task: string = ""

  constructor(private taskService: TaskService){}

  ngOnInit(){
    //Called after the constructor, initializing input properties, and the first call to ngOnChanges.
    //Add 'implements OnInit' to the class.
   
    setInterval(() => {
      this.taskService.getConcluidas()
        .then((request) => {
          
          if (request.toString() != this.feitas.toString()){
            this.feitas = request
          }
          
        })
        .then(() => {
          
          this.taskService.getPendentes()
            .then((request) => {

              this.pendentes = request
              this.todas = this.pendentes.concat(this.feitas)

            })

        })
    }, 1000)
    

    
    console.log("inicialize component")


  }

  public atualizaTask(resposta: Event): void{
    this.task = (<HTMLInputElement>resposta.target).value 
    console.log(this.task)
    // o (<HTMLInputElement>resposta.target) recupera o estado do elemento e diz que se 
    // trata de um input HTML, logo, é possivel acessar o parametro Value
  }

  public adcionaTarefa(): void{

    if (this.task != "") {

      let task = new Task(this.task)
      this.pendentes.push(task)

      this.todas = this.pendentes.concat(this.feitas)

      this.task = ""
      console.log(this.pendentes)
      console.log(this.todas)

      this.taskService.postTask(task)
        .then((response) => {
          console.log(response.description)
        })

    }

  }
}
