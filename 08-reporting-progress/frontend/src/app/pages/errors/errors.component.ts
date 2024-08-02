import { Component, effect, input, signal } from '@angular/core';
import { Pager, PagerListener } from '../../widgets/pagination/pagination.component';
import { WidgetsModule } from '../../widgets/widgets.module';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UploadService } from '../../services/client/upload.service';

@Component({
  selector: 'app-errors',
  standalone: true,
  imports: [WidgetsModule, ReactiveFormsModule, CommonModule],
  templateUrl: './errors.component.html',
  styles: ``
})
export class ErrorsComponent implements PagerListener{

  id = input.required<string>()

  history = signal<any>(undefined)
  contents = signal<any[]>([])
  pager = signal<Pager | undefined>(undefined)
  details = signal<any>(undefined)

  form:FormGroup

  constructor(builder:FormBuilder, private service:UploadService) {
    this.form = builder.group({
      history: '',
      page: 0,
      size: 10
    })

    effect(() => {
      if(this.id()) {
        this.form.patchValue({history: this.id()})
        this.search()

        service.findById(this.id()).subscribe(result => this.details.set(result))
      }
      },
      {allowSignalWrites: true}
    )
  }

  onPageChage(page: number): void {
    this.form.patchValue({page: page})
    this.search()
  }

  onSizeChange(size: number): void {
    this.form.patchValue({page: 0, size: size})
    this.search()
  }

  search() {
    this.service.searchError(this.form.value).subscribe(result => {
      const {contents, ... pager} = result
      this.contents.set(contents)
      this.pager.set(pager)
    })
  }
}
