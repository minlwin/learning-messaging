import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';

const URL = `${environment.baseUrl}/services`

@Injectable({
  providedIn: 'root'
})
export class SystemService {

  constructor(private http:HttpClient) { }

  getSystems() {
    return this.http.get<string[]>(URL)
  }

  getTownships(system:string) {
    return this.http.get<any[]>(`${URL}/township/${system}`)
  }
}
