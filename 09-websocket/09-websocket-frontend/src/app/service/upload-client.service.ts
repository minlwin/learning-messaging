import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

@Injectable({providedIn: 'root'})
export class UploadClient {

  constructor(private http:HttpClient) {}

  send(form:any) {
    return this.http.post<any>('http://localhost:8080/progress', form)
  }

  search() {
    return this.http.get<any[]>('http://localhost:8080/progress')
  }
}
