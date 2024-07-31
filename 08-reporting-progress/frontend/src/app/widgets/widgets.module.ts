import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroupComponent } from './form-group/form-group.component';
import { PageWithListComponent } from './page-with-list/page-with-list.component';



@NgModule({
  declarations: [
    FormGroupComponent,
    PageWithListComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    FormGroupComponent,
    PageWithListComponent
  ]
})
export class WidgetsModule { }
