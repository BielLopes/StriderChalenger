export class Task {
    public id: number
    public description: string
    public status: boolean

    constructor(description: string, status: boolean = false){
        this.description = description
        this.status = status
    }
}