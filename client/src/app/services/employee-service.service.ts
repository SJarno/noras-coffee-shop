import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserDto } from '../modules/user-dto';
import { environment } from 'src/environments/environment';
import { catchError, Observable, of, tap } from 'rxjs';
import { MessageService } from './message.service';

@Injectable({
  providedIn: 'root'
})
export class EmployeeServiceService {
  url: string = environment.baseUrl;
  constructor(private http: HttpClient, private messages: MessageService) { }

  createNewEmployee(employee: UserDto): Observable<any> {
    console.log("Yritetään postata tieto eteenpäin");
    return this.http.post<UserDto>(this.url + "employee/create",
      employee,
      { observe: 'response' })
      .pipe(
        tap(_ => this.messages.addSuccessMessage("New employee created")),
        catchError(this.handleError<any>('Create new employee', []))
      );
  }
  getAllEmployees() {

  }
  updateEmployee() {

  }
  deleteEmployee() {

  }
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error("An error occured: " + error.error);

      return of(result as T);
    }
  }
}
