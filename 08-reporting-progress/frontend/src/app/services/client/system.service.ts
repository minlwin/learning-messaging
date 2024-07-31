import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';

const URL = `${environment}/services`

@Injectable({
  providedIn: 'root'
})
export class SystemService {

  constructor(private http:HttpClient) { }
}
