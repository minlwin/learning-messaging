import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroupComponent } from './form-group/form-group.component';
import { PaginationComponent } from './pagination/pagination.component';
import { ProgressComponent } from './progress/progress.component';

@NgModule({
  declarations: [
    FormGroupComponent,
    PaginationComponent,
    ProgressComponent,
  ],
  imports: [
    CommonModule
  ],
  exports: [
    FormGroupComponent,
    PaginationComponent,
    ProgressComponent,
  ]
})
export class WidgetsModule { }
