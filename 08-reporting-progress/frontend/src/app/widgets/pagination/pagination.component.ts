import { Component, computed, EventEmitter, input, Output } from '@angular/core';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: [
    './pagination.component.scss'
  ]
})
export class PaginationComponent {

  pager = input<Pager>()
  pageList = computed(() => this.pager()?.pageList || [])

  topPage = computed(() => {
    if(this.pageList().length > 0) {
      return this.pageList()[0]
    }

    return undefined
  })

  lastPage = computed(() => {
    if(this.pageList().length > 0) {
      return this.pageList()[this.pageList().length - 1]
    }

    return undefined
  })

  @Output() onPageChange = new EventEmitter
  @Output() onSizeChange = new EventEmitter

  showFirst = computed(() => {
    if(this.topPage()) {
      return this.topPage()! > 0
    }
    return false
  })

  showLast = computed(() => {
    if(this.lastPage() && this.pager()?.totalPages != undefined) {
      return this.lastPage()! < this.pager()?.totalPages!
    }
    return false
  })

  pageChange(page:number) {
    this.onPageChange.emit(page)
  }

  sizeChange(size:any) {
    this.onSizeChange.emit(size)
  }

}

export interface Pager {
  page:number
  size:number
  total:number
  totalPages: number
  pageList:number[]
}

export interface PagerListener {
  onPageChage(page:number):void
  onSizeChange(size:number):void
}
