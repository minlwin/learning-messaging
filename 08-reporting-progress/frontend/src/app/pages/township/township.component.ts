import { Component, signal } from '@angular/core';
import { SystemService } from '../../services/client/system.service';

@Component({
  selector: 'app-township',
  standalone: true,
  imports: [],
  templateUrl: './township.component.html',
  styles: ``
})
export class TownshipComponent {

  services = signal<string[]>([])
  townships = signal<any[]>([])
  selected = signal<string | undefined>(undefined)

  constructor(private service:SystemService) {
    service.getSystems().subscribe(result => {
      this.services.set(result || [])

      if(result.length > 0) {
        this.loadTownship(result[0])
      }
    })
  }

  loadTownship(system:string) {
    this.selected.set(system)
    this.service.getTownships(system).subscribe(result => {
      this.townships.set(result || [])
    })
  }
}
