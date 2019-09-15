import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavigationComponent } from './navigation/navigation.component'; 

import { TaskService } from './tasks.service';

import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatButtonModule} from '@angular/material/button';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatGridListModule} from '@angular/material/grid-list'
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatStepperModule} from '@angular/material/stepper'
import {MatInputModule} from '@angular/material/input'
import {MatListModule} from '@angular/material/list'
import {MatExpansionModule} from '@angular/material/expansion'
import { HttpClientModule } from '@angular/common/http'


@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatCheckboxModule,
    MatGridListModule,
    MatFormFieldModule,
    MatStepperModule,
    MatInputModule,
    MatListModule,
    MatExpansionModule,
  ],
  providers: [
    TaskService
  ],
  bootstrap: [AppComponent],
})
export class AppModule { }
