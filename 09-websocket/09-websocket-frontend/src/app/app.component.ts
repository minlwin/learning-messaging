import { Component, effect, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterOutlet } from '@angular/router';
import { UploadClient } from './service/upload-client.service';
import { ProgressService } from './service/progress.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ReactiveFormsModule],
  templateUrl: './app.component.html',
  styles: [],
})
export class AppComponent {

  form:FormGroup
  history = signal<any[]>([])

  constructor(builder:FormBuilder, private client:UploadClient, public progress:ProgressService) {
    this.form = builder.group({
      title: ['', Validators.required],
      delayInSec: ['', Validators.required]
    })

    effect(() => {
      if(!progress.connected()) {
        client.search().subscribe(result => {
          this.history.set(result || [])
        })
      }
    }, {allowSignalWrites: true})
  }

  send() {
    if(this.form.valid) {
      this.client.send(this.form.value).subscribe(result => {
        console.log(result)
        this.progress.listen(result.id)
      })
    }
  }
}
