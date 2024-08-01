import { Component, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { WidgetsModule } from '../../widgets/widgets.module';
import { SystemService } from '../../services/client/system.service';
import { ProgressListener } from '../../services/websocket/progress.lietener';
import { UploadService } from '../../services/client/upload.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [WidgetsModule, ReactiveFormsModule, CommonModule],
  templateUrl: './home.component.html',
  styles: ``
})
export class HomeComponent {

  form:FormGroup
  systems = signal<string[]>([])

  constructor(builder:FormBuilder,
    systemService:SystemService,
    private uploadService:UploadService,
    public progress:ProgressListener) {
    this.form = builder.group({
      system: ['', Validators.required],
      uploadBy: ['', Validators.required]
    })

    systemService.getSystems().subscribe(result => this.systems.set(result || []))
  }

  get canUpload():boolean {
    return (this.form.get('system')?.valid && this.form.get('uploadBy')?.valid) || false
  }

  upload(files:FileList | null) {
    if(null != files && files.length > 0 && this.form.valid) {

      this.uploadService.upload(this.form.value, files.item(0)!).subscribe(result => {
        this.progress.connect(result.id)
        console.log(result)
      })
  }
  }
}
