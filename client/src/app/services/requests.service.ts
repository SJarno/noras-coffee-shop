import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { catchError, Observable, of, tap } from 'rxjs';
import { MessageService } from './message.service';

@Injectable({
  providedIn: 'root'
})
export class RequestsService {
  url: string = environment.baseUrl;
  constructor(private http: HttpClient, private messages: MessageService) { }

  getGreeting(): Observable<any> {
    return this.http.get(this.url + "greet", { observe: 'response' });
  }

  /* Update username. Consider moving this to own service: */
  updateUsername(newUsername: any): Observable<any> {
    console.log("Ollaan täällä requestissa");
    return this.http.put<any>(`${this.url}update-username`,
      newUsername, 
      { observe: 'response' })
        .pipe(
          tap(_ => this.messages.addSuccessMessage("Username updated")),
          catchError(this.handleError<any>('Update username', []))
        );
  }
  private handleError<T>(operation = 'operation', result?:T) {
    return (error: any): Observable<T> => {
      console.error("Tapahtui virhe "+error.error);
      this.messages.addErrorMessage(error.error);
      return of(result as T);
    };
  }
}
