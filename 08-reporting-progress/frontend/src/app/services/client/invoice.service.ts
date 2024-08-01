import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';

const URL = `${environment.baseUrl}/invoice`

@Injectable({
  providedIn: 'root'
})
export class InvoiceService {

  constructor(private http:HttpClient) { }

  search(form:any) {
    return this.http.get<any[]>(URL, {params: form})
  }
}
