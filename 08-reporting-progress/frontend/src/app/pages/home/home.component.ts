import { Component, effect, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { WidgetsModule } from '../../widgets/widgets.module';
import { SystemService } from '../../services/client/system.service';
import { ProgressListener } from '../../services/websocket/progress.lietener';
import { UploadService } from '../../services/client/upload.service';
import { CommonModule } from '@angular/common';
import { Pager, PagerListener } from '../../widgets/pagination/pagination.component';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [WidgetsModule, ReactiveFormsModule, CommonModule, RouterLink],
  templateUrl: './home.component.html',
  styles: ``
})
export class HomeComponent implements PagerListener{

  form:FormGroup
  searchForm:FormGroup

  systems = signal<string[]>([])

  contents = signal<any[]>([])
  pager = signal<Pager | undefined>(undefined)

  constructor(builder:FormBuilder,
    systemService:SystemService,
    private uploadService:UploadService,
    public progress:ProgressListener) {

    this.form = builder.group({
      system: ['', Validators.required],
      uploadBy: ['', Validators.required],
    })

    this.searchForm = builder.group({
      page: 0,
      size: 10
    })

    systemService.getSystems().subscribe(result => this.systems.set(result || []))

    effect(() => {
      if(!progress.connected()) {
        this.search()
      }
    }, {allowSignalWrites: true})
  }

  onPageChage(page: number): void {
    this.searchForm.patchValue({page: page})
    this.search()
  }

  onSizeChange(size: number): void {
    this.searchForm.patchValue({page: 0, size: size})
    this.search()
  }

  get canUpload():boolean {
    return (this.form.get('system')?.valid && this.form.get('uploadBy')?.valid) || false
  }

  upload(files:FileList | null) {
    if(null != files && files.length > 0 && this.form.valid) {

      this.uploadService.upload(this.form.value, files.item(0)!).subscribe(result => {
        this.progress.connect(result.id)
      })
    }
  }

  search() {
    this.uploadService.search(this.searchForm.value).subscribe(result => {
      const {content, ... pager} = result
      this.contents.set(content)
      this.pager.set(pager)
    })
  }
}
