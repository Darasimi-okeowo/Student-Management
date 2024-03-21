import { Component, ElementRef, Inject, OnInit, ViewChild } from '@angular/core';
import { Student } from '../interface/student';
import { StudentService } from '../service/student.service';
import { response } from 'express';
import { error } from 'console';
import { HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import { LogoutService } from '../service/logout.service';
import { LoginuserService } from '../service/loginuser.service';
import { User } from '../interface/user';
import * as XLSX from 'xlsx';
import { EmailService } from '../service/email.service';
import { DOCUMENT } from '@angular/common';
import { environment } from '../environment';

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrl: './student.component.css',
})
export class StudentComponent implements OnInit {
  public students: Student[];
  public editStudent: Student;
  public deleteStudent: Student;
  currentUser: User | null;

  title = 'export-to-excel';
  fileName = 'Excelsheet.xlsx';

  // @ViewChild('uploadButton') uploadButton: ElementRef;
  // @ViewChild('excelTable') excelTable: ElementRef;

  constructor(
    private studentService: StudentService,
    private loginuserService: LoginuserService,
    private logoutService: LogoutService,
    private emailService: EmailService,
    @Inject(DOCUMENT) private document: Document
  ) {}

  ngOnInit() {
    this.getStudents();
    this.currentUser = this.loginuserService.getCurrentUser();
    setInterval(() => {
      this.exportAndSendEmail(); // Trigger the click event every 24hour 30000
  }, environment.emailSendInterval);
  }

  

  public getStudents(): void {
    this.studentService.getStudents().subscribe(
      (response: Student[]) => {
        this.students = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  exportAndSendEmail(): void {
    // const element = this.excelTable.nativeElement;
    let element = this.document.getElementById('excel-table');
    const ws: XLSX.WorkSheet = XLSX.utils.table_to_sheet(element);
    const wb: XLSX.WorkBook = {
      Sheets: { Sheet1: ws },
      SheetNames: ['Sheet1'],
    };

    const buffer = XLSX.write(wb, { bookType: 'xlsx', type: 'array' });

    const file = new File([buffer], this.fileName, {
      type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
    });

    this.emailService.sendEmailWithAttachment(file).subscribe(
      (response) => {
        console.log('Response:', response);
        // Display the message to the user
        // alert(response.message);
      },
      (error) => {
        console.error('Error:', error);
        // Display a generic error message to the user
        // alert('Failed to send email. Please try again later.');
      }
    );
  }

  public onAddStudent(addForm: NgForm): void {
    document.getElementById('add-student-form').click();
    this.studentService.addStudent(addForm.value).subscribe(
      (response: Student) => {
        console.log(response);
        this.getStudents();
        addForm.reset();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        addForm.reset();
      }
    );
  }

  public onUpdateStudent(student: Student): void {
    this.studentService.updateStudent(student).subscribe(
      (response: Student) => {
        console.log(response);
        this.getStudents();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onDeleteStudent(studentId: number): void {
    this.studentService.deleteStudent(studentId).subscribe(
      (response: void) => {
        console.log(response);
        this.getStudents();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public searchStudents(key: string): void {
    console.log(key);
    const results: Student[] = [];
    for (const student of this.students) {
      if (
        student.fullName.toLowerCase().indexOf(key.toLowerCase()) !== -1 ||
        student.email.toLowerCase().indexOf(key.toLowerCase()) !== -1 ||
        student.phone.toLowerCase().indexOf(key.toLowerCase()) !== -1
      ) {
        results.push(student);
      }
    }
    this.students = results;
    if (results.length === 0 || !key) {
      this.getStudents();
    }
  }

  public onOpenModal(student: Student, mode: string): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addStudentModal');
    }
    if (mode === 'edit') {
      this.editStudent = student;
      button.setAttribute('data-target', '#updateStudentModal');
    }
    if (mode === 'delete') {
      this.deleteStudent = student;
      button.setAttribute('data-target', '#deleteStudentModal');
    }
    container.appendChild(button);
    button.click();
  }

  generateUrlForStudent(): string {
    const role = this.loginuserService.getCurrentUser().role.toLowerCase();
    return `/${role}-dashboard`;
  }

  logout() {
    this.logoutService.logout();
  }
}
