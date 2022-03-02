import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ErrorHandler, Injectable } from '@angular/core';
import { catchError, finalize, Observable, of, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { MessageService } from './message.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  username?: string;
  authenticated: boolean = false;
  roles?: [];
  credentials = { 'username': '', 'password': '' };

  constructor(private http: HttpClient, private messageService: MessageService) { }

  public logout(): void {
    this.http.post(environment.baseUrl+'logout', {})
      .pipe(finalize(() => {
        this.username = '';
        this.authenticated = false;
        this.roles = [];
        this.messageService.addSuccessMessage("Logout successfull");
      }))
      .subscribe();
  }

  authenticate(credentials: any, callback: any, errorHandler: any, successMessage: string) {
    const headers = new HttpHeaders(credentials ? {
      authorization: 'Basic '+btoa(`${this.credentials.username}:${this.credentials.password}`)
    } : {});
    this.http.get(environment.baseUrl + "user", { headers: headers })
      .pipe(
        catchError(errorHandler))
        .subscribe(response => {
          console.log("Vastaus kun autentikoidaan: ");
          console.log(response);

          if (response['name']) {
            this.username = response['name'];
            this.authenticated = true;
            console.log(response['authorities']);
            this.roles = response['authorities'];
            this.messageService.addSuccessMessage(successMessage);
          } else {
            this.username = '';
            this.authenticated = false;
            this.roles = [];
            
          }
          return callback && callback();
        });

  }
  login(credentials: any, callback: any) {
    this.authenticate(credentials, callback, this.handleLoginError<any>('login', []), "Login success");
  }
  checkAuthentication(credentials: any, callback:any) {
    this.authenticate(credentials, callback, this.handleCheckAuthError<any>('auth check', []), "Auth success");
  }
  updateUsername(credentials: any, callback: any) {
    this.authenticate(credentials, callback, this.handleLoginError<any>('update username', []), "Username updated");
  }
  
  private handleLoginError<T>(operation = 'operation', result?: T) {
    return (error:any): Observable<T> => {
      console.error("Error");
      console.error(error);
      console.error("Result");
      console.error(result);
      console.error("Status");
      console.error(error.status);
      if (error.status === 401) {
        this.messageService.addErrorMessage(`Wrong username or password`);
        
      } else {
        this.messageService.addErrorMessage("Something went wrong");
      }
      this.messageService.addErrorMessage(`Error status: ${error.status}`);
      
      return of(result as T);
    }
  }
  private handleCheckAuthError<T>(operation = 'operation', result?: T) {
    return (error:any): Observable<T> => {
      /* console.error("This error is only handled in console");
      console.error("Status");
      console.error(error.status); */
      console.log("Handled in console only");
      console.log(operation);
      return of(result as T);
    }
  }

}
