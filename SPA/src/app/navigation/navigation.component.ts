import {Component, OnInit, Input} from '@angular/core';
import { Task } from '../shared/task.model';

/**
 * @title Stepper overview
 */
@Component({
  selector: 'navigation-custom',
  templateUrl: 'navigation.component.html',
  styleUrls: ['navigation.component.css'],
})
export class NavigationComponent implements OnInit {


  @Input() public todas: Task[]
  @Input() public feitas: Task[]
  @Input() public pendentes: Task[]

  constructor() { 
  }

  ngOnInit() {    
    console.log(this.feitas)
  }
}
