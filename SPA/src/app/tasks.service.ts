import { Task } from "./shared/task.model"
import { HttpClient } from '@angular/common/http'
import { Injectable } from '@angular/core'

@Injectable()
export class TaskService {

    constructor(private http: HttpClient){}

    private url: string = 'http://localhost:8080/api/tasks'

    public getPendentes(): Promise<Task[]>{
        /*Efeturar uma requisição HTTP
        e retornar um array de ofertas*/

        return this.http.get(this.url + "/pendents")
            .toPromise()
            .then((request: any) => request)
    }

    public getConcluidas(): Promise<Task[]>{

        return this.http.get(this.url + "/complete")
            .toPromise()
            .then((request: any) => request)

    }

    public postTask(task: Task){
        
        return this.http.post(
            this.url, 
            JSON.stringify({
                description: task.description
            }), 
            {
                headers: {
                    "Content-Type": "application/json"
                }
            }
        )
            .toPromise()
            .then((response:any) => response)

    }

}