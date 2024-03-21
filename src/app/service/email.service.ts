import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import * as XLSX from 'xlsx';


@Injectable({
  providedIn: 'root',
})
export class EmailService {
  title = 'export-to-excel';
  fileName = 'Excelsheet.xlsx';

  constructor(private http: HttpClient) {}

  sendEmailWithAttachment(file: File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('file', file, file.name);
    return this.http.post<any>('http://localhost:8080/send-email', formData);
  }
  
}
