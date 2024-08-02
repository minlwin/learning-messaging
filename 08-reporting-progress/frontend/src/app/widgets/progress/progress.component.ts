import { Component, computed, input } from '@angular/core';
import { Progress } from '../../services/websocket/progress.lietener';

@Component({
  selector: 'app-progress',
  templateUrl: './progress.component.html',
  styles: ``
})
export class ProgressComponent {

  progress = input<Progress | undefined>(undefined)

  state = computed(() => this.progress()?.state)

  percent = computed(() => this.progress()?.percent)
}
