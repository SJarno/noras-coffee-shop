import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ErrorHandler, Injectable } from '@angular/core';
import { catchError, finalize, Observable, of, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  username?: string;
  authenticated: boolean = false;
  roles?: [];

  constructor(private http: HttpClient) { }

  public logout(): void {
    this.http.post(environment.baseUrl+'logout', {})
      .pipe(finalize(() => {
        this.username = '';
        this.authenticated = false;
        this.roles = [];
      }))
      .subscribe();
  }

  authenticate(credentials: any, callback: any, errorHandler: any) {
    const headers = new HttpHeaders(credentials ? {
      authorization: 'Basic '+btoa(`${credentials.username}:${credentials.password}`)
    } : {});
    this.http.get(environment.baseUrl + "user", { headers: headers })
      .pipe(
        catchError(errorHandler))
        .subscribe(response => {
          console.log("Vastaus");
          console.log(response);

          if (response['name']) {
            this.username = response['name'];
            this.authenticated = true;
            console.log(response['authorities']);
            this.roles = response['authorities'];
          } else {
            this.username = '';
            this.authenticated = false;
            this.roles = [];
          }
          return callback && callback();
        });

  }
  login(credentials: any, callback: any) {
    this.authenticate(credentials, callback, this.handleLoginError<any>('login', []));
  }
  checkAuthentication(credentials: any, callback:any) {
    this.authenticate(credentials, callback, this.handleCheckAuthError<any>('auth check', []));
  }
  
  private handleLoginError<T>(operation = 'operation', result?: T) {
    return (error:any): Observable<T> => {
      console.error("This error goes to message service");
      console.error("Status");
      console.error(error.status);
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
