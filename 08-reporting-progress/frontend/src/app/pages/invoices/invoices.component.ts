import { Component, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { WidgetsModule } from '../../widgets/widgets.module';
import { CommonModule } from '@angular/common';
import { InvoiceService } from '../../services/client/invoice.service';
import { Pager, PagerListener } from '../../widgets/pagination/pagination.component';
import { SystemService } from '../../services/client/system.service';

@Component({
  selector: 'app-invoices',
  standalone: true,
  imports: [ReactiveFormsModule, WidgetsModule, CommonModule],
  templateUrl: './invoices.component.html',
  styles: ``
})
export class InvoicesComponent implements PagerListener{

  form:FormGroup
  statusList = ['New', 'Paid', 'Timeout']
  systems = signal<string[]>([])

  pager = signal<Pager | undefined>(undefined)
  contents = signal<any[]>([])

  constructor(builder:FormBuilder, private client:InvoiceService, systemService:SystemService) {
    this.form = builder.group({
      service: '',
      status: '',
      customerCode: '',
      page: 0,
      size: 10
    })

    systemService.getSystems().subscribe(result => this.systems.set(result))
  }

  search() {
    this.client.search(this.form.value).subscribe(result => {
      const {content, ... pager} = result
      this.contents.set(content)
      this.pager.set(pager)
    })
  }

  onPageChage(page: number): void {
    this.form.patchValue({page: page})
    this.search()
  }

  onSizeChange(size: number): void {
    this.form.patchValue({
      page: 0,
      size: size
    })

    this.search()
  }

}
