import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';

const URL = `${environment.baseUrl}/upload`

@Injectable({
  providedIn: 'root'
})
export class UploadService {

  constructor(private http:HttpClient) { }

  upload(data:any, file:File) {
    const form = new FormData
    Object.keys(data).forEach(key => form.append(key, data[key]))
    form.append('file', file, file.name)
    console.log(form)
    return this.http.post<any>(URL, form)
  }

  search(form:any) {
    return this.http.get(URL, {params: form})
  }
}
